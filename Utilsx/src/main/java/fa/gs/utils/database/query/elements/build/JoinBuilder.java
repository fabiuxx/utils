/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.query.elements.build;

import fa.gs.utils.database.query.elements.Expression;
import fa.gs.utils.database.query.elements.Join;
import fa.gs.utils.database.query.elements.Table;
import fa.gs.utils.mixins.Self;

/**
 *
 * @author Fabio A. González Sosa
 */
public class JoinBuilder<T extends JoinBuilder<T>> implements Self<T> {

    private Join.Type type;
    private final TableBuilder table;
    private final ExpressionBuilder on;

    JoinBuilder() {
        this.type = Join.Type.NORMAL;
        this.table = TableBuilder.instance();
        this.on = ExpressionBuilder.instance();
    }

    public static JoinBuilder instance() {
        return new JoinBuilder();
    }

    public JoinBuilder type(Join.Type type) {
        this.type = type;
        return self();
    }

    public TableBuilder table() {
        return table;
    }

    public ExpressionBuilder on() {
        return on;
    }

    public Join build() {
        Join.Type type0 = type;
        Table table0 = (Table) table.build();
        Expression on0 = on.build();
        return new Join(type0, table0, on0);
    }

}
