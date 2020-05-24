/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.query.elements.build;

import fa.gs.utils.database.query.Dialect;
import fa.gs.utils.database.query.elements.Expression;
import fa.gs.utils.database.query.elements.Name;
import fa.gs.utils.database.query.elements.Table;
import fa.gs.utils.mixins.Self;

/**
 *
 * @author Fabio A. González Sosa
 * @param <T> Parametro de tipo.
 */
public class TableBuilder<T extends TableBuilder<T>> implements Self<T> {

    private final ExpressionBuilder table;
    private Name alias;

    TableBuilder() {
        this.table = ExpressionBuilder.instance();
        this.alias = null;
    }

    public static TableBuilder instance() {
        return new TableBuilder();
    }

    public ExpressionBuilder table() {
        return table;
    }

    public T as(String... parts) {
        this.alias = new Name(parts);
        return self();
    }

    public T as(Name name) {
        this.alias = name;
        return self();
    }

    public Table build(Dialect dialect) {
        Expression table0 = table.build(dialect);
        Name alias0 = alias;
        return new Table(table0, alias0);
    }

}
