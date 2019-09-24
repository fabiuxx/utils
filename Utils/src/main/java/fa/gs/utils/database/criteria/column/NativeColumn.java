/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.criteria.column;

/**
 *
 * @author Fabio A. González Sosa
 * @param <T> Parametro de tipo.
 */
public class NativeColumn<T> extends AbstractColumn<T> {

    protected NativeColumn(String name, Class<T> type, boolean acceptsNull) {
        super(null, name, type, acceptsNull);
    }

    public static <T> NativeColumn<T> instance(String name, Class<T> type) {
        return instance(name, type, false);
    }

    public static <T> NativeColumn<T> instance(String name, Class<T> type, boolean acceptsNull) {
        return new NativeColumn<>(name, type, acceptsNull);
    }

}
