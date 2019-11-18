/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.workers;

import fa.gs.utils.logging.app.AppLogger;
import fa.gs.utils.misc.Assertions;
import fa.gs.utils.result.simple.Result;
import fa.gs.utils.result.simple.Results;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 *
 * @author Fabio A. González Sosa
 */
public abstract class ApplicationWorkerExecutor implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    /**
     * Servicio de ejecucion de tareas en segundo plano.
     */
    private volatile ExecutorService executor;

    /**
     * Indica el estado del ejecutor de trabajadores.
     */
    private volatile Status status;

    /**
     * Coleccion de instancias registradas para realizar trabajo en segundo
     * plano.
     */
    private final Set<Class<? extends ApplicationWorker>> classes;

    /**
     * Coleccion de instancias registradas para realizar trabajo en segundo
     * plano.
     */
    private final Map<String, WorkerInstanceInfo> instances;
    //</editor-fold>

    /**
     * Constructor.
     */
    public ApplicationWorkerExecutor() {
        this.classes = new HashSet<>();
        this.instances = new ConcurrentHashMap<>();
        this.executor = null;
        this.status = Status.INDEFINIDO;
    }

    /**
     * Obtiene la instancia de logger para mensajes de diagnostico.
     *
     * @return Logger.
     */
    public abstract AppLogger getLogger();

    /**
     * Permite crear instancias del servicio de ejecucion de tareas en segundo
     * plano a utilizar.
     *
     * @return Servicio de ejecucion de tareas en segundo plano.
     */
    protected abstract ExecutorService createExecutorService();

    /**
     * Detiene el servicio concreto de ejecucion de tareas en segundo plano. El
     * servicio a destruir debe ser idealmente uno creado mediante
     * {@link #createExecutorService() createExecutorService}.
     *
     * @param executor Servicio de ejecucion de tareas.
     * @return {@code true} si el servicio de ejecucion fue detenido
     * correctamente, caso contrario {@code false}.
     */
    protected abstract boolean destroyExecutorService(ExecutorService executor);

    /**
     * Escanea en busca de clases que implementen el contrato de un trabajador
     * compatible.
     *
     * @return Cantidad de clases encontradas.
     */
    protected abstract Collection<Class<? extends ApplicationWorker>> populateWorkers();

    /**
     * Inicia todos los trabajadores detectados.
     */
    public void start() {
        Status prev = status;
        if (prev == Status.INICIADO) {
            return;
        }

        status = Status.INICIANDO;
        try {
            // Controlar que exista un servicio de ejecucion  de tareas.
            if (executor == null) {
                executor = createExecutorService();
                getLogger().debug()
                        .message("Servicio de ejecucion de tareas creado")
                        .tag("ok", (executor != null))
                        .log();
            }

            // Obtener clases de trabajadores.
            this.classes.clear();
            Collection<Class<? extends ApplicationWorker>> classes0 = populateWorkers();
            if (!Assertions.isNullOrEmpty(classes0)) {
                this.classes.addAll(classes0);
            }

            // Crear instancias de trabajadores registrados.
            for (Class<? extends ApplicationWorker> klass : classes) {
                Result<String> result = start(klass);
                if (result.isFailure()) {
                    getLogger().error()
                            .cause(result.failure().cause())
                            .message("Ocurrio un error instanciando trabajador")
                            .tag("worker.class", klass.getCanonicalName())
                            .log();
                }
            }

            status = Status.INICIADO;
        } catch (Throwable thr) {
            status = prev;
        }
    }

    /**
     * Termina todos los trabajadores activos.
     */
    public void stop() {
        Status prev = status;
        if (prev == Status.TERMINADO) {
            return;
        }

        status = Status.TERMINANDO;
        try {
            // Detener instancias de trabajadores en ejecucion.
            Collection<WorkerInstanceInfo> infos = getInstancesInfo();
            for (WorkerInstanceInfo info : infos) {
                stop(info.id);
            }

            // Detener servicio de ejecucion de tareas.
            try {
                boolean ok = destroyExecutorService(executor);
                getLogger().debug()
                        .message("Servicio de ejecucion de tareas terminado")
                        .tag("ok", ok)
                        .log();
            } finally {
                executor = null;
            }

            status = Status.TERMINADO;
        } catch (Throwable thr) {
            status = prev;
        }
    }

    /**
     * Reinicia todos los trabajadores.
     */
    public void restart() {
        Collection<WorkerInstanceInfo> infos = getInstancesInfo();
        for (WorkerInstanceInfo info : infos) {
            restart(info.id);
        }
    }

    /**
     * Inicia una nueva instancia de trabajador en segundo plano. Si ya existe
     * una instancia activa del trabajador, entonces no se crea ninguna nueva
     * instancia.
     *
     * @param klass Clase del trabajador en segundo plano.
     * @return Identificador del trabajador iniciado.
     */
    private Result<String> start(Class<? extends ApplicationWorker> klass) {
        Result<String> result;

        try {
            // Instanciar trabajador.
            Result<ApplicationWorker> resWorker = instantiate(klass);
            resWorker.raise();

            // Trabajador instanciado.
            ApplicationWorker worker = resWorker.value();

            // Verifica si no existe una instancia activa.
            String id = worker.getWorkerId();
            WorkerInstanceInfo info = instances.get(id);
            if (info == null) {
                // Iniciar ejecucion de trabajador.
                Future future = executor.submit(worker);
                info = new WorkerInstanceInfo(id, worker, future);
                instances.put(id, info);
            }

            result = Results.ok().value(info.id).build();
        } catch (Throwable thr) {
            result = Results.ko()
                    .cause(thr)
                    .message("No se pudo iniciar un trabajador de tipo '%s'", klass.getCanonicalName())
                    .build();
        }

        return result;
    }

    /**
     * Termina una instancia de un trabajador en segundo plano, si hubiere.
     *
     * @param workerId Identificador del trabajador en segundo plano.
     */
    private void stop(String workerId) {
        interrupt(workerId, false);
    }

    /**
     * Reinicia una instancia de un trabajador en segundo plano, si hubiere.
     *
     * @param workerId Identificador del trabajador en segundo plano.
     */
    private void restart(String workerId) {
        interrupt(workerId, true);
    }

    /**
     * Interrumpe la ejecucion de una instancia de trabajador en segundo plano,
     * si hubiere.
     *
     * @param workerId Identificador del trabajador en segundo plano.
     * @param markForRestart Indica si la interrupcion debe ser para reiniciar
     * el trabajador o para terminar el mismo.
     */
    private void interrupt(String workerId, boolean markForRestart) {
        WorkerInstanceInfo info = instances.get(workerId);
        if (info != null) {
            getLogger().warning()
                    .message("Interrumpiendo trabajador")
                    .tag("worker.name", info.instance.getWorkerName())
                    .log();

            // Marcar tarea para su reinicio.
            info.markForRestart = markForRestart;

            // Cancelar hilo mediante su futuro.
            boolean a = info.future.isDone();
            boolean b = info.future.isCancelled();
            if (!(a || b)) {
                info.future.cancel(true);
            } else {
                cleanup(info.instance);
            }
        }
    }

    /**
     * Utilizado por los trabajadores en segundo plano, que han finalizado su
     * trabajo, para inidicar al administrador de trabajadores que debe realizar
     * operaciones de limpieza.
     *
     * @param worker Instancia de trabajador en segundo plano.
     */
    public void cleanup(ApplicationWorker worker) {
        WorkerInstanceInfo info = instances.remove(worker.getWorkerId());
        if (info != null && info.markForRestart) {
            start(worker.getClass());
        }
    }

    /**
     * Crea una nueva instancia de trabajador en segundo plano.
     *
     * @param klass Clase del trabajador en segundo plano a instanciar.
     * @return Instancia de trabajador en segundo plano.
     */
    private Result<ApplicationWorker> instantiate(Class<? extends ApplicationWorker> klass) {
        Result<ApplicationWorker> result;

        try {
            // Obtener constructor por defecto que toma una referencia a un ejecutor de trabajadores.
            Constructor ctor = klass.getConstructor(ApplicationWorkerExecutor.class);
            if (!ctor.isAccessible()) {
                ctor.setAccessible(true);
            }

            // Instanciar trabajador mediante constructor encontrado.
            Object object = ctor.newInstance(this);
            result = Results.ok()
                    .value(klass.cast(object))
                    .build();
        } catch (Throwable thr) {
            result = Results.ko()
                    .cause(thr)
                    .message("No se pudo crear una instancia de '%s'", klass.getCanonicalName())
                    .build();
        }
        return result;
    }

    /**
     * Emite informacion sobre el estado actual de las instancias de
     * trabajadores en segundo plano.
     *
     * @return Informacion de instancias de trabajadores.
     */
    public Collection<ApplicationWorkerInfo> dump() {
        return getInstancesInfo().stream()
                .map((i) -> new WorkerInfo(i.instance))
                .collect(Collectors.toList());
    }

    /**
     * Obtiene el estado del ejecutor de trabajadores.
     *
     * @return Estado del ejecutor.
     */
    public Status status() {
        return status;
    }

    /**
     * Obtiene una coleccion de la informacion interna de instancias que
     * mantiene el ejecutor de trabajadores.
     *
     * @return Coleccion de informacion interna de trabajadores.
     */
    private Collection<WorkerInstanceInfo> getInstancesInfo() {
        Collection<WorkerInstanceInfo> infos = new LinkedList<>();
        infos.addAll(instances.values());
        return infos;
    }

    /**
     * Clase que contiene informacion de seguimiento para instancias de
     * trabajadores en segundo plano. La informacion contenida sirve al ejecutor
     * de trabajadores manipular los mismos.
     */
    private static class WorkerInstanceInfo {

        //<editor-fold defaultstate="collapsed" desc="Atributos">
        /**
         * Identificador de trabajador.
         */
        final String id;

        /**
         * Instancia de trabajador.
         */
        final ApplicationWorker instance;

        /**
         * Futuro obtenido al calendarizar el trabajador en el servicio de
         * ejecucion en segundo plano.
         */
        final Future future;

        /**
         * Bandera que indica si el trabajador debe ser reiniciado o no.
         */
        volatile boolean markForRestart;
        //</editor-fold>

        /**
         * Constructor.
         *
         * @param id Identificador del trabajador en segundo plano.
         * @param instance Instancia de trabajador en segundo plano.
         * @param future Futuro.
         */
        public WorkerInstanceInfo(String id, ApplicationWorker instance, Future future) {
            this.id = id;
            this.instance = instance;
            this.future = future;
            this.markForRestart = false;
        }

    }

    /**
     * Clase que contiene informacion publica de consulta sobre los trabajadores
     * en segundo plano activos.
     */
    private static class WorkerInfo implements ApplicationWorkerInfo {

        //<editor-fold defaultstate="collapsed" desc="Atributos">
        /**
         * Identificador de trabajador.
         */
        private final String id;

        /**
         * Nombre de trabajador.
         */
        private final String name;

        /**
         * Nombre de clase de trabajador.
         */
        private final String klass;

        /**
         * Configuraciones activas.
         */
        private final Map<String, String> configurations;

        /**
         * Ultimo tiempo de ejecucion.
         */
        private final Date lastExecution;
        //</editor-fold>

        /**
         * Constructor.
         *
         * @param instance Instancia activa de trabajador en segundo plano.
         */
        public WorkerInfo(ApplicationWorker instance) {
            this.id = instance.getWorkerId();
            this.name = instance.getWorkerName();
            this.klass = instance.getClass().getCanonicalName();
            this.configurations = new HashMap<>();
            this.configurations.putAll(instance.getActiveConfigurations());
            this.lastExecution = instance.getLastExecution();
        }

        //<editor-fold defaultstate="collapsed" desc="Getters">
        @Override
        public String getWorkerId() {
            return id;
        }

        @Override
        public String getWorkerName() {
            return name;
        }

        @Override
        public String getWorkerClass() {
            return klass;
        }

        @Override
        public Map<String, String> getActiveConfigurations() {
            return configurations;
        }

        @Override
        public Date getLastExecution() {
            return lastExecution;
        }
        //</editor-fold>

    }

    public enum Status {
        INDEFINIDO,
        INICIANDO,
        INICIADO,
        TERMINANDO,
        TERMINADO
    }

}
