/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.query.expressions.names;

import fa.gs.utils.collections.Lists;
import fa.gs.utils.database.query.Dialect;
import fa.gs.utils.misc.text.Joiner;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 *
 * @author Fabio A. González Sosa
 */
public class QualifiedName implements Name<Collection<SimpleName>> {

    private final Collection<SimpleName> names;

    public QualifiedName(String... parts0) {
        /**
         * Separar partes individuales que en realidad representen
         * derreferencias. Ejemplo: ["db", "info.persona"] => ["db", "info",
         * "persona"].
         */
        Collection<String> parts = Lists.empty();
        for (String part : parts0) {
            String[] parts1 = part.split("\\.");
            Collections.addAll(parts, parts1);
        }

        this.names = parts.stream()
                .map(p -> new SimpleName(p))
                .collect(Collectors.toList());
    }

    public QualifiedName(SimpleName... names) {
        this.names = Arrays.asList(names);
    }

    @Override
    public Collection<SimpleName> value() {
        return names;
    }

    @Override
    public String stringify(Dialect dialect) {
        return Joiner.of(names)
                .separator(".")
                .adapter((Object obj, Object... args) -> ((Name) obj).stringify(dialect))
                .join();
    }

}
