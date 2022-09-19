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
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java8.util.stream.StreamSupport;

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

    public static CollectionLiteral instance(Integer[] values) {
        Collection<Integer> collection = Lists.wrap(values);
        Literal[] literals = StreamSupport.stream(collection)
                .map(v -> new NumberLiteral(v))
                .toArray(Literal[]::new);
        return new CollectionLiteral(literals);
    }

    public static CollectionLiteral instance(Long[] values) {
        Collection<Long> collection = Lists.wrap(values);
        Literal[] literals = StreamSupport.stream(collection)
                .map(v -> new NumberLiteral(v))
                .toArray(Literal[]::new);
        return new CollectionLiteral(literals);
    }

    public static CollectionLiteral instance(BigInteger[] values) {
        Collection<BigInteger> collection = Lists.wrap(values);
        Literal[] literals = StreamSupport.stream(collection)
                .map(v -> new NumberLiteral(v))
                .toArray(Literal[]::new);
        return new CollectionLiteral(literals);
    }

    public static CollectionLiteral instance(BigDecimal[] values) {
        Collection<BigDecimal> collection = Lists.wrap(values);
        Literal[] literals = StreamSupport.stream(collection)
                .map(v -> new NumberLiteral(v))
                .toArray(Literal[]::new);
        return new CollectionLiteral(literals);
    }

    public static CollectionLiteral instance(String[] values) {
        Collection<String> collection = Lists.wrap(values);
        Literal[] literals = StreamSupport.stream(collection)
                .map(v -> new StringLiteral(v))
                .toArray(Literal[]::new);
        return new CollectionLiteral(literals);
    }

    public static CollectionLiteral instance(Date[] values) {
        return instance(values, DateLiteral.DateType.FECHA_HORA);
    }

    public static CollectionLiteral instance(Date[] values, DateLiteral.DateType dateType) {
        Collection<Date> collection = Lists.wrap(values);
        Literal[] literals = StreamSupport.stream(collection)
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
        Joiner joiner = Joiner
                .of(values)
                .adapter(obj -> {
                    Literal l = (Literal) obj;
                    return l.stringify(dialect);
                });
        return Strings.format("(%s)", joiner.join());
    }

}
