package com.jamk.thesis.modules;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
    private Calculator calculator;
    private static final double DELTA = 1e-10;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    // Basic Arithmetic Tests
    @ParameterizedTest
    @CsvSource({
        "0, 0, 0",
        "1, 1, 2",
        "-1, 1, 0",
//        "Integer.MAX_VALUE, 1, Integer.MIN_VALUE",  // Overflow case
//        "Integer.MIN_VALUE, -1, Integer.MAX_VALUE"  // Underflow case
    })
    @DisplayName("Should correctly perform addition with various inputs")
    void add_WithVariousInputs_ShouldCalculateCorrectly(int a, int b, int expected) {
        assertEquals(expected, calculator.add(a, b));
    }

    @ParameterizedTest
    @CsvSource({
        "0, 0, 0",
        "1, 1, 0",
        "-1, -1, 0",
//        "Integer.MAX_VALUE, -1, Integer.MIN_VALUE",  // Overflow case
//        "Integer.MIN_VALUE, 1, Integer.MAX_VALUE"    // Underflow case
    })
    @DisplayName("Should correctly perform subtraction with various inputs")
    void subtract_WithVariousInputs_ShouldCalculateCorrectly(int a, int b, int expected) {
        assertEquals(expected, calculator.subtract(a, b));
    }

    @ParameterizedTest
    @CsvSource({
        "0, 0, 0",
        "1, 1, 1",
        "-1, 1, -1",
        "-1, -1, 1",
//        "Integer.MAX_VALUE, 1, Integer.MAX_VALUE",
//        "Integer.MAX_VALUE, 2, -2"  // Overflow case
    })
    @DisplayName("Should correctly perform multiplication with various inputs")
    void multiply_WithVariousInputs_ShouldCalculateCorrectly(int a, int b, int expected) {
        assertEquals(expected, calculator.multiply(a, b));
    }

    @ParameterizedTest
    @CsvSource({
        "4, 2, 2.0",
        "1, 2, 0.5",
        "-4, 2, -2.0",
        "0, 1, 0.0",
//        "Integer.MAX_VALUE, 1, Integer.MAX_VALUE"
    })
    @DisplayName("Should correctly perform division with valid inputs")
    void divide_WithValidInputs_ShouldCalculateCorrectly(int a, int b, double expected) {
        assertEquals(expected, calculator.divide(a, b), DELTA);
    }

    @Test
    @DisplayName("Should throw exception when dividing by zero")
    void divide_ByZero_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> calculator.divide(1, 0));
    }

    // Factorial Tests
    @ParameterizedTest
    @CsvSource({
        "0, 1",
        "1, 1",
        "2, 2",
        "3, 6",
        "5, 120"
    })
    @DisplayName("Should correctly calculate factorial for valid inputs")
    void factorial_WithValidInputs_ShouldCalculateCorrectly(int n, int expected) {
        assertEquals(expected, calculator.factorial(n));
    }

    @Test
    @DisplayName("Should throw exception for negative factorial input")
    void factorial_WithNegativeInput_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> calculator.factorial(-1));
    }

    // Fibonacci Tests
    @ParameterizedTest
    @CsvSource({
        "0, 0",
        "1, 1",
        "2, 1",
        "3, 2",
        "4, 3",
        "5, 5",
        "6, 8"
    })
    @DisplayName("Should correctly calculate Fibonacci numbers")
    void fibonacci_WithValidInputs_ShouldCalculateCorrectly(int n, int expected) {
        assertEquals(expected, calculator.fibonacci(n));
    }

    @Test
    @DisplayName("Should throw exception for negative Fibonacci index")
    void fibonacci_WithNegativeInput_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> calculator.fibonacci(-1));
    }

    // Power Tests
    @ParameterizedTest
    @CsvSource({
        "2.0, 2.0, 4.0",
        "2.0, 0.0, 1.0",
        "2.0, -1.0, 0.5",
        "0.0, 0.0, 1.0",
        "-2.0, 2.0, 4.0",
        "-2.0, 3.0, -8.0"
    })
    @DisplayName("Should correctly calculate power with various inputs")
    void power_WithVariousInputs_ShouldCalculateCorrectly(double base, double exponent, double expected) {
        assertEquals(expected, calculator.power(base, exponent), DELTA);
    }

    // Logarithm Tests
    @ParameterizedTest
    @CsvSource({
        "8.0, 2.0, 3.0",
        "100.0, 10.0, 2.0",
        "2.718281828459045, 2.718281828459045, 1.0"  // e, e, 1
    })
    @DisplayName("Should correctly calculate logarithm with valid inputs")
    void logarithm_WithValidInputs_ShouldCalculateCorrectly(double value, double base, double expected) {
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
    @DisplayName("Should throw exception for invalid logarithm inputs")
    void logarithm_WithInvalidInputs_ShouldThrowException(double value, double base) {
        assertThrows(IllegalArgumentException.class, () -> calculator.logarithm(value, base));
    }

    // Trigonometric Function Tests
    @ParameterizedTest
    @CsvSource({
        "0.0, 0.0",
        "1.5707963267948966, 1.0",  // π/2
        "3.141592653589793, 0.0"    // π
    })
    @DisplayName("Should correctly calculate sine")
    void sin_WithVariousAngles_ShouldCalculateCorrectly(double angle, double expected) {
        assertEquals(expected, calculator.sin(angle), DELTA);
    }

    @ParameterizedTest
    @CsvSource({
        "0.0, 1.0",
        "1.5707963267948966, 0.0",  // π/2
        "3.141592653589793, -1.0"   // π
    })
    @DisplayName("Should correctly calculate cosine")
    void cos_WithVariousAngles_ShouldCalculateCorrectly(double angle, double expected) {
        assertEquals(expected, calculator.cos(angle), DELTA);
    }

    @ParameterizedTest
    @CsvSource({
        "0.0, 0.0",
        "0.7853981633974483, 1.0"  // π/4
    })
    @DisplayName("Should correctly calculate tangent")
    void tan_WithVariousAngles_ShouldCalculateCorrectly(double angle, double expected) {
        assertEquals(expected, calculator.tan(angle), DELTA);
    }

    // GCD Tests
    @ParameterizedTest
    @CsvSource({
        "48, 18, 6",
        "0, 5, 5",
        "5, 0, 5",
        "-48, 18, 6",
        "48, -18, 6",
        "-48, -18, 6"
    })
    @DisplayName("Should correctly calculate GCD")
    void gcd_WithVariousInputs_ShouldCalculateCorrectly(int a, int b, int expected) {
        assertEquals(expected, calculator.gcd(a, b));
    }

    // LCM Tests
    @ParameterizedTest
    @CsvSource({
        "4, 6, 12",
        "0, 5, 0",
        "5, 0, 0",
        "-4, 6, 12",
        "4, -6, 12",
        "-4, -6, 12"
    })
    @DisplayName("Should correctly calculate LCM")
    void lcm_WithVariousInputs_ShouldCalculateCorrectly(int a, int b, int expected) {
        assertEquals(expected, calculator.lcm(a, b));
    }

    // Quadratic Equation Tests
    @Test
    @DisplayName("Should correctly solve quadratic equation with two real roots")
    void solveQuadratic_WithTwoRealRoots_ShouldReturnBothRoots() {
        double[] roots = calculator.solveQuadratic(1, -5, 6);  // x² - 5x + 6 = 0
        assertEquals(2, roots.length);
        assertArrayEquals(new double[]{3, 2}, roots, DELTA);
    }

    @Test
    @DisplayName("Should correctly solve quadratic equation with one real root")
    void solveQuadratic_WithOneRealRoot_ShouldReturnOneRoot() {
        double[] roots = calculator.solveQuadratic(1, -2, 1);  // x² - 2x + 1 = 0
        assertEquals(1, roots.length);
        assertEquals(1, roots[0], DELTA);
    }

    @Test
    @DisplayName("Should return empty array for quadratic equation with no real roots")
    void solveQuadratic_WithNoRealRoots_ShouldReturnEmptyArray() {
        double[] roots = calculator.solveQuadratic(1, 0, 1);  // x² + 1 = 0
        assertEquals(0, roots.length);
    }

    @Test
    @DisplayName("Should throw exception when 'a' coefficient is zero")
    void solveQuadratic_WithZeroA_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> calculator.solveQuadratic(0, 1, 1));
    }

    // Permutation Tests
    @ParameterizedTest
    @CsvSource({
        "5, 3, 60",
        "5, 5, 120",
        "5, 0, 1",
        "0, 0, 1"
    })
    @DisplayName("Should correctly calculate permutations")
    void permutation_WithValidInputs_ShouldCalculateCorrectly(int n, int r, long expected) {
        assertEquals(expected, calculator.permutation(n, r));
    }

    @ParameterizedTest
    @CsvSource({
        "-1, 1",
        "1, -1",
        "1, 2"
    })
    @DisplayName("Should throw exception for invalid permutation inputs")
    void permutation_WithInvalidInputs_ShouldThrowException(int n, int r) {
        assertThrows(IllegalArgumentException.class, () -> calculator.permutation(n, r));
    }

    // Combination Tests
    @ParameterizedTest
    @CsvSource({
        "5, 3, 10",
        "5, 5, 1",
        "5, 0, 1",
        "0, 0, 1"
    })
    @DisplayName("Should correctly calculate combinations")
    void combination_WithValidInputs_ShouldCalculateCorrectly(int n, int r, long expected) {
        assertEquals(expected, calculator.combination(n, r));
    }

    @ParameterizedTest
    @CsvSource({
        "-1, 1",
        "1, -1",
        "1, 2"
    })
    @DisplayName("Should throw exception for invalid combination inputs")
    void combination_WithInvalidInputs_ShouldThrowException(int n, int r) {
        assertThrows(IllegalArgumentException.class, () -> calculator.combination(n, r));
    }

    // Polynomial Derivative Tests
    @Test
    @DisplayName("Should correctly calculate derivative of polynomial")
    void polynomialDerivative_WithValidPolynomial_ShouldCalculateCorrectly() {
        // Test polynomial: 2x³ + 3x² + 4x + 5
        // Derivative should be: 6x² + 6x + 4
        double[] polynomial = {2, 3, 4, 5};
        double[] expectedDerivative = {6, 6, 4};
        assertArrayEquals(expectedDerivative, calculator.polynomialDerivative(polynomial), DELTA);
    }

    @Test
    @DisplayName("Should return [0] for constant polynomial")
    void polynomialDerivative_WithConstantPolynomial_ShouldReturnZero() {
        double[] polynomial = {5};
        double[] expectedDerivative = {0};
        assertArrayEquals(expectedDerivative, calculator.polynomialDerivative(polynomial), DELTA);
    }

    @Test
    @DisplayName("Should throw exception for null polynomial coefficients")
    void polynomialDerivative_WithNullInput_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> calculator.polynomialDerivative(null));
    }

    @Test
    @DisplayName("Should throw exception for empty polynomial coefficients")
    void polynomialDerivative_WithEmptyInput_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> calculator.polynomialDerivative(new double[]{}));
    }
} 