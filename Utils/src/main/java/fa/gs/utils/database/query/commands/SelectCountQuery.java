/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.query.commands;

import fa.gs.utils.database.query.Dialect;
import fa.gs.utils.misc.text.StringBuilder2;
import fa.gs.utils.mixins.Self;

/**
 *
 * @author Fabio A. González Sosa
 */
public class SelectCountQuery implements Query, Self<SelectCountQuery> {

    public static final String COUNT_FIELD_NAME = "total_count";
    private final String materializedSelect;

    public SelectCountQuery(String materializedSelect) {
        this.materializedSelect = materializedSelect;
    }

    static Query instance(SelectQuery select) {
        // Crear una sentencia de seleccion omitiendo algunas clausulas no necesarias.
        SelectQuery transformed = new SelectQuery();
        transformed.projectionClause = select.projectionClause;
        transformed.fromClause = select.fromClause;
        transformed.joinClause = select.joinClause;
        transformed.whereClause = select.whereClause;
        transformed.groupClause = select.groupClause;
        transformed.havingClause = select.havingClause;

        // Materializar sentencia transformada.
        String sql = transformed.stringify(null);
        return new SelectCountQuery(sql);
    }

    @Override
    public String stringify(Dialect dialect) {
        StringBuilder2 builder = new StringBuilder2();
        builder.append(" SELECT ");
        builder.append("   count(tmp.*) as \"%s\"", SelectCountQuery.COUNT_FIELD_NAME);
        builder.append(" FROM ");
        builder.append("   (%s) as tmp", materializedSelect);
        return builder.toString();
    }

}
