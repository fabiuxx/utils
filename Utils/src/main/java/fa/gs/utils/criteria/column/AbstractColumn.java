/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.criteria.column;

/**
 *
 * @author Fabio A. González Sosa
 * @param <T>
 */
public abstract class AbstractColumn<T> implements Column<T> {

    protected final Column<?> parent;
    protected final String name;
    protected final Class<T> type;
    protected final boolean acceptsNull;

    protected AbstractColumn(Column<?> parent, String name, Class<T> type, boolean acceptsNull) {
        this.parent = parent;
        this.name = name;
        this.type = type;
        this.acceptsNull = acceptsNull;
    }

    @Override
    public Column<?> getParent() {
        return parent;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Class<T> getType() {
        return type;
    }

    @Override
    public boolean accepsNull() {
        return acceptsNull;
    }

    /**
     * {@inheritDoc }.
     */
    @Override
    public boolean accepts(Object value) {
        if (value == null) {
            return acceptsNull;
        } else {
            Class B = value.getClass();
            return classIsCompatible(B);
        }
    }

    /**
     * {@inheritDoc }.
     */
    @Override
    public boolean accepts(Object[] values) {
        if (values == null) {
            return acceptsNull;
        } else {
            Class B = values.getClass().getComponentType();
            return classIsCompatible(B);
        }
    }

    /**
     * Comprueba si el tipo de valor que acepta la columna es compatible con
     * algun otro tipo de valor.
     *
     * @param B Tipo.
     * @return {@code true} si los tipos son compatibles, caso contrario
     * {@code false}.
     */
    private boolean classIsCompatible(Class B) {
        Class A = getType();
        return A.isAssignableFrom(B);
    }

    @Override
    public String toString() {
        return getName();
    }
}
