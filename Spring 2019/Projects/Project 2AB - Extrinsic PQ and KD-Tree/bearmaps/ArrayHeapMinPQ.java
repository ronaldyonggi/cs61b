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
    private HashMap<T, Integer> maps;

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
        items.add(new Node(item, priority));
        maps.put(item, 1);
        fixIndex(size()-1);
    }

    @Override
    public boolean contains(T item){
        return maps.containsKey(item);
    }

    @Override
    public T getSmallest(){
        return items.get(0).getItem();
    }

    @Override
    public T removeSmallest(){
        T toBeReturned = getSmallest();
        items.remove(0);
        swap(0, size()-1);
        return toBeReturned;
    }

    @Override
    public int size(){
        return items.size();
    }

    @Override
    public void changePriority(T item, double priority){
        int toBeChangedIndex = findIndex(1, item);
        Node toBeChangedNode = getNodeAtIndex(toBeChangedIndex);
        toBeChangedNode.setPriority(priority);
        fixIndex(toBeChangedIndex);
    }

    /** ==== Array Representation of the Heap ====
     *
     * Items indexing starts from 0.
     *
     * Methods below are adapted from Heaps and PQs Lab CS61B Fall 2018.
     */

    private int getLeftChild(int index){
        return (index * 2) + 1;
    }

    private int getRightChild(int index){
        return (index * 2) + 2;
    }

    private int getParent(int index){
        return (index - 1) / 2;
    }

    private Node getNodeAtIndex(int index){
        return items.get(index);
    }

    private void setNodeAtIndex(int index, Node n){
        items.set(index, n);
    }

    private void swap(int index1, int index2){
        Node node1 = getNodeAtIndex(index1);
        Node node2 = getNodeAtIndex(index2);
        items.set(index1, node2);
        items.set(index2, node1);
    }

    /** Helper method that returns the index of the Node that contains
     * the input item. If the node isn't found, return -1.
     */
    private int findIndex(int index, T item){
        Node currentNode = getNodeAtIndex(index);
        /** If we've reached a null node, that means the node isn't found.
         * Return -1.
         */
        if (currentNode == null) return -1;

        /** If the item is found, return the index.
         *
         */
        else if (currentNode.getItem().equals(item)) return index;
        /** Otherwise, recursive call findIndex on both left child
         * and right child and return whichever's final result is not -1. If
         * both are -1, then it will return -1.
         */
        else {
            int leftSide = findIndex(getLeftChild(index), item);
            int rightSide = findIndex(getRightChild(index), item);
            if (leftSide == -1) return rightSide;
            else return leftSide;
        }
    }

    private boolean isRoot(int index){
        return getParent(index) == 0;
    }

    private boolean isLeaf(int index){
        /** In heaps, there can't exist a node with only a single right child.
         * Therefore to check whether a node is a leaf, simply check whether
         * it has a left child.
         */
        int leftChildIndex = getLeftChild(index);
        return getNodeAtIndex(leftChildIndex) == null;
    }

    private boolean parentMorePriority(int index){
        double parentPriority = getNodeAtIndex(getParent(index)).getPriority();
        double currentPriority = getNodeAtIndex(index).getPriority();
        /** Returns true if the priority of the parent Node less than the priority
         * of the current node. Returns false otherwise.
         */
        return parentPriority > currentPriority;
    }

    private boolean oneChildIsLess(int index){
        double currentPrio = getNodeAtIndex(index).getPriority();

        /** If the node has only one (left) child, compare the priority of the
         * left child with the current node's.
         */
        if (hasOnlyOneChild(index)){
            double leftChildPrio = getNodeAtIndex(getLeftChild(index)).getPriority();
            return leftChildPrio < currentPrio;
        }
        /** Otherwise if there are 2 children, get the priority of the
         * less of the 2 children and compare it with the current node's.
         */
        else {
            int greaterChildIndex = min(getLeftChild(index), getRightChild(index));
            return getNodeAtIndex(greaterChildIndex).getPriority() < currentPrio;
        }
    }

    /** Helper method to check whether whether a node has only one (left) child.
     * Simply check whether a node has a right child.
     */
    private boolean hasOnlyOneChild(int index){
        int rightChildIndex = getRightChild(index);
        Node rightChildNode = getNodeAtIndex(rightChildIndex);
        return rightChildNode == null;
    }

    private int max(int index1, int index2) {
        /** Obtain the priorities of both Nodes and compare them.
         * Returns the index of the node whose priority is greater.
         */
        Node node1 = getNodeAtIndex(index1);
        if (node1.equals(null)) return index2;
        Node node2 = getNodeAtIndex(index2);
        if (node2.equals(null)) return index1;
        double prio1 = node1.getPriority();
        double prio2 = node2.getPriority();

        if (prio1 > prio2) return index1;
        else return index2;
    }

    private int min(int index1, int index2) {
        if (max(index1, index2) == index1) return index2;
        else return index1;
    }

    private void swimUp(int index) {
        /** Notice that we have the 'getParent' method and 'swap' method
         * at our disposal. Simply use these methods to swap between the
         * node and its parent.
         */
        swap(index, getParent(index));
    }

    private void swimDown(int index){
        swap(index, min(getLeftChild(index), getRightChild(index)));
    }

    private void continuousSwimUp(int index){
        while ((!isRoot(index)) && parentMorePriority(index)) {
            swimUp(index);
            index = getParent(index);
        }
    }


    /** Helper method that continuously move down a node as long as:
     * 1. The node is not a leaf
     * 2. There exists a child that has greater priority than the node's priority.
     */
    private void continuousSwimDown(int index) {
        while ((!isLeaf(index)) && oneChildIsLess(index)) {
            int nextPosition = min(getLeftChild(index), getRightChild(index));
            swimDown(index);
            index = nextPosition;
        }
    }

    /** Helper method that fixes a node's index after it's mutated. Makes use of
     * the continuousBubbleUp and continuousBubbleDown method.
     */
    private void fixIndex(int index){
        /** As long as the current node is not a root and its priority is greater than its
         * parent, move up.
         */
        if ((!isRoot(index)) && parentMorePriority(index)) {
            continuousSwimUp(index);
        }
        /** Otherwise, as long as the current node is not a leaf and its
         * child / children's priority is greater than the priority of the current node, move
         * down.
         */
        else if ((!isLeaf(index)) && oneChildIsLess(index)){
            continuousSwimDown(index);
        }
        /** Otherwise do nothing.*/
    }
}
