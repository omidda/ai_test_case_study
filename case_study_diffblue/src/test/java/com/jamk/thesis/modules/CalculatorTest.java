package com.jamk.thesis.modules;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
}
