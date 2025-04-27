package com.jamk.thesis.modules;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Calculator Class Tests")
class CalculatorTest {

    private Calculator calculator;
    private static final double DELTA = 1e-9; // Precision for double comparisons

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    @Nested
    @DisplayName("Basic Arithmetic Tests")
    class BasicArithmeticTests {

        @ParameterizedTest
        @CsvSource({"5, 3, 8", "-2, 7, 5", "0, 0, 0", "-5, -3, -8"})
        @DisplayName("add should return correct sum")
        void add_shouldReturnCorrectSum(int a, int b, int expected) {
            assertEquals(expected, calculator.add(a, b));
        }

        @ParameterizedTest
        @CsvSource({"5, 3, 2", "3, 5, -2", "0, 0, 0", "-5, -3, -2", "-3, 5, -8"})
        @DisplayName("subtract should return correct difference")
        void subtract_shouldReturnCorrectDifference(int a, int b, int expected) {
            assertEquals(expected, calculator.subtract(a, b));
        }

        @ParameterizedTest
        @CsvSource({"5, 3, 15", "-2, 7, -14", "0, 5, 0", "-5, -3, 15", "10, 0, 0"})
        @DisplayName("multiply should return correct product")
        void multiply_shouldReturnCorrectProduct(int a, int b, int expected) {
            assertEquals(expected, calculator.multiply(a, b));
        }

        @ParameterizedTest
        @CsvSource({"10, 2, 5.0", "-10, 2, -5.0", "5, 2, 2.5", "0, 5, 0.0", "10, -4, -2.5"})
        @DisplayName("divide should return correct quotient")
        void divide_whenDivisorIsNotZero_shouldReturnQuotient(int a, int b, double expected) {
            assertEquals(expected, calculator.divide(a, b), DELTA);
        }

