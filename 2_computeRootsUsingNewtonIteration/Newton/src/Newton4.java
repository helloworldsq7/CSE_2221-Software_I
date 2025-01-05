import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * The {@code Newton4} class provides a method to calculate the square root of a
 * non-negative number using the Newton-Raphson iterative method with a
 * user-defined precision (epsilon). It interacts with the user through simple
 * input and output streams to perform multiple calculations based on user input
 * until the user decides to quit by entering a negative number.
 *
 * <p>
 * This class utilizes the {@link SimpleReader} and {@link SimpleWriter}
 * interfaces for handling input and output operations.
 * </p>
 *
 * <p>
 * Example usage:
 * </p>
 * <pre>
 * Enter the value of ε:
 * 0.0001
 * Enter a positive number x:
 * 25
 * The square root of 25.0 is approximately 5.0
 * Enter another positive number x:
 * -1
 * </pre>
 *
 * <p>
 * This version allows the user to specify the precision for the square root
 * calculation, providing greater flexibility and control over the accuracy of
 * the result. The program continues to prompt the user for numbers to calculate
 * their square roots until a negative number is entered, which terminates the
 * program.
 * </p>
 *
 * @author Spencer Qin
 */
public final class Newton4 {

    /**
     * Private constructor to prevent instantiation of the {@code Newton4}
     * class.
     */
    private Newton4() {
    }

    /**
     * Calculates the square root of a given non-negative number using the
     * Newton-Raphson iterative method with a specified precision (epsilon). The
     * calculation continues until the relative error is less than the square of
     * epsilon.
     *
     * <p>
     * If the input {@code x} is zero, the method immediately returns zero. For
     * positive inputs, it iteratively approximates the square root. If a
     * negative number is provided, the method throws an
     * {@link IllegalArgumentException}.
     * </p>
     *
     * @param x
     *            the non-negative number whose square root is to be calculated
     * @param epsilon
     *            the precision factor determining the stopping condition of the
     *            iteration
     * @return the approximate square root of {@code x} with the specified
     *         precision
     * @throws IllegalArgumentException
     *             if {@code x} is negative or if {@code epsilon} is
     *             non-positive
     */
    private static double sqrt(double x, double epsilon) {
        // Handle the case when x is zero
        if (x == 0) {
            return 0;
        }

        // Handle negative inputs by throwing an exception
        if (x < 0) {
            throw new IllegalArgumentException(
                    "Cannot compute square root of a negative number.");
        }

        // Handle non-positive epsilon by throwing an exception
        if (epsilon <= 0) {
            throw new IllegalArgumentException("Epsilon must be a positive number.");
        }

        double r = x;

        // Ensure relative error is no more than epsilon squared
        while (relativeError(r, x) >= epsilon * epsilon) {
            r = (r + x / r) / 2;
        }
        return r;
    }

    /**
     * Computes the relative error between the square of the current
     * approximation and the original number.
     *
     * @param r
     *            the current approximation of the square root
     * @param x
     *            the original number
     * @return the relative error as a double
     */
    private static double relativeError(double r, double x) {
        // Calculate the error (Compare r^2 with x)
        double error = (r * r) - x;
        // Get the absolute value of the error
        if (error < 0) {
            error = -error;
        }
        return error / x;
    }

    /**
     * The main method interacts with the user to perform square root
     * calculations. It prompts the user to enter a precision value (epsilon)
     * and then repeatedly asks for non-negative numbers to calculate their
     * square roots until the user decides to quit by entering a negative
     * number.
     *
     * <p>
     * The method handles invalid inputs by notifying the user and prompting
     * again.
     * </p>
     *
     * @param args
     *            command line arguments (not used)
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        // Prompt the user to enter the value of epsilon (precision)
        out.println("Enter the value of ε (precision):");
        double epsilon;
        while (true) {
            try {
                epsilon = in.nextDouble();
                if (epsilon <= 0) {
                    out.println("Epsilon must be a positive number. Please enter again:");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                out.println("Invalid input. Please enter a numeric value for ε:");
                // Clear the invalid input
                in.nextLine();
            }
        }

        // Prompt the user to enter the first positive number x
        out.println("Enter a positive number x:");
        double x;
        while (true) {
            try {
                x = in.nextDouble();
                if (x < 0) {
                    out.println("Negative number entered. Terminating the program.");
                    break;
                } else {
                    double result = sqrt(x, epsilon);
                    out.println(
                            "The square root of " + x + " is approximately " + result);
                }
            } catch (NumberFormatException e) {
                out.println("Invalid input. Please enter a numeric value for x:");
                // Clear the invalid input
                in.nextLine();
                continue;
            }

            // Prompt the user to enter another positive number x
            out.println(
                    "Enter another positive number x (enter a negative number to quit):");
        }

        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
