/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.jsf;

import fa.gs.utils.collections.Maps;
import fa.gs.utils.misc.Assertions;
import fa.gs.utils.misc.text.Joiner;
import fa.gs.utils.misc.text.Text;
import fa.gs.utils.mixins.Self;
import java.util.ArrayList;
import java.util.Map;
import java8.util.stream.StreamSupport;

/**
 *
 * @author Fabio A. González Sosa
 */
public class JsfNavigationOutcomeBuilder implements Self<JsfNavigationOutcomeBuilder> {

    private ArrayList<String> pathParts;
    private Map<String, String> queryParams;

    private JsfNavigationOutcomeBuilder() {
        this.pathParts = new ArrayList<>();
        this.queryParams = Maps.empty();
        this.queryParams.put("faces-redirect", "true");
    }

    public static JsfNavigationOutcomeBuilder instance() {
        return new JsfNavigationOutcomeBuilder();
    }

    public JsfNavigationOutcomeBuilder path(String... paths) {
        for (String path : paths) {
            if (!Assertions.stringNullOrEmpty(path)) {
                pathParts.add(path);
            }
        }
        return self();
    }

    public JsfNavigationOutcomeBuilder arg(String name, Object value) {
        if (!Assertions.stringNullOrEmpty(name) && !Assertions.isNull(value)) {
            queryParams.put(name, String.valueOf(value));
        }
        return self();
    }

    private String buildOutcome() {
        if (Assertions.isNullOrEmpty(pathParts)) {
            return "";
        }

        String first = pathParts.get(0);
        if (!first.startsWith("/")) {
            pathParts.set(0, "/" + first);
        }

        String last = pathParts.get(pathParts.size() - 1);
        if (!last.endsWith(".xhtml")) {
            pathParts.set(pathParts.size() - 1, last + ".xhtml");
        }

        String outcome = Joiner.of(pathParts).separator("/").join();
        Text.normalizeSlashes(outcome);
        return outcome.trim();
    }

    public String build() {
        String outcome = buildOutcome();

        if (!queryParams.isEmpty()) {
            String[] args0 = StreamSupport.stream(queryParams.entrySet())
                    .map(e -> String.format("%s=%s", e.getKey(), e.getValue()))
                    .toArray(String[]::new);
            String args = Joiner.of(args0).separator("&").join();
            outcome = String.format("%s?%s", outcome, args);
        }

        return outcome;
    }

}
