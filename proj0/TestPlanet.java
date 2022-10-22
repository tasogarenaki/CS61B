/**
 * Tests Planet
 */
public class TestPlanet {

    /**
     * Tests CalcNetForceExertedByX and CalcNetForceExertedByY (pairwise force)
     */
    public static void main(String[] args) {
        //checkCalcNetForceExertedByX();
        //checkCalcNetForceExertedByY();
        checkCalcNetForceExerted();
    }

    /**
     *  Checks whether or not two Doubles are equal and prints the result.
     *
     *  @param  expected    Expected double
     *  @param  actual      Double received
     *  @param  label       Label for the 'test' case
     *  @param  eps         Tolerance for the double comparison.
     */
    public static void checkEquals(double actual, double expected, String label, double eps) {
        if (Math.abs(expected - actual) <= eps * Math.max(expected, actual)) {
            System.out.println("PASS: " + label + ": Expected " + expected + " and you gave " + actual);
        } else {
            System.out.println("FAIL: " + label + ": Expected " + expected + " and you gave " + actual);
        }
    }

    /* creates planets */
    private static Planet p1 = new Planet(1.0, 1.0, 3.0, 4.0, 5.0, "mercury.gif");
    private static Planet p2 = new Planet(4.0, 5.0, 3.0, 4.0, 5.0, "venus.gif");
    private static Planet[] planets = new Planet[]{p1, p2};

    /**
     *  Checks the Planet class to make sure checkCalcNetForceExertedByX / -Y works.
     */
    private static void checkCalcNetForceExerted() {
        checkEquals(p1.calcNetForceExertedByX(planets), -p2.calcNetForceExertedByX(planets),
                "calcNetForceExertedByX()", 0.01);
        checkEquals(p1.calcNetForceExertedByY(planets), -p2.calcNetForceExertedByY(planets),
                "calcNetForceExertedByY()", 0.01);

    }
}
