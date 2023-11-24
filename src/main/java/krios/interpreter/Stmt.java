package krios.interpreter;

abstract class Stmt {
    abstract <T> T accept(Visitor<T> visitor);

    interface Visitor<T> {
        T visitExpressionStmt(Expression stmt);

        T visitPrintStmt(Print stmt);
    }

    static class Expression extends Stmt {

        private final Expr expression;

        Expression(Expr expression) {
            this.expression = expression;
        }

        @Override
        <T> T accept(Visitor<T> visitor) {
            return visitor.visitExpressionStmt(this);
        }

        public Expr getExpression() {
            return expression;
        }
    }

    static class Print extends Stmt {

        private final Expr expression;

        Print(Expr expression) {
            this.expression = expression;
        }

        @Override
        <T> T accept(Visitor<T> visitor) {
            return visitor.visitPrintStmt(this);
        }

        public Expr getExpression() {
            return expression;
        }
    }
}
