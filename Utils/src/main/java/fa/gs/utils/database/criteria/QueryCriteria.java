/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.criteria;

import fa.gs.utils.collections.Lists;
import fa.gs.utils.collections.Maps;
import fa.gs.utils.database.criteria.column.Column;
import fa.gs.utils.database.sql.build.Conditions;
import fa.gs.utils.mixins.Self;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 *
 * @author Fabio A. González Sosa
 * @param <T> Parametro de tipo.
 */
public class QueryCriteria<T extends QueryCriteria<T>> implements Serializable, Self<T> {

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    /**
     * Tipo de consulta. Los tipos soportados son:
     * <ul>
     * <li>{@link QueryKind#SELECT SELECT} Para consultas de seleccion de
     * datos.</li>
     * <li>{@link QueryKind#COUNT COUNT} Para consultas de conteo de
     * registros.</li>
     * </ul>
     */
    protected QueryKind kind;

    /**
     * Proyeccion de origen para datos.
     */
    protected Projection from;

    /**
     * Coleccion de proyeccion de columnas.
     */
    protected final Collection<Projection> projections;

    /**
     * Coleccion de clausulas de union de tablas.
     */
    protected final Collection<Join> joins;

    /**
     * Coleccion de criterios de filtrado.
     */
    protected Collection<Condition> filters;

    /**
     * Coleccion de criterios de ordenacion.
     */
    protected Collection<Sorting> sorts;

    /**
     * Coleccion de criterios de agrupacion.
     */
    protected final Collection<Grouping> groupings;

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
     * Campos de "ayuda" para el gestor de sentencias SQL.
     */
    protected Map<String, Object> hints;
    //</editor-fold>

    /**
     * Constructor.
     *
     * @param kind Tipo de consulta.
     */
    private QueryCriteria(QueryKind kind) {
        this.kind = kind;
        this.from = null;
        this.projections = Lists.empty();
        this.joins = Lists.empty();
        this.filters = Lists.empty();
        this.sorts = Lists.empty();
        this.groupings = Lists.empty();
        this.limit = null;
        this.offset = null;
        this.hints = Maps.empty();
    }

    /**
     * Inicializador estatico para consultas de tipo COUNT.
     *
     * @return Nueva instancia.
     */
    public static QueryCriteria count() {
        return new QueryCriteria(QueryKind.COUNT);
    }

    /**
     * Inicializador estatico para consultas de tipo COUNT.
     *
     * @return Nueva instancia.
     */
    public static QueryCriteria select() {
        return new QueryCriteria(QueryKind.SELECT);
    }

    public QueryCriteria from(Projection value) {
        this.from = value;
        return self();
    }

    public QueryCriteria projection(Projection value) {
        this.projections.add(value);
        return self();
    }

    public QueryCriteria projection(Collection<Projection> values) {
        this.projections.addAll(values);
        return self();
    }

    public QueryCriteria join(Join value) {
        this.joins.add(value);
        return self();
    }

    public QueryCriteria join(Collection<Join> value) {
        this.joins.addAll(value);
        return self();
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
     * @deprecated usar {@link Conditions conditions}.
     * @param column Descriptor de columna.
     * @param operator Operador.
     * @param value Valor de filtro.
     * @return Esta misma instancia.
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
     * @deprecated usar {@link Conditions conditions}.
     * @param column Descriptor de columna.
     * @param operator Operador.
     * @param value Valores de filtro.
     * @return Esta misma instancia.
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
     * @deprecated usar {@link Conditions conditions}.
     * @param column Descriptor de columna.
     * @param order Tipo de ordenamiento.
     * @return Esta misma instancia.
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

    /**
     * Establece criterios de agrupacion de registros.
     *
     * @param grouping Criterio de agrupacion.
     * @return Esta misma instancia.
     */
    public QueryCriteria group(Grouping grouping) {
        this.groupings.add(grouping);
        return self();
    }

    //<editor-fold defaultstate="collapsed" desc="Getters y Setters">
    public QueryKind getKind() {
        return kind;
    }

    public Projection getFrom() {
        return from;
    }

    public Collection<Projection> getProjections() {
        return projections;
    }

    public Collection<Join> getJoins() {
        return joins;
    }

    public Collection<Sorting> getSorts() {
        return sorts;
    }

    public Collection<Condition> getFilters() {
        return filters;
    }

    public Collection<Grouping> getGroupings() {
        return groupings;
    }

    public Integer getLimit() {
        return limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public Map<String, Object> getHints() {
        return hints;
    }

    public void setKind(QueryKind kind) {
        this.kind = kind;
    }

    public void setFrom(Projection from) {
        this.from = from;
    }

    public void setSorts(Collection<Sorting> sorts) {
        this.sorts = sorts;
    }

    public void setFilters(Collection<Condition> filters) {
        this.filters = filters;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public void setHints(Map<String, Object> hints) {
        this.hints = hints;
    }
    //</editor-fold>

}
