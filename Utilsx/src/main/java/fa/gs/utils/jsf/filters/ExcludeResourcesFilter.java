/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.jsf.filters;

import fa.gs.utils.collections.Lists;
import fa.gs.utils.collections.Maps;
import fa.gs.utils.misc.Assertions;
import fa.gs.utils.misc.json.serialization.JsonDeserializer;
import fa.gs.utils.misc.json.serialization.JsonProperty;
import fa.gs.utils.misc.json.serialization.JsonResolution;
import fa.gs.utils.misc.text.Text;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.Data;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author Fabio A. González Sosa
 */
public class ExcludeResourcesFilter implements Filter {

    private Config config;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        try {
            String config0 = filterConfig.getInitParameter("config");
            this.config = JsonDeserializer.deserialize(config0, Config.class);
        } catch (Throwable thr) {
            throw new ServletException("Ocurrió un error inicializando filtro.", thr);
        }
    }

    @Override
    public void destroy() {
        ;
    }

    @Override
    public void doFilter(ServletRequest request0, ServletResponse response0, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) request0;
        HttpServletResponse response = (HttpServletResponse) response0;

        String url = request.getRequestURL().toString();
        Map<String, String> queryParams = getQueryParams(request);
        for (ResourceInfo resource : config.resources) {
            if (!url.contains(resource.name)) {
                continue;
            }

            if (!Assertions.stringNullOrEmpty(resource.library)) {
                String library = Maps.get(queryParams, "ln");
                if (!Objects.equals(resource.library, library)) {
                    continue;
                }
            }

            if (!Assertions.stringNullOrEmpty(resource.version)) {
                String version = Maps.get(queryParams, "v");
                if (!Objects.equals(resource.version, version)) {
                    continue;
                }
            }

            /**
             * Match con el recurso a excluir. Actualmente el mecanismo de
             * exclusion consiste en enviar 0 bytes de datos.
             */
            String mimeType = Text.select(resource.mimeType, "*/*");
            response.setContentType(mimeType);
            response.setContentLength(0);
            response.setCharacterEncoding("UTF-8");
            response.setStatus(200);
            return;
        }

        chain.doFilter(request0, response0);
    }

    private Map<String, String> getQueryParams(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        String queryParams = request.getQueryString();
        MultiValueMap<String, String> mmap = UriComponentsBuilder.fromUriString(url + "?" + queryParams).build().getQueryParams();
        Map<String, String> map = Maps.empty();
        for (Map.Entry<String, List<String>> entry : mmap.entrySet()) {
            String key = entry.getKey();
            String val = Lists.first(entry.getValue());
            map.put(key, val);
        }
        return map;
    }

    @Data
    private static class Config {

        @JsonProperty(name = "resources")
        private Collection<ResourceInfo> resources;
    }

    @Data
    private static class ResourceInfo {

        @JsonProperty(name = "name")
        private String name;

        @JsonProperty(name = "library", resolution = JsonResolution.OPTIONAL)
        private String library;

        @JsonProperty(name = "version", resolution = JsonResolution.OPTIONAL)
        private String version;

        @JsonProperty(name = "mime", resolution = JsonResolution.OPTIONAL)
        private String mimeType;

    }

}
