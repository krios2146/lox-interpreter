package krios.interpreter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static java.nio.charset.Charset.defaultCharset;

public class Lox {

    private static boolean hasError;
    private static boolean hasRuntimeError;

    public static void main(String[] args) throws IOException {
        if (args.length > 1) {
            System.out.println("Usage: jlox [script]");
            System.exit(64);
        }

        if (args.length == 1) {
            runFile(args[0]);
        } else {
            runPrompt();
        }
    }

    public static void runtimeError(RuntimeError error) {
        System.err.printf("%s \n[line: %d]\n", error.getMessage(), error.getToken().getLine());
        hasRuntimeError = true;
    }

    public static void error(int line, String message) {
        System.err.printf("[line: %s] Error: %s \n", line, message);
        hasError = true;
    }

    static void error(Token token, String message) {
        if (token.getType() == TokenType.EOF) {
            error(token.getLine(), " at end " + message);
        } else {
            error(token.getLine(), " at '" + token.getLexeme() + "' " + message);
        }
    }

    private static void runFile(String path) throws IOException {
        byte[] fileContent = Files.readAllBytes(Paths.get(path));

        String sourceCode = new String(fileContent, defaultCharset());

        run(sourceCode);

        if (hasError) {
            System.exit(65);
        }
        if (hasRuntimeError) {
            System.exit(70);
        }
    }

    private static void runPrompt() throws IOException {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        for (; ; ) {
            System.out.print("> ");
            String line = reader.readLine();

            if (line == null) {
                break;
            }

            run(line);

            hasError = false;
        }
    }

    private static void run(String sourceCode) {
        Scanner scanner = new Scanner(sourceCode);
        List<Token> tokens = scanner.scanTokens();

        Parser parser = new Parser(tokens);
        Expr expression = parser.parse();

        if (hasError) {
            return;
        }

        System.out.println(new AstPrinter().print(expression));
    }
}