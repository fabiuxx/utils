/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.generica.utils.criteria.column;

import py.com.generica.utils.misc.Assertions;
import py.com.generica.utils.misc.text.StringBuilder2;

/**
 *
 * @author Fabio A. González Sosa
 * @param <T>
 */
public class JpaColumn<T> extends AbstractColumn<T> {

    protected JpaColumn(Column<?> parent, String name, Class<T> type, boolean acceptsNull) {
        super(parent, name, type, acceptsNull);
    }

    public static <T> JpaColumn<T> instance(String name, Class<T> type) {
        return instance(null, name, type, false);
    }

    public static <T> JpaColumn<T> instance(String name, Class<T> type, boolean acceptsNull) {
        return instance(null, name, type, acceptsNull);
    }

    public static <T> JpaColumn<T> instance(Column<?> parent, String name, Class<T> type, boolean acceptsNull) {
        return new JpaColumn<>(parent, name, type, acceptsNull);
    }

    @Override
    public String getName() {
        StringBuilder2 builder = new StringBuilder2();
        if (getParent() != null) {
            String parentName = getParent().getName();
            if (!Assertions.stringNullOrEmpty(parentName)) {
                builder.append(parentName);
                builder.append(".");
            }
        }
        if (!Assertions.stringNullOrEmpty(name)) {
            builder.append(name);
        }
        return builder.toString();
    }

}
