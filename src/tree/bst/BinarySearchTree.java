package tree.bst;

public class BinarySearchTree {
    private Node root = null;

    public boolean valueExists(int targetValue) {
        return (_searchNode(targetValue) == null) ? false : true;
    }

    public void insertNode(int value) {
        // This Block is excuted when there is no Node in the BST.
        if (this.root == null) {
            this.root = new Node(value, null);
            return;
        }

        Node comparisonNode = this.root;
        while (true) {
            LR lr = Node.determineLR(value, comparisonNode);
            if (comparisonNode.childExists(lr) == false) {
                comparisonNode.setNewChild(value, lr);
                return;
            }
            comparisonNode = comparisonNode.getChild(lr);
        }
    }

    public void deleteNode(int targetValue) {
        Node targetNode = _searchNode(targetValue);
        int childrenNum = targetNode.countChildren();

        switch (childrenNum) {
            case 0: // This BST has no Nodes. 
                _deleteLeaf(targetNode);
                return;

            case 1:
                _deleteNode1(targetNode);
                return;

            case 2:
                _deleteNode2(targetNode);
                return;
        }
    }

    private void _deleteLeaf(Node targetNode) {
        targetNode.isolate();
        if (targetNode.isRoot()) this.root = null;
    }

    private void _deleteNode1(Node targetNode) {
        Node parent = targetNode.getParent();
        LR targetNodeLr = targetNode.askMyLrFor(parent);
        Node child;
        if (targetNode.childExists(LR.LEFT)) {
            child = targetNode.getChild(LR.LEFT);
        } else {
            child = targetNode.getChild(LR.RIGHT);
        }

        targetNode.isolate();

        if (targetNode.isRoot()) {
            this.root = child;
        } else {
            parent.setChild(child, targetNodeLr);
        }
    }

    private void _deleteNode2(Node targetNode) {
        Node parent = targetNode.getParent();
        LR targetNodeLr = targetNode.askMyLrFor(parent);
        Node leftChild = targetNode.getChild(LR.LEFT);
        Node rightChild = targetNode.getChild(LR.RIGHT);

        Node leftMax = _searchMaxInLeftSubTree(targetNode);
        Node leftMaxParent = leftMax.getParent();
        Node leftMaxLeftChild = leftMax.getChild(LR.LEFT);

        targetNode.isolate();
        
        // case 1. 
        if (leftMaxParent.equals(targetNode)) {
            if (targetNode.isRoot()) {
                this.root = leftMax;
            } else {
                parent.setChild(leftMax, targetNodeLr);
            }
            leftMax.setChild(rightChild, LR.RIGHT);
            return;
        } 

        // case 2. 
        // When leftMaxParent does not equal targetNode.
        leftMax.isolate();
        if (targetNode.isRoot()) {
            this.root = leftMax;
        } else {
            parent.setChild(leftMax, targetNodeLr);
        }
        leftMax.setChildren(leftChild, rightChild);

        if (leftMaxLeftChild != null) {
            leftMaxParent.setChild(leftMaxLeftChild, LR.RIGHT);
        }
    }

    // If there are multiple Nodes having targetValue, the first Node found is returned. 
    // If there are no Nodes having targetValue, null is returned. 
    private Node _searchNode(int targetValue) {
        Node comparisonNode = this.root;
        while (comparisonNode != null) {
            if (Node.isSameValue(targetValue, comparisonNode)) {
                break;
            }
            LR lr = Node.determineLR(targetValue, comparisonNode);
            comparisonNode = comparisonNode.getChild(lr);
        }
        Node targetNode = comparisonNode;
        return targetNode;
    }

    private Node _searchMaxInLeftSubTree(Node targetNode) {
        Node node = targetNode.getChild(LR.LEFT);
        while (node.childExists(LR.RIGHT)) {
            node = node.getChild(LR.RIGHT);
        }
        Node maxInLeftSubTree = node;
        return maxInLeftSubTree;
    }
}
