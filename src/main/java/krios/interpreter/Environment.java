package krios.interpreter;

import java.util.HashMap;
import java.util.Map;

public class Environment {

    private final Environment enclosing;
    private final Map<String, Object> values;

    public Environment() {
        this.enclosing = null;
        this.values = new HashMap<>();
    }

    public Environment(Environment enclosing) {
        this.enclosing = enclosing;
        this.values = new HashMap<>();
    }

    public void define(String name, Object value) {
        values.put(name, value);
    }

    public Object get(Token name) {
        if (values.containsKey(name.getLexeme())) {
            return values.get(name.getLexeme());
        }

        if (enclosing != null) {
            return enclosing.get(name);
        }

        throw new RuntimeError(name, "Undefined variable '" + name.getLexeme() + "'");
    }

    public void assign(Token name, Object value) {
        if (values.containsKey(name.getLexeme())) {
            values.put(name.getLexeme(), value);
            return;
        }

        if (enclosing != null) {
            enclosing.assign(name, value);
            return;
        }

        throw new RuntimeError(name, "Undefined variable '" + name.getLexeme() + "'");
    }
}
