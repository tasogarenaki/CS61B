/**
 * ArrayDeque
 * Double Ended Queue (deque) data structure.
 * @param <T> is the type of objects to be stored in the deque.
 * @author TC
 *
 * Uses a circular array with 2 pointers.
 * 1) The initial length of array should be 8.
 * 2) If the Array is full, its size is increassed by growthFactor.
 * 3) If the array's length is greater or equal to 16 and (the number of items / the array's length) is
 *    smaller than the usageFacor (25%), array is reduced.
 */
public class ArrayDeque<T> {

    private static final int initialLength = 8;
    private static final int growthFactor = 2;
    private static final double usageFactor = 0.25;

    private T[] items;
    private int head;   // head pointer at first item
    private int tail;   // end pointer at last item
    private int size;   // current length

    /**
     * Creates an empty array deque.
     */
    public ArrayDeque() {
        items = (T[]) new Object[initialLength];
        head = 0;
        tail = 0;
        size = 0;
    }

    /**
     * Adds an item to the head of the deque.
     * @param item the item to be added at the beginning of the deque.
     */
    public void addFirst(T item) {
        /* circular, to located the position of the head pointer.
         * e.g. if items.length = 4 and head = 0 then 3 % 4 = 3,
         * so item adds on position 3 (head points the end of array),
         * and every time adds an item will started from back n-1, n-2 and so on. */
        head = (head - 1 + items.length) % items.length;
        items[head] = item;
        size++;
        /* Change the capacity of the array */
        //if (size == items.length) {
        if (head == tail) {
            doubleSize(size * growthFactor);
        }
    }

    /**
     * Adds an item to the back of the deque.
     * @param item the item to be added at the end of the deque.
     */
    public void addLast(T item) {


    }

    /**
     * @return ture if deque is empty, false otherwise
     */
    public boolean isEmpty() {
        return true;

    }

    /**
     * @return the number of items in the deque.
     */
    public int size() {
        return size;

    }

    /**
     * Prints the items in the deque from first to last, separated by a space.
     */
    public void printDeque() {

    }

    /**
     * Removes and returns the item at the front of the deque.
     * Uses helper function remove() with input sentinel.next
     * @return the first item in the deque, if no such item exists, returns null.
     */
    public T removeFirst() {
        return null;

    }

    /**
     * Removes and returns the item at the back of the deque.
     * Uses helper function with input sentinel.prev
     * @return the last item in the deque, if no such item exists, returns null.
     */
    public T removeLast() {
        return null;

    }

    /**
     * Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * @param index of the item to be returned
     * @return      the item at the given index, or null if no such item exists
     */
    public T get(int index) {
        return null;

    }

    /**
     * Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * Uses recursion with helper function
     * @param index of the item to be returned
     * @return      the item at the given index, or null if no such item exists
     */
    public T getRecursive(int index) {
        return null;

    }

    /**
     * Removes whether the first or last node and returns its value.
     * @param node node to be removed
     * @return     the value of the removed node
     */
    // private T remove(LinkedListDeque.Node<T> node) {
    //     return null;
    // }

    /**
     * Gets the item at the given index.
     * @param index of the item to be returned
     * @param n     the node to count
     * @return      the value at the node
     */
    // private T helperGetRecursive(int index, LinkedListDeque.Node<T> n) {
    //     return null;
    //
    // }

    /**
     * Double the size of the array with newSize, if array is full,
     * which is head equals tail.
     * @param newSize is the new length of the array.
     */
    private void doubleSize(int newSize) {
        T[] resized = (T[]) new Object[newSize];
        /* Copy Array */
        // for (int i = 0; i < size; i++) {
        //     int src = (head + i) % size;
        //     int desk = i;
        //     resized[desk] = items[src];
        // }
        /* Copy left side items of head */
        System.arraycopy(items, head, resized, 0, items.length - head);
        /* Copy right side items of head */
        System.arraycopy(items, 0, resized, items.length - head, head);
        items = resized;
        head = 0;
        tail = size;
    }

}
