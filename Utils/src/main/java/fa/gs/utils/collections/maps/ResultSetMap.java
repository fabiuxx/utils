/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.collections.maps;

import fa.gs.utils.collections.Maps;
import fa.gs.utils.collections.enums.EnumerableAdapter;
import fa.gs.utils.database.criteria.column.Column;
import fa.gs.utils.database.criteria.column.NativeColumn;
import fa.gs.utils.misc.Numeric;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

/**
 * TODO: ELIMINAR.
 *
 * @author Fabio A. González Sosa
 */
@Deprecated
public class ResultSetMap {

    private final Map<String, Object> map;

    public ResultSetMap(Map<String, Object> rows) {
        this.map = Maps.empty();
        if (rows != null) {
            this.map.putAll(rows);
        }
    }

    public Map<String, Object> getMap() {
        return Collections.unmodifiableMap(map);
    }

    private String resolveColumnName(Column<?> column) {
        if (column == null) {
            return "";
        }

        if (column instanceof NativeColumn) {
            return ((NativeColumn<?>) column).getSimpleName();
        }

        return column.getName();
    }

    public <T extends Enum<T>> T enumerable(String key) {
        return enumerable(key, null);
    }

    public <T extends Enum<T>> T enumerable(Column<?> key) {
        return enumerable(key, null);
    }

    public <T extends Enum<T>> T enumerable(Column<?> key, EnumerableAdapter<T> adapter) {
        return enumerable(resolveColumnName(key), adapter);
    }

    public <T extends Enum<T>> T enumerable(String key, EnumerableAdapter<T> adapter) {
        Object obj = Maps.get(map, key);
        if (obj == null) {
            return null;
        }
        return adapter.getEnumerable(obj);
    }

    public String string(String key) {
        return string(key, null);
    }

    public String string(Column<String> key) {
        return string(resolveColumnName(key), null);
    }

    public String string(String key, String fallback) {
        return Maps.get(map, key, fallback, String.class);
    }

    public Character character(String key) {
        return character(key, null);
    }

    public Character character(Column<Character> key) {
        return character(resolveColumnName(key), null);
    }

    public Character character(String key, Character fallback) {
        return Maps.get(map, key, fallback, Character.class);
    }

    public Integer integer(String key) {
        return integer(key, null);
    }

    public Integer integer(Column<Integer> key) {
        return integer(resolveColumnName(key), null);
    }

    public Integer integer(String key, Integer fallback) {
        return Maps.get(map, key, fallback, Integer.class);
    }

    public Long long0(String key) {
        return long0(key, null);
    }

    public Long long0(Column<Long> key) {
        return long0(resolveColumnName(key), null);
    }

    public Long long0(String key, Long fallback) {
        /**
         * Se utiliza este artificio a manera de adaptar valores obtenidos de
         * otras fuentes. Por ejemplo, desde registros de base de datos donde la
         * representacion nativa de una columna esperada como Long podria ser
         * tanto Long como BigInteger o BigDecimal.
         */
        try {
            Object value = map.getOrDefault(key, null);
            if (value != null) {
                return Numeric.adaptAsLong(value);
            } else {
                return fallback;
            }
        } catch (Throwable thr) {
            return fallback;
        }
    }

    public BigInteger biginteger(String key) {
        return biginteger(key, null);
    }

    public BigInteger biginteger(Column<BigInteger> key) {
        return biginteger(resolveColumnName(key), null);
    }

    public BigInteger biginteger(String key, BigInteger fallback) {
        return Maps.get(map, key, fallback, BigInteger.class);
    }

    public BigDecimal bigdecimal(String key) {
        return bigdecimal(key, null);
    }

    public BigDecimal bigdecimal(Column<BigDecimal> key) {
        return bigdecimal(resolveColumnName(key), null);
    }

    public BigDecimal bigdecimal(String key, BigDecimal fallback) {
        return Maps.get(map, key, fallback, BigDecimal.class);
    }

    public Date date(String key) {
        return date(key, null);
    }

    public Date date(Column<Date> key) {
        return date(resolveColumnName(key), null);
    }

    public Date date(String key, Date fallback) {
        return Maps.get(map, key, fallback, Date.class);
    }

}
