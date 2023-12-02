package krios.interpreter;

abstract class Expr {
    abstract <T> T accept(Visitor<T> visitor);

    interface Visitor<T> {
        T visitBinaryExpr(Binary expr);

        T visitGroupingExpr(Grouping expr);

        T visitLiteralExpr(Literal expr);

        T visitUnaryExpr(Unary expr);

        T visitVariableExpr(Variable expr);

        T visitAssignExpr(Assign assign);

        T visitLogicalExpr(Logical logical);
    }

    static class Logical extends Expr {

        private final Expr left;
        private final Token operator;
        private final Expr right;

        Logical(Expr left, Token operator, Expr right) {
            this.left = left;
            this.operator = operator;
            this.right = right;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitLogicalExpr(this);
        }

        public Expr getLeft() {
            return left;
        }

        public Token getOperator() {
            return operator;
        }

        public Expr getRight() {
            return right;
        }
    }

    static class Assign extends Expr {

        private final Token name;
        private final Expr value;

        Assign(Token name, Expr value) {
            this.name = name;
            this.value = value;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitAssignExpr(this);
        }

        public Token getName() {
            return name;
        }

        public Expr getValue() {
            return value;
        }
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

        public Expr getLeft() {
            return left;
        }

        public Token getOperator() {
            return operator;
        }

        public Expr getRight() {
            return right;
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

        public Expr getExpression() {
            return expression;
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

        public Object getValue() {
            return value;
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

        public Token getOperator() {
            return operator;
        }

        public Expr getRight() {
            return right;
        }
    }

    static class Variable extends Expr {

        private final Token name;

        Variable(Token name) {
            this.name = name;
        }

        @Override
        <T> T accept(Visitor<T> visitor) {
            return visitor.visitVariableExpr(this);
        }

        public Token getName() {
            return name;
        }
    }
}
