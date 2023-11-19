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

    public static void error(int line, String message) {
        System.err.printf("[line: %s] Error: %s", line, message);
        hasError = true;
    }

    private static void runFile(String path) throws IOException {
        byte[] fileContent = Files.readAllBytes(Paths.get(path));

        String sourceCode = new String(fileContent, defaultCharset());

        run(sourceCode);

        if (hasError) {
            System.exit(65);
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

        for (Token token : tokens) {
            System.out.println(token);
        }
    }
}