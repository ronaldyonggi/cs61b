import java.util.ArrayList;

/** A Generic heap class. Unlike Java's priority queue, this heap doesn't just
  * store Comparable objects. Instead, it can store any type of object
  * (represented by type T) and an associated priority value.
  * @author CS 61BL Staff*/
public class ArrayHeap<T> {

    /* DO NOT CHANGE THESE METHODS. */

    /* An ArrayList that stores the nodes in this binary heap. */
    private ArrayList<Node> contents;

    /* A constructor that initializes an empty ArrayHeap. */
    public ArrayHeap() {
        contents = new ArrayList<>();
        contents.add(null);
    }

    /* Returns the number of elements in the priority queue. */
    public int size() {
        return contents.size() - 1;
    }

    /* Returns the node at index INDEX. */
    private Node getNode(int index) {
        if (index >= contents.size()) {
            return null;
        } else {
            return contents.get(index);
        }
    }

    /* Sets the node at INDEX to N */
    private void setNode(int index, Node n) {
        // In the case that the ArrayList is not big enough
        // add null elements until it is the right size
        while (index + 1 > contents.size()) {
            contents.add(null);
        }
        contents.set(index, n);
    }

    /* Returns and removes the node located at INDEX. */
    private Node removeNode(int index) {
        if (index >= contents.size()) {
            return null;
        } else {
            return contents.remove(index);
        }
    }

    /* Swap the nodes at the two indices. */
    private void swap(int index1, int index2) {
        Node node1 = getNode(index1);
        Node node2 = getNode(index2);
        this.contents.set(index1, node2);
        this.contents.set(index2, node1);
    }

    /* Prints out the heap sideways. Use for debugging. */
    @Override
    public String toString() {
        return toStringHelper(1, "");
    }

    /* Recursive helper method for toString. */
    private String toStringHelper(int index, String soFar) {
        if (getNode(index) == null) {
            return "";
        } else {
            String toReturn = "";
            int rightChild = getRightOf(index);
            toReturn += toStringHelper(rightChild, "        " + soFar);
            if (getNode(rightChild) != null) {
                toReturn += soFar + "    /";
            }
            toReturn += "\n" + soFar + getNode(index) + "\n";
            int leftChild = getLeftOf(index);
            if (getNode(leftChild) != null) {
                toReturn += soFar + "    \\";
            }
            toReturn += toStringHelper(leftChild, "        " + soFar);
            return toReturn;
        }
    }

    /* A Node class that stores items and their associated priorities. */
    public class Node {
        private T item;
        private double priority;

        private Node(T item, double priority) {
            this.item = item;
            this.priority = priority;
        }

        public T item() {
            return this.item;
        }

        public double priority() {
            return this.priority;
        }

        public void setPriority(double priority) {
            this.priority = priority;
        }

        @Override
        public String toString() {
            return this.item.toString() + ", " + this.priority;
        }
    }

    /* FILL IN THE METHODS BELOW. */

    /* Returns the index of the node to the left of the node at i. */
    private int getLeftOf(int i) {
        /**
         * Looking at the ArrayHeap constructor, it adds a null Node in the beginning.
         * Thus, our actual elements starts at index 1 since index 0 is reserved for
         * the null Node.
         *
         * Note that the getNode method also follows this convention. Thus the root element
         * is at index 1.
         */
        return i * 2;
    }

    /* Returns the index of the node to the right of the node at i. */
    private int getRightOf(int i) {
        return (i * 2) + 1;
    }

    /* Returns the index of the node that is the parent of the node at i. */
    private int getParentOf(int i) {
        /** Recall in Java, both odd numbers disregard the reminder when being divided.
         * For example, 5 / 2 = 2.
         *
         */
        return i / 2;
    }

    /* Adds the given node as a left child of the node at the given index. */
    private void setLeft(int index, Node n) {
        //YOUR CODE HERE
        int toBeChanged = getLeftOf(index);
        setNode(toBeChanged, n);
        fixIndex(toBeChanged);
    }

    /* Adds the given node as the right child of the node at the given index. */
    private void setRight(int index, Node n) {
        //YOUR CODE HERE
        int toBeChanged = getRightOf(index);
        setNode(toBeChanged, n);
        fixIndex(toBeChanged);
    }

    /** Returns the index of the node with smaller priority. Precondition: not
     * both nodes are null. */
    private int min(int index1, int index2) {
        //YOUR CODE HERE
        /** Obtain the priorities of both Nodes and compare them. Then return the nodes whose
         * priority is lower.
         */
        Node node1 = getNode(index1);
        if (node1.equals(null)) return index2;
        Node node2 = getNode(index2);
        if (node2.equals(null)) return index1;
        double prio1 = node1.priority();
        double prio2 = node2.priority();

        if (prio1 > prio2) return index2;
        else return index1;

    }

    /* Returns the Node with the smallest priority value, but does not remove it
     * from the heap. */
    public Node peek() {
        //YOUR CODE HERE
        /**
         * 'minIndex' keeps track of the index of the smallest priority Node. Initially set it to 1,
         * then loop through the rest of the nodes and compare their priority values. In the end,
         * we'll return the Node with the smallest priority.
         */
        int minPrioIndex = 1;
        for (int i = 2; i < size(); i++){
            minPrioIndex = min(minPrioIndex, i);
        }
        return getNode(minPrioIndex);
    }

