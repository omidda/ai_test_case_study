package com.jamk.thesis.modules;


public class Calculator {

    // Basic arithmetic methods
    public int add(int a, int b) {
        return a + b;
    }

    public int subtract(int a, int b) {
        return a - b;
    }

    public int multiply(int a, int b) {
        return a * b;
    }

    public double divide(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("Divider cannot be zero.");
        }
        return (double) a / b;
    }

    // Factorial (recursive)
    public int factorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Negative number not allowed.");
        }
        if (n == 0 || n == 1) {
            return 1;
        }
        return n * factorial(n - 1);
    }

    // Fibonacci (recursive)
    public int fibonacci(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Negative index not allowed.");
        }
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    // Advanced Functions

    // Exponentiation using Math.pow
    public double power(double base, double exponent) {
        return Math.pow(base, exponent);
    }

    // Logarithm with specified base
    public double logarithm(double value, double base) {
        if (value <= 0 || base <= 0 || base == 1) {
            throw new IllegalArgumentException("Invalid value or base for logarithm.");
        }
        return Math.log(value) / Math.log(base);
    }

    // Trigonometric functions
    public double sin(double angleRadians) {
        return Math.sin(angleRadians);
    }

    public double cos(double angleRadians) {
        return Math.cos(angleRadians);
    }

    public double tan(double angleRadians) {
        return Math.tan(angleRadians);
    }

    // Greatest Common Divisor (GCD) using the Euclidean algorithm
    public int gcd(int a, int b) {
        if (b == 0) {
            return Math.abs(a);
        }
        return gcd(b, a % b);
    }

    // Least Common Multiple (LCM)
    public int lcm(int a, int b) {
        if (a == 0 || b == 0) {
            return 0;
        }
        return Math.abs(a * b) / gcd(a, b);
    }

    // Solve quadratic equation: ax^2 + bx + c = 0
    // Returns an array of real roots; returns an empty array if no real roots exist.
    public double[] solveQuadratic(double a, double b, double c) {
        if (a == 0) {
            throw new IllegalArgumentException("Coefficient a cannot be zero for a quadratic equation.");
        }
        double discriminant = b * b - 4 * a * c;
        if (discriminant < 0) {
            // No real roots
            return new double[0];
        } else if (discriminant == 0) {
            double root = -b / (2 * a);
            return new double[]{root};
        } else {
            double sqrtDisc = Math.sqrt(discriminant);
            double root1 = (-b + sqrtDisc) / (2 * a);
            double root2 = (-b - sqrtDisc) / (2 * a);
            return new double[]{root1, root2};
        }
    }

    // Permutations: P(n, r) = n! / (n - r)!
    public long permutation(int n, int r) {
        if (n < 0 || r < 0 || r > n) {
            throw new IllegalArgumentException("Invalid values for permutation.");
        }
        long result = 1;
        for (int i = 0; i < r; i++) {
            result *= (n - i);
        }
        return result;
    }

    // Combinations: C(n, r) = n! / (r! * (n - r)!)
    public long combination(int n, int r) {
        if (n < 0 || r < 0 || r > n) {
            throw new IllegalArgumentException("Invalid values for combination.");
        }
        return permutation(n, r) / factorial(r);
    }

    // Polynomial derivative:
    // Given an array of coefficients [a_n, a_(n-1), ..., a_0] representing the polynomial
    // a_n*x^n + a_(n-1)*x^(n-1) + ... + a_0, return the derivative's coefficients.
    public double[] polynomialDerivative(double[] coefficients) {
        if (coefficients == null || coefficients.length == 0) {
            throw new IllegalArgumentException("Coefficient array is empty.");
        }
        int degree = coefficients.length - 1;
        if (degree == 0) {
            return new double[]{0}; // The derivative of a constant is zero.
        }
        double[] derivative = new double[degree];
        for (int i = 0; i < degree; i++) {
            derivative[i] = coefficients[i] * (degree - i);
        }
        return derivative;
    }
}

