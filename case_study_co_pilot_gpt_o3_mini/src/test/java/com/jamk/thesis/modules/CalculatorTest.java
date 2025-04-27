package com.jamk.thesis.modules;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.within;


public class CalculatorTest {

    private final Calculator calculator = new Calculator();

    // Tests for add
    @Test
    void add_PositiveNumbers_ReturnsSum() {
        int result = calculator.add(5, 3);
        assertThat(result).isEqualTo(8);
    }

    @Test
    void add_NegativeNumbers_ReturnsSum() {
        int result = calculator.add(-5, -3);
        assertThat(result).isEqualTo(-8);
    }

    @Test
    void add_ZeroAndNumber_ReturnsSameNumber() {
        int result = calculator.add(0, 7);
        assertThat(result).isEqualTo(7);
    }
    
    // Tests for subtract
    @Test
    void subtract_PositiveNumbers_ReturnsDifference() {
        int result = calculator.subtract(10, 4);
        assertThat(result).isEqualTo(6);
    }

    @Test
    void subtract_NegativeNumbers_ReturnsDifference() {
        int result = calculator.subtract(-10, -4);
        assertThat(result).isEqualTo(-6);
    }
    
    // Tests for multiply
    @Test
    void multiply_PositiveNumbers_ReturnsProduct() {
        int result = calculator.multiply(7, 6);
        assertThat(result).isEqualTo(42);
    }

    @Test
    void multiply_WithZero_ReturnsZero() {
        int result = calculator.multiply(7, 0);
        assertThat(result).isEqualTo(0);
    }
    
    // Tests for divide
    @Test
    void divide_ValidInputs_ReturnsQuotient() {
        double result = calculator.divide(10, 2);
        assertThat(result).isEqualTo(5.0);
    }
    
    @Test
    void divide_DivisionByZero_ThrowsException() {
        Throwable thrown = catchThrowable(() -> calculator.divide(10, 0));
        assertThat(thrown)
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Divider cannot be zero.");
    }
    
    // Tests for factorial
    @Test
    void factorial_Zero_ReturnsOne() {
        int result = calculator.factorial(0);
        assertThat(result).isEqualTo(1);
    }

    @Test
    void factorial_PositiveNumber_ReturnsCorrectFactorial() {
        int result = calculator.factorial(5);
        assertThat(result).isEqualTo(120);
    }

    @Test
    void factorial_NegativeNumber_ThrowsException() {
        Throwable thrown = catchThrowable(() -> calculator.factorial(-3));
        assertThat(thrown)
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Negative number not allowed.");
    }
    
    // Tests for fibonacci
    @Test
    void fibonacci_Zero_ReturnsZero() {
        int result = calculator.fibonacci(0);
        assertThat(result).isEqualTo(0);
    }

    @Test
    void fibonacci_One_ReturnsOne() {
        int result = calculator.fibonacci(1);
        assertThat(result).isEqualTo(1);
    }

    @Test
    void fibonacci_PositiveNumber_ReturnsCorrectValue() {
        int result = calculator.fibonacci(7);
        // Fibonacci sequence: 0, 1, 1, 2, 3, 5, 8, 13...
        assertThat(result).isEqualTo(13);
    }

    @Test
    void fibonacci_NegativeNumber_ThrowsException() {
        Throwable thrown = catchThrowable(() -> calculator.fibonacci(-1));
        assertThat(thrown)
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Negative index not allowed.");
    }
    
    // Tests for power
    @Test
    void power_PositiveExponent_ReturnsCorrectResult() {
        double result = calculator.power(2, 3);
        assertThat(result).isEqualTo(8.0);
    }
    
    @Test
    void power_NegativeExponent_ReturnsCorrectResult() {
        double result = calculator.power(2, -2);
        assertThat(result).isEqualTo(0.25);
    }
    
    // Tests for logarithm
    @Test
    void logarithm_ValidInputs_ReturnsLogarithm() {
        double result = calculator.logarithm(8, 2);
        assertThat(result).isEqualTo(3.0);
    }

    @Test
    void logarithm_InvalidValueOrBase_ThrowsException() {
        Throwable t1 = catchThrowable(() -> calculator.logarithm(0, 10));
        assertThat(t1).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Invalid value or base for logarithm.");

        Throwable t2 = catchThrowable(() -> calculator.logarithm(10, 0));
        assertThat(t2).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Invalid value or base for logarithm.");

        Throwable t3 = catchThrowable(() -> calculator.logarithm(10, 1));
        assertThat(t3).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Invalid value or base for logarithm.");
    }
    
