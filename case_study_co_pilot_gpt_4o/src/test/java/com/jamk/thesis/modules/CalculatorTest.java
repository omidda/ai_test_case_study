package com.jamk.thesis.modules;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    private final Calculator calculator = new Calculator();

    // Basic Arithmetic Tests
    @Test
    void testAddTwoPositiveNumbers() {
        assertEquals(5, calculator.add(2, 3));
    }

    @Test
    void testAddPositiveAndNegativeNumber() {
        assertEquals(1, calculator.add(3, -2));
    }

    @Test
    void testSubtractTwoNumbers() {
        assertEquals(1, calculator.subtract(3, 2));
    }

    @Test
    void testMultiplyTwoNumbers() {
        assertEquals(6, calculator.multiply(2, 3));
    }

    @Test
    void testDivideTwoNumbers() {
        assertEquals(2.0, calculator.divide(6, 3));
    }

    @Test
    void testDivideByZeroThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> calculator.divide(6, 0));
        assertEquals("Divider cannot be zero.", exception.getMessage());
    }

    // Factorial Tests
    @Test
    void testFactorialOfPositiveNumber() {
        assertEquals(120, calculator.factorial(5));
    }

    @Test
    void testFactorialOfZero() {
        assertEquals(1, calculator.factorial(0));
    }

    @Test
    void testFactorialOfNegativeNumberThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> calculator.factorial(-1));
        assertEquals("Negative number not allowed.", exception.getMessage());
    }

    // Fibonacci Tests
    @Test
    void testFibonacciOfZero() {
        assertEquals(0, calculator.fibonacci(0));
    }

    @Test
    void testFibonacciOfOne() {
        assertEquals(1, calculator.fibonacci(1));
    }

    @Test
    void testFibonacciOfPositiveNumber() {
        assertEquals(8, calculator.fibonacci(6));
    }

    @Test
    void testFibonacciOfNegativeNumberThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> calculator.fibonacci(-1));
        assertEquals("Negative index not allowed.", exception.getMessage());
    }

    // Power Tests
    @Test
    void testPowerOfPositiveBaseAndExponent() {
        assertEquals(8.0, calculator.power(2, 3));
    }

    @Test
    void testPowerOfBaseZeroAndPositiveExponent() {
        assertEquals(0.0, calculator.power(0, 3));
    }

    @Test
    void testPowerOfBaseZeroAndExponentZero() {
        assertEquals(1.0, calculator.power(0, 0));
    }

    // Logarithm Tests
    @Test
    void testLogarithmWithValidInputs() {
        assertEquals(2.0, calculator.logarithm(100, 10), 0.0001);
    }

    @Test
    void testLogarithmWithInvalidBaseThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> calculator.logarithm(100, 1));
        assertEquals("Invalid value or base for logarithm.", exception.getMessage());
    }

    @Test
    void testLogarithmWithNegativeValueThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> calculator.logarithm(-100, 10));
        assertEquals("Invalid value or base for logarithm.", exception.getMessage());
    }

    // Trigonometric Function Tests
    @Test
    void testSinOfAngle() {
        assertEquals(0.0, calculator.sin(0), 0.0001);
    }

    @Test
    void testCosOfAngle() {
        assertEquals(1.0, calculator.cos(0), 0.0001);
    }

    @Test
    void testTanOfAngle() {
        assertEquals(0.0, calculator.tan(0), 0.0001);
    }

    // GCD and LCM Tests
    @Test
    void testGCDOfTwoNumbers() {
        assertEquals(6, calculator.gcd(54, 24));
    }

    @Test
    void testLCMOfTwoNumbers() {
        assertEquals(72, calculator.lcm(24, 18));
    }

    // Quadratic Equation Tests
    @Test
    void testSolveQuadraticWithTwoRealRoots() {
        double[] roots = calculator.solveQuadratic(1, -3, 2);
        assertArrayEquals(new double[]{2.0, 1.0}, roots, 0.0001);
    }

    @Test
    void testSolveQuadraticWithOneRealRoot() {
        double[] roots = calculator.solveQuadratic(1, -2, 1);
        assertArrayEquals(new double[]{1.0}, roots, 0.0001);
    }

    @Test
    void testSolveQuadraticWithNoRealRoots() {
        double[] roots = calculator.solveQuadratic(1, 0, 1);
        assertArrayEquals(new double[]{}, roots);
    }

    @Test
    void testSolveQuadraticWithZeroCoefficientAThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> calculator.solveQuadratic(0, 2, 1));
        assertEquals("Coefficient a cannot be zero for a quadratic equation.", exception.getMessage());
    }

    // Permutation and Combination Tests
    @Test
    void testPermutationWithValidInputs() {
        assertEquals(20, calculator.permutation(5, 2));
    }

    @Test
    void testPermutationWithInvalidInputsThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> calculator.permutation(5, -1));
        assertEquals("Invalid values for permutation.", exception.getMessage());
    }

    @Test
    void testCombinationWithValidInputs() {
        assertEquals(10, calculator.combination(5, 2));
    }

    @Test
    void testCombinationWithInvalidInputsThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> calculator.combination(5, 6));
        assertEquals("Invalid values for combination.", exception.getMessage());
    }

    // Polynomial Derivative Tests
    @Test
    void testPolynomialDerivativeWithValidCoefficients() {
        double[] derivative = calculator.polynomialDerivative(new double[]{3, 2, 1});
        assertArrayEquals(new double[]{6, 2}, derivative, 0.0001);
    }

    @Test
    void testPolynomialDerivativeWithEmptyArrayThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> calculator.polynomialDerivative(new double[]{}));
        assertEquals("Coefficient array is empty.", exception.getMessage());
    }

    @Test
    void testPolynomialDerivativeWithNullArrayThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> calculator.polynomialDerivative(null));
        assertEquals("Coefficient array is empty.", exception.getMessage());
    }
}