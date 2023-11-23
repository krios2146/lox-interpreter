package krios.interpreter;

public class Interpreter implements Expr.Visitor<Object> {

    @Override
    public Object visitBinaryExpr(Expr.Binary expr) {
        Object left = evaluate(expr.getLeft());
        Object right = evaluate(expr.getRight());

        switch (expr.getOperator().getType()) {
            case GREATER -> {
                return (double) left > (double) right;
            }
            case GREATER_EQUAL -> {
                return (double) left >= (double) right;
            }
            case LESS -> {
                return (double) left < (double) right;
            }
            case LESS_EQUAL -> {
                return (double) left <= (double) right;
            }
            case BANG_EQUAL -> {
                return !isEqual(left, right);
            }
            case EQUAL_EQUAL -> {
                return isEqual(left, right);
            }
            case MINUS -> {
                return (double) left - (double) right;
            }
            case SLASH -> {
                return (double) left / (double) right;
            }
            case STAR -> {
                return (double) left * (double) right;
            }
            case PLUS -> {
                if (left instanceof Double && right instanceof Double) {
                    return (double) left + (double) right;
                }
                if (left instanceof String && right instanceof String) {
                    return (double) left + (double) right;
                }
            }
        }

        return null;
    }

    @Override
    public Object visitGroupingExpr(Expr.Grouping expr) {
        return evaluate(expr.getExpression());
    }

    @Override
    public Object visitLiteralExpr(Expr.Literal expr) {
        return expr.getValue();
    }

    @Override
    public Object visitUnaryExpr(Expr.Unary expr) {
        Object right = evaluate(expr.getRight());

        switch (expr.getOperator().getType()) {
            case MINUS -> {
                return -(double) right;
            }
            case BANG -> {
                return !isTruthy(right);
            }
        }

        return null;
    }

    private boolean isEqual(Object a, Object b) {
        if (a == null && b == null) {
            return true;
        }
        if (a == null) {
            return false;
        }
        return a.equals(b);
    }

    private boolean isTruthy(Object object) {
        if (object == null) {
            return false;
        }
        if (object instanceof Boolean) {
            return (boolean) object;
        }
        return true;
    }

    private Object evaluate(Expr expr) {
        return expr.accept(this);
    }
}
