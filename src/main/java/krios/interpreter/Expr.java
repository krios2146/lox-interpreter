package krios.interpreter;

abstract class Expr {
    abstract <T> T accept(Visitor<T> visitor);

    interface Visitor<T> {
        T visitBinaryExpr(Binary expr);

        T visitGroupingExpr(Grouping expr);

        T visitLiteralExpr(Literal expr);

        T visitUnaryExpr(Unary expr);
    }

    static class Binary extends Expr {

        private final Expr left;
        private final Token operator;
        private final Expr right;

        Binary(Expr left, Token operator, Expr right) {
            this.left = left;
            this.operator = operator;
            this.right = right;
        }

        @Override
        <T> T accept(Visitor<T> visitor) {
            return visitor.visitBinaryExpr(this);
        }
    }

    static class Grouping extends Expr {

        private final Expr expression;

        Grouping(Expr expression) {
            this.expression = expression;
        }

        @Override
        <T> T accept(Visitor<T> visitor) {
            return visitor.visitGroupingExpr(this);
        }
    }

    static class Literal extends Expr {

        private final Object value;

        Literal(Object value) {
            this.value = value;
        }

        @Override
        <T> T accept(Visitor<T> visitor) {
            return visitor.visitLiteralExpr(this);
        }
    }

    static class Unary extends Expr {

        private final Token operator;
        private final Expr right;

        Unary(Token operator, Expr right) {
            this.operator = operator;
            this.right = right;
        }

        @Override
        <T> T accept(Visitor<T> visitor) {
            return visitor.visitUnaryExpr(this);
        }
    }
}
