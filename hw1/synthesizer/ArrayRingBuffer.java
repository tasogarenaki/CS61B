package synthesizer;

import java.util.Iterator;

public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {

    /* Index for the next dequeue or peek. The least recently inserted item. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private final T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        first = 0;
        last = 0;
        fillCount = 0;
        rb = (T[]) new Object[capacity];
        this.capacity = rb.length;
    }

    /**
     * Adds x to the end of the ring buffer.
     */
    public void enqueue(T x) {
        if (isFull()) {
            throw new RuntimeException("Ring buffer overflow");
        } else {
            rb[last] = x;
            last = (last + 1) % capacity;
            fillCount += 1;
        }
    }

    /**
     * Dequeue the oldest item in the ring buffer.
     */
    public T dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        } else {
            T item = rb[first];
            rb[first] = null;
            first = (first + 1) % capacity;
            fillCount -= 1;
            return item;
        }
    }

    /**
     * Return oldest item, but don't remove it.
     */
    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer is empty");
        }
        return rb[first];
    }

    /**
     * @return an Iterator over the elements of the Ring Buffer.
     */
    @Override
    public Iterator<T> iterator() {
        return new KeyIterator();
    }

    private class KeyIterator implements Iterator<T> {
        private int temp;

        KeyIterator() {
            temp = first;
        }

        @Override
        public boolean hasNext() {
            return (temp != last);
        }

        @Override
        public T next() {
            T item = rb[temp];
            temp = (temp + 1) % capacity;
            return item;
        }
    }
}
