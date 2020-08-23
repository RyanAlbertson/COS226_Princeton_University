package main;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Creates a queue object such that items are added to the back but removed randomly.
 *
 * @param <Item> Generic non-primative data type to store in the src.RandomizedQueue.
 * @author Ryan Albertson
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    // Stores the queue data.
    private Item[] randomizedQueue;

    // Tracks number of items in queue.
    private int size;

    // Tracks max item that can be stored in queue.
    private int capacity;


    /**
     * Implements an Iterator such that items in a src.RandomizedQueue can be
     * iterated over in random order.
     */
    private class RandomizedQueueIterator implements Iterator<Item> {


        // Make a copy such that the Fihser-Yates shuffle doesn't mutate original.
        private Item[] randomizedQueueCopy;

        // Track number of remaining items in src.RandomizedQueue.
        private int remaining;


        /**
         * Constructs iterator.
         */
        public RandomizedQueueIterator() {

            randomizedQueueCopy = randomizedQueue.clone();
            remaining = size;
        }

        /**
         * Returns true if the iterator has a next item, false otherwise.
         */
        public boolean hasNext() {

            return remaining >= 1;
        }


        /**
         * Uses Fisher-Yates shuffle to choose a random item.
         */
        public Item next() {

            // Check if next item exists.
            if (!hasNext()) {

                throw new NoSuchElementException();
            }

            // Choose random index from the remaining items.
            int randomIdx = (StdRandom.uniform(remaining));

            Item returnItem = randomizedQueueCopy[randomIdx];

            // Swap next item with the last item in src.RandomizedQueue.
            swap(randomizedQueueCopy, randomIdx, remaining - 1);

            remaining--;

            return returnItem;
        }


        /**
         * remove() method is not currently supported for this iterator.
         */
        public void remove() {

            throw new UnsupportedOperationException("remove() not supported.");
        }

    }


    /**
     * Constructs an empty randomized queue.
     */
    @SuppressWarnings("unchecked")
    public RandomizedQueue() {

        randomizedQueue = (Item[]) new Object[1];
        size = 0;
        capacity = 1;
    }


    /**
     * Returns true if queue is empty, false otherwise.
     */
    public boolean isEmpty() {

        return size == 0;
    }


    /**
     * Returns the number of items in the queue.
     */
    public int size() {

        return size;
    }


    /**
     * Resizes the randomized queue by doubling its capacity.
     */
    private void addCapacity() {

        randomizedQueue = Arrays.copyOfRange(randomizedQueue, 0, capacity * 2);

        capacity = randomizedQueue.length;
    }


    /**
     * Resizes the randomized queue by halving its capacity.
     */
    private void subtractCapacity() {

        randomizedQueue = Arrays.copyOfRange(randomizedQueue, 0, capacity / 2);

        capacity = randomizedQueue.length;
    }


    /**
     * Adds the given Item to the back of the queue. Also increases the queue
     * capacity if necessary.
     *
     * @param item Generic data to be added to the queue.
     * @throws IllegalArgumentException Given item is null.
     */
    public void enqueue(Item item) {

        // Check if given Item is null.
        if (item == null) {

            throw new IllegalArgumentException("Cannot add NULL to src.Deque.");
        }

        // Check if the queue needs to be resized.
        if (size == capacity) {

            addCapacity();
        }

        size++;

        // Add the Item to the back of the queue.
        randomizedQueue[size - 1] = item;
    }


    /**
     * Swaps two items within a given array.
     *
     * @param arr Generic array.
     * @param i   Index of first item.
     * @param j   Index of second item.
     */
    private void swap(Item[] arr, int i, int j) {

        Item temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }


    /**
     * Finds and returns a random item in the queue, then removes it from queue.
     * ALso reduces the queue capacity if necessary.
     *
     * @throws java.util.NoSuchElementException Queue is currently empty.
     */
    public Item dequeue() {

        // Check if queue is empty.
        if (isEmpty()) {

            throw new NoSuchElementException("src.RandomizedQueue is currently empty.");
        }

        // Resize queue if less than 1/4 of the space is being used.
        if (size <= capacity / 4) {

            subtractCapacity();
        }

        // Randomly choose an index within the queue.
        int randomIdx = StdRandom.uniform(size);

        // Store the random item so it can be returned.
        Item dequedItem = randomizedQueue[randomIdx];

        // Swap item at randomIdx with the item at backIdx.
        swap(randomizedQueue, randomIdx, size - 1);

        size--;

        return dequedItem;
    }


    /**
     * Finds and returns a random item in the queue.
     *
     * @throws java.util.NoSuchElementException Queue is currently empty.
     */
    public Item sample() {

        // Check if queue is empty.
        if (isEmpty()) {

            throw new NoSuchElementException("src.RandomizedQueue is currently empty.");
        }

        // Randomly choose an index within the queue.
        int randomIdx = StdRandom.uniform(size);

        return randomizedQueue[randomIdx];

    }


    public Iterator<Item> iterator() {

        return new RandomizedQueueIterator();
    }


    public static void main(String[] args) {

    }
}

