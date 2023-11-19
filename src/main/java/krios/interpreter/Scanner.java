package krios.interpreter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static krios.interpreter.TokenType.*;

public class Scanner {

    private static final Map<String, TokenType> keywords = new HashMap<>();

    static {
        keywords.put("class", CLASS);
        keywords.put("fun", FUN);
        keywords.put("return", RETURN);
        keywords.put("var", VAR);
        keywords.put("nil", NIL);
        keywords.put("print", PRINT);

        keywords.put("and", AND);
        keywords.put("or", OR);

        keywords.put("if", IF);
        keywords.put("else", ELSE);

        keywords.put("true", TRUE);
        keywords.put("false", FALSE);

        keywords.put("for", FOR);
        keywords.put("while", WHILE);

        keywords.put("this", THIS);
        keywords.put("super", SUPER);
    }

    private final String source;
    private final List<Token> tokens;
    private int start = 0;
    private int current = 0;
    private int line = 1;

    public Scanner(String source) {
        this.source = source;
        this.tokens = new ArrayList<>();
    }

    public List<Token> scanTokens() {
        while (!isAtEnd()) {
            start = current;
            scanToken();
        }

        tokens.add(new Token(EOF, "", null, line));
        return tokens;
    }

    private void scanToken() {
        char c = advance();

        switch (c) {
            case '(' -> addToken(LEFT_PARENTHESIS);
            case ')' -> addToken(RIGHT_PARENTHESIS);
            case '{' -> addToken(LEFT_BRACE);
            case '}' -> addToken(RIGHT_BRACE);
            case '.' -> addToken(DOT);
            case ',' -> addToken(COMMA);
            case '-' -> addToken(MINUS);
            case '+' -> addToken(PLUS);
            case ';' -> addToken(SEMICOLON);
            case '*' -> addToken(STAR);

            case '!' -> addToken(match('=') ? BANG_EQUAL : BANG);
            case '=' -> addToken(match('=') ? EQUAL_EQUAL : EQUAL);
            case '<' -> addToken(match('=') ? LESS_EQUAL : LESS);
            case '>' -> addToken(match('=') ? GREATER_EQUAL : GREATER);

            case '\n' -> line++;

            case ' ', '\t', '\r' -> {
            }

            case '/' -> {
                if (match('/')) {
                    while (peek() != '\n' && !isAtEnd()) {
                        advance();
                    }
                } else {
                    addToken(SLASH);
                }
            }

            case '"' -> string();

            default -> {
                if (isDigit(c)) {
                    number();
                    break;
                }
                if (isAlpha(c)) {
                    identifier();
                    break;
                }
                Lox.error(line, "Unexpected character.");
            }
        }
    }

    private void string() {
        while (peek() != '"' && !isAtEnd()) {
            if (peek() == '\n') {
                line++;
            }
            advance();
        }

        if (isAtEnd()) {
            Lox.error(line, "Unterminated string.");
            return;
        }

        // Pass over closing "
        advance();

        // Excluding " from the string value
        String value = source.substring(start + 1, current - 1);

        addToken(STRING, value);
    }

    private void number() {
        while (isDigit(peek())) {
            advance();
        }

        if (peek() == '.' && isDigit(peekNext())) {
            advance();

            while (isDigit(peek())) {
                advance();
            }
        }

        Double value = Double.valueOf(source.substring(start, current));
        addToken(NUMBER, value);
    }

    private void identifier() {
        while (isAlphaNumeric(peek())) {
            advance();
        }

        String text = source.substring(start, current);
        TokenType type = keywords.get(text);

        if (type == null) {
            type = IDENTIFIER;
        }
        
        addToken(type);
    }

    private char advance() {
        return source.charAt(current++);
    }

    private char peek() {
        if (isAtEnd()) {
            return '\0';
        }
        return source.charAt(current);
    }

    private char peekNext() {
        if (current + 1 >= source.length()) {
            return '\0';
        }
        return source.charAt(current + 1);
    }

    private boolean isAtEnd() {
        return current >= source.length();
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean isAlpha(char c) {
        if (c >= 'a' && c <= 'z') {
            return true;
        }
        if (c >= 'A' && c <= 'Z') {
            return true;
        }
        if (c == '_') {
            return true;
        }
        return false;
    }

    private boolean isAlphaNumeric(char c) {
        return isAlpha(c) || isDigit(c);
    }

    private boolean match(char expected) {
        if (isAtEnd()) {
            return false;
        }

        if (source.charAt(current) != expected) {
            return false;
        }

        current++;
        return true;
    }

    private void addToken(TokenType type) {
        addToken(type, null);
    }

    private void addToken(TokenType type, Object literal) {
        String text = source.substring(start, current);

        tokens.add(new Token(type, text, literal, line));
    }
}
