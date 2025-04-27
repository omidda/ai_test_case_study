package com.jamk.thesis.modules;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class CalculatorTest {

    private Calculator calculator;
    private static final double DELTA = 1e-10;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    @Nested
    class ArithmeticOperations {
        
        @ParameterizedTest
        @CsvSource({
            "1, 1, 2",
            "0, 0, 0",
            "-1, 1, 0"
        })
        void add_ShouldReturnExpectedResult(int a, int b, int expected) {
            assertEquals(expected, calculator.add(a, b));
        }

        @ParameterizedTest
        @CsvSource({
            "1, 1, 0",
            "0, 0, 0",
            "-1, -1, 0"
        })
        void subtract_ShouldReturnExpectedResult(int a, int b, int expected) {
            assertEquals(expected, calculator.subtract(a, b));
        }

        @ParameterizedTest
        @CsvSource({
            "2, 3, 6",
            "0, 5, 0",
            "-2, 3, -6",
            "-2, -3, 6"
        })
        void multiply_ShouldReturnExpectedResult(int a, int b, int expected) {
            assertEquals(expected, calculator.multiply(a, b));
        }

        @ParameterizedTest
        @CsvSource({
            "6, 2, 3.0",
            "5, 2, 2.5",
            "-6, 2, -3.0",
            "0, 5, 0.0"
        })
        void divide_WithValidInputs_ShouldReturnExpectedResult(int a, int b, double expected) {
            assertEquals(expected, calculator.divide(a, b), DELTA);
        }

        @Test
        void divide_WithZeroDenominator_ShouldThrowException() {
            assertThrows(IllegalArgumentException.class, () -> calculator.divide(1, 0));
        }
    }

    @Nested
    class FactorialTests {
        
        @ParameterizedTest
        @CsvSource({
            "0, 1",
            "1, 1",
            "2, 2",
            "3, 6",
            "4, 24",
            "5, 120"
        })
        void factorial_WithValidInput_ShouldReturnExpectedResult(int n, int expected) {
            assertEquals(expected, calculator.factorial(n));
        }

        @ParameterizedTest
        @ValueSource(ints = {-1, -5, -10})
        void factorial_WithNegativeInput_ShouldThrowException(int n) {
            assertThrows(IllegalArgumentException.class, () -> calculator.factorial(n));
        }
    }

    @Nested
    class FibonacciTests {
        
        @ParameterizedTest
        @CsvSource({
            "0, 0",
            "1, 1",
            "2, 1",
            "3, 2",
            "4, 3",
            "5, 5",
            "6, 8",
            "7, 13"
        })
        void fibonacci_WithValidInput_ShouldReturnExpectedResult(int n, int expected) {
            assertEquals(expected, calculator.fibonacci(n));
        }

        @ParameterizedTest
        @ValueSource(ints = {-1, -5, -10})
        void fibonacci_WithNegativeInput_ShouldThrowException(int n) {
            assertThrows(IllegalArgumentException.class, () -> calculator.fibonacci(n));
        }
    }

    @Nested
    class PowerAndLogarithmTests {
        
        @ParameterizedTest
        @CsvSource({
            "2.0, 2.0, 4.0",
            "2.0, 0.0, 1.0",
            "2.0, -2.0, 0.25",
            "0.0, 2.0, 0.0"
        })
        void power_ShouldReturnExpectedResult(double base, double exponent, double expected) {
            assertEquals(expected, calculator.power(base, exponent), DELTA);
        }

        @ParameterizedTest
        @CsvSource({
            "8.0, 2.0, 3.0",
            "1.0, 2.0, 0.0",
            "2.0, 2.0, 1.0"
        })
        void logarithm_WithValidInput_ShouldReturnExpectedResult(double value, double base, double expected) {
            assertEquals(expected, calculator.logarithm(value, base), DELTA);
        }

        @ParameterizedTest
        @CsvSource({
            "0.0, 2.0",
            "-1.0, 2.0",
            "2.0, 0.0",
            "2.0, 1.0",
            "2.0, -1.0"
        })
        void logarithm_WithInvalidInput_ShouldThrowException(double value, double base) {
            assertThrows(IllegalArgumentException.class, () -> calculator.logarithm(value, base));
        }
    }

    @Nested
    class TrigonometricTests {
        
        @ParameterizedTest
        @CsvSource({
            "0.0, 0.0",
            "1.5707963267948966, 1.0",
            "3.141592653589793, 0.0"
        })
        void sin_ShouldReturnExpectedResult(double angle, double expected) {
            assertEquals(expected, calculator.sin(angle), DELTA);
        }

        @ParameterizedTest
        @CsvSource({
            "0.0, 1.0",
            "1.5707963267948966, 0.0",
            "3.141592653589793, -1.0"
        })
        void cos_ShouldReturnExpectedResult(double angle, double expected) {
            assertEquals(expected, calculator.cos(angle), DELTA);
        }

        @Test
        void tan_ShouldReturnExpectedResult() {
            assertEquals(0.0, calculator.tan(0.0), DELTA);
            assertEquals(1.0, calculator.tan(Math.PI/4), DELTA);
        }
    }

    @Nested
    class GcdLcmTests {
        
        @ParameterizedTest
        @CsvSource({
            "12, 8, 4",
            "54, 24, 6",
            "7, 13, 1",
            "-12, 8, 4",
            "12, -8, 4"
        })
        void gcd_ShouldReturnExpectedResult(int a, int b, int expected) {
            assertEquals(expected, calculator.gcd(a, b));
        }

        @ParameterizedTest
        @CsvSource({
            "12, 8, 24",
            "54, 24, 216",
            "7, 13, 91",
            "0, 5, 0",
            "5, 0, 0"
        })
        void lcm_ShouldReturnExpectedResult(int a, int b, int expected) {
            assertEquals(expected, calculator.lcm(a, b));
        }
    }

    @Nested
    class QuadraticTests {
        
        @Test
        void solveQuadratic_WithNoRealRoots_ShouldReturnEmptyArray() {
            double[] roots = calculator.solveQuadratic(1, 0, 1); // x² + 1 = 0
            assertEquals(0, roots.length);
        }

        @Test
        void solveQuadratic_WithOneRoot_ShouldReturnSingleRoot() {
            double[] roots = calculator.solveQuadratic(1, 2, 1); // x² + 2x + 1 = 0
            assertEquals(1, roots.length);
            assertEquals(-1.0, roots[0], DELTA);
        }

        @Test
        void solveQuadratic_WithTwoRoots_ShouldReturnBothRoots() {
            double[] roots = calculator.solveQuadratic(1, 5, 6); // x² + 5x + 6 = 0
            assertEquals(2, roots.length);
            assertEquals(-2.0, roots[0], DELTA);
            assertEquals(-3.0, roots[1], DELTA);
        }

        @Test
        void solveQuadratic_WithZeroA_ShouldThrowException() {
            assertThrows(IllegalArgumentException.class, () -> calculator.solveQuadratic(0, 1, 1));
        }
    }

    @Nested
    class PermutationCombinationTests {
        
        @ParameterizedTest
        @CsvSource({
            "5, 3, 60",
            "5, 5, 120",
            "5, 0, 1",
            "0, 0, 1"
        })
        void permutation_WithValidInput_ShouldReturnExpectedResult(int n, int r, long expected) {
            assertEquals(expected, calculator.permutation(n, r));
        }

        @ParameterizedTest
        @CsvSource({
            "-1, 1",
            "1, -1",
            "1, 2"
        })
        void permutation_WithInvalidInput_ShouldThrowException(int n, int r) {
            assertThrows(IllegalArgumentException.class, () -> calculator.permutation(n, r));
        }

        @ParameterizedTest
        @CsvSource({
            "5, 3, 10",
            "5, 5, 1",
            "5, 0, 1",
            "0, 0, 1"
        })
        void combination_WithValidInput_ShouldReturnExpectedResult(int n, int r, long expected) {
            assertEquals(expected, calculator.combination(n, r));
        }

        @ParameterizedTest
        @CsvSource({
            "-1, 1",
            "1, -1",
            "1, 2"
        })
        void combination_WithInvalidInput_ShouldThrowException(int n, int r) {
            assertThrows(IllegalArgumentException.class, () -> calculator.combination(n, r));
        }
    }

    @Nested
    class PolynomialTests {
        
        @Test
        void polynomialDerivative_WithConstantPolynomial_ShouldReturnZero() {
            double[] coefficients = {5.0}; // f(x) = 5
            double[] derivative = calculator.polynomialDerivative(coefficients);
            assertArrayEquals(new double[]{0}, derivative, DELTA);
        }

        @Test
        void polynomialDerivative_WithLinearPolynomial_ShouldReturnConstant() {
            double[] coefficients = {2.0, 1.0}; // f(x) = 2x + 1
            double[] derivative = calculator.polynomialDerivative(coefficients);
            assertArrayEquals(new double[]{2.0}, derivative, DELTA);
        }

        @Test
        void polynomialDerivative_WithQuadraticPolynomial_ShouldReturnLinear() {
            double[] coefficients = {1.0, 2.0, 1.0}; // f(x) = x² + 2x + 1
            double[] derivative = calculator.polynomialDerivative(coefficients);
            assertArrayEquals(new double[]{2.0, 2.0}, derivative, DELTA);
        }

        @Test
        void polynomialDerivative_WithNullInput_ShouldThrowException() {
            assertThrows(IllegalArgumentException.class, () -> calculator.polynomialDerivative(null));
        }

        @Test
        void polynomialDerivative_WithEmptyArray_ShouldThrowException() {
            assertThrows(IllegalArgumentException.class, () -> calculator.plug-i(new double[]{}));
        }
    }
}