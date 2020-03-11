package bearmaps;

import java.util.ArrayList;
import java.util.HashMap;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {

    /** Java doesn't allow generic array creation, thus I use ArrayList.
     *
     */
    private ArrayList<Node> items;

    /** We can use a HashMap containing...
     * 1. Key: the item of each node
     * 2. Value: integer representing how many occurrences of each item is found.
     *
     * With a HashMap, we can determine whether a Key is contained using the
     * containsKey method. According to the official Oracle API docs, assuming the hash function
     * disperse the items properly among buckets, contains runtime is constant.
     */
    private HashMap<T, int> maps;

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

    private ArrayHeapMinPQ() {
        items = new ArrayList<>();
    }

    @Override
    public void add (T item, double priority) {

    }

    @Override
    public boolean contains(T item){
        return maps.containsKey(item);
    }

    @Override
    public T getSmallest(){
    }

    @Override
    public T removeSmallest(){

    }

    @Override
    public int size(){
        return items.size();
    }

    @Override
    public void changePriority(T item, double priority){

    }

    /** ==== Array Representation of the Heap ====
     *
     * Items indexing starts from 0.
     */

    private int getLeftChild(int index){
        return (index * 2) + 1;
    }

    private int getRightChild(int index){
        return (index * 2) + 2;
    }

    private getParent(int index){
        return (index - 1) / 2;
    }

    private Node getNodeAtIndex(int index){
        return items.get(index);
    }

    private void swap(int index1, int index2){
        Node node1 = getNodeAtIndex(index1);
        Node node2 = getNodeAtIndex(index2);
        items.set(index1, node2);
        items.set(index2, node1);
    }
}
