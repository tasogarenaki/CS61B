import static org.junit.Assert.*;
import org.junit.Test;

/**
 * TestArrayDequeGold
 * @author Terry
 */
public class TestArrayDequeGold {

    @Test
    public void randomTestArrayDeque() {

        /* Number of elements to load to the Deque to test. */
        final int NUM_TEST = 10;

        /* Implementation Test and Reference. */
        StudentArrayDeque<Integer> dequeTest = new StudentArrayDeque<Integer>();
        ArrayDequeSolution<Integer> dequeRef = new ArrayDequeSolution<Integer>();

        /* Message to be displayed, containing the sequence of method calls. */
        String message = "";
        String methodCalled = "";

        /* add numbers to test and reference */
        for (int i = 0; i < NUM_TEST; i++) {
            Integer randInt = StdRandom.uniform(50);

            if (i % 2 == 0) {
                methodCalled = "addFirst(" + randInt + ")" +"\n";
                message += methodCalled;

                dequeTest.addFirst(randInt);
                dequeRef.addFirst(randInt);
            } else {
                methodCalled = "addLast(" + randInt + ")" +"\n";
                message += methodCalled;

                dequeTest.addLast(randInt);
                dequeRef.addLast(randInt);
            }
        }

        /* test student and reference. */
        while (!dequeRef.isEmpty()) {
            double randDouble = StdRandom.uniform();

            if (randDouble < 0.5) {
                methodCalled = "removeFirst()" + "\n";
                message += methodCalled;

                assertEquals(message, dequeRef.removeFirst(), dequeTest.removeFirst());
            } else {
                methodCalled = "removeLast()" + "\n";
                message += methodCalled;

                assertEquals(message, dequeRef.removeLast(), dequeTest.removeLast());
            }
        }
    }
}
