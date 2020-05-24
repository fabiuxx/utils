/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.query.elements;

import fa.gs.utils.collections.Arrays;
import fa.gs.utils.collections.Lists;
import fa.gs.utils.database.query.Dialect;
import fa.gs.utils.database.query.QueryPart;
import fa.gs.utils.misc.Assertions;
import fa.gs.utils.misc.text.Joiner;
import fa.gs.utils.misc.text.Text;
import java.util.Collection;
import java.util.Collections;
import lombok.Data;

/**
 *
 * @author Fabio A. González Sosa
 */
@Data
public class Name implements QueryPart {

    private final String[] parts;

    public Name(String... parts) {
        /**
         * Separar partes individuales que en realidad representen
         * derreferencias. Ejemplo: ["db", "info.persona"] => ["db", "info",
         * "persona"].
         */
        Collection<String> parts0 = Lists.empty();
        for (String part0 : parts) {
            String[] parts1 = part0.split("\\.");
            for (String part1 : parts1) {
                if (!Assertions.stringNullOrEmpty(part1)) {
                    Collections.addAll(parts0, part1);
                }
            }
        }

        this.parts = Arrays.unwrap(parts0, String.class);
    }

    @Override
    public String stringify(Dialect dialect) {
        return Joiner.of(parts)
                .separator(".")
                .adapter((Object obj, Object... args) -> {
                    String part = (String) obj;
                    if (part.equals("*")) {
                        return part;
                    } else {
                        return Text.quoteDouble(part);
                    }
                })
                .join();
    }

}
