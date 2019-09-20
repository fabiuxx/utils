/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.generica.utils.criteria;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import py.com.generica.utils.collections.Lists;
import py.com.generica.utils.collections.Maps;
import py.com.generica.utils.criteria.column.Column;
import py.com.generica.utils.criteria.sql.build.Criterias;

/**
 *
 * @author Fabio A. González Sosa
 * @param <T>
 */
public class QueryCriteria<T extends QueryCriteria<T>> implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    /**
     * Indica la cantidad de registros a retornar.
     */
    protected Integer limit;

    /**
     * Indica la cantidad de registros a omitir antes de materializar la lista
     * final de registros de la consulta.
     */
    protected Integer offset;

    /**
     * Campos de ordenacion.
     */
    protected Collection<Sorting> sorts;

    /**
     * Coleccion de campos de filtro.
     */
    protected Collection<Condition> filters;

    /**
     * Campos de "ayuda" para el gestor de sentencias SQL.
     */
    protected Map<String, Object> hints;
    //</editor-fold>

    /**
     * Constructor.
     */
    protected QueryCriteria() {
        this.limit = null;
        this.offset = null;
        this.sorts = Lists.empty();
        this.filters = Lists.empty();
        this.hints = Maps.empty();
    }

    protected T self() {
        return (T) this;
    }

    /**
     * Obtiene una nueva instancia de esta clase.
     *
     * @return Instancia nueva.
     */
    public static QueryCriteria instance() {
        return new QueryCriteria();
    }

    /**
     * Establece una condicion de filtro para la consulta.
     *
     * @param condition Condicion de filtro.
     * @return Esta misma instancia.
     */
    public T where(Condition condition) {
        if (condition != null) {
            this.filters.add(condition);
        }
        return self();
    }

    /**
     * @deprecated usar {@link Criterias criterias}.
     * @param column
     * @param operator
     * @param value
     * @return
     */
    @Deprecated
    public T where(Column<?> column, Operator operator, Object value) {
        if (!column.accepts(value)) {
            throw new IllegalArgumentException("Valor no aceptado por columna");
        }

        where(new Condition(column.getName(), operator, value));
        return self();
    }

    /**
     * @deprecated usar {@link Criterias criterias}.
     * @param column
     * @param operator
     * @param value
     * @return
     */
    @Deprecated
    public T where(Column<?> column, Operator operator, Object[] value) {
        if (!column.accepts(value)) {
            throw new IllegalArgumentException("Valor no aceptado por columna");
        }

        where(new Condition(column.getName(), operator, value));
        return self();
    }

    /**
     * Establece un criterio de ordenacion para la consulta.
     *
     * @param sorting Criterio de ordenacion.
     * @return Esta misma instancia.
     */
    public T order(Sorting sorting) {
        if (sorting != null) {
            this.sorts.add(sorting);
        }
        return self();
    }

    /**
     * @deprecated usar {@link Criterias criterias}.
     * @param column
     * @param order
     * @return
     */
    @Deprecated
    public T order(Column<?> column, OrderKind order) {
        Sorting sorting = new Sorting(column.getName(), order);
        order(sorting);
        return self();
    }

    /**
     * Establece la cantidad maxima de registros a retornar en la consulta.
     *
     * @param limit Cantidad maxima de registros a retornar.
     * @return Esta misma instancia.
     */
    public T limit(Integer limit) {
        if (limit != null) {
            this.limit = limit;
        }
        return self();
    }

    /**
     * Establece la cantidad de registros a omitir inicialmente en la consulta.
     *
     * @param offset Cantidad de registros a omitir en la consulta.
     * @return Esta misma instancia.
     */
    public T offset(Integer offset) {
        if (offset != null) {
            this.offset = offset;
        }
        return self();
    }

    /**
     * Establece valores de ayuda para el planificador de sentencias SQL
     * subyacente.
     *
     * @param name Nombre del hint.
     * @param value Valor a utilizar.
     * @return Esta misma instancia.
     */
    public T hint(String name, String value) {
        if (name != null && !name.isEmpty()) {
            hints.put(name, value);
        }
        return self();
    }

    //<editor-fold defaultstate="collapsed" desc="Getters y Setters">
    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Collection<Sorting> getSorts() {
        return sorts;
    }

    public void setSorts(List<Sorting> sorts) {
        this.sorts = sorts;
    }

    public boolean hasSortSpecifiers() {
        return !(sorts == null || sorts.isEmpty());
    }

    public Collection<Condition> getFilters() {
        return filters;
    }

    public void setFilters(Collection<Condition> filters) {
        this.filters = filters;
    }

    public boolean hasFilterSpecifiers() {
        return !(filters == null || filters.isEmpty());
    }

    public Map<String, Object> getHints() {
        return hints;
    }

    public void setHints(Map<String, Object> hints) {
        this.hints = hints;
    }

    public boolean hasHintSpecifiers() {
        return !(hints == null || hints.isEmpty());
    }
    //</editor-fold>

}
