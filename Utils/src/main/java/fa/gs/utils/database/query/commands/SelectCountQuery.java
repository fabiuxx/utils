/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.query.commands;

import fa.gs.utils.database.query.expressions.build.ProjectionExpressionBuilder;

/**
 *
 * @author Fabio A. González Sosa
 */
public class SelectCountQuery extends SelectQuery {

    public static final String COUNT_FIELD_NAME = "total_count";

    public SelectCountQuery() {
        super();
        super.projection().raw("count(*) as ");
    }

    @Override
    public ProjectionExpressionBuilder projection() {
        throw new UnsupportedOperationException();
    }

}
