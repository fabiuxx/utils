/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.query.expressions.literals;

import fa.gs.utils.collections.Lists;
import fa.gs.utils.database.query.Dialect;
import fa.gs.utils.misc.text.Joiner;
import fa.gs.utils.misc.text.Strings;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

/**
 *
 * @author Fabio A. González Sosa
 */
public class CollectionLiteral implements Literal<Collection<Literal>> {

    private final Collection<Literal> values;

    CollectionLiteral(Literal[] values) {
        this.values = Lists.empty();
        Collections.addAll(this.values, values);
    }

    public static CollectionLiteral instance(Integer... values) {
        Literal[] literals = Arrays.asList(values)
                .stream()
                .map(v -> new NumberLiteral(v))
                .toArray(Literal[]::new);
        return new CollectionLiteral(literals);
    }

    public static CollectionLiteral instance(Long... values) {
        Literal[] literals = Arrays.asList(values)
                .stream()
                .map(v -> new NumberLiteral(v))
                .toArray(Literal[]::new);
        return new CollectionLiteral(literals);
    }

    public static CollectionLiteral instance(BigInteger... values) {
        Literal[] literals = Arrays.asList(values)
                .stream()
                .map(v -> new NumberLiteral(v))
                .toArray(Literal[]::new);
        return new CollectionLiteral(literals);
    }

    public static CollectionLiteral instance(BigDecimal... values) {
        Literal[] literals = Arrays.asList(values)
                .stream()
                .map(v -> new NumberLiteral(v))
                .toArray(Literal[]::new);
        return new CollectionLiteral(literals);
    }

    public static CollectionLiteral instance(String... values) {
        Literal[] literals = Arrays.asList(values)
                .stream()
                .map(v -> new StringLiteral(v))
                .toArray(Literal[]::new);
        return new CollectionLiteral(literals);
    }

    public static CollectionLiteral instance(Date... values) {
        return instance(DateLiteral.DateType.FECHA_HORA, values);
    }

    public static CollectionLiteral instance(DateLiteral.DateType dateType, Date... values) {
        Literal[] literals = Arrays.asList(values)
                .stream()
                .map(v -> new DateLiteral(v, dateType))
                .toArray(Literal[]::new);
        return new CollectionLiteral(literals);
    }

    @Override
    public Collection<Literal> value() {
        return values;
    }

    @Override
    public String stringify(final Dialect dialect) {
        Joiner j = Joiner
                .of(values)
                .adapter((Object obj, Object... args) -> {
                    Literal l = (Literal) obj;
                    return l.stringify(dialect);
                });

        return Strings.format("(%s)", j.join());
    }

}
