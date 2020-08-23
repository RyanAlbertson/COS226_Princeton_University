package main;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Creates a double-ended queue data type, using a doubly linked list.
 *
 * @param <Item> Generic data type to stores in the src.Deque.
 * @author Ryan Albertson
 */
public class Deque<Item> implements Iterable<Item> {


    // Reference to the oldest Node in linked list.
    private Node front;

    // Reference to the newest Node in linked list.
    private Node back;

    // Number of Nodes in linked list.
    private int size;


    /**
     * Implements a node for use in a doubly linked list.
     */
    private class Node {


        // Store generic data type stored at this Node.
        private Item item;

        // Reference to previous Node in linked list.
        private Node prev;

        // Reference to next Node in linked list.
        private Node next;


        /**
         * Constructor for the first Node (when src.Deque is empty).
         *
         * @param item Generic data to store at this Node.
         */
        private Node(Item item) {

            this.prev = null;
            this.item = item;
            this.next = null;
        }


        /**
         * Constructor for any additional Nodes.
         *
         * @param prev Reference to the Node behind this Node.
         * @param item Generic data to store at this Node.
         * @param next Reference to the Node in front of this Node.
         */
        private Node(Node prev, Item item, Node next) {

            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }


    /**
     * Implements an Iterator such that items in a src.Deque can be iterated over
     * from front to back.
     */
    private class DequeIterator implements Iterator<Item> {


        // Stores current Node in the src.Deque. (Returned by next()).
        private Node current;


        /**
         * Constructs iterator.
         */
        public DequeIterator() {

            current = front;
        }


        /**
         * Returns true if the iterator has a next item, false otherwise.
         */
        @Override
        public boolean hasNext() {

            return current != null;
        }


        /**
         * Returns Item stored at current Node, then iterates to the next Node.
         */
        @Override
        public Item next() {

            // Check if next item exists.
            if (!hasNext()) {

                throw new NoSuchElementException();
            }

            // Get current Item.
            Item item = current.item;

            // Update reference of current to the Node behind current Node.
            current = current.prev;

            return item;
        }
    }


    /**
     * Constructs an empty deque.
     */
    public Deque() {

        // Initialize size to 0.
        size = 0;
    }


    /**
     * Returns true if deque is empty, false otherwise.
     */
    public boolean isEmpty() {

        return size == 0;
    }


    /**
     * Returns the number of items in the deque.
     */
    public int size() {

        return size;
    }


    /**
     * Adds a generic data type to the front of the deque.
     *
     * @param item Generic data to be added.
     * @throws IllegalArgumentException If item is null.
     */
    public void addFirst(Item item) {

        // Check if given Item is null.
        if (item == null) {

            throw new IllegalArgumentException("Cannot add NULL to src.Deque.");
        }

        // If this is the first added item.
        if (isEmpty()) {

            // Initialize front Node reference.
            front = back = new Node(item);
        }
        else {

            // Insert this item in front of the current front Node.
            front = new Node(front, item, null);

            // Link the inserted Node.
            front.prev.next = front;
        }

        size++;
    }


    /**
     * Adds a generic data type to the back of the deque.
     *
     * @param item Generic data to be added.
     * @throws IllegalArgumentException If item is null.
     */
    public void addLast(Item item) {

        // Check if given Item is null.
        if (item == null) {

            throw new IllegalArgumentException("Cannot add NULL to src.Deque.");
        }

        // If this is the first added item.
        if (isEmpty()) {

            // Initialize back Node reference.
            back = front = new Node(item);
        }
        else {

            // Insert this item behind the current back Node.
            back = new Node(null, item, back);

            // Link the inserted Node.
            back.next.prev = back;
        }

        size++;
    }


    /**
     * Removes the first item in the deque.
     *
     * @throws NoSuchElementException If src.Deque is empty.
     */
    public void removeFirst() {

        // Check if src.Deque is empty.
        if (isEmpty()) {

            throw new NoSuchElementException();
        }

        // Replace front reference with the Node behind the removed Node.
        front = front.prev;

        // Check if there's a Node behind this Node.
        if (size() > 1) {

            // Unlink the removed front Node from that Node.
            front.next = null;
        }

        size--;
    }


    /**
     * Removes the last item in the deque.
     *
     * @throws NoSuchElementException If src.Deque is empty.
     */
    public void removeLast() {

        // Check if src.Deque is empty.
        if (isEmpty()) {

            throw new NoSuchElementException();
        }

        // Replace back reference with the Node in front of the removed Node.
        back = back.next;

        // Check if there's a Node behind this Node.
        if (size() > 1) {

            // Unlink the removed back Node from that Node.
            back.prev = null;
        }

        size--;
    }


    /**
     * Returns an Iterator over items in a src.Deque, in order from front to back.
     */
    @Override
    public Iterator<Item> iterator() {

        return new DequeIterator();
    }


    public static void main(String[] args) {
        
    }
}
