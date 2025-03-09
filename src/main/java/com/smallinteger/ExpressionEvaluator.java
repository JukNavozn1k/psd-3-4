package src.main.java.com.smallinteger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс для разбора и вычисления арифметических выражений с использованием SmallInteger.
 */
public class ExpressionEvaluator {
    private static final Pattern TOKEN_PATTERN = Pattern.compile("\\s*([()*/%]|\\d+|[-+])\\s*");
    private static final String VALID_TOKENS = "0123456789()+-*/%"; // Разрешённые символы

    private int pos;
    private String[] tokens;

    /**
     * Вычисляет значение арифметического выражения.
     *
     * @param input строка с арифметическим выражением
     * @return результат вычисления в виде SmallInteger
     * @throws IllegalArgumentException если выражение некорректно
     */
    public SmallInteger evaluate(String input) {
        validateCharacters(input);
        tokenizeExpression(input);
        return parseExpression();
    }

    /**
     * Проверяет, содержит ли выражение недопустимые символы.
     *
     * @param input строка с выражением
     * @throws IllegalArgumentException если найден недопустимый символ
     */
    private void validateCharacters(String input) {
        for (char c : input.toCharArray()) {
            if (VALID_TOKENS.indexOf(c) == -1 && !Character.isWhitespace(c)) {
                throw new IllegalArgumentException("Invalid character: " + c);
            }
        }
    }

    /**
     * Разбирает выражение на токены, учитывая неявное умножение.
     *
     * @param input строка с выражением
     */
    private void tokenizeExpression(String input) {
        Matcher matcher = TOKEN_PATTERN.matcher(input);
        StringBuilder parsedInput = new StringBuilder();
        String prevToken = "";

        while (matcher.find()) {
            String token = matcher.group(1);

            // Обрабатываем неявное умножение
            if (!prevToken.isEmpty() && (
                    (isNumber(prevToken) && token.equals("(")) ||
                    (prevToken.equals(")") && isNumber(token)) ||
                    (prevToken.equals(")") && token.equals("("))
            )) {
                parsedInput.append("* ");
            }

            parsedInput.append(token).append(" ");
            prevToken = token;
        }

        tokens = parsedInput.toString().trim().split(" ");
        pos = 0;
    }

    /**
     * Проверяет, является ли строка числом.
     *
     * @param s строка
     * @return true, если строка является числом, иначе false
     */
    private boolean isNumber(String s) {
        return s.matches("\\d+");
    }

    /**
     * Парсит и вычисляет выражение, учитывая приоритет операторов.
     *
     * @return вычисленный результат в виде SmallInteger
     */
    private SmallInteger parseExpression() {
        SmallInteger result = parseTerm();
        while (pos < tokens.length && (tokens[pos].equals("+") || tokens[pos].equals("-"))) {
            String operator = tokens[pos++];
            SmallInteger nextTerm = parseTerm();
            result = applyOperator(result, nextTerm, operator);
        }
        return result;
    }

    /**
     * Парсит и вычисляет терм (умножение, деление, остаток от деления).
     *
     * @return вычисленный терм
     */
    private SmallInteger parseTerm() {
        SmallInteger result = parseFactor();
        while (pos < tokens.length && (tokens[pos].equals("*") || tokens[pos].equals("/") || tokens[pos].equals("%"))) {
            String operator = tokens[pos++];
            SmallInteger nextFactor = parseFactor();
            result = applyOperator(result, nextFactor, operator);
        }
        return result;
    }

    /**
     * Парсит фактор (число или выражение в скобках).
     *
     * @return вычисленный фактор
     * @throws IllegalArgumentException если выражение некорректно
     */
    private SmallInteger parseFactor() {
        if (pos >= tokens.length) {
            throw new IllegalArgumentException("Unexpected end of expression");
        }

        String token = tokens[pos++];

        if (token.equals("(")) {
            SmallInteger result = parseExpression();
            if (pos >= tokens.length || !tokens[pos].equals(")")) {
                throw new IllegalArgumentException("Expected closing parenthesis at position " + pos);
            }
            pos++; // Пропускаем закрывающую скобку
            return result;
        } else if (token.equals("-")) {
            return new SmallInteger(parseFactor().getValue() * -1);
        } else if (token.equals("+")) {
            return parseFactor();
        } else {
            return new SmallInteger(Integer.parseInt(token));
        }
    }

    /**
     * Применяет оператор к двум значениям SmallInteger.
     *
     * @param left  левый операнд
     * @param right правый операнд
     * @param operator оператор (+, -, *, /, %)
     * @return результат применения оператора
     * @throws IllegalStateException если оператор неизвестен
     */
    private SmallInteger applyOperator(SmallInteger left, SmallInteger right, String operator) {
        switch (operator) {
            case "+":
                return left.add(right);
            case "-":
                return left.subtract(right);
            case "*":
                return left.multiply(right);
            case "/":
                return left.divide(right);
            case "%":
                return left.mod(right);
            default:
                throw new IllegalStateException("Unknown operator: " + operator);
        }
    }
}