    // Tests for sin, cos, tan using a known angle (PI/6)
    @Test
    void trigonometricFunctions_KnownAngle_ReturnsExpectedValues() {
        double angle = Math.PI / 6; // 30 degrees
        double sinVal = calculator.sin(angle);
        double cosVal = calculator.cos(angle);
        double tanVal = calculator.tan(angle);
        
        assertThat(sinVal).isCloseTo(0.5, within(0.0001));
        assertThat(cosVal).isCloseTo(Math.sqrt(3)/2, within(0.0001));
        assertThat(tanVal).isCloseTo(1/Math.sqrt(3), within(0.0001));
    }
    
    // Tests for gcd
    @Test
    void gcd_PositiveNumbers_ReturnsGreatestCommonDivisor() {
        int result = calculator.gcd(48, 18);
        assertThat(result).isEqualTo(6);
    }

    @Test
    void gcd_WithNegativeNumbers_ReturnsGreatestCommonDivisor() {
        int result = calculator.gcd(-48, 18);
        assertThat(result).isEqualTo(6);
    }
    
    @Test
    void gcd_SecondNumberZero_ReturnsAbsoluteFirst() {
        int result = calculator.gcd(5, 0);
        assertThat(result).isEqualTo(5);
    }
    
    // Tests for lcm
    @Test
    void lcm_ValidInputs_ReturnsLeastCommonMultiple() {
        int result = calculator.lcm(4, 6);
        assertThat(result).isEqualTo(12);
    }

    @Test
    void lcm_WithZero_ReturnsZero() {
        int result = calculator.lcm(0, 6);
        assertThat(result).isEqualTo(0);
    }
    
    // Tests for solveQuadratic
    @Test
    void solveQuadratic_CoefficientAZero_ThrowsException() {
        Throwable thrown = catchThrowable(() -> calculator.solveQuadratic(0, 4, 2));
        assertThat(thrown)
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Coefficient a cannot be zero for a quadratic equation.");
    }
    
    @Test
    void solveQuadratic_NegativeDiscriminant_ReturnsEmptyArray() {
        double[] roots = calculator.solveQuadratic(1, 2, 5);
        assertThat(roots).isEmpty();
    }
    
    @Test
    void solveQuadratic_ZeroDiscriminant_ReturnsSingleRoot() {
        double[] roots = calculator.solveQuadratic(1, 2, 1);
        assertThat(roots).hasSize(1);
        assertThat(roots[0]).isEqualTo(-1.0);
    }
    
    @Test
    void solveQuadratic_PositiveDiscriminant_ReturnsTwoRoots() {
        double[] roots = calculator.solveQuadratic(1, -3, 2);
        assertThat(roots).hasSize(2);
        // The roots are 1.0 and 2.0
        assertThat(roots).containsExactlyInAnyOrder(1.0, 2.0);
    }
    
    // Tests for permutation
    @Test
    void permutation_ValidInputs_ReturnsCorrectPermutation() {
        long result = calculator.permutation(5, 3);
        // 5*4*3 = 60
        assertThat(result).isEqualTo(60);
    }
    
    @Test
    void permutation_InvalidInputs_ThrowsException() {
        Throwable thrown = catchThrowable(() -> calculator.permutation(3, 5));
        assertThat(thrown)
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Invalid values for permutation.");
    }
    
    // Tests for combination
    @Test
    void combination_ValidInputs_ReturnsCorrectCombination() {
        long result = calculator.combination(5, 3);
        // 5C3 = 10
        assertThat(result).isEqualTo(10);
    }
    
    @Test
    void combination_InvalidInputs_ThrowsException() {
        Throwable thrown = catchThrowable(() -> calculator.combination(3, 5));
        assertThat(thrown)
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Invalid values for combination.");
    }
    
    // Tests for polynomialDerivative
    @Test
    void polynomialDerivative_ValidCoefficients_ReturnsDerivativeCoefficients() {
        // Polynomial: 3x^2 + 2x + 1 -> coefficients [3,2,1]
        double[] derivative = calculator.polynomialDerivative(new double[]{3, 2, 1});
        // Derivative: 6x + 2 -> coefficients [6, 2]
        assertThat(derivative).containsExactly(6.0, 2.0);
    }
    
    @Test
    void polynomialDerivative_SingleCoefficient_ReturnsZero() {
        // Constant polynomial: 5 -> derivative is 0.
        double[] derivative = calculator.polynomialDerivative(new double[]{5});
        assertThat(derivative).containsExactly(0);
    }
    
    @Test
    void polynomialDerivative_EmptyCoefficientArray_ThrowsException() {
        Throwable thrown = catchThrowable(() -> calculator.polynomialDerivative(new double[]{}));
        assertThat(thrown)
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Coefficient array is empty.");
    }
}