import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private SNode<Item> last;
    private int rQueueSize;
    // construct an empty randomized queue
    public RandomizedQueue() {
        last = null;
        rQueueSize = 0;
    }

    private class SNode<T> {
        private SNode<T> next;
        private T value;
        SNode(T value) {
            this.value = value;
        }
        public T getValue() {
            return value;
        }
        public void setValue(T value) {
            this.value = value;
        }
        public SNode<T> getNext() {
            return next;
        }
        public void setNext(SNode<T> next) {
            this.next = next;
        }
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return last == null;
    }

    // return the number of items on the randomized queue
    public int size() {
        return rQueueSize;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Null argument");
        }
        SNode<Item> newNode = new SNode<Item>(item);
        newNode.setNext(last);
        last = newNode;
        rQueueSize++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("RQueue empty");
        }
        int index = StdRandom.uniformInt(rQueueSize);
        rQueueSize--;
        if (index == 0) {
            Item value = last.getValue();
            last = last.getNext();
            return value;
        }
        else {
            SNode<Item> previous = last;
            for (int counter = 0; counter < index - 1; counter++) {
                previous = previous.getNext();
            }
            Item value = previous.getNext().getValue();
            previous.setNext(previous.getNext().getNext());
            return value;
        }
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("RQueue empty");
        }
        int index = StdRandom.uniformInt(rQueueSize);
        if (index == 0) {
            Item value = last.getValue();
            return value;
        }
        else {
            SNode<Item> previous = last;
            for (int counter = 0; counter < index - 1; counter++) {
                previous = previous.getNext();
            }
            Item value = previous.getNext().getValue();
            return value;
        }
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RDequeIterator();
    }
    
    private class RDequeIterator implements Iterator<Item> {
        private SNode<Item> current;
        private int[] order;
        private int index;
        public RDequeIterator() {
            current = null;
            order = new int[size()];
            for (int i = 0; i < size(); i++) {
                order[i] = i;
            }
            StdRandom.shuffle(order);
            index = 0;
        }
        @Override
        public boolean hasNext() {
            return index < size();
        }
        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements");
            }
            current = last;
            for (int counter = 0; counter < order[index]; counter++) {
                current = current.getNext();
            }
            Item value = current.getValue();
            index++;
            return value;
        }
        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove is not supported");
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        // Create a new randomized queue
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        // Test 1: Check if the queue is empty
        StdOut.println("Is the queue empty? " + rq.isEmpty());  // Expected: true
        // Test 2: Add elements to the queue (enqueue)
        StdOut.println("Enqueueing elements: 1, 2, 3, 4, 5");
        rq.enqueue(1);
        rq.enqueue(2);
        rq.enqueue(3);
        rq.enqueue(4);
        rq.enqueue(5);
        StdOut.println("Queue size: " + rq.size());  // Expected: 5
        StdOut.println("Is the queue empty? " + rq.isEmpty());  // Expected: false

        // Test 3: Sample an element (without removing it)
        StdOut.println("Sampled element: " + rq.sample());  // Expected: random element between 1 and 5

        // Test 4: Dequeue some elements (remove and return a random item)
        StdOut.println("Dequeued element: " + rq.dequeue());  // Expected: random element between 1 and 5
        StdOut.println("Queue size after dequeue: " + rq.size());  // Expected: 4

        // Test 5: Iterate through the queue elements in random order using the iterator
        StdOut.println("Iterating through the queue:");
        for (int num : rq) {
            StdOut.println(num);  // Expected: Random order of the remaining elements in the queue
        }

        // Test 6: Dequeue all remaining elements
        StdOut.println("Dequeuing all elements:");
        while (!rq.isEmpty()) {
            StdOut.println("Dequeued element: " + rq.dequeue());  // Expected: All remaining elements in random order
        }
        StdOut.println("Queue size after dequeuing all elements: " + rq.size());  // Expected: 0
        StdOut.println("Is the queue empty? " + rq.isEmpty());  // Expected: true

        // Test 7: Handle edge cases with empty queue
        try {
            StdOut.println("Attempting to sample from empty queue...");
            rq.sample();  // Expected: NoSuchElementException
        } catch (NoSuchElementException e) {
            StdOut.println("Caught expected exception: " + e.getMessage());
        }

        try {
            StdOut.println("Attempting to dequeue from empty queue...");
            rq.dequeue();  // Expected: NoSuchElementException
        } catch (NoSuchElementException e) {
            StdOut.println("Caught expected exception: " + e.getMessage());
        }

        // Test 8: Attempting to enqueue a null item
        try {
            StdOut.println("Attempting to enqueue a null item...");
            rq.enqueue(null);  // Expected: IllegalArgumentException
        } catch (IllegalArgumentException e) {
            StdOut.println("Caught expected exception: " + e.getMessage());
        }
    }

}