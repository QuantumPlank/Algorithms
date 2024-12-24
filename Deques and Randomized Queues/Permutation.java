import java.util.Iterator;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> rQueue = new RandomizedQueue<String>();
        String data;
        while (!StdIn.isEmpty()) {
            data = StdIn.readString();
            rQueue.enqueue(data);
        }
        Iterator<String> rQueueIterator = rQueue.iterator();
        int counter = 0;
        while (counter < k) {
            String value = rQueueIterator.next();
            counter++;
            StdOut.println(value);
        }
    }
 }
 