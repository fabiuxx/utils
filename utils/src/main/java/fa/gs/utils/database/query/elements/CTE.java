/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.query.elements;

import fa.gs.utils.collections.Lists;
import fa.gs.utils.database.query.Dialect;
import fa.gs.utils.database.query.QueryPart;
import fa.gs.utils.misc.Assertions;
import fa.gs.utils.misc.text.Joiner;
import fa.gs.utils.misc.text.Strings;
import java.util.Collection;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Fabio A. González Sosa
 */
public class CTE implements QueryPart {

    @Getter
    @Setter
    private String name;

    private final Collection<String> projections;

    @Getter
    @Setter
    private String body;

    @Getter
    @Setter
    private Boolean recursive;

    public CTE() {
        this.name = "";
        this.projections = Lists.empty();
        this.body = "";
        this.recursive = false;
    }

    private String buildProjections() {
        if (Assertions.isNullOrEmpty(projections)) {
            return "";
        } else {
            String value = Joiner.of(projections).separator(",").join();
            value = "(" + value + ")";
            return value.trim();
        }
    }

    public CTE setProjections(String... projections) {
        Lists.add(this.projections, projections);
        return this;
    }

    @Override
    public String stringify(Dialect dialect) {
        if (!Assertions.stringNullOrEmpty(name) && !Assertions.stringNullOrEmpty(body)) {
            String kw = (recursive) ? "RECURSIVE" : "";
            String pj = buildProjections();
            String ql = Strings.format(" %s %s%s as (%s) ", kw, name, pj, body);
            return ql.trim();
        } else {
            return "";
        }
    }

}
