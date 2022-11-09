/**
 * LinkedListDeque
 * Deque Data Structure
 * @param <T> the type of objects to be stored in the deque
 * @author    TC
 */
public class LinkedListDeque<T> {
    private Node<T> sentinel;
    private int size;

    /**
     * A node has a pointer to the next and the previous node in the linked list.
     */
    private static class Node<T> {
        T item;
        Node<T> next;
        Node<T> prev;

        /* empty node */
        Node() { }

        Node(T item) {
            this.item = item;
        }
    }

    /**
     * Creates an empty linked list deque with a sentinel node null.
     */
    public LinkedListDeque() {
        sentinel = new Node<T>();
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    /**
     * Adds an item to the front of the deque.
     * @param item the item to be added at the beginning of the deque.
     */
    public void addFirst(T item) {
        Node<T> firstNode = new Node<T>(item);
        /* save the next node of sentinel in `next`, which should be pos 1. */
        Node<T> next = sentinel.next;
        /* copy `next`. */
        firstNode.next = next;
        /* points firstNode is the prev node of `next` */
        next.prev = firstNode;
        /* relocated the sentinel node */
        sentinel.next = firstNode;
        firstNode.prev = sentinel;

        size += 1;
    }

    /**
     * Adds an item to the back of the deque.
     * @param item the item to be added at the end of the deque.
     */
    public void addLast(T item) {
        Node<T> lastNode = new Node<T>(item);
        /* the prev node of the sentinel node points to the last node. */
        Node<T> prev = sentinel.prev;

        lastNode.prev = prev;
        prev.next = lastNode;
        sentinel.prev = lastNode;
        lastNode.next = sentinel;

        size += 1;
    }

    /**
     * @return ture if deque is empty, false otherwise
     */
    public boolean isEmpty() {

    }

    /**
     * @return the number of items in the deque.
     */
    public int size() {

    }

    /**
     * Prints the items in the deque from first to last, separated by a space.
     */
    public void printDeque() {

    }

    /**
     * Removes and returns the item at the front of the deque.
     * @return the first item in the deque, if no such item exists, returns null.
     */
    public T removeFirst() {

    }

    /**
     * Removes and returns the item at the back of the deque.
     * @return the last item in the deque, if no such item exists, returns null.
     */
    public T removeLast() {

    }

    /**
     * Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * @param index of the item to be returned
     * @return      the item at the given index, or null if no such item exists
     */
    public T get(int index) {

    }

    /**
     * Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * Uses recursion.
     * @param index of the item to be returned
     * @return      the item at the given index, or null if no such item exists
     */
    public T getRecursive(int index) {

    }









}
