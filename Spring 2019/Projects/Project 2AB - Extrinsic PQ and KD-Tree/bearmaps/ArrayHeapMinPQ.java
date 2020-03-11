package bearmaps;

import java.util.ArrayList;
import java.util.HashMap;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {

    /** Java doesn't allow generic array creation, thus I use ArrayList.
     *
     */
    private ArrayList<Node> itemsArray;

    /** We can use a HashMap containing...
     * 1. Key: the item of each node
     * 2. Value: integer representing how many occurrences of each item is found.
     *
     * With a HashMap, we can determine whether a Key is contained using the
     * containsKey method. According to the official Oracle API docs, assuming the hash function
     * disperse the items properly among buckets, contains runtime is constant.
     */
    private HashMap<T, int> map;

    private class Node {
        private T item;
        private double priority;

        Node(T e, double p){
            item = e;
            priority = p;
        }

        T getItem() {
            return item;
        }

        double getPriority(){
            return priority;
        }

        void setPriority(double p){
            priority = p;
        }
    }
}
