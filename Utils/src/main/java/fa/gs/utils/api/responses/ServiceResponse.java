/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.api.responses;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import fa.gs.utils.api.exceptions.ApiRollbackException;
import fa.gs.utils.misc.Files;
import fa.gs.utils.misc.errors.Errno;
import fa.gs.utils.result.simple.Result;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

/**
 *
 * @author Fabio A. González Sosa
 */
public class ServiceResponse {

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    /**
     * La peticion fue procesada correctamente.
     */
    public static final int HTTP_OK = Response.Status.OK.getStatusCode();

    /**
     * La peticion fue aceptada correctamente.
     */
    public static final int HTTP_ACCEPTED = Response.Status.ACCEPTED.getStatusCode();

    /**
     * La peticion presenta un formato invalido.
     */
    public static final int HTTP_BAD_REQUEST = Response.Status.BAD_REQUEST.getStatusCode();

    /**
     * El cliente no esta autorizado a realizar la peticion.
     */
    public static final int HTTP_UNAUTHORIZED = Response.Status.UNAUTHORIZED.getStatusCode();

    /**
     * El cliente tiene prohibido realizar la peticion.
     */
    public static final int HTTP_FORBIDDEN = Response.Status.FORBIDDEN.getStatusCode();

    /**
     * El servidor no pudo procesar la peticion por un error interno grave.
     */
    public static final int HTTP_SERVER_ERROR = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();

    /**
     * La peticion es desconocida.
     */
    public static final int HTTP_NOT_FOUND = Response.Status.NOT_FOUND.getStatusCode();
    //</editor-fold>

    /**
     * Obtiene un constructor de respuestas para operaciones exitosas del API
     * REST.
     *
     * @return Constructor de respuestas.
     */
    public static OK ok() {
        return new OK();
    }

    /**
     * Obtiene un constructor de respuestas para operaciones fallidas del API
     * REST.
     *
     * @return Constructor de respuestas.
     */
    public static KO ko() {
        return new KO();
    }

    /**
     * Obtiene un constructor de respuestas para operaciones donde ocurrieron
     * errores internos del servidor.
     *
     * @return Constructor de respuestas.
     */
    public static KO error() {
        return new ERROR();
    }

    /**
     * Obtiene un constructor de respuestas para operaciones rechazadas por
     * falta de datos de entrada en formato correctos.
     *
     * @return Constructor de respuestas.
     */
    public static KO badRequest() {
        return new BAD_REQUEST();
    }

    /**
     * Obtiene un constructor de respuestas para operaciones donde el usuario no
     * puede efectuar dicha operacion del API REST.
     *
     * @return Constructor de respuestas.
     */
    public static KO forbidden() {
        return new FORBIDDEN();
    }

    /**
     * Obtiene un constructor de respuestas para operaciones donde el usuario no
     * puede ser identificado.
     *
     * @return Constructor de respuestas.
     */
    public static KO unauthorized() {
        return new UNAUTHORIZED();
    }

    /**
     * Obtiene un constructor de respuestas para operaciones donde el recurso no
     * puede ser identificado.
     *
     * @return Constructor de respuestas.
     */
    public static KO notFound() {
        return new NOT_FOUND();
    }

    /**
     * Construye una respuesta segun el formato base definido por el servicio
     * del API REST.
     *
     * @param payload Carga util de la respuesta.
     * @param status Codigo de estado HTTP de la respuesta.
     * @return Respuesta.
     */
    private static Response response(JsonElement payload, int status) {
        Response.ResponseBuilder builder = Response
                .status(status)
                .entity(payload.toString())
                .type(MediaType.APPLICATION_JSON);
        return builder.build();
    }

