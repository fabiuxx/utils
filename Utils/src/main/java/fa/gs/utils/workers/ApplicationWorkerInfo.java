/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.workers;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author Fabio A. González Sosa
 */
public interface ApplicationWorkerInfo extends Serializable {

    /**
     * Obtiene el identificador asociado al trabajador en segundo plano.
     *
     * @return Identificador del trabajador.
     */
    public String getWorkerId();

    /**
     * Obtiene el nombre amigable asociado al trabajador en segundo plano.
     *
     * @return Nombre del trabajador.
     */
    public String getWorkerName();

    /**
     * Obtiene el nombre de la clase concreta que define al trabajador en
     * segundo plano.
     *
     * @return Nombre canonico de clase.
     */
    public String getWorkerClass();

    /**
     * Obtiene la coleccion de configuraciones activas para la ejecucion del
     * trabajador en segundo plano.
     *
     * @return Configuraciones activas al momento de llamar a este metodo.
     */
    public Map<String, String> getActiveConfigurations();

    /**
     * Obtiene el instante de tiempo en que el trabajador fue ejecutado por
     * ultima vez.
     *
     * @return Fecha.
     */
    public Date getLastExecution();
}
