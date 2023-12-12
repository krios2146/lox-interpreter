package krios.interpreter;

import java.util.List;

public class LoxFunction implements LoxCallable {

    private final Stmt.Function declaration;

    public LoxFunction(Stmt.Function declaration) {
        this.declaration = declaration;
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {
        Environment environment = new Environment(interpreter.getGlobals());

        for (int i = 0; i < declaration.getParams().size(); i++) {
            environment.define(
                    declaration.getParams().get(i).getLexeme(),
                    arguments.get(i)
            );
        }

        interpreter.executeBlock(declaration.getBody(), environment);
        return null;
    }

    @Override
    public int arity() {
        return declaration.getParams().size();
    }

    @Override
    public String toString() {
        return "<fn " + declaration.getName().getLexeme() + ">";
    }
}
