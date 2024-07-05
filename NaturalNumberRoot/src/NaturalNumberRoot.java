import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Program with implementation of {@code NaturalNumber} secondary operation
 * {@code root} implemented as static method.
 *
 * @author Elijah Paulman
 *
 */
public final class NaturalNumberRoot {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private NaturalNumberRoot() {
    }

    /**
     * Updates {@code n} to the {@code r}-th root of its incoming value.
     *
     * @param n
     *            the number whose root to compute
     * @param r
     *            root
     * @updates n
     * @requires r >= 2
     * @ensures n ^ (r) <= #n < (n + 1) ^ (r)
     */
    public static void root(NaturalNumber n, int r) {
        assert n != null : "Violation of: n is  not null";
        assert r >= 2 : "Violation of: r >= 2";

        // Initializes natural numbers for low, high, estimate, and power
        NaturalNumber lowEnough = n.newInstance();
        NaturalNumber tooHigh = n.newInstance();
        NaturalNumber estimate;
        NaturalNumber power;

        // Natural numbers for 1 and 2 used in calculations
        NaturalNumber one = new NaturalNumber2(1);
        NaturalNumber two = new NaturalNumber2(2);

        // Copies tooHigh to n which is a separate object
        tooHigh.copyFrom(n);
        // Adds 1 to tooHigh
        tooHigh.increment();

        // While loop condition variable
        boolean loop = true;
        while (loop) {
            // Sets estimate as new object equal to n
            estimate = n.newInstance();

            // Checks if n is 0 (tooHigh = n + 1)
            if (tooHigh.equals(one)) {
                loop = false;
            } else {
                // Resets tooHigh after if statement above
                tooHigh.add(lowEnough);
                // Estimate = (tooHigh + lowEnough) / 2
                estimate.add(tooHigh);
                estimate.add(lowEnough);
                estimate.divide(two);

                // Tests if estimate^r > n
                power = n.newInstance();
                power.copyFrom(estimate);
                power.power(r);

                // Runs if estimate^r > n
                if (power.compareTo(n) > 0) {
                    // Sets tooHigh to estimate
                    tooHigh.copyFrom(estimate);
                    // Runs if estimate^r == n
                } else if (power.compareTo(n) == 0) {
                    // Exits loop and returns estimate
                    lowEnough.copyFrom(estimate);
                    loop = false;
                    // Runs if estimate^r < n
                } else {
                    // Sets lowEnough to estimate
                    lowEnough.copyFrom(estimate);
                }
                // Checks if (tooHigh - lowEnough) > 1
                tooHigh.subtract(lowEnough);
            }
        }
        // Gives n the value of lowEnough
        n.transferFrom(lowEnough);
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleWriter out = new SimpleWriter1L();

        final String[] numbers = { "0", "1", "13", "1024", "189943527", "0",
                "1", "13", "4096", "189943527", "0", "1", "13", "1024",
                "189943527", "82", "82", "82", "82", "82", "9", "27", "81",
                "243", "143489073", "2147483647", "2147483648",
                "9223372036854775807", "9223372036854775808",
                "618970019642690137449562111",
                "162259276829213363391578010288127",
                "170141183460469231731687303715884105727" };
        final int[] roots = { 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 15, 15, 15, 15, 15,
                2, 3, 4, 5, 15, 2, 3, 4, 5, 15, 2, 2, 3, 3, 4, 5, 6 };
        final String[] results = { "0", "1", "3", "32", "13782", "0", "1", "2",
                "16", "574", "0", "1", "1", "1", "3", "9", "4", "3", "2", "1",
                "3", "3", "3", "3", "3", "46340", "46340", "2097151", "2097152",
                "4987896", "2767208", "2353973" };

        for (int i = 0; i < numbers.length; i++) {
            NaturalNumber n = new NaturalNumber2(numbers[i]);
            NaturalNumber r = new NaturalNumber2(results[i]);
            root(n, roots[i]);
            if (n.equals(r)) {
                out.println("Test " + (i + 1) + " passed: root(" + numbers[i]
                        + ", " + roots[i] + ") = " + results[i]);
            } else {
                out.println("*** Test " + (i + 1) + " failed: root("
                        + numbers[i] + ", " + roots[i] + ") expected <"
                        + results[i] + "> but was <" + n + ">");
            }
        }

        out.close();
    }

}
