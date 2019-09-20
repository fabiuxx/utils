/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.jsf.resources.types;

import fa.gs.utils.misc.EmptyInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author Fabio A. González Sosa
 */
public class EmptyResource extends BaseResource {

    /**
     * Constructor.
     *
     * @param libraryName Nombre de libreria.
     * @param resourceName Nombre de recurso.
     * @param contentType Tipo de contenido.
     */
    protected EmptyResource(String libraryName, String resourceName, String contentType) {
        super(libraryName, resourceName);
        setContentType(contentType);
    }

    /**
     * Inicializador estatico.
     *
     * @param libraryName Nombre de libreria.
     * @param resourceName Nombre de recurso.
     * @return Recurso.
     */
    public static EmptyResource instance(String libraryName, String resourceName) {
        return instance(libraryName, resourceName, "text/plain");
    }

    /**
     * Inicializador estatico.
     *
     * @param libraryName Nombre de libreria.
     * @param resourceName Nombre de recurso.
     * @param contentType Tipo de contenido.
     * @return Recurso.
     */
    public static EmptyResource instance(String libraryName, String resourceName, String contentType) {
        return new EmptyResource(libraryName, resourceName, contentType);
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new EmptyInputStream();
    }

}
