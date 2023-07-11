package synthesizer;

/**
 * AbstractBoundedQueue.
 * Inherits behavior from BoundedQueue interface.
 */
public abstract class AbstractBoundedQueue<T> implements BoundedQueue<T> {

    protected int fillCount; // Number of items currently in the Queue
    protected int capacity; // Capacity of the Queue

    /* Returns the capacity of the Queue. */
    @Override
    public int capacity() {
        return capacity;
    }

    /* Returns the number of items currently in the Queue. */
    @Override
    public int fillCount() {
        return fillCount;
    }
}
