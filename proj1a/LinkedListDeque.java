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

        /** Version 2 */
        /* 1) Creates a Node with (item, prev, next) with f.prev -> s and f.next -> s.next */
        // Node firstNode = new Node(item, sentinel, sentinel.next);
        /* 2) s.next.prev -> f  */
        // sentinel.next.prev = firstNode;
        /* 3) new s.next -> f */
        // sentinel.next = firstNode;
        // size += 1;
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
        return size == 0;
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
        for (Node<T> ptr = sentinel.next; ptr != sentinel; ptr = ptr.next) {
            System.out.print(ptr.item + " ");
        }
    }

    /**
     * Removes and returns the item at the front of the deque.
     * Uses helper function remove() with input sentinel.next
     * @return the first item in the deque, if no such item exists, returns null.
     */
    public T removeFirst() {
        return remove(sentinel.next);
    }

    /**
     * Removes and returns the item at the back of the deque.
     * Uses helper function with input sentinel.prev
     * @return the last item in the deque, if no such item exists, returns null.
     */
    public T removeLast() {
        return remove(sentinel.prev);
    }

    /**
     * Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * @param index of the item to be returned
     * @return      the item at the given index, or null if no such item exists
     */
    public T get(int index) {
        Node<T> ptr = sentinel.next;

        for (int i = 0; i != index; i++) {
            ptr = ptr.next;
        }

        return ptr.item;
    }

    /**
     * Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * Uses recursion with helper function
     * @param index of the item to be returned
     * @return      the item at the given index, or null if no such item exists
     */
    public T getRecursive(int index) {
        return helperGetRecursive(index, sentinel.next);
    }

    /**
     * Removes whether the first or last node and returns its value.
     * @param node node to be removed
     * @return     the value of the removed node
     */
    private T remove(Node<T> node) {
        if (size == 0) {
            return null;
        }

        Node<T> tempNext = node.next;
        Node<T> tempPrev = node.prev;

        tempPrev.next = tempNext;
        tempNext.prev = tempPrev;
        size--;

        return node.item;
    }

    /**
     * Gets the item at the given index.
     * @param index of the item to be returned
     * @param n     the node to count 
     * @return      the value at the node
     */
    private T helperGetRecursive(int index, Node<T> n) {
        if (index == 0) {
            return n.item;
        }
        return helperGetRecursive(index - 1, n.next);
    }







}
