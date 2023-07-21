package synthesizer;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer arb = new ArrayRingBuffer(4);

        /* testEmpty */
        assertTrue(arb.isEmpty());
        arb.enqueue(1);
        assertFalse(arb.isEmpty());

        /* testFillcount */
        arb.enqueue(2);
        arb.enqueue(3);
        arb.enqueue(4);
        assertTrue(arb.isFull());

        /* Test same Value. */
        assertEquals(1, arb.dequeue());
        assertEquals(3, arb.fillCount());

        /* testPeek */
        assertEquals(2, arb.peek());

        /* testIterator */
        Iterator<Integer> temp = arb.iterator();
        assertTrue(temp.hasNext());
        assertEquals((Integer) 2, temp.next());
    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
}
