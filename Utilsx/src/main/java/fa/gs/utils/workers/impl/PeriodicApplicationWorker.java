/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.workers.impl;

import fa.gs.utils.workers.ApplicationWorkerExecutor;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.CancellationException;

/**
 *
 * @author Fabio A. González Sosa
 */
public abstract class PeriodicApplicationWorker extends AbstractApplicationWorker {

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    /**
     * Fecha de ultima ejecucion del trabajador.
     */
    private volatile Date lastExecution;
    //</editor-fold>

    /**
     * Constructor.
     *
     * @param executor Ejecutor de tareas en segundo plano.
     */
    public PeriodicApplicationWorker(ApplicationWorkerExecutor executor) {
        super(executor);
        lastExecution = null;
    }

    @Override
    public Date getLastExecution() {
        return lastExecution;
    }

    /**
     * Obtiene el tiempo (en milisegundos) que debe transcurrir entre cada
     * ejecucion del trabajador.
     *
     * @return Milisegundos.
     */
    public abstract long getSleepInterval();

    /**
     * Implementa la logica principal del trabajador que es ejecutado
     * periodicamente.
     *
     * @throws Throwable Error capturado durante la ejecucion del trabajador.
     */
    public abstract void onRun() throws Throwable;

    /**
     * Indica si el trabajador puede continuar realizando trabajo.
     *
     * @return {@code true} si el trabajador puede continuar su ciclo de
     * trabajado, caso contrario {@code false}.
     */
    private boolean canWork() {
        boolean a = Thread.currentThread().isInterrupted();
        boolean b = Thread.currentThread().isAlive();
        return !a && b;
    }

    @Override
    public final void onWork() throws Throwable {
        while (canWork()) {
            boolean canSleep = true;
            lastExecution = Calendar.getInstance().getTime();
            try {
                onRun();
            } catch (Throwable thr) {
                canSleep = !(thr instanceof CancellationException || thr instanceof InterruptedException);
                handleThrowable(thr);
            } finally {
                if (canSleep) {
                    long millis = getSleepInterval();
                    sleep(millis);
                }
            }
        }
    }

}
