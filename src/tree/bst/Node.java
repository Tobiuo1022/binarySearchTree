package tree.bst;

class Node {

    // TODO: I think there are better method name than it.
    static LR determineLR(int value, Node comparisonNode) {
        return (value <= comparisonNode.value) ? LR.LEFT : LR.RIGHT;
    }

    static boolean isSameValue(int value, Node comparisonNode) {
        return (value == comparisonNode.value) ? true : false;
    }

    private int value;
    private Node parent;
    private Node right;
    private Node left;

    Node(int value, Node parent) {
        this.value = value;
        this.parent = parent;
    }

    boolean childExists(LR lr) {
        if (lr == LR.LEFT) {
            return (this.left == null) ? false : true;
        } else {
            return (this.right == null) ? false : true;
        }
    }

    boolean isLeaf() {
        return (this.left == null && this.right == null) ? true : false;
    }

    boolean isRoot() {
        return (this.parent == null) ? true : false;
    }

    Node getParent() {
        return this.parent;
    }

    LR askMyLrFor(Node parent) {
        LR myLr = Node.determineLR(this.value, parent);
        return myLr;
    }

    Node getChild(LR lr) {
        return (lr == LR.LEFT) ? this.left : this.right;
    }

    void setNewChild(int value, LR lr) {
        if (lr == LR.LEFT) {
            this.left = new Node(value, this);
        } else {
            this.right = new Node(value, this);
        }
    }

    void setChild(Node child, LR lr) {
        if (lr == LR.LEFT) {
            this.left = child;
        } else {
            this.right = child;
        }
        if (child != null) child.parent = this;
    }

    void setChildren(Node leftChild, Node rightChild) {
        this.left = leftChild;
        if (leftChild != null) leftChild.parent = this;
        this.right = rightChild;
        if (rightChild != null) rightChild.parent = this;
    }

    // This is danger. 
    void isolate() {
        if (this.parent != null) {
            LR thisLr = this.askMyLrFor(this.parent);
            this.parent.setChild(null, thisLr);
        }
        this.parent = null;

        this.setChildren(null, null);
    }

    int countChildren() {
        int count = 0;
        if (this.left != null) count++;
        if (this.right != null) count++;
        return count;
    }
}
