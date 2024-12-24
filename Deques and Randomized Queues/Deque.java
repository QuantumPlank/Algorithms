import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private DNode<Item> first;
    private DNode<Item> last;
    private int dequeSize;

    private class DNode<Type> {
        private DNode<Type> prev;
        private DNode<Type> next;
        private Type item;
        public DNode(Type item){
            this.item = item;
            this.prev = null;
            this.next = null;
        }
        public Type getItem() {
            return item;
        }
        public void setItem(Type item) {
            this.item = item;
        }
        public DNode<Type> getNext() {
            return next;
        }
        public void setNext(DNode<Type> next) {
            this.next = next;
        }
        public DNode<Type> getPrev() {
            return prev;
        }
        public void setPrev(DNode<Type> prev) {
            this.prev = prev;
        }
    }
    // construct an empty deque
    public Deque() {
        this.first = null;
        this.last = null;
        this.dequeSize = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return dequeSize;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Null value");
        }
        DNode<Item> newItem = new DNode<Item>(item);
        if (first == null) {
            first = newItem;
            last = newItem;
        }
        else {
            DNode<Item> oldItem = first;
            oldItem.setPrev(newItem);
            newItem.setNext(oldItem);
            first = newItem;
        }
        dequeSize++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Null value");
        }
        DNode<Item> newItem = new DNode<Item>(item);
        if (last == null) {
            last = newItem;
            first = newItem;
        }
        else {
            DNode<Item> oldItem = last;
            oldItem.setNext(newItem);
            newItem.setPrev(oldItem);
            last = newItem;
        }
        dequeSize++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (size() == 0) {
            throw new NoSuchElementException("Empty deque");
        }
        DNode<Item> oldItem = first;
        first = first.getNext();
        if (first != null) {
            first.setPrev(null);
        }
        else {
            last = null;
        }
        dequeSize--;
        return oldItem.getItem();
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (size() == 0) {
            throw new NoSuchElementException("Empty deque");
        }
        DNode<Item> oldItem = last;
        last = last.getPrev();
        if (last != null) {
            last.setNext(null);
        }
        else {
            first = null;
        }
        dequeSize--;
        return oldItem.getItem();
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator(first);
    }
    
    private class DequeIterator implements Iterator<Item> {
        private DNode<Item> current;
        public DequeIterator(DNode<Item> first) {
            current = first;
        }
        @Override
        public boolean hasNext() {
            return current != null;
    
        }
        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements");
            }
            Item value = current.getItem();
            current = current.getNext();
            return value;
        }
        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove is not supported");
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();

        deque.addFirst(10);
        deque.addFirst(20);
        deque.addLast(30);
        deque.addLast(40);

        System.out.println("Size of deque: " + deque.size()); // Expected: 4

        // Testing removeFirst and removeLast
        System.out.println("Removed first: " + deque.removeFirst()); // Expected: 20
        System.out.println("Removed last: " + deque.removeLast());   // Expected: 40
        System.out.println("Size after removals: " + deque.size()); // Expected: 2

        // Iterating over deque
        System.out.print("Deque elements: ");
        for (Integer item : deque) {
            System.out.print(item + " ");  // Expected: 10 30
        }
    }
}

