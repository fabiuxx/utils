/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.jsf.resources.types;

import fa.gs.utils.collections.Maps;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import javax.faces.application.Resource;
import javax.faces.context.FacesContext;

/**
 *
 * @author Fabio A. González Sosa
 */
public class CacheableResourceDecorator extends Resource {

    private final Resource wrapped;
    private final Integer secondsToExpire;
    private final String dateToExpire;

    /**
     * Constructor.
     *
     * @param wrapped Recurso a extender como cacheable.
     * @param expiration Tiempo de expiracion para el recurso cacheable.
     * @param unit Unidad de tiempo en el que se expresa el tiempo de
     * expiracion.
     */
    private CacheableResourceDecorator(Resource wrapped, long expiration, TimeUnit unit) {
        this.wrapped = wrapped;
        this.secondsToExpire = (int) unit.toSeconds(expiration);
        this.dateToExpire = CacheableResourceDecorator.getExpirationDate(new Date(), secondsToExpire);
    }

    /**
     * Inicializador estatico.
     *
     * @param resource Recurso a encapsular.
     * @return Recurso con soporte para cacheado.
     */
    public static Resource instance(Resource resource) {
        return instance(resource, 30, TimeUnit.MINUTES);
    }

    /**
     * Inicializador estatico.
     *
     * @param wrapped Recurso a extender como cacheable.
     * @param expiration Tiempo de expiracion para el recurso cacheable.
     * @param unit Unidad de tiempo en el que se expresa el tiempo de
     * expiracion.
     */
    public static Resource instance(Resource resource, long expiration, TimeUnit unit) {
        return new CacheableResourceDecorator(resource, expiration, unit);
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return wrapped.getInputStream();
    }

    @Override
    public String getContentType() {
        return wrapped.getContentType();
    }

    @Override
    public String getResourceName() {
        return wrapped.getResourceName();
    }

    @Override
    public String getLibraryName() {
        return wrapped.getLibraryName();
    }

    @Override
    public String getRequestPath() {
        return wrapped.getRequestPath();
    }

    @Override
    public URL getURL() {
        return wrapped.getURL();
    }

    @Override
    public boolean userAgentNeedsUpdate(FacesContext context) {
        return wrapped.userAgentNeedsUpdate(context);
    }

    @Override
    public Map<String, String> getResponseHeaders() {
        // Agregar cabeceras originales.
        Map<String, String> headers = Maps.empty();
        headers.putAll(wrapped.getResponseHeaders());

        // Agregar cabeceras de control de cache.
        headers.put("Cache-Control", String.format("private; max-age=%d", secondsToExpire));
        headers.put("Expires", dateToExpire);
        headers.put("Pragma", "cache");
        return headers;
    }

    /**
     * Obtiene la fecha de expiracion para el recurso a partir de un momento
     * dado.
     *
     * @param from Fecha desde la cual se considera el calculo de expiracion del
     * recurso.
     * @param seconds Tiempo, en segundos, para expiracion del recurso.
     * @return Representacion en forma de texto (HTTP-date RFC7231) de la fecha
     * de expiracion.
     */
    private static String getExpirationDate(Date from, long seconds) {
        Calendar c = Calendar.getInstance();
        c.setTime(from);
        c.add(Calendar.SECOND, (int) seconds);
        Date to = c.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd-MMM-yyyy HH:mm:ss zzz", Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        String txt = sdf.format(to).toUpperCase();
        return txt;
    }

}
