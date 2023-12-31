package krios.interpreter;

import java.util.List;

abstract class Stmt {
    abstract <T> T accept(Visitor<T> visitor);

    interface Visitor<T> {
        T visitExpressionStmt(Expression stmt);

        T visitPrintStmt(Print stmt);

        T visitReturnStmt(Return aReturn);

        T visitVarStmt(Var stmt);

        T visitBlockStmt(Block stmt);

        T visitIfStmt(If stmt);

        T visitWhileStmt(While stmt);

        T visitFunctionStmt(Function function);
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

    static class Return extends Stmt {

        private final Token keyword;
        private final Expr value;

        Return(Token keyword, Expr value) {
            this.keyword = keyword;
            this.value = value;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitReturnStmt(this);
        }

        public Token getKeyword() {
            return keyword;
        }

        public Expr getValue() {
            return value;
        }
    }

    static class Var extends Stmt {

        private final Token name;
        private final Expr initializer;

        Var(Token name, Expr initializer) {
            this.name = name;
            this.initializer = initializer;
        }

        @Override
        <T> T accept(Visitor<T> visitor) {
            return visitor.visitVarStmt(this);
        }

        public Token getName() {
            return name;
        }

        public Expr getInitializer() {
            return initializer;
        }

    }

    static class Block extends Stmt {

        private final List<Stmt> statements;

        Block(List<Stmt> statements) {
            this.statements = statements;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitBlockStmt(this);
        }

        public List<Stmt> getStatements() {
            return statements;
        }

    }

    static class If extends Stmt {

        private final Expr condition;
        private final Stmt thenBranch;
        private final Stmt elseBranch;

        If(Expr condition, Stmt thenBranch, Stmt elseBranch) {
            this.condition = condition;
            this.thenBranch = thenBranch;
            this.elseBranch = elseBranch;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitIfStmt(this);
        }

        public Expr getCondition() {
            return condition;
        }

        public Stmt getThenBranch() {
            return thenBranch;
        }

        public Stmt getElseBranch() {
            return elseBranch;
        }

    }

    static class While extends Stmt {

        private final Expr condition;
        private final Stmt body;

        While(Expr condition, Stmt body) {
            this.condition = condition;
            this.body = body;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitWhileStmt(this);
        }

        public Expr getCondition() {
            return condition;
        }

        public Stmt getBody() {
            return body;
        }

    }

    static class Function extends Stmt {

        private final Token name;
        private final List<Token> params;
        private final List<Stmt> body;

        Function(Token name, List<Token> params, List<Stmt> body) {
            this.name = name;
            this.params = params;
            this.body = body;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitFunctionStmt(this);
        }

        public Token getName() {
            return name;
        }

        public List<Token> getParams() {
            return params;
        }

        public List<Stmt> getBody() {
            return body;
        }
    }
}
