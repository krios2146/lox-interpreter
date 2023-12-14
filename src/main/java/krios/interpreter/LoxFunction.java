package krios.interpreter;

import java.util.List;

public class LoxFunction implements LoxCallable {

    private final Stmt.Function declaration;
    private final Environment closure;

    public LoxFunction(Stmt.Function declaration, Environment closure) {
        this.declaration = declaration;
        this.closure = closure;
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {
        Environment environment = new Environment(closure);

        for (int i = 0; i < declaration.getParams().size(); i++) {
            environment.define(
                    declaration.getParams().get(i).getLexeme(),
                    arguments.get(i)
            );
        }

        try {
            interpreter.executeBlock(declaration.getBody(), environment);
        }
        catch (Return returnValue) {
            return returnValue.getValue();
        }

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