        @Test
        @DisplayName("divide should throw IllegalArgumentException for division by zero")
        void divide_whenDivisorIsZero_shouldThrowIllegalArgumentException() {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                calculator.divide(10, 0);
            });
            assertEquals("Divider cannot be zero.", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Factorial Tests")
    class FactorialTests {

        @ParameterizedTest
        @CsvSource({"0, 1", "1, 1", "5, 120", "10, 3628800"})
        @DisplayName("factorial should return correct value for non-negative integers")
        void factorial_whenNonNegative_shouldReturnCorrectValue(int n, int expected) {
            assertEquals(expected, calculator.factorial(n));
        }

        @Test
        @DisplayName("factorial should throw IllegalArgumentException for negative input")
        void factorial_whenNegative_shouldThrowIllegalArgumentException() {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                calculator.factorial(-1);
            });
            assertEquals("Negative number not allowed.", exception.getMessage());
        }
        // Note: Factorial grows very fast. Testing large numbers might lead to integer overflow.
        // Test for a reasonably large value that fits in int.
        @Test
        @DisplayName("factorial should handle moderately large input (e.g., 12)")
        void factorial_whenModeratelyLargeInput() {
            assertEquals(479001600, calculator.factorial(12)); // 13! overflows standard int
        }
    }

    @Nested
    @DisplayName("Fibonacci Tests")
    class FibonacciTests {

        @ParameterizedTest
        @CsvSource({"0, 0", "1, 1", "2, 1", "3, 2", "10, 55", "20, 6765"})
        @DisplayName("fibonacci should return correct value for non-negative integers")
        void fibonacci_whenNonNegative_shouldReturnCorrectValue(int n, int expected) {
            assertEquals(expected, calculator.fibonacci(n));
        }

        @Test
        @DisplayName("fibonacci should throw IllegalArgumentException for negative input")
        void fibonacci_whenNegative_shouldThrowIllegalArgumentException() {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                calculator.fibonacci(-1);
            });
            assertEquals("Negative index not allowed.", exception.getMessage());
        }
         // Note: Recursive Fibonacci is inefficient. Test a moderately large value.
        @Test
        @DisplayName("fibonacci should handle moderately large input (e.g., 30)")
        void fibonacci_whenModeratelyLargeInput() {
             assertEquals(832040, calculator.fibonacci(30));
        }
    }

    @Nested
    @DisplayName("Advanced Function Tests")
    class AdvancedFunctionTests {

        @ParameterizedTest
        @CsvSource({"2.0, 3.0, 8.0", "5.0, 0.0, 1.0", "10.0, -1.0, 0.1", "4.0, 0.5, 2.0", "-2.0, 2.0, 4.0" , "-2.0, 3.0, -8.0"})
        @DisplayName("power should return base raised to the exponent")
        void power_shouldReturnCorrectValue(double base, double exp, double expected) {
            assertEquals(expected, calculator.power(base, exp), DELTA);
        }

        @ParameterizedTest
        @CsvSource({"100.0, 10.0, 2.0",
                "8.0, 2.0, 3.0",
                "27.0, 3.0, 3.0",
//                "Math.E, Math.E, 1.0"
        })
        @DisplayName("logarithm should return correct value for valid inputs")
        void logarithm_whenValidInputs_shouldReturnCorrectValue(double value, double base, double expected) {
             // Need to use Math.E directly for the CSV source if needed, or handle it here
             if (base == Math.E && value == Math.E) {
                 assertEquals(1.0, calculator.logarithm(Math.E, Math.E), DELTA);
             } else {
                 assertEquals(expected, calculator.logarithm(value, base), DELTA);
             }
        }

        @ParameterizedTest
        @CsvSource({"0.0, 10.0", "-1.0, 10.0", "10.0, 0.0", "10.0, -1.0", "10.0, 1.0"})
        @DisplayName("logarithm should throw IllegalArgumentException for invalid inputs")
        void logarithm_whenInvalidInputs_shouldThrowIllegalArgumentException(double value, double base) {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                calculator.logarithm(value, base);
            });
            assertEquals("Invalid value or base for logarithm.", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Trigonometric Function Tests")
    class TrigonometricTests {

        @Test
        @DisplayName("sin should return correct value")
        void sin_shouldReturnCorrectValue() {
            assertEquals(0.0, calculator.sin(0.0), DELTA);
            assertEquals(1.0, calculator.sin(Math.PI / 2), DELTA);
            assertEquals(-1.0, calculator.sin(3 * Math.PI / 2), DELTA);
            assertEquals(Math.sqrt(2) / 2, calculator.sin(Math.PI / 4), DELTA);
        }

        @Test
        @DisplayName("cos should return correct value")
        void cos_shouldReturnCorrectValue() {
            assertEquals(1.0, calculator.cos(0.0), DELTA);
            assertEquals(0.0, calculator.cos(Math.PI / 2), DELTA);
            assertEquals(-1.0, calculator.cos(Math.PI), DELTA);
            assertEquals(Math.sqrt(2) / 2, calculator.cos(Math.PI / 4), DELTA);
        }

        @Test
        @DisplayName("tan should return correct value")
        void tan_shouldReturnCorrectValue() {
            assertEquals(0.0, calculator.tan(0.0), DELTA);
            assertEquals(1.0, calculator.tan(Math.PI / 4), DELTA);
             // tan(pi/2) is undefined, tan approaches infinity. Math.tan reflects this.
             // assertEquals(Double.POSITIVE_INFINITY, calculator.tan(Math.PI / 2), DELTA); // This won't work due to precision
            assertTrue(Double.isFinite(calculator.tan(1.0))); // Check for a standard angle
            // Consider testing values near asymptotes if high precision is critical, but Math.tan handles it.
        }
    }

    @Nested
    @DisplayName("Number Theory Tests")
    class NumberTheoryTests {

        @ParameterizedTest
        @CsvSource({"48, 18, 6", "18, 48, 6", "-48, 18, 6", "48, -18, 6", "-48, -18, 6", "5, 0, 5", "0, 5, 5", "0, 0, 0", "17, 5, 1"})
        @DisplayName("gcd should return greatest common divisor")
        void gcd_shouldReturnCorrectValue(int a, int b, int expected) {
            assertEquals(expected, calculator.gcd(a, b));
        }

        @ParameterizedTest
        @CsvSource({"12, 18, 36", "18, 12, 36", "-12, 18, 36", "12, -18, 36", "-12, -18, 36", "5, 7, 35", "6, 8, 24"})
        @DisplayName("lcm should return least common multiple for non-zero inputs")
        void lcm_whenNonZero_shouldReturnCorrectValue(int a, int b, int expected) {
            assertEquals(expected, calculator.lcm(a, b));
        }

        @ParameterizedTest
        @ValueSource(ints = {0, 5})
        @DisplayName("lcm should return 0 if either input is zero")
        void lcm_whenZeroInput_shouldReturnZero(int nonZero) {
            assertEquals(0, calculator.lcm(nonZero, 0));
            assertEquals(0, calculator.lcm(0, nonZero));
            assertEquals(0, calculator.lcm(0, 0));
        }
    }

    @Nested
    @DisplayName("solveQuadratic Tests")
    class SolveQuadraticTests {

        @Test
        @DisplayName("should throw IllegalArgumentException if a is zero")
        void solveQuadratic_whenAisZero_shouldThrowIllegalArgumentException() {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                calculator.solveQuadratic(0, 5, 6);
            });
            assertEquals("Coefficient a cannot be zero for a quadratic equation.", exception.getMessage());
        }

        @Test
        @DisplayName("should return two distinct real roots when discriminant is positive")
        void solveQuadratic_whenDiscriminantPositive_shouldReturnTwoRoots() {
            // x^2 - 5x + 6 = 0 => (x-2)(x-3)=0 => roots are 2, 3
            double[] roots = calculator.solveQuadratic(1, -5, 6);
            assertArrayEquals(new double[]{3.0, 2.0}, roots, DELTA);
        }

        @Test
        @DisplayName("should return one real root when discriminant is zero")
        void solveQuadratic_whenDiscriminantZero_shouldReturnOneRoot() {
            // x^2 - 4x + 4 = 0 => (x-2)^2=0 => root is 2
            double[] roots = calculator.solveQuadratic(1, -4, 4);
            assertArrayEquals(new double[]{2.0}, roots, DELTA);
        }

        @Test
        @DisplayName("should return empty array when discriminant is negative (no real roots)")
        void solveQuadratic_whenDiscriminantNegative_shouldReturnEmptyArray() {
            // x^2 + x + 1 = 0 => discriminant = 1 - 4 = -3
            double[] roots = calculator.solveQuadratic(1, 1, 1);
            assertArrayEquals(new double[0], roots, DELTA);
        }

        @Test
        @DisplayName("should handle non-integer coefficients")
        void solveQuadratic_withNonIntegerCoefficients() {
             // 2x^2 + 5x - 3 = 0 => (2x-1)(x+3) = 0 => roots 0.5, -3
             double[] roots = calculator.solveQuadratic(2.0, 5.0, -3.0);
             Arrays.sort(roots); // Sort for consistent comparison
             assertArrayEquals(new double[]{-3.0, 0.5}, roots, DELTA);
        }
    }

    @Nested
    @DisplayName("Combinatorics Tests")
    class CombinatoricsTests {

        @ParameterizedTest
        @CsvSource({"5, 2, 20", "10, 3, 720", "6, 6, 720", "5, 0, 1", "5, 1, 5"})
        @DisplayName("permutation P(n, r) should return correct value")
        void permutation_whenValidInput_shouldReturnCorrectValue(int n, int r, long expected) {
            assertEquals(expected, calculator.permutation(n, r));
        }

        @ParameterizedTest
        @CsvSource({"-1, 2", "5, -1", "3, 5"})
        @DisplayName("permutation P(n, r) should throw IllegalArgumentException for invalid input")
        void permutation_whenInvalidInput_shouldThrowIllegalArgumentException(int n, int r) {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                calculator.permutation(n, r);
            });
            assertEquals("Invalid values for permutation.", exception.getMessage());
        }

        @ParameterizedTest
        @CsvSource({"5, 2, 10", "10, 3, 120", "6, 6, 1", "5, 0, 1", "5, 1, 5", "7, 3, 35"})
        @DisplayName("combination C(n, r) should return correct value")
        void combination_whenValidInput_shouldReturnCorrectValue(int n, int r, long expected) {
            assertEquals(expected, calculator.combination(n, r));
        }

        @ParameterizedTest
        @CsvSource({"-1, 2", "5, -1", "3, 5"})
        @DisplayName("combination C(n, r) should throw IllegalArgumentException for invalid input")
        void combination_whenInvalidInput_shouldThrowIllegalArgumentException(int n, int r) {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                calculator.combination(n, r);
            });
            assertEquals("Invalid values for combination.", exception.getMessage());
        }


        // EXCLUDED UNIT TEST
        // Test potential overflow with large factorials indirectly via combination
