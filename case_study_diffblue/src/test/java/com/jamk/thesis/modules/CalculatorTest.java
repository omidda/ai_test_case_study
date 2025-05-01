package com.jamk.thesis.modules;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class CalculatorTest {
    /**
     * Test {@link Calculator#add(int, int)}.
     * <ul>
     *   <li>When one.</li>
     *   <li>Then return three.</li>
     * </ul>
     * <p>
     * Method under test: {@link Calculator#add(int, int)}
     */
    @Test
    @DisplayName("Test add(int, int); when one; then return three")
    void testAdd_whenOne_thenReturnThree() {
        // Arrange, Act and Assert
        assertEquals(3, (new Calculator()).add(1, 2));
    }

    /**
     * Test {@link Calculator#add(int, int)}.
     * <ul>
     *   <li>When three.</li>
     *   <li>Then return five.</li>
     * </ul>
     * <p>
     * Method under test: {@link Calculator#add(int, int)}
     */
    @Test
    @DisplayName("Test add(int, int); when three; then return five")
    void testAdd_whenThree_thenReturnFive() {
        // Arrange, Act and Assert
        assertEquals(5, (new Calculator()).add(3, 2));
    }

    /**
     * Test {@link Calculator#add(int, int)}.
     * <ul>
     *   <li>When two.</li>
     *   <li>Then return four.</li>
     * </ul>
     * <p>
     * Method under test: {@link Calculator#add(int, int)}
     */
    @Test
    @DisplayName("Test add(int, int); when two; then return four")
    void testAdd_whenTwo_thenReturnFour() {
        // Arrange, Act and Assert
        assertEquals(4, (new Calculator()).add(2, 2));
    }

    /**
     * Test {@link Calculator#add(int, int)}.
     * <ul>
     *   <li>When zero.</li>
     *   <li>Then return two.</li>
     * </ul>
     * <p>
     * Method under test: {@link Calculator#add(int, int)}
     */
    @Test
    @DisplayName("Test add(int, int); when zero; then return two")
    void testAdd_whenZero_thenReturnTwo() {
        // Arrange, Act and Assert
        assertEquals(2, (new Calculator()).add(0, 2));
    }

    /**
     * Test {@link Calculator#divide(int, int)}.
     * <ul>
     *   <li>When minus one.</li>
     *   <li>Then return {@code -0.3333333333333333}.</li>
     * </ul>
     * <p>
     * Method under test: {@link Calculator#divide(int, int)}
     */
    @Test
    @DisplayName("Test divide(int, int); when minus one; then return '-0.3333333333333333'")
    void testDivide_whenMinusOne_thenReturn03333333333333333() {
        // Arrange, Act and Assert
        assertEquals(-0.3333333333333333d, (new Calculator()).divide(-1, 3));
    }

    /**
     * Test {@link Calculator#divide(int, int)}.
     * <ul>
     *   <li>When one.</li>
     *   <li>Then return {@code 0.3333333333333333}.</li>
     * </ul>
     * <p>
     * Method under test: {@link Calculator#divide(int, int)}
     */
    @Test
    @DisplayName("Test divide(int, int); when one; then return '0.3333333333333333'")
    void testDivide_whenOne_thenReturn03333333333333333() {
        // Arrange, Act and Assert
        assertEquals(0.3333333333333333d, (new Calculator()).divide(1, 3));
    }

    /**
     * Test {@link Calculator#divide(int, int)}.
     * <ul>
     *   <li>When three.</li>
     *   <li>Then return one.</li>
     * </ul>
     * <p>
     * Method under test: {@link Calculator#divide(int, int)}
     */
    @Test
    @DisplayName("Test divide(int, int); when three; then return one")
    void testDivide_whenThree_thenReturnOne() {
        // Arrange, Act and Assert
        assertEquals(1.0d, (new Calculator()).divide(3, 3));
    }

    /**
     * Test {@link Calculator#divide(int, int)}.
     * <ul>
     *   <li>When zero.</li>
     *   <li>Then throw {@link IllegalArgumentException}.</li>
     * </ul>
     * <p>
     * Method under test: {@link Calculator#divide(int, int)}
     */
    @Test
    @DisplayName("Test divide(int, int); when zero; then throw IllegalArgumentException")
    void testDivide_whenZero_thenThrowIllegalArgumentException() {
        // Arrange, Act and Assert
        assertThrows(IllegalArgumentException.class, () -> (new Calculator()).divide(3, 0));
    }

    /**
     * Test {@link Calculator#cos(double)}.
     * <p>
     * Method under test: {@link Calculator#cos(double)}
     */
    @Test
    @DisplayName("Test cos(double)")
    void testCos() {
        // Arrange, Act and Assert
        assertEquals(-0.8390715290764524d, (new Calculator()).cos(10.0d));
    }

    /**
     * Test {@link Calculator#combination(int, int)}.
     * <ul>
     *   <li>When minus one.</li>
     *   <li>Then throw {@link IllegalArgumentException}.</li>
     * </ul>
     * <p>
     * Method under test: {@link Calculator#combination(int, int)}
     */
    @Test
    @DisplayName("Test combination(int, int); when minus one; then throw IllegalArgumentException")
    void testCombination_whenMinusOne_thenThrowIllegalArgumentException() {
        // Arrange, Act and Assert
        assertThrows(IllegalArgumentException.class, () -> (new Calculator()).combination(0, -1));
        assertThrows(IllegalArgumentException.class, () -> (new Calculator()).combination(-1, 0));
    }

    /**
     * Test {@link Calculator#combination(int, int)}.
     * <ul>
     *   <li>When one.</li>
     *   <li>Then throw {@link IllegalArgumentException}.</li>
     * </ul>
     * <p>
     * Method under test: {@link Calculator#combination(int, int)}
     */
    @Test
    @DisplayName("Test combination(int, int); when one; then throw IllegalArgumentException")
    void testCombination_whenOne_thenThrowIllegalArgumentException() {
        // Arrange, Act and Assert
        assertThrows(IllegalArgumentException.class, () -> (new Calculator()).combination(0, 1));
    }

    /**
     * Test {@link Calculator#combination(int, int)}.
     * <ul>
     *   <li>When three.</li>
     *   <li>Then return one.</li>
     * </ul>
     * <p>
     * Method under test: {@link Calculator#combination(int, int)}
     */
    @Test
    @DisplayName("Test combination(int, int); when three; then return one")
    void testCombination_whenThree_thenReturnOne() {
        // Arrange, Act and Assert
        assertEquals(1L, (new Calculator()).combination(3, 3));
    }

    /**
     * Test {@link Calculator#combination(int, int)}.
     * <ul>
     *   <li>When zero.</li>
     *   <li>Then return one.</li>
     * </ul>
     * <p>
     * Method under test: {@link Calculator#combination(int, int)}
     */
    @Test
    @DisplayName("Test combination(int, int); when zero; then return one")
    void testCombination_whenZero_thenReturnOne() {
        // Arrange, Act and Assert
        assertEquals(1L, (new Calculator()).combination(0, 0));
    }

    /**
     * Test {@link Calculator#fibonacci(int)}.
     * <ul>
     *   <li>When minus one.</li>
     *   <li>Then throw {@link IllegalArgumentException}.</li>
     * </ul>
     * <p>
     * Method under test: {@link Calculator#fibonacci(int)}
     */
    @Test
    @DisplayName("Test fibonacci(int); when minus one; then throw IllegalArgumentException")
    void testFibonacci_whenMinusOne_thenThrowIllegalArgumentException() {
        // Arrange, Act and Assert
        assertThrows(IllegalArgumentException.class, () -> (new Calculator()).fibonacci(-1));
    }

    /**
     * Test {@link Calculator#fibonacci(int)}.
     * <ul>
     *   <li>When one.</li>
     *   <li>Then return one.</li>
     * </ul>
     * <p>
     * Method under test: {@link Calculator#fibonacci(int)}
     */
    @Test
    @DisplayName("Test fibonacci(int); when one; then return one")
    void testFibonacci_whenOne_thenReturnOne() {
        // Arrange, Act and Assert
        assertEquals(1, (new Calculator()).fibonacci(1));
    }

    /**
     * Test {@link Calculator#fibonacci(int)}.
     * <ul>
     *   <li>When two.</li>
     *   <li>Then return one.</li>
     * </ul>
     * <p>
     * Method under test: {@link Calculator#fibonacci(int)}
     */
    @Test
    @DisplayName("Test fibonacci(int); when two; then return one")
    void testFibonacci_whenTwo_thenReturnOne() {
        // Arrange, Act and Assert
        assertEquals(1, (new Calculator()).fibonacci(2));
    }

    /**
     * Test {@link Calculator#fibonacci(int)}.
     * <ul>
     *   <li>When zero.</li>
     *   <li>Then return zero.</li>
     * </ul>
     * <p>
     * Method under test: {@link Calculator#fibonacci(int)}
     */
    @Test
    @DisplayName("Test fibonacci(int); when zero; then return zero")
    void testFibonacci_whenZero_thenReturnZero() {
        // Arrange, Act and Assert
        assertEquals(0, (new Calculator()).fibonacci(0));
    }


    /**
     * Test {@link Calculator#power(double, double)}.
     * <ul>
     *   <li>When {@code 0.5}.</li>
     *   <li>Then return {@code 9.765625E-4}.</li>
     * </ul>
     * <p>
     * Method under test: {@link Calculator#power(double, double)}
     */
    @Test
    @DisplayName("Test power(double, double); when '0.5'; then return '9.765625E-4'")
    void testPower_when05_thenReturn9765625e4() {
        // Arrange, Act and Assert
        assertEquals(9.765625E-4d, (new Calculator()).power(0.5d, 10.0d));
    }

    /**
     * Test {@link Calculator#power(double, double)}.
     * <ul>
     *   <li>When one.</li>
     *   <li>Then return one.</li>
     * </ul>
     * <p>
     * Method under test: {@link Calculator#power(double, double)}
     */
    @Test
    @DisplayName("Test power(double, double); when one; then return one")
    void testPower_whenOne_thenReturnOne() {
        // Arrange, Act and Assert
        assertEquals(1.0d, (new Calculator()).power(1.0d, 10.0d));
    }

    /**
     * Test {@link Calculator#power(double, double)}.
     * <ul>
     *   <li>When ten.</li>
     *   <li>Then return {@code 1.0E10}.</li>
     * </ul>
     * <p>
     * Method under test: {@link Calculator#power(double, double)}
     */
    @Test
    @DisplayName("Test power(double, double); when ten; then return '1.0E10'")
    void testPower_whenTen_thenReturn10e10() {
        // Arrange, Act and Assert
        assertEquals(1.0E10d, (new Calculator()).power(10.0d, 10.0d));
    }

    /**
     * Test {@link Calculator#power(double, double)}.
     * <ul>
     *   <li>When two.</li>
     *   <li>Then return {@code 1024.0}.</li>
     * </ul>
     * <p>
     * Method under test: {@link Calculator#power(double, double)}
     */
    @Test
    @DisplayName("Test power(double, double); when two; then return '1024.0'")
    void testPower_whenTwo_thenReturn10240() {
        // Arrange, Act and Assert
        assertEquals(1024.0d, (new Calculator()).power(2.0d, 10.0d));
    }

    /**
     * Test {@link Calculator#logarithm(double, double)}.
     * <ul>
     *   <li>When {@code 1.0E-10}.</li>
     *   <li>Then throw {@link IllegalArgumentException}.</li>
     * </ul>
     * <p>
     * Method under test: {@link Calculator#logarithm(double, double)}
     */
    @Test
    @DisplayName("Test logarithm(double, double); when '1.0E-10'; then throw IllegalArgumentException")
    void testLogarithm_when10e10_thenThrowIllegalArgumentException() {
        // Arrange, Act and Assert
        assertThrows(IllegalArgumentException.class, () -> (new Calculator()).logarithm(0.0d, 1.0E-10d));
        assertThrows(IllegalArgumentException.class, () -> (new Calculator()).logarithm(1.0E-10d, 0.0d));
    }

    /**
     * Test {@link Calculator#logarithm(double, double)}.
     * <ul>
     *   <li>When one.</li>
     *   <li>Then throw {@link IllegalArgumentException}.</li>
     * </ul>
     * <p>
     * Method under test: {@link Calculator#logarithm(double, double)}
     */
    @Test
    @DisplayName("Test logarithm(double, double); when one; then throw IllegalArgumentException")
    void testLogarithm_whenOne_thenThrowIllegalArgumentException() {
        // Arrange, Act and Assert
        assertThrows(IllegalArgumentException.class, () -> (new Calculator()).logarithm(0.0d, 1.0d));
        assertThrows(IllegalArgumentException.class, () -> (new Calculator()).logarithm(1.0E-10d, 1.0d));
    }

    /**
     * Test {@link Calculator#logarithm(double, double)}.
     * <ul>
     *   <li>When ten.</li>
     *   <li>Then return one.</li>
     * </ul>
     * <p>
     * Method under test: {@link Calculator#logarithm(double, double)}
     */
    @Test
    @DisplayName("Test logarithm(double, double); when ten; then return one")
    void testLogarithm_whenTen_thenReturnOne() {
        // Arrange, Act and Assert
        assertEquals(1.0d, (new Calculator()).logarithm(10.0d, 10.0d));
    }

    /**
     * Test {@link Calculator#logarithm(double, double)}.
     * <ul>
     *   <li>When zero.</li>
     *   <li>Then throw {@link IllegalArgumentException}.</li>
     * </ul>
     * <p>
     * Method under test: {@link Calculator#logarithm(double, double)}
     */
    @Test
    @DisplayName("Test logarithm(double, double); when zero; then throw IllegalArgumentException")
    void testLogarithm_whenZero_thenThrowIllegalArgumentException() {
        // Arrange, Act and Assert
        assertThrows(IllegalArgumentException.class, () -> (new Calculator()).logarithm(0.0d, 0.0d));
    }

    /**
     * Test {@link Calculator#lcm(int, int)}.
     * <ul>
     *   <li>When minus one.</li>
     *   <li>Then return three.</li>
     * </ul>
     * <p>
     * Method under test: {@link Calculator#lcm(int, int)}
     */
    @Test
    @DisplayName("Test lcm(int, int); when minus one; then return three")
    void testLcm_whenMinusOne_thenReturnThree() {
        // Arrange, Act and Assert
        assertEquals(3, (new Calculator()).lcm(-1, 3));
    }

    /**
     * Test {@link Calculator#lcm(int, int)}.
     * <ul>
     *   <li>When one.</li>
     *   <li>Then return three.</li>
     * </ul>
     * <p>
     * Method under test: {@link Calculator#lcm(int, int)}
     */
    @Test
    @DisplayName("Test lcm(int, int); when one; then return three")
    void testLcm_whenOne_thenReturnThree() {
        // Arrange, Act and Assert
        assertEquals(3, (new Calculator()).lcm(1, 3));
    }

    /**
     * Test {@link Calculator#lcm(int, int)}.
     * <ul>
     *   <li>When three.</li>
     *   <li>Then return three.</li>
     * </ul>
     * <p>
     * Method under test: {@link Calculator#lcm(int, int)}
     */
    @Test
    @DisplayName("Test lcm(int, int); when three; then return three")
    void testLcm_whenThree_thenReturnThree() {
        // Arrange, Act and Assert
        assertEquals(3, (new Calculator()).lcm(3, 3));
    }

    /**
     * Test {@link Calculator#lcm(int, int)}.
     * <ul>
     *   <li>When zero.</li>
     *   <li>Then return zero.</li>
     * </ul>
     * <p>
     * Method under test: {@link Calculator#lcm(int, int)}
     */
    @Test
    @DisplayName("Test lcm(int, int); when zero; then return zero")
    void testLcm_whenZero_thenReturnZero() {
        // Arrange, Act and Assert
        assertEquals(0, (new Calculator()).lcm(0, 3));
        assertEquals(0, (new Calculator()).lcm(3, 0));
    }

    /**
     * Test {@link Calculator#solveQuadratic(double, double, double)}.
     * <ul>
     *   <li>Then return array of {@code double} with {@code -0.16666666666666666} and
     * {@code 0.2}.</li>
     * </ul>
     * <p>
     * Method under test: {@link Calculator#solveQuadratic(double, double, double)}
     */
    @Test
    @DisplayName("Test solveQuadratic(double, double, double); then return array of double with '-0.16666666666666666' and '0.2'")
    void testSolveQuadratic_thenReturnArrayOfDoubleWith016666666666666666And02() {
        // Arrange, Act and Assert
        assertArrayEquals(new double[]{-0.16666666666666666d, 0.2d},
                (new Calculator()).solveQuadratic(-300.0d, 10.0d, 10.0d), 0.0);
    }

    /**
     * Test {@link Calculator#solveQuadratic(double, double, double)}.
     * <ul>
     *   <li>When ten.</li>
     *   <li>Then return empty array of {@code double}.</li>
     * </ul>
     * <p>
     * Method under test: {@link Calculator#solveQuadratic(double, double, double)}
     */
    @Test
    @DisplayName("Test solveQuadratic(double, double, double); when ten; then return empty array of double")
    void testSolveQuadratic_whenTen_thenReturnEmptyArrayOfDouble() {
        // Arrange, Act and Assert
        assertArrayEquals(new double[]{}, (new Calculator()).solveQuadratic(10.0d, 10.0d, 10.0d), 0.0);
    }

    /**
     * Test {@link Calculator#solveQuadratic(double, double, double)}.
     * <ul>
     *   <li>When zero.</li>
     *   <li>Then return array of {@code double} with {@code -0.0}.</li>
     * </ul>
     * <p>
     * Method under test: {@link Calculator#solveQuadratic(double, double, double)}
     */
    @Test
    @DisplayName("Test solveQuadratic(double, double, double); when zero; then return array of double with '-0.0'")
    void testSolveQuadratic_whenZero_thenReturnArrayOfDoubleWith00() {
        // Arrange, Act and Assert
        assertArrayEquals(new double[]{-0.0d}, (new Calculator()).solveQuadratic(10.0d, 0.0d, 0.0d), 0.0);
    }

    /**
     * Test {@link Calculator#solveQuadratic(double, double, double)}.
     * <ul>
     *   <li>When zero.</li>
     *   <li>Then throw {@link IllegalArgumentException}.</li>
     * </ul>
     * <p>
     * Method under test: {@link Calculator#solveQuadratic(double, double, double)}
     */
    @Test
    @DisplayName("Test solveQuadratic(double, double, double); when zero; then throw IllegalArgumentException")
    void testSolveQuadratic_whenZero_thenThrowIllegalArgumentException() {
        // Arrange, Act and Assert
        assertThrows(IllegalArgumentException.class, () -> (new Calculator()).solveQuadratic(0.0d, 10.0d, 10.0d));
    }

    /**
     * Test {@link Calculator#polynomialDerivative(double[])}.
     * <ul>
     *   <li>Then return array of {@code double} with thirty and two.</li>
     * </ul>
     * <p>
     * Method under test: {@link Calculator#polynomialDerivative(double[])}
     */
    @Test
    @DisplayName("Test polynomialDerivative(double[]); then return array of double with thirty and two")
    void testPolynomialDerivative_thenReturnArrayOfDoubleWithThirtyAndTwo() {
        // Arrange, Act and Assert
        assertArrayEquals(new double[]{30.0d, 2.0d, 10.0d},
                (new Calculator()).polynomialDerivative(new double[]{10.0d, 1.0d, 10.0d, 1.0d}), 0.0);
    }

    /**
     * Test {@link Calculator#polynomialDerivative(double[])}.
     * <ul>
     *   <li>When empty array of {@code double}.</li>
     * </ul>
     * <p>
     * Method under test: {@link Calculator#polynomialDerivative(double[])}
     */
    @Test
    @DisplayName("Test polynomialDerivative(double[]); when empty array of double")
    void testPolynomialDerivative_whenEmptyArrayOfDouble() {
        // Arrange, Act and Assert
        assertThrows(IllegalArgumentException.class, () -> (new Calculator()).polynomialDerivative(new double[]{}));
    }

    /**
     * Test {@link Calculator#polynomialDerivative(double[])}.
     * <ul>
     *   <li>When {@code null}.</li>
     *   <li>Then throw {@link IllegalArgumentException}.</li>
     * </ul>
     * <p>
     * Method under test: {@link Calculator#polynomialDerivative(double[])}
     */
    @Test
    @DisplayName("Test polynomialDerivative(double[]); when 'null'; then throw IllegalArgumentException")
    void testPolynomialDerivative_whenNull_thenThrowIllegalArgumentException() {
        // Arrange, Act and Assert
        assertThrows(IllegalArgumentException.class, () -> (new Calculator()).polynomialDerivative(null));
    }

    /**
     * Test {@link Calculator#subtract(int, int)}.
     * <ul>
     *   <li>When minus one.</li>
     *   <li>Then return minus four.</li>
     * </ul>
     * <p>
     * Method under test: {@link Calculator#subtract(int, int)}
     */
    @Test
    @DisplayName("Test subtract(int, int); when minus one; then return minus four")
    @Tag("MaintainedByDiffblue")
    void testSubtract_whenMinusOne_thenReturnMinusFour() {
        // Arrange, Act and Assert
        assertEquals(-4, (new Calculator()).subtract(-1, 3));
    }

    /**
     * Test {@link Calculator#subtract(int, int)}.
     * <ul>
     *   <li>When one.</li>
     *   <li>Then return minus two.</li>
     * </ul>
     * <p>
     * Method under test: {@link Calculator#subtract(int, int)}
     */
    @Test
    @DisplayName("Test subtract(int, int); when one; then return minus two")
    @Tag("MaintainedByDiffblue")
    void testSubtract_whenOne_thenReturnMinusTwo() {
        // Arrange, Act and Assert
        assertEquals(-2, (new Calculator()).subtract(1, 3));
    }

    /**
     * Test {@link Calculator#subtract(int, int)}.
     * <ul>
     *   <li>When three.</li>
     *   <li>Then return zero.</li>
     * </ul>
     * <p>
     * Method under test: {@link Calculator#subtract(int, int)}
     */
    @Test
    @DisplayName("Test subtract(int, int); when three; then return zero")
    @Tag("MaintainedByDiffblue")
    void testSubtract_whenThree_thenReturnZero() {
        // Arrange, Act and Assert
        assertEquals(0, (new Calculator()).subtract(3, 3));
    }

    /**
     * Test {@link Calculator#subtract(int, int)}.
     * <ul>
     *   <li>When zero.</li>
     *   <li>Then return minus three.</li>
     * </ul>
     * <p>
     * Method under test: {@link Calculator#subtract(int, int)}
     */
    @Test
    @DisplayName("Test subtract(int, int); when zero; then return minus three")
    @Tag("MaintainedByDiffblue")
    void testSubtract_whenZero_thenReturnMinusThree() {
        // Arrange, Act and Assert
        assertEquals(-3, (new Calculator()).subtract(0, 3));
    }

    /**
     * Test {@link Calculator#multiply(int, int)}.
     * <ul>
     *   <li>When minus one.</li>
     *   <li>Then return minus three.</li>
     * </ul>
     * <p>
     * Method under test: {@link Calculator#multiply(int, int)}
     */
    @Test
    @DisplayName("Test multiply(int, int); when minus one; then return minus three")
    @Tag("MaintainedByDiffblue")
    void testMultiply_whenMinusOne_thenReturnMinusThree() {
        // Arrange, Act and Assert
        assertEquals(-3, (new Calculator()).multiply(-1, 3));
    }

    /**
     * Test {@link Calculator#multiply(int, int)}.
     * <ul>
     *   <li>When one.</li>
     *   <li>Then return three.</li>
     * </ul>
     * <p>
     * Method under test: {@link Calculator#multiply(int, int)}
     */
    @Test
    @DisplayName("Test multiply(int, int); when one; then return three")
    @Tag("MaintainedByDiffblue")
    void testMultiply_whenOne_thenReturnThree() {
        // Arrange, Act and Assert
        assertEquals(3, (new Calculator()).multiply(1, 3));
    }

    /**
     * Test {@link Calculator#multiply(int, int)}.
     * <ul>
     *   <li>When three.</li>
     *   <li>Then return nine.</li>
     * </ul>
     * <p>
     * Method under test: {@link Calculator#multiply(int, int)}
     */
    @Test
    @DisplayName("Test multiply(int, int); when three; then return nine")
    @Tag("MaintainedByDiffblue")
    void testMultiply_whenThree_thenReturnNine() {
        // Arrange, Act and Assert
        assertEquals(9, (new Calculator()).multiply(3, 3));
    }

    /**
     * Test {@link Calculator#multiply(int, int)}.
     * <ul>
     *   <li>When zero.</li>
     *   <li>Then return zero.</li>
     * </ul>
     * <p>
     * Method under test: {@link Calculator#multiply(int, int)}
     */
    @Test
    @DisplayName("Test multiply(int, int); when zero; then return zero")
    @Tag("MaintainedByDiffblue")
    void testMultiply_whenZero_thenReturnZero() {
        // Arrange, Act and Assert
        assertEquals(0, (new Calculator()).multiply(0, 3));
    }

    /**
     * Test {@link Calculator#sin(double)}.
     * <p>
     * Method under test: {@link Calculator#sin(double)}
     */
    @Test
    @DisplayName("Test sin(double)")
    @Tag("MaintainedByDiffblue")
    void testSin() {
        // Arrange, Act and Assert
        assertEquals(-0.5440211108893698d, (new Calculator()).sin(10.0d));
    }

    /**
     * Test {@link Calculator#tan(double)}.
     * <p>
     * Method under test: {@link Calculator#tan(double)}
     */
    @Test
    @DisplayName("Test tan(double)")
    @Tag("MaintainedByDiffblue")
    void testTan() {
        // Arrange, Act and Assert
        assertEquals(0.6483608274590866d, (new Calculator()).tan(10.0d));
    }

    /**
     * Test {@link Calculator#factorial(int)}.
     * <ul>
     *   <li>When minus one.</li>
     *   <li>Then throw {@link IllegalArgumentException}.</li>
     * </ul>
     * <p>
     * Method under test: {@link Calculator#factorial(int)}
     */
    @Test
    @DisplayName("Test factorial(int); when minus one; then throw IllegalArgumentException")
    @Tag("MaintainedByDiffblue")
    void testFactorial_whenMinusOne_thenThrowIllegalArgumentException() {
        // Arrange, Act and Assert
        assertThrows(IllegalArgumentException.class, () -> (new Calculator()).factorial(-1));
    }

    /**
     * Test {@link Calculator#permutation(int, int)}.
     * <ul>
     *   <li>When minus one.</li>
     *   <li>Then throw {@link IllegalArgumentException}.</li>
     * </ul>
     * <p>
     * Method under test: {@link Calculator#permutation(int, int)}
     */
    @Test
    @DisplayName("Test permutation(int, int); when minus one; then throw IllegalArgumentException")
    @Tag("MaintainedByDiffblue")
    void testPermutation_whenMinusOne_thenThrowIllegalArgumentException() {
        // Arrange, Act and Assert
        assertThrows(IllegalArgumentException.class, () -> (new Calculator()).permutation(0, -1));
    }

    /**
     * Test {@link Calculator#permutation(int, int)}.
     * <ul>
     *   <li>When minus one.</li>
     *   <li>Then throw {@link IllegalArgumentException}.</li>
     * </ul>
     * <p>
     * Method under test: {@link Calculator#permutation(int, int)}
     */
    @Test
    @DisplayName("Test permutation(int, int); when minus one; then throw IllegalArgumentException")
    @Tag("MaintainedByDiffblue")
    void testPermutation_whenMinusOne_thenThrowIllegalArgumentException2() {
        // Arrange, Act and Assert
        assertThrows(IllegalArgumentException.class, () -> (new Calculator()).permutation(-1, 0));
    }

    /**
     * Test {@link Calculator#permutation(int, int)}.
     * <ul>
     *   <li>When one.</li>
     *   <li>Then throw {@link IllegalArgumentException}.</li>
     * </ul>
     * <p>
     * Method under test: {@link Calculator#permutation(int, int)}
     */
    @Test
    @DisplayName("Test permutation(int, int); when one; then throw IllegalArgumentException")
    void testPermutation_whenOne_thenThrowIllegalArgumentException() {
        // Arrange, Act and Assert
        assertThrows(IllegalArgumentException.class, () -> (new Calculator()).permutation(0, 1));
    }


    /**
     * Test {@link Calculator#factorial(int)}.
     * <ul>
     *   <li>When one.</li>
     *   <li>Then return one.</li>
     * </ul>
     * <p>
     * Method under test: {@link Calculator#factorial(int)}
     */
    @Test
    @DisplayName("Test factorial(int); when one; then return one")
    @Tag("MaintainedByDiffblue")
    void testFactorial_whenOne_thenReturnOne() {
        // Arrange, Act and Assert
        assertEquals(1, (new Calculator()).factorial(1));
    }

    /**
     * Test {@link Calculator#factorial(int)}.
     * <ul>
     *   <li>When two.</li>
     *   <li>Then return two.</li>
     * </ul>
     * <p>
     * Method under test: {@link Calculator#factorial(int)}
     */
    @Test
    @DisplayName("Test factorial(int); when two; then return two")
    @Tag("MaintainedByDiffblue")
    void testFactorial_whenTwo_thenReturnTwo() {
        // Arrange, Act and Assert
        assertEquals(2, (new Calculator()).factorial(2));
    }

    /**
     * Test {@link Calculator#factorial(int)}.
     * <ul>
     *   <li>When zero.</li>
     *   <li>Then return one.</li>
     * </ul>
     * <p>
     * Method under test: {@link Calculator#factorial(int)}
     */
    @Test
    @DisplayName("Test factorial(int); when zero; then return one")
    @Tag("MaintainedByDiffblue")
    void testFactorial_whenZero_thenReturnOne() {
        // Arrange, Act and Assert
        assertEquals(1, (new Calculator()).factorial(0));
    }
}
