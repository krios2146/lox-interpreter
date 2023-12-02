package krios.interpreter;

import java.util.List;

abstract class Stmt {
    abstract <T> T accept(Visitor<T> visitor);

    interface Visitor<T> {
        T visitExpressionStmt(Expression stmt);

        T visitPrintStmt(Print stmt);

        T visitVarStmt(Var stmt);

        T visitBlockStmt(Block block);

        T visitIfStmt(If anIf);
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
}
