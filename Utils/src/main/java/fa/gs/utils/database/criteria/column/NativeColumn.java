/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.criteria.column;

import fa.gs.utils.misc.Assertions;
import fa.gs.utils.misc.text.Strings;
import java.util.regex.Pattern;

/**
 *
 * @author Fabio A. González Sosa
 * @param <T> Parametro de tipo.
 */
public class NativeColumn<T> extends AbstractColumn<T> {

    protected final String tableName;

    protected NativeColumn(String tableName, String name, Class<T> type) {
        super(null, name, type, true);
        this.tableName = tableName;
    }

    @Deprecated
    public static <T> NativeColumn<T> instance(String name, Class<T> type) {
        String[] parts = name.split(Pattern.quote("."));
        if (parts.length > 1) {
            return instance(parts[0], parts[1], type);
        } else {
            return instance(null, name, type);
        }
    }

    public static <T> NativeColumn<T> instance(String tableName, String name, Class<T> type) {
        return new NativeColumn<>(tableName, name, type);
    }

    @Override
    public String getName() {
        if (Assertions.stringNullOrEmpty(name)) {
            return "";
        }

        // Agregar comillas para preservar el case. Fuente: https://stackoverflow.com/a/43112096.
        if (Assertions.stringNullOrEmpty(tableName)) {
            return Strings.format("\"%s\"", name);
        } else {
            return Strings.format("\"%s\".\"%s\"", tableName, name);
        }
    }

    /**
     * TODO: ELIMINAR.
     *
     * @return Nombre simple.
     * @deprecated
     */
    @Deprecated
    public String getSimpleName() {
        return name;
    }

}
