package src.main.java.com.smallinteger;

import java.util.Scanner;

/**
 * Консольный калькулятор, вычисляющий выражения с использованием SmallInteger.
 * Поддерживает операторы +, -, *, /, %, а также скобки для приоритетов.
 * Для очистки экрана можно ввести "cls" или "clear".
 * Для выхода из программы введите "exit".
 */
public class SmallIntegerCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ExpressionEvaluator evaluator = new ExpressionEvaluator();

        System.out.println("Enter an expression (e.g., (5 + 3) * 2):");

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("cls") || input.equalsIgnoreCase("clear")) {
                clearConsole();
                continue;
            }

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Exiting...");
                break;
            }

            try {
                SmallInteger result = evaluator.evaluate(input);
                System.out.println("= " + result);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        scanner.close();
    }

    /**
     * Очищает консоль в зависимости от операционной системы.
     */
    private static void clearConsole() {
        String os = System.getProperty("os.name").toLowerCase();
        try {
            if (os.contains("win")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
