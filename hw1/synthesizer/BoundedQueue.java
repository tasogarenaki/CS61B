package synthesizer;

import java.util.Iterator;

/**
 * BoundedQueue Interface.
 * Items can only be enqueued at the back of the queue,
 * and can only be dequeued from the front of the queue.
 * Nothing is allowed to enqueue if the queue is full.
 */
public interface BoundedQueue<T> extends Iterable<T> {

    int capacity();     // return size of the buffer
    int fillCount();    // return number of items currently in the buffer
    void enqueue(T x);  // add item x to the end
    T dequeue();        // delete and return item from the front
    T peek();           // return (but do not delete) item from the front

    /* Return whether the Queue is empty or not */
    default boolean isEmpty() {
        return fillCount() == 0;
    }

    /* Return whether the Queue is full or not */
    default boolean isFull() {
        return fillCount() == capacity();
    }
}