    /* Bubbles up the node currently at the given index. */
    private void bubbleUp(int index) {
        /** Notice that we have the 'getParentOf' method and 'swap' method
         * at our disposal. Simply use this method to swap between the
         * node and its parent.
         */
        swap(index, getParentOf(index));
    }

    /* Bubbles down the node currently at the given index. */
    private void bubbleDown(int index) {
        /** Technically it's easier to swap positions simply using the swap method. However
         * for the sake of using the provided setLeft and setRight methods, use them
         * instead.
         */

        /** If there's no right node, swap with left node.
         */
        if (hasOnlyLeftChild(index)){
            Node currentNode = getNode(index);
            Node leftChild = getNode(getLeftOf(index));
            /** Set the current Node = left child Node first. Then set the left child
             * = current Node.
             */
            setNode(index, leftChild);
            setLeft(index, currentNode);
        }
        /** If there's no left node, swap with right node.
         */
        else if (hasOnlyRightChild(index)) {
            Node currentNode = getNode(index);
            Node rightChild = getNode(getRightOf(index));
            /** Set the current Node = right child Node first. Then set the right child
             * = current Node.
             */
            setNode(index, rightChild);
            setRight(index, currentNode);
        }
        else {
            /** If the node has 2 children, we choose the child with greater priority
             * between the 2 and switch it with the current node.
             */
            swap(index, max(getLeftOf(index), getRightOf(index)));
        }

    }

    /* Inserts an item with the given priority value. Same as enqueue, or offer. */
    public void insert(T item, double priority) {
        /** If we insert for the first time (e.g. start with an empty array), then
         * simply call setNode at index size() + 1. Remember it's size() + 1
         * because with the way the ArrayHeap is constructred, the size
         * includes the null Node at the beginning.
         *
         */
        if (size() == 0) setNode(size()+1, new Node(item, priority));
        else {
            /** Otherwise, per insertion rule of Heaps, we add the new Node at
             * index 1 more than the current size (we are adding a node!) and
             * make that Node swim up until it reaches the point where its parent has
             * higher priority.
             */
            setNode(size()+1, new Node(item, priority));
            /** Once we added a node, remember that:
             * 1. The total size of the heap increases
             * 2. The index of the node that we just added is equal to the updated
             * size of the heap.
             *
             * Now fix the position of the node that we just added, if needed.
             *
             */
            fixIndex(size());
        }
    }

    /* Returns the element with the smallest priority value, and removes it from
     * the heap. Same as dequeue, or poll. */
    public T removeMin() {
        /** Find the node with the smallest priority using the helper
         * smallestPrioIndex method. We call this method on index 1 since we
         * start scanning from the root.
         *
         */
        Node elementToBeReturned = removeNode(smallestPrioIndex(1));
        return elementToBeReturned.item();
    }

    /* Changes the node in this heap with the given item to have the given
     * priority. You can assume the heap will not have two nodes with the same
     * item. Check for item equality with .equals(), not == */
    public void changePriority(T item, double priority) {
        int toBeChangedIndex = findIndex(1, item);
        Node toBeChangedNode = getNode(toBeChangedIndex);
        toBeChangedNode.setPriority(priority);
        fixIndex(toBeChangedIndex);

    }

    /** ======= SELF WRITTEN METHODS ========
     *
     */

    /** Helper method that scans through the heap starting from input node and returns the
     * index of the node with the smallest priority.
     */
    private int smallestPrioIndex(int index){
        /** If the Node doesn't have any child, then we've arrived at the right node.
         */
        if (isLeaf(index)) return index;
        /** If the left child is null, continue calling smallestPrioIndex
         * on the node's right child.
         */
        else if (hasOnlyRightChild(index)) return smallestPrioIndex(getRightOf(index));
        /** If the right child is null, continue calling smallestPrioIndex on
         * the node's left child.
         */
        else if (hasOnlyLeftChild(index)) return smallestPrioIndex(getLeftOf(index));
        /**
         * Otherwise, there has to be 2 children! Return the smallest of the result of
         * calling smallestPrioIndex on both the left child and right child.
         */
        else return min(smallestPrioIndex(getLeftOf(index)), smallestPrioIndex(getRightOf(index)));
    }

    /** Helper method that returns the index of the Node that contains
     * the corresponding item. If the node doesn't exist, return -1.
     */
    private int findIndex(int index, T item){
        Node currentNode = getNode(index);
        if (currentNode.item().equals(item)) return index;
        else {
            Node leftChild = getNode(getLeftOf(index));
            Node rightChild = getNode(getRightOf(index));
            if (leftChild == null && rightChild == null) return -1;
            else if (leftChild == null) return findIndex(getRightOf(index), item);
            else if (rightChild == null) return findIndex(getLeftOf(index), item);
            else {
                int leftSide = findIndex(getLeftOf(index), item);
                int rightSide = findIndex(getRightOf(index), item);
                if (leftSide == -1) return rightSide;
                else return leftSide;
            }
        }
    }

