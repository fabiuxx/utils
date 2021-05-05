/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.jpa.types.pg;

/**
 *
 * @author Fabio A. González Sosa
 */
public abstract class PgScalarType<T> extends PgType {

    private final int[] sqlTypes;

    private final Class<T> javaType;

    public PgScalarType(Class<T> javaType, int sqlType) {
        this.sqlTypes = new int[]{sqlType};
        this.javaType = javaType;
    }

    @Override
    public int[] sqlTypes() {
        return sqlTypes;
    }

    @Override
    public Class returnedClass() {
        return javaType;
    }

}