//        @Test
//        @DisplayName("combination C(n, r) should handle moderately large inputs")
//        void combination_withLargeInputs() {
//             C(30, 15) is large but should fit in long
//             assertEquals(155117520L, calculator.combination(30, 15));
//        }
    }

    @Nested
    @DisplayName("Polynomial Derivative Tests")
    class PolynomialDerivativeTests {

        @Test
        @DisplayName("derivative of a constant should be [0]")
        void derivative_whenConstant_shouldReturnZero() {
            // P(x) = 5 => P'(x) = 0
            double[] coeffs = {5.0};
            assertArrayEquals(new double[]{0.0}, calculator.polynomialDerivative(coeffs), DELTA);
        }

        @Test
        @DisplayName("derivative of a linear polynomial should be [a1]")
        void derivative_whenLinear_shouldReturnConstant() {
            // P(x) = 3x + 2 => P'(x) = 3
            double[] coeffs = {3.0, 2.0};
            assertArrayEquals(new double[]{3.0}, calculator.polynomialDerivative(coeffs), DELTA);
        }

        @Test
        @DisplayName("derivative of a quadratic polynomial")
        void derivative_whenQuadratic() {
            // P(x) = 4x^2 - 2x + 1 => P'(x) = 8x - 2
            double[] coeffs = {4.0, -2.0, 1.0};
            assertArrayEquals(new double[]{8.0, -2.0}, calculator.polynomialDerivative(coeffs), DELTA);
        }

        @Test
        @DisplayName("derivative of a higher degree polynomial")
        void derivative_whenHigherDegree() {
            // P(x) = x^3 + 0x^2 + 2x - 5 => P'(x) = 3x^2 + 0x + 2
            double[] coeffs = {1.0, 0.0, 2.0, -5.0};
            assertArrayEquals(new double[]{3.0, 0.0, 2.0}, calculator.polynomialDerivative(coeffs), DELTA);
        }

        @Test
        @DisplayName("derivative should throw IllegalArgumentException for null coefficients")
        void derivative_whenCoefficientsNull_shouldThrowIllegalArgumentException() {
             IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                 calculator.polynomialDerivative(null);
             });
             assertEquals("Coefficient array is empty.", exception.getMessage());
        }

        @Test
        @DisplayName("derivative should throw IllegalArgumentException for empty coefficients")
        void derivative_whenCoefficientsEmpty_shouldThrowIllegalArgumentException() {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                calculator.polynomialDerivative(new double[0]);
            });
            assertEquals("Coefficient array is empty.", exception.getMessage());
        }
    }
} 