/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.jsf.resources.types;

import fa.gs.utils.misc.text.Text;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author Fabio A. González Sosa
 */
public class JarResource extends BaseResource {

    /**
     * Ruta de acceso base para la carga de recursos empaquetados dentro del
     * JAR.
     */
    private final String basePath;

    /**
     * Constructor.
     *
     * @param libraryName Nombre de la libreria que contiene el recurso.
     * @param resourceName Identificador del recurso, normalmente un nombre.
     * @param basePath Path base para recurso.
     */
    public JarResource(String libraryName, String resourceName, String basePath) {
        super(libraryName, resourceName);
        this.basePath = basePath;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public InputStream getInputStream() throws IOException {
        String path = basePath + "/" + getResourceName();
        return getClassLoader().getResourceAsStream(Text.normalizeSlashes(path));
    }

}
