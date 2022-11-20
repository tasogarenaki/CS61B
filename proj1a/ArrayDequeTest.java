/** Performs some basic array deque tests. */
public class ArrayDequeTest {
    /* Utility method for printing out empty checks. */
    public static boolean checkEmpty(boolean expected, boolean actual) {
        if (expected != actual) {
            System.out.println("isEmpty() returned " + actual + ", but expected: " + expected);
            return false;
        }
        return true;
    }

    /* Utility method for printing out empty checks. */
    public static boolean checkSize(int expected, int actual) {
        if (expected != actual) {
            System.out.println("size() returned " + actual + ", but expected: " + expected);
            return false;
        }
        return true;
    }

    /* Utility method for printing out get() checks. */
    public static boolean checkGet(Integer expected, Integer actual) {
        if (expected != actual) {
            System.out.println("get() returned " + actual + ", but expected: " + expected);
            return false;
        }
        return true;
    }

    /* Prints a nice message based on whether a test passed.
     * The \n means newline. */
    public static void printTestStatus(boolean passed) {
        if (passed) {
            System.out.println("Test passed!\n");
        } else {
            System.out.println("Test failed!\n");
        }
    }

    /** Adds a few things to the list, checking isEmpty() and size() are correct,
     * finally printing the results.
     *
     * && is the "and" operation. */
    public static void addIsEmptySizeTest() {
        System.out.println("Running add/isEmpty/Size test.");

        ArrayDeque<String> lld1 = new ArrayDeque<String>();

        boolean passed = checkEmpty(true, lld1.isEmpty());

        lld1.addFirst("front");

        // The && operator is the same as "and" in Python.
        // It's a binary operator that returns true if both arguments true, and false otherwise.
        passed = checkSize(1, lld1.size()) && passed;
        passed = checkEmpty(false, lld1.isEmpty()) && passed;

        lld1.addLast("middle");
        passed = checkSize(2, lld1.size()) && passed;

        lld1.addLast("back");
        passed = checkSize(3, lld1.size()) && passed;

        System.out.println("Printing out deque: ");
        lld1.printDeque();
        System.out.println();
        printTestStatus(passed);

    }

    /** Adds an item, then removes an item, and ensures that dll is empty afterwards. */
    public static void addRemoveTest() {

        System.out.println("Running add/remove test.");

        ArrayDeque<Integer> lld1 = new ArrayDeque<Integer>();
        // should be empty
        boolean passed = checkEmpty(true, lld1.isEmpty());

        /* Tests resize the array. */
        for (int i = 0; i < 1024; i++) {
            lld1.addFirst(9999);
        }
        // should not be empty
        passed = checkEmpty(false, lld1.isEmpty()) && passed;

        for (int i = 0; i < 1024; i++) {
            lld1.removeFirst();
        }
        // should be empty
        passed = checkEmpty(true, lld1.isEmpty()) && passed;

        for (int i = 0; i < 1024; i++) {
            lld1.addLast(9999);
        }
        // should not be empty
        passed = checkEmpty(false, lld1.isEmpty()) && passed;

        for (int i = 0; i < 1024; i++) {
            lld1.removeLast();
        }
        // should be empty
        passed = checkEmpty(true, lld1.isEmpty()) && passed;


        /* Some add and remove Tests then print the deque out. */
        lld1.addFirst(1);
        lld1.addFirst(2);
        //lld1.addLast(null);
        lld1.addLast(3);
        lld1.addLast(4);
        lld1.addLast(5);
        lld1.addLast(6);
        lld1.addLast(7);
        // should not be empty
        passed = checkEmpty(false, lld1.isEmpty()) && passed;

        lld1.removeFirst();
        // should be size 6
        passed = checkSize(6, lld1.size()) && passed;

        lld1.removeLast();
        lld1.removeLast();
        // should be size 4
        passed = checkSize(4, lld1.size()) && passed;

        System.out.println("Printing out deque (should print: 1 3 4 5):");
        lld1.printDeque();

        System.out.println();
        printTestStatus(passed);
    }

    /** Adds 3 items, then gets items at certain position. */
    public static void addGetTest() {

        System.out.println("Running get test.");

        ArrayDeque<Integer> lld1 = new ArrayDeque<Integer>();

        lld1.addFirst(1);
        lld1.addFirst(2);
        lld1.addLast(3);
        lld1.addLast(4);
        // should not be empty
        boolean passed = checkEmpty(false, lld1.isEmpty());

        System.out.println("Testing get().");
        // return null if index is beyond the length
        passed = checkGet(null, lld1.get(4)) && passed;
        passed = checkGet(2, lld1.get(0)) && passed;
        passed = checkGet(1, lld1.get(1)) && passed;
        passed = checkGet(4, lld1.get(3)) && passed;

        printTestStatus(passed);
    }

    /** Check if its contains null. */
    public static void someTest() {
        System.out.println("Running some tests.");

        ArrayDeque<Integer> lld1 = new ArrayDeque<Integer>();
        // should be empty
        boolean passed = checkEmpty(true, lld1.isEmpty());

        /* T1 */
        lld1.addLast(0);
        lld1.get(0);
        // should be 0
        passed = checkSize(0, lld1.removeFirst()) && passed;


        /* T2 */
        lld1.addFirst(0);
        lld1.addFirst(1);
        lld1.addFirst(2);
        lld1.addFirst(3);
        lld1.addFirst(4);
        lld1.addFirst(5);
        // should be 0
        passed = checkSize(0, lld1.removeLast()) && passed;

        printTestStatus(passed);
    }

    public static void main(String[] args) {
        System.out.println("Running tests.\n");
        addIsEmptySizeTest();
        addRemoveTest();
        addGetTest();
        someTest();
    }

}
