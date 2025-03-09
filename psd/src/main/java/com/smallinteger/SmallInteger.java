package main.java.com.smallinteger;

/**
 * Класс SmallInteger представляет собой ограниченный целочисленный тип данных,
 * который позволяет выполнять основные арифметические операции в пределах фиксированного диапазона.
 * 
 * Допустимые значения находятся в диапазоне от {@value #MIN_VALUE} до {@value #MAX_VALUE}.
 */
public class SmallInteger {
    private static final int MIN_VALUE = -10000;
    private static final int MAX_VALUE = 10000;
    private final int value;

    /**
     * Исключение, выбрасываемое при выходе значения за границы допустимого диапазона.
     */
    public static class SmallIntegerOutOfRangeException extends RuntimeException {
        /**
         * Создает исключение с указанным сообщением.
         * @param message сообщение ошибки
         */
        public SmallIntegerOutOfRangeException(String message) {
            super(message);
        }
    }

    /**
     * Создает объект SmallInteger с заданным значением.
     * @param value целочисленное значение в допустимом диапазоне
     * @throws SmallIntegerOutOfRangeException если значение выходит за пределы диапазона
     */
    public SmallInteger(int value) {
        if (value < MIN_VALUE || value > MAX_VALUE) {
            throw new SmallIntegerOutOfRangeException("Value out of range: " + value);
        }
        this.value = value;
    }

    /**
     * Конструктор копирования.
     * @param other другой объект SmallInteger
     */
    public SmallInteger(SmallInteger other) {
        this.value = other.value;
    }

    /**
     * Складывает два числа SmallInteger.
     * @param arg второе слагаемое
     * @return новый объект SmallInteger, содержащий сумму
     */
    public SmallInteger add(SmallInteger arg) {
        return new SmallInteger(this.value + arg.value);
    }

    /**
     * Вычитает одно число SmallInteger из другого.
     * @param arg вычитаемое число
     * @return новый объект SmallInteger, содержащий разность
     */
    public SmallInteger subtract(SmallInteger arg) {
        return new SmallInteger(this.value - arg.value);
    }

    /**
     * Умножает два числа SmallInteger.
     * @param arg множитель
     * @return новый объект SmallInteger, содержащий произведение
     */
    public SmallInteger multiply(SmallInteger arg) {
        return new SmallInteger(this.value * arg.value);
    }

    /**
     * Делит одно число SmallInteger на другое.
     * @param arg делитель
     * @return новый объект SmallInteger, содержащий частное
     * @throws ArithmeticException если деление на ноль
     */
    public SmallInteger divide(SmallInteger arg) {
        if (arg.value == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return new SmallInteger(this.value / arg.value);
    }

    /**
     * Вычисляет остаток от деления одного SmallInteger на другое.
     * @param arg делитель
     * @return новый объект SmallInteger, содержащий остаток от деления
     * @throws ArithmeticException если деление на ноль
     */
    public SmallInteger mod(SmallInteger arg) {
        if (arg.value == 0) {
            throw new ArithmeticException("Modulo by zero");
        }
        return new SmallInteger(this.value % arg.value);
    }

    /**
     * Возвращает строковое представление числа SmallInteger.
     * @return строка, представляющая значение числа
     */
    @Override
    public String toString() {
        return String.valueOf(value);
    }

    /**
     * Возвращает значение числа SmallInteger.
     * @return значение числа
     */
    public int getValue() {
        return value;
    }
}
