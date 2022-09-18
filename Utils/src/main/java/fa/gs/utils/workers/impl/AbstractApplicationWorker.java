/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.workers.impl;

import fa.gs.utils.logging.app.AppLogger;
import fa.gs.utils.misc.text.Strings;
import fa.gs.utils.workers.ApplicationWorker;
import fa.gs.utils.workers.ApplicationWorkerExecutor;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Future;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.concurrent.ManagedTaskListener;

/**
 *
 * @author Fabio A. González Sosa
 */
public abstract class AbstractApplicationWorker implements ApplicationWorker {

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    /**
     * Logger.
     */
    protected final AppLogger log;

    /**
     * Instancia del administrador de tareas que ejecuta a este trabajador.
     */
    protected final ApplicationWorkerExecutor executor;

    /**
     * Listener para los eventos del ciclo de vida de la tarea en segundo plano.
     */
    private final ManagedTaskListener listener;
    //</editor-fold>

    /**
     * Constructor.
     *
     * @param executor Ejecutor de tareas en segundo plano.
     */
    public AbstractApplicationWorker(ApplicationWorkerExecutor executor) {
        this.executor = executor;
        this.log = executor.getLogger();
        this.listener = new TaskListener();
    }

    @Override
    public ManagedTaskListener getManagedTaskListener() {
        return listener;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<String, String> getExecutionProperties() {
        return Collections.EMPTY_MAP;
    }

    @Override
    public String getWorkerName() {
        return this.getClass().getName();
    }

    @Override
    public String getWorkerClass() {
        return this.getClass().getCanonicalName();
    }

    @Override
    public void onInit() throws Throwable {
        ;
    }

    @Override
    public void onFinish() {
        ;
    }

    @Override
    public void onAbort(Throwable thr) {
        ;
    }

    @Override
    public void onError(Throwable thr) {
        log.error()
                .cause(thr)
                .message("Ocurrió un error inesperado")
                .tag("on", "error")
                .log();
    }

    /**
     * Permite suspender el hilo asignado para la ejecucion del trabajador en
     * segundo plano.
     *
     * @param millis Tiempo de suspension en milisegundos.
     */
    protected void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Se encarga de la inicializacion del trabajador en segundo plano.
     *
     * @return {@code true} si el trabajador fue inicializado correctamente,
     * caso contrario {@code false}.
     */
    protected boolean initializationPhase() {
        try {
            onInit();
            return true;
        } catch (Throwable thr) {
            onAbort(thr);
            return false;
        }
    }

    /**
     * Se encarga de llamar al bloque de codigo con la logica principal de
     * trabajo del trabajador en segundo plano.
     */
    protected void workingPhase() {
        try {
            // Ejecutar trabajo principal.
            onWork();
        } catch (Throwable thr) {
            handleThrowable(thr);
        }
    }

    /**
     * Maneja un error ocurrido durante la fase de trabajo y lo dirije a la fase
     * apropiada dependiendo de la clase de excepcion capturada.
     *
     * @param thr Error capturado durante la fase de trabajo.
     */
    protected void handleThrowable(Throwable thr) {
        if (thr instanceof CancellationException || thr instanceof InterruptedException) {
            // Manejar interrupcion.
            interruptionPhase(thr);
        } else {
            // Manejar error.
            errorPhase(thr);
        }
    }

    /**
     * Se encarga de realizar operaciones de soporte cuando el administrador de
     * trabajadores interrumpe al trabajador en segundo plano.
     *
     * @param thr Error capturado durante la interrupcion del trabajador.
     */
    protected void interruptionPhase(Throwable thr) {
        onAbort(thr);
        Thread.currentThread().interrupt();
    }

    /**
     * Se encarga de realizar operaciones de soporte cuando ocurre algun error
     * durante la fase principal de trabajo del trabajador en segundo plano.
     *
     * @param thr Error capturado durante la ejecucion principal del trabajador.
     */
    protected void errorPhase(Throwable thr) {
        onError(thr);
    }

    /**
     * Se encarga de realizar operaciones de soporte para finalizar un
     * trabajador.
     */
    protected void finalizationPhase() {
        onFinish();
    }

    @Override
    public final void run() {
        // Cambiar dinamicamente el nombre del thread para fines de monitoreo externo.
        String tname = Strings.format("worker-%s[%s]", getWorkerId(), getWorkerName());
        Thread.currentThread().setName(tname);

        log.getContext().push("traza", getWorkerId());
        log.debug()
                .message("Iniciando trabajador")
                .tag("worker.name", getWorkerName())
                .log();

        try {
            // Inicializar trabajador.
            boolean ok = initializationPhase();
            if (!ok) {
                return;
            }

            try {
                // Realizar trabajo.
                workingPhase();
            } finally {
                // Finalizar trabajo.
                finalizationPhase();
            }
        } finally {
            log.debug()
                    .message("Finalizando trabajador")
                    .tag("worker.name", getWorkerName())
                    .log();
            log.getContext().pop("traza");
            executor.cleanup(this);
        }
    }

    private class TaskListener implements ManagedTaskListener {

        @Override
        public void taskSubmitted(Future<?> future, ManagedExecutorService executor, Object task) {
            ;
        }

        @Override
        public void taskAborted(Future<?> future, ManagedExecutorService executor, Object task, Throwable exception) {
            ;
        }

        @Override
        public void taskDone(Future<?> future, ManagedExecutorService executor, Object task, Throwable exception) {
            ;
        }

        @Override
        public void taskStarting(Future<?> future, ManagedExecutorService executor, Object task) {
            ;
        }

    }

}
