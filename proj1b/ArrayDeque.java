/**
 * ArrayDeque
 * Double Ended Queue (deque) data structure.
 * @param <T> is the type of objects to be stored in the deque.
 * @author TC
 *
 * Uses a circular array with 2 pointers.
 * 1) The initial length of array should be 8.
 * 2) If the Array is full, its size should increassed by GROWTH_FACTOR.
 * 3) If the array's length is greater or equal to 16 and
 *    (the number of elements / the array's length) is
 *    smaller than the usageFacor (25%), then array is reduced.
 */
public class ArrayDeque<T> implements Deque<T> {

    private static final int INITIAL_LENGTH = 8;
    private static final int GROWTH_FACTOR = 2;
    private static final int DOWN_USAGE_FACTOR = 4;

    private T[] elements;
    private int head;   // head pointer at first item
    private int tail;   // end pointer at last item
    private int size;   // current length

    /**
     * Creates an empty array deque with head and tail start
     * at the same position 0.
     */
    public ArrayDeque() {
        elements = (T[]) new Object[INITIAL_LENGTH];
        head = 0;
        tail = 0;
        size = 0;
    }

    /**
     * Adds an item to the head of the deque.
     * @param item is the item to be added at the front of the deque.
     */
    @Override
    public void addFirst(T item) {
        /* Null values are not allowed in ArrayDeque. */
        if (item == null) {
            throw new NullPointerException();
        }

        /*
         * circular, to locate the position of the head pointer.
         * e.g. if element.length = 4 and head = 0 then 3 % 4 = 3,
         * so item adds on position 3 (head points at the end of array),
         * and every time an item is added, it will start from back n-1, n-2 and so on.
         */
        head = (head - 1 + elements.length) % elements.length;
        elements[head] = item;
        size++;

        /* Change the capacity of the array. */
        if (head == tail) {
            calculateSize(size * GROWTH_FACTOR);
        }
    }

    /**
     * Adds an item to the back of the deque.
     * @param item is the item to be added at the end of the deque.
     */
    public void addLast(T item) {
        /* Null values are not allowed in ArrayDeque. */
        if (item == null) {
            throw new NullPointerException();
        }

        elements[tail] = item;
        /* circular adds the tail's position by 1. */
        tail = (tail + 1) % elements.length;
        size++;

        if (head == tail) {
            calculateSize(size * GROWTH_FACTOR);
        }
    }

    /**
     * @return true if deque is empty, false otherwise.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * @return the number of elements in the deque.
     */
    public int size() {
        return size;
    }

    /**
     * Prints the elements in the deque from first to last, separated by a space.
     */
    public void printDeque() {
        for (int i = 0; i < size; i++) {
            System.out.print(get(i) + " ");
        }
    }

    /**
     * Removes and returns the first item in the deque.
     * @return the first item in the deque, if no such item exists, returns null.
     */
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }

        /* Gets the value of the head element. */
        T item = elements[head];

        elements[head] = null;
        /*
         * After removing the value of the head element,
         * the head pointer's position should move (+1).
         */
        head = (head + 1) % elements.length;
        size--;

        /*
         * Reduce the array to save the memory
         * ((the number of elements / the array's length) < usage Factor).
         */
        if (size > INITIAL_LENGTH && size < elements.length / DOWN_USAGE_FACTOR) {
            calculateSize(elements.length / GROWTH_FACTOR);
        }
        return item;
    }

    /**
     * Removes and returns the item to the back of the deque.
     * @return the last item in the deque, if no such item exists, returns null.
     */
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }

        /* First move the tail pointer's position (-1). */
        tail = ((tail - 1) + elements.length) % elements.length;

        /* Gets the value of the tail element. */
        T item = elements[tail];

        elements[tail] = null;
        size--;

        /* Reduce the array to save the memory. */
        if (size > INITIAL_LENGTH && size < elements.length / DOWN_USAGE_FACTOR) {
            calculateSize(elements.length / GROWTH_FACTOR);
        }
        return item;
    }

    /**
     * Gets the item from the given index, which starts after head pointer,
     * because the head pointer is where the array starts.
     * @param index of the item to be returned (!= pointer's position)
     * @return      the item at the given index, or null if no such item exists
     */
    public T get(int index) {
        if (isEmpty() || index > size) {
            return null;
        }

        int theIndex = (head + index) % elements.length;
        return elements[theIndex];
    }

    /**
     * Double the size of the array with newSize, if array is full,
     * which is head equals tail.
     * Note: ArrayDeque can not contain null.
     * @param newSize is the new length of the array.
     */
    private void calculateSize(int newSize) {
        T[] resized = (T[]) new Object[newSize];
        /* V1
         * Adds items direct after head, avoid null.
         */
        for (int i = 0; i < size; i++) {
            int src = (head + i) % elements.length;
            int dest = i;
            resized[dest] = elements[src];
        }

        /* V2: only copy non-null items.
         * 1) If the number of the items plus the index of the head pointer
         * is less than the length of the array, it means
         * there's nulls before the head pointer.
         * So only non-null items after the head pointer should be copied.
         * 2) Otherwise, just copy the items on the left and right sides
         * of the head pointer separately.
         */
        // if (size + head < elements.length) {
        //     System.arraycopy(elements, head, resized, 0, size);
        // } else {
        //     /* Copy left side items of the head pointer. */
        //     System.arraycopy(elements, head, resized, 0, elements.length - head);
        //     /* Copy right side items of the head pointer. */
        //     System.arraycopy(elements, 0, resized, elements.length - head, head);
        // }

        elements = resized;
        head = 0;
        tail = size;
    }

}