    /** Helper method to check whether a node is a root.
     */
    private boolean isRoot(int index){
        return getParentOf(index) == 0;
    }

    /** Helper method to check whether a parent's priority is less than the
     * current node's.
     */
    private boolean parentLessPriority(int index){
        double parentPriority = getNode(getParentOf(index)).priority();
        double currentPriority = getNode(index).priority();
        /** Returns true if the priority of the parent Node less than the priority
         * of the current node. Returns false otherwise.
         */
        return parentPriority < currentPriority;
    }

    /** Helper method to check whether a node is a leaf (has no children).
     * In a heap, it is not allowed to have a node with
     * only a single right child. Either a node...
     * 1. ...has no children (a leaf node)
     * 2. ...has a single left children
     * 3. ...has 2 children, left and right.
     *
     */
    private boolean isLeaf(int index){
        int leftChildIndex = getLeftOf(index);
        Node leftChildNode = getNode(leftChildIndex);
        return leftChildNode == null;
    }

    /** Helper method that returns true if a node's child/children has a greater
     * priority than the current node's priority.
     */
    private boolean oneChildIsGreater(int index){
        double currentPrio = getNode(index).priority();

        /** If the node has only left child, compare the priority of that
         * left child with the current node's.
         */
        if (hasOnlyLeftChild(index)){
            double leftChildPrio = getNode(getLeftOf(index)).priority();
            return leftChildPrio > currentPrio;
        }
        /** If the node has only right child, compare the priority of that
         * left child with the current node's.
         */
        else if (hasOnlyRightChild(index)){
            double rightChildPrio = getNode(getRightOf(index)).priority();
            return rightChildPrio > currentPrio;
        }
        /** Otherwise if there are 2 children, get the priority of the
         * greatest of the 2 and compare it with the current node's.
         */
        else {
            int greaterChildIndex = max(getLeftOf(index), getRightOf(index));
            return getNode(greaterChildIndex).priority() > currentPrio;
        }
    }

    /** Helper method to check whether a node has only left child.
     */
    private boolean hasOnlyLeftChild(int index){
        int leftChildIndex = getLeftOf(index);
        int rightChildIndex = getRightOf(index);
        Node leftChildNode = getNode(leftChildIndex);
        Node rightChildNode = getNode(rightChildIndex);
        /** Returns true if the left child is not null and the right child is null.
         * Returns false otherwise.
         */
        return (leftChildNode != null && rightChildNode == null);
    }

    /** Returns the index of the node with greater priority. Precondition: not
     * both nodes are null. */
    private int max(int index1, int index2) {
        /** Obtain the priorities of both Nodes and compare them.
         * Returns the index of the node whose priority is greater.
         */
        Node node1 = getNode(index1);
        Node node2 = getNode(index2);
        if (node1.equals(null)) return index2;
        if (node2.equals(null)) return index1;
        double prio1 = node1.priority();
        double prio2 = node2.priority();

        if (prio1 > prio2) return index1;
        else return index2;

    }

    /** Helper method that continuously move up a node as long as:
     * 1. It is not a root
     * 2. Its priority is greater than its parent.
     */
    private void continuousBubbleUp(int index){
        while ((!isRoot(index)) && parentLessPriority(index)) {
            bubbleUp(index);
            index = getParentOf(index);
        }
    }

    /** Helper method that continuously move down a node as long as:
     * 1. The node is not a leaf
     * 2. Its child/children's priority is greater than the node's priority.
     */
    private void continuousBubbleDown(int index) {
        while ((!isLeaf(index)) && oneChildIsGreater(index)) {
            /** If there's only left child, swap with the left child and update
             * toBeChangedIndex to be the index of the left child.
             */
            if (hasOnlyLeftChild(index)) {
                int nextPosition = getLeftOf(index);
                bubbleDown(index);
                index = nextPosition;
            }
            /** If there's only right child, swap with the right child and update
             * input 'index' to be the index of the right child.
             */
            else if (hasOnlyRightChild(index)) {
                int nextPosition = getRightOf(index);
                bubbleDown(index);
                index = nextPosition;
            }
            /** Otherwise, there are 2 children. Swap with whichever has greater priority and
             * update input 'index' to be the index of the child with greater priority.
             */
            else {
                int nextPosition = max(getLeftOf(index), getRightOf(index));
                bubbleDown(index);
                index = nextPosition;
            }
        }
    }

    /** Helper method that fixes a node's index after it's mutated. Makes use of
     * the continuousBubbleUp and continuousBubbleDown method.
     */
    private void fixIndex(int index){
        /** As long as the current node is not a root and its priority is greater than its
         * parent, move up.
         */
        if ((!isRoot(index)) && parentLessPriority(index)) {
            continuousBubbleUp(index);
        }
        /** Otherwise, as long as the current node is not a leaf and its
         * child / children's priority is greater than the priority of the current node, move
         * down.
         */
        else if ((!isLeaf(index)) && oneChildIsGreater(index)){
            continuousBubbleDown(index);
        }
        /** Otherwise do nothing.*/
        else;
    }
}
