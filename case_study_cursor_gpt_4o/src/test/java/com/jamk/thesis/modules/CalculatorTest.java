package com.jamk.thesis.modules;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CalculatorTest {

    private Calculator calculator;

    @BeforeEach
    public void setUp() {
        calculator = new Calculator();
    }

    @Test
    public void testAdd() {
        assertEquals(5, calculator.add(2, 3));
    }

    @Test
    public void testSubtract() {
        assertEquals(1, calculator.subtract(3, 2));
    }

    @Test
    public void testMultiply() {
        assertEquals(6, calculator.multiply(2, 3));
    }

    @Test
    public void testDivide() {
        assertEquals(2.0, calculator.divide(6, 3));
    }

    @Test
    public void testDivideByZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            calculator.divide(6, 0);
        });
    }

    @Test
    public void testFactorial() {
        assertEquals(120, calculator.factorial(5));
    }

    @Test
    public void testFactorialOfZero() {
        assertEquals(1, calculator.factorial(0));
    }

    @Test
    public void testFactorialNegativeNumber() {
        assertThrows(IllegalArgumentException.class, () -> {
            calculator.factorial(-1);
        });
    }

    @Test
    public void testFibonacci() {
        assertEquals(5, calculator.fibonacci(5));
    }

    @Test
    public void testFibonacciNegativeIndex() {
        assertThrows(IllegalArgumentException.class, () -> {
            calculator.fibonacci(-1);
        });
    }

    @Test
    public void testPower() {
        assertEquals(8.0, calculator.power(2, 3));
    }

    @Test
    public void testLogarithm() {
        assertEquals(2.0, calculator.logarithm(100, 10));
    }

    @Test
    public void testLogarithmInvalidBase() {
        assertThrows(IllegalArgumentException.class, () -> {
            calculator.logarithm(100, 1);
        });
    }

    @Test
    public void testSin() {
        assertEquals(0.0, calculator.sin(0), 0.0001);
    }

    @Test
    public void testCos() {
        assertEquals(1.0, calculator.cos(0), 0.0001);
    }

    @Test
    public void testTan() {
        assertEquals(0.0, calculator.tan(0), 0.0001);
    }

    @Test
    public void testGcd() {
        assertEquals(5, calculator.gcd(10, 5));
    }

    @Test
    public void testLcm() {
        assertEquals(10, calculator.lcm(5, 10));
    }

    @Test
    public void testSolveQuadratic() {
        double[] roots = calculator.solveQuadratic(1, -3, 2);
        assertArrayEquals(new double[]{2.0, 1.0}, roots, 0.0001);
    }

    @Test
    public void testSolveQuadraticNoRealRoots() {
        double[] roots = calculator.solveQuadratic(1, 0, 1);
        assertEquals(0, roots.length);
    }

    @Test
    public void testPermutation() {
        assertEquals(6, calculator.permutation(3, 2));
    }

    @Test
    public void testPermutationInvalidValues() {
        assertThrows(IllegalArgumentException.class, () -> {
            calculator.permutation(3, 4);
        });
    }

    @Test
    public void testCombination() {
        assertEquals(3, calculator.combination(3, 2));
    }

    @Test
    public void testCombinationInvalidValues() {
        assertThrows(IllegalArgumentException.class, () -> {
            calculator.combination(3, 4);
        });
    }

    @Test
    public void testPolynomialDerivative() {
        double[] derivative = calculator.polynomialDerivative(new double[]{3, 2, 1});
        assertArrayEquals(new double[]{6, 2}, derivative, 0.0001);
    }

    @Test
    public void testPolynomialDerivativeEmptyArray() {
        assertThrows(IllegalArgumentException.class, () -> {
            calculator.polynomialDerivative(new double[]{});
        });
    }
} 