    /**
     * Construye una respuesta para el envio de bytes puros obtenidos desde un
     * archivo. La respuesta no es encapsulada en el formato de respuestas por
     * defecto.
     *
     * @param file Archivo de entrada.
     * @param name Nombre de archivo.
     * @param deleteAfter Indica si el archivo debe ser eliminado luego de su
     * uso.
     * @return Respuesta.
     */
    public static Response file(File file, String name, final boolean deleteAfter) {
        return file(file, name, MediaType.APPLICATION_OCTET_STREAM, deleteAfter);
    }

    /**
     * Construye una respuesta para el envio de bytes puros obtenidos desde un
     * archivo. La respuesta no es encapsulada en el formato de respuestas por
     * defecto.
     *
     * @param file Archivo de entrada.
     * @param name Nombre de archivo.
     * @param mediaType MIME Type de tipo de archivo.
     * @param deleteAfter Indica si el archivo debe ser eliminado luego de su
     * uso.
     * @return Respuesta.
     */
    public static Response file(final File file, String name, String mediaType, final boolean deleteAfter) {
        return file(file, name, mediaType, deleteAfter, -1);
    }

    /**
     * Construye una respuesta para el envio de bytes puros obtenidos desde un
     * archivo. La respuesta no es encapsulada en el formato de respuestas por
     * defecto.
     *
     * @param file Archivo de entrada.
     * @param name Nombre de archivo.
     * @param mediaType MIME Type de tipo de archivo.
     * @param deleteAfter Indica si el archivo debe ser eliminado luego de su
     * uso.
     * @param cacheSeconds Cantidad de segundos para expiracion de imagen en
     * cache.
     * @return Respuesta.
     */
    public static Response file(final File file, String name, String mediaType, final boolean deleteAfter, final long cacheSeconds) {
        StreamingOutput stream = new StreamingOutput() {
            @Override
            public void write(OutputStream output) throws IOException, WebApplicationException {
                try (FileInputStream reader = new FileInputStream(file)) {
                    byte[] chunk = new byte[4096];
                    int read = 0;
                    while ((read = reader.read(chunk)) > 0) {
                        output.write(chunk, 0, read);
                    }
                } finally {
                    if (deleteAfter) {
                        Files.delete(file);
                    }
                }
            }
        };

        Response.ResponseBuilder builder = Response
                .status(HTTP_OK)
                .entity(stream)
                .header("Cache-Control", String.format("max-age=%d", Math.max(0, cacheSeconds)))
                .header("Content-Disposition", "attachment; filename=\"" + name + "\"")
                .header("X-Epos-Filename", name)
                .type(mediaType);
        return builder.build();
    }

    /**
     * Clase que abstrae la construccion de respuestas HTTP validas.
     */
    public static abstract class Builder {

        /**
         * Construye una respuesta HTTP valida que sera utilizada como respuesta
         * a una peticion dada.
         *
         * @return Respuesta HTTP.
         */
        public abstract Response build();

        /**
         * Permite a una operacion deshacer todos sus cambios forzando un
         * rollback (administrado por el contenedor de aplicaciones) pero
         * manteniendo alguna respuesta que pudiera ser construida.
         */
        public void rollback() {
            Response re = build();
            ApiRollbackException ex = new ApiRollbackException(re);
            throw ex;
        }
    }

    /**
     * Permite construir una respuesta apropiada para casos donde se ha
     * efectuado una operacion de manera exitosa.
     */
    public static class OK extends ServiceResponse.Builder {

        //<editor-fold defaultstate="collapsed" desc="Atributos">
        /**
         * Codigo de estado HTTP.
         */
        private int status;

        /**
         * Otra informacion adicional que se adjunta como parte del cuerpo de la
         * respuesta.
         */
        private JsonElement raw;
        //</editor-fold>

        /**
         * Constructor.
         */
        public OK() {
            status = Response.Status.OK.getStatusCode();
            raw = null;
        }

        public OK status(int status) {
            this.status = status;
            return this;
        }

        public OK payload(JsonElement payload) {
            if (payload != null) {
                this.raw = payload;
            }
            return this;
        }

