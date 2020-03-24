/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.query.commands;

import fa.gs.utils.collections.Lists;
import fa.gs.utils.database.query.Dialect;
import fa.gs.utils.database.query.expressions.EmptyExpression;
import fa.gs.utils.database.query.expressions.Expression;
import fa.gs.utils.database.query.expressions.JoinExpression;
import fa.gs.utils.database.query.expressions.build.ConditionsExpressionBuilder;
import fa.gs.utils.database.query.expressions.build.JoinExpressionBuilder;
import fa.gs.utils.database.query.expressions.build.OrderExpressionBuilder;
import fa.gs.utils.database.query.expressions.build.ProjectionExpressionBuilder;
import fa.gs.utils.database.query.expressions.build.TableExpressionBuilder;
import fa.gs.utils.misc.Assertions;
import fa.gs.utils.misc.text.Joiner;
import fa.gs.utils.misc.text.StringBuilder2;
import fa.gs.utils.mixins.Self;
import java.util.Collection;

/**
 *
 * @author Fabio A. González Sosa
 */
public class SelectQuery implements Query, Self<SelectQuery> {

    private final Collection<ProjectionExpressionBuilder> projectionClause;
    private final TableExpressionBuilder fromClause;
    private final Collection<JoinExpressionBuilder> joinClause;
    private final ConditionsExpressionBuilder whereClause;
    private final Collection<ConditionsExpressionBuilder> groupClause;
    private final Collection<OrderExpressionBuilder> orderClause;
    private final ConditionsExpressionBuilder havingClause;
    private Long limit;
    private Long offset;

    public SelectQuery() {
        this.projectionClause = Lists.empty();
        this.fromClause = TableExpressionBuilder.instance();
        this.joinClause = Lists.empty();
        this.whereClause = ConditionsExpressionBuilder.instance();
        this.groupClause = Lists.empty();
        this.havingClause = ConditionsExpressionBuilder.instance();
        this.orderClause = Lists.empty();
        this.limit = null;
        this.offset = null;
    }

    public ProjectionExpressionBuilder projection() {
        ProjectionExpressionBuilder builder = ProjectionExpressionBuilder.instance();
        projectionClause.add(builder);
        return builder;
    }

    public TableExpressionBuilder from() {
        return fromClause;
    }

    public JoinExpressionBuilder join() {
        JoinExpressionBuilder builder = JoinExpressionBuilder.instance();
        joinClause.add(builder);
        return builder;
    }

    public JoinExpressionBuilder join(JoinExpression.Type type) {
        JoinExpressionBuilder builder = join();
        builder.type(type);
        return builder;
    }

    public JoinExpressionBuilder joinLeft() {
        return join(JoinExpression.Type.LEFT);
    }

    public JoinExpressionBuilder joinRight() {
        return join(JoinExpression.Type.RIGHT);
    }

    public ConditionsExpressionBuilder where() {
        return whereClause;
    }

    public ConditionsExpressionBuilder group() {
        ConditionsExpressionBuilder builder = ConditionsExpressionBuilder.instance();
        groupClause.add(builder);
        return builder;
    }

    public ConditionsExpressionBuilder having() {
        return havingClause;
    }

    public OrderExpressionBuilder order() {
        OrderExpressionBuilder builder = OrderExpressionBuilder.instance();
        orderClause.add(builder);
        return builder;
    }

    public SelectQuery limit(Long limit) {
        this.limit = limit;
        return self();
    }

    public SelectQuery offset(Long offset) {
        this.offset = offset;
        return self();
    }

    @Override
    public String stringify(final Dialect dialect) {
        StringBuilder2 builder = new StringBuilder2();

        // Projections.
        builder.append(" SELECT ");
        if (!Assertions.isNullOrEmpty(projectionClause)) {
            String[] projections = projectionClause.stream()
                    .filter(c -> c != null)
                    .map(c -> c.build())
                    .filter(e -> e != null)
                    .filter(e -> !(e instanceof EmptyExpression))
                    .map(e -> e.stringify(dialect))
                    .distinct()
                    .toArray(String[]::new);
            if (!Assertions.isNullOrEmpty(projections)) {
                builder.append(Joiner.of(projections).separator(", ").join());
            }
        }

        // From.
        if (!Assertions.isNull(fromClause)) {
            Expression fromExpression = fromClause.build();
            if (fromExpression != null && (fromExpression instanceof EmptyExpression) == false) {
                builder.append(" FROM ");
                builder.append(fromExpression.stringify(dialect));
            }
        }

        // Joins.
        if (!Assertions.isNullOrEmpty(joinClause)) {
            String[] joins = joinClause.stream()
                    .filter(c -> c != null)
                    .map(c -> c.build())
                    .filter(e -> e != null)
                    .filter(e -> !(e instanceof EmptyExpression))
                    .map(e -> e.stringify(dialect))
                    .distinct()
                    .toArray(String[]::new);
            if (!Assertions.isNullOrEmpty(joins)) {
                builder.append(" ");
                builder.append(Joiner.of(joins).separator(" ").join());
            }
        }

        // Where.
        if (!Assertions.isNull(whereClause)) {
            Expression whereExpression = whereClause.build();
            if (whereExpression != null && (whereExpression instanceof EmptyExpression) == false) {
                builder.append(" WHERE ");
                builder.append(whereExpression.stringify(dialect));
            }
        }

        // Group by.
        if (!Assertions.isNullOrEmpty(groupClause)) {
            String[] groupings = groupClause.stream()
                    .filter(c -> c != null)
                    .map(c -> c.build())
                    .filter(e -> e != null)
                    .filter(e -> !(e instanceof EmptyExpression))
                    .map(e -> e.stringify(dialect))
                    .distinct()
                    .toArray(String[]::new);
            if (!Assertions.isNullOrEmpty(groupings)) {
                builder.append(" GROUP BY ");
                builder.append(Joiner.of(groupings).separator(", ").join());
            }
        }

        // Having.
        if (!Assertions.isNull(havingClause)) {
            Expression havingExpression = havingClause.build();
            if (havingExpression != null && (havingExpression instanceof EmptyExpression) == false) {
                builder.append(" HAVING ");
                builder.append(havingExpression.stringify(dialect));
            }
        }

        // Order by.
        if (!Assertions.isNullOrEmpty(orderClause)) {
            String[] groupings = orderClause.stream()
                    .filter(c -> c != null)
                    .map(c -> c.build())
                    .filter(e -> e != null)
                    .filter(e -> !(e instanceof EmptyExpression))
                    .map(e -> e.stringify(dialect))
                    .distinct()
                    .toArray(String[]::new);
            if (!Assertions.isNullOrEmpty(groupings)) {
                builder.append(" ORDER BY ");
                builder.append(Joiner.of(groupings).separator(", ").join());
            }
        }

        // Limit.
        if (limit != null) {
            builder.append(" LIMIT ");
            builder.append(limit);
        }

        // Offset.
        if (offset != null) {
            builder.append(" OFFSET ");
            builder.append(offset);
        }

        builder.append(" ; ");

        return builder.toString();
    }

}
