/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.workers;

import javax.enterprise.concurrent.ManagedTask;

/**
 *
 * @author Fabio A. González Sosa
 */
public interface ApplicationWorker extends Runnable, ManagedTask, ApplicationWorkerInfo {

    /**
     * Inicializa el trabajador en segundo plano.
     *
     * @throws Throwable Si ocurre algun error durante la inicializacion.
     */
    public void onInit() throws Throwable;

    /**
     * Contiene la logica principal de ejecucion del trabajador en segundo
     * plano.
     *
     * @throws Throwable Si ocurre algun error durante el trabajo principal.
     */
    public void onWork() throws Throwable;

    /**
     * Llamado cuando ocurre algun error en
     * {@link ApplicationWorker#onWork() onWork}.
     *
     * @param thr Error capturado durante la ejecucion de
     * {@link ApplicationWorker#onWork() onWork}.
     */
    public void onError(Throwable thr);

    /**
     * Llamado cuando el trabajador en seguno plano sera terminado por algun
     * error no contemplado.
     *
     * @param thr Error capturado por el administrador de trabajadores durante
     * la ejecucion del trabajador.
     */
    public void onAbort(Throwable thr);

    /**
     * Llamado para finalizar el trabajador en segundo plano.
     */
    public void onFinish();

}