        public OK payload(String fmt, Object... args) {
            String msg = String.format(fmt, args);
            return payload(new JsonPrimitive(msg));
        }

        @Override
        public Response build() {
            JsonElement payload = JsonResponseWrapper.success(raw);
            return ServiceResponse.response(payload, status);
        }
    }

    /**
     * Permite construir una respuesta apropiada para casos donde se ha
     * producido un fallo.
     */
    public static class KO extends ServiceResponse.Builder {

        //<editor-fold defaultstate="collapsed" desc="Atributos">
        /**
         * Codigo de estado HTTP.
         */
        protected int status;

        /**
         * Codigo de error interno.
         */
        protected Errno errno;

        /**
         * Mensaje que indica la causa del error producido.
         */
        protected String cause;

        /**
         * Otra informacion adicional que se adjunta como parte del cuerpo de la
         * respuesta.
         */
        protected JsonElement raw;
        //</editor-fold>

        /**
         * Constructor.
         */
        private KO() {
            status = HTTP_OK;
            errno = null;
            cause = "ERROR";
            raw = null;
        }

        public KO status(int status) {
            this.status = status;
            return this;
        }

        public KO status(Response.Status status) {
            if (status != null) {
                this.status = status.getStatusCode();
            }
            return this;
        }

        public KO errno(Errno errno) {
            this.errno = errno;
            return this;
        }

        public KO cause(String fmt, Object... args) {
            this.cause = String.format(fmt, args);
            return this;
        }

        public KO cause(Throwable throwable) {
            if (throwable != null) {
                this.cause = throwable.getLocalizedMessage();
            }
            return this;
        }

        public KO cause(Result result) {
            if (result.isFailure()) {
                errno(result.failure().errno());
                cause(result.failure().message());
            }
            return this;
        }

        public KO payload(JsonElement payload) {
            if (payload != null) {
                this.raw = payload;
            }
            return this;
        }

        @Override
        public Response build() {
            JsonElement payload = JsonResponseWrapper.failure(raw, cause, errno);
            return ServiceResponse.response(payload, status);
        }
    }

    /**
     * Permite construir una respuesta apropiada para casos donde se ha
     * producido un fallo por una peticion con datos en formato incorrecto.
     */
    public static class BAD_REQUEST extends KO {

        /**
         * Constructor.
         */
        public BAD_REQUEST() {
            super();
            this.cause = "BAD_REQUEST";
            this.status = HTTP_BAD_REQUEST;
        }

    }

    /**
     * Permite construir una respuesta apropiada para casos donde se ha
     * producido un fallo por una peticion prohibida.
     */
    public static class FORBIDDEN extends KO {

        /**
         * Constructor.
         */
        public FORBIDDEN() {
            super();
            this.cause = "FORBIDDEN";
            this.status = HTTP_FORBIDDEN;
        }
    }

    /**
     * Permite construir una respuesta apropiada para casos donde se ha
     * producido un fallo por una peticion donde el emisor no esta autorizado
     * para el mismo.
     */
    public static class UNAUTHORIZED extends KO {

        /**
         * Constructor.
         */
        public UNAUTHORIZED() {
            super();
            this.cause = "UNAUTHORIZED";
            this.status = HTTP_UNAUTHORIZED;
        }
    }

    /**
     * Permite construir una respuesta apropiada para casos donde no se puede
     * encontrar un recurso dado.
     */
    public static class NOT_FOUND extends KO {

        /**
         * Constructor.
         */
        public NOT_FOUND() {
            super();
            this.cause = "NOT_FOUND";
            this.status = HTTP_NOT_FOUND;
        }

    }

    /**
     * Permite construir una respuesta apropiada para casos donde se ha
     * producido un fallo interno del servidor.
     */
    public static class ERROR extends KO {

        /**
         * Constructor.
         */
        public ERROR() {
            super();
            cause = "INTERNAL SERVER ERROR";
            this.status = HTTP_SERVER_ERROR;
        }
    }

}
