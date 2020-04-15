/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.query.expressions.build;

import fa.gs.utils.database.query.expressions.BinaryExpression;
import fa.gs.utils.database.query.expressions.Expression;
import fa.gs.utils.database.query.expressions.LiteralExpression;
import fa.gs.utils.database.query.expressions.NameExpression;
import fa.gs.utils.database.query.expressions.Operator;
import fa.gs.utils.database.query.expressions.Operators;
import fa.gs.utils.database.query.expressions.UnaryExpression;
import fa.gs.utils.database.query.expressions.literals.Literal;
import fa.gs.utils.database.query.expressions.names.Name;
import fa.gs.utils.misc.errors.Errors;
import java.util.Stack;

/**
 *
 * @author Fabio A. González Sosa
 */
public abstract class StackBasedExpressionBuilder implements ExpressionBuilder {

    protected final Stack<Expression> output;

    protected final Stack<Operator> operators;

    public StackBasedExpressionBuilder() {
        this.output = new Stack<>();
        this.operators = new Stack<>();
    }

    protected void pushLiteral(Literal literal) {
        Expression exp = new LiteralExpression(literal);
        pushExpression(exp);
    }

    protected Literal popLiteral() {
        Expression exp = popExpression();
        if (exp instanceof LiteralExpression) {
            return ((LiteralExpression) exp).literal();
        }

        throw Errors.illegalArgument();
    }

    protected void pushName(Name name) {
        Expression exp = new NameExpression(name);
        pushExpression(exp);
    }

    protected Name popName() {
        Expression exp = popExpression();
        if (exp instanceof NameExpression) {
            return ((NameExpression) exp).name();
        }

        throw Errors.illegalArgument();
    }

    protected void pushExpression(Expression exp) {
        push0(exp);
    }

    protected Expression popExpression() {
        return output.pop();
    }

    protected void pushOperator(Operator op) {
        push0(op);
    }

    protected Operator popOperator() {
        return operators.pop();
    }

    protected void push0(Expression expression) {
        if (expression != null) {
            output.push(expression);
        }
    }

    protected void push0(Operator operator) {
        // Control de pila vacia.
        if (operators.isEmpty()) {
            operators.push(operator);
            return;
        }

        // Se abre parentesis.
        if (operator.equals(Operators.LEFT_PAR)) {
            operators.push(operator);
            return;
        }

        // Se cierra parentesis.
        if (operator.equals(Operators.RIGHT_PAR)) {
            while (true) {
                if (operators.isEmpty()) {
                    break;
                }

                Operator previous = popOperator();
                if (previous.equals(Operators.LEFT_PAR)) {
                    break;
                }

                consumeOperator(previous);
            }
            return;
        }

        // Operador anterior.
        Operator previous = operators.peek();

        // 1. Operador actual tiene mayor precedencia.
        if (operator.comparePrecedence(previous) > 0) {
            operators.push(operator);
            return;
        }

        // 2. Operador actual tiene igual precedencia.
        if (operator.comparePrecedence(previous) == 0) {
            // TODO: IMPLEMENTAR ASOCIATIVIDAD.
            // http://csis.pace.edu/~wolf/CS122/infix-postfix.htm
            consumeOperator(operator);
            return;
        }

        // 3. Operador actual tiene menor precedencia.
        if (operator.comparePrecedence(previous) < 0) {
            // Hacer pop de operador anterior.
            if (!previous.equals(Operators.LEFT_PAR)) {
                popOperator();
                consumeOperator(previous);
            }
            operators.push(operator);
            return;
        }
    }

    protected void consumeStack() {
        while (!operators.isEmpty()) {
            Operator operator = popOperator();
            consumeOperator(operator);
        }
    }

    /**
     * Consume un operador en la pila de operadores, generando un nodo de
     * expresion si fuera necesario.
     *
     * @param operator Operador a procesar.
     */
    protected void consumeOperator(Operator operator) {
        // Operador unario generico.
        if (operator.cardinality() == Operators.Cardinalities.UNARY) {
            Expression a = popExpression();
            Expression e = new UnaryExpression(operator, a);
            output.push(e);
            return;
        }

        // Operador binario generico.
        if (operator.cardinality() == Operators.Cardinalities.BINARY) {
            Expression b = popExpression();
            Expression a = popExpression();
            Expression e = new BinaryExpression(a, operator, b);
            output.push(e);
            return;
        }
    }

}
