/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.logging;

/**
 *
 * @author Fabio A. González Sosa
 */
public interface LoggerFactory {

    /**
     * Obtiene una instancia de los servicios de logueo.
     *
     * @param name Nombre del servicio de logueo a utilizar
     * @return Servicio de logueo.
     */
    Logger getLogger(String name);

}
