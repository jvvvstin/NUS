/**
 * Scapegoat Tree class
 *
 * This class contains an implementation of a Scapegoat tree.
 */

public class SGTree {
    /**
     * TreeNode class.
     *
     * This class holds the data for a node in a binary tree.
     *
     * Note: we have made things public here to facilitate problem set grading/testing.
     * In general, making everything public like this is a bad idea!
     *
     */
    public static class TreeNode {
        int key;
        public TreeNode left = null;
        public TreeNode right = null;
        public TreeNode parent = null;
        public double weight = 1;

        TreeNode(int k) {
            key = k;
        }
    }

    // Root of the binary tree
    public TreeNode root = null;

    /**
     * Counts the number of nodes in the subtree rooted at node
     *
     * @param node the root of the subtree
     * @return number of nodes
     */
    public int countNodes(TreeNode node) {
        // TODO: Implement this
        int total = 0;
        if (node == null) {
            return total;
        }
        if (node.left != null) {
            total += countNodes(node.left);
        }
        if (node.right != null) {
            total += countNodes(node.right);
        }
        return total + 1;
    }

    /**
     * Builds an array of nodes in the subtree rooted at node
     *
     * @param node the root of the subtree
     * @return array of nodes
     */
    public TreeNode[] enumerateNodes(TreeNode node) {
        // TODO: Implement this
        if (node == null) {
            return new TreeNode[0];
        }
        TreeNode[] nodes = new TreeNode[countNodes(node)];
        int[] pointer = { 0 };
        return inOrderTraversal(node, nodes, pointer);
    }

    public TreeNode[] inOrderTraversal(TreeNode node, TreeNode[] nodes, int[] pointer) {
        if (node.left != null) {
            inOrderTraversal(node.left, nodes, pointer);
        }

        nodes[pointer[0]] = node;
        pointer[0]++;

        if (node.right != null) {
            inOrderTraversal(node.right, nodes, pointer);
        }

        return nodes;
    }

    /**
     * Builds a tree from the list of nodes
     * Returns the node that is the new root of the subtree
     *
     * @param nodeList ordered array of nodes
     * @return the new root node
     */
    public TreeNode buildTree(TreeNode[] nodeList) {
        // TODO: Implement this
        int begin = 0;
        int end = nodeList.length - 1;

        return buildTreeRecursive(nodeList, begin, end);
    }

    public TreeNode buildTreeRecursive(TreeNode[] nodeList, int begin, int end) {
        if (begin > end) {
            return null;
        }

        int mid = begin + (end - begin) / 2;

        TreeNode root = nodeList[mid];
        TreeNode leftSubtree = buildTreeRecursive(nodeList, begin, mid - 1);
        TreeNode rightSubtree = buildTreeRecursive(nodeList, mid + 1, end);

        root.left = leftSubtree;
        root.right = rightSubtree;
        double leftWeight = 0;
        double rightWeight = 0;

        if (root.left != null) {
            root.left.parent = root;
            leftWeight = root.left.weight;
        }

        if (root.right != null) {
            root.right.parent = root;
            rightWeight = root.right.weight;
        }

        root.weight = leftWeight + rightWeight + 1;
        return root;
    }

    /**
     * Determines if a node is balanced. If the node is balanced, this should return true. Otherwise, it should return
     * false. A node is unbalanced if either of its children has weight greater than 2/3 of its weight.
     *
     * @param node a node to check balance on
     * @return true if the node is balanced, false otherwise
     */
    public boolean checkBalance(TreeNode node) {
        // TODO: Implement this
        boolean isBalanced = true;
        if (node.left != null) {
            isBalanced = node.left.weight <= 2.0 / 3.0 * node.weight;
        }
        if (isBalanced) {
            if (node.right != null) {
                isBalanced = node.right.weight <= 2.0 / 3.0 * node.weight;
            }
        }
        return isBalanced;
    }

    /**
     * Rebuilds the subtree rooted at node
     *
     * @param node the root of the subtree to rebuild
     */
    public void rebuild(TreeNode node) {
        // Error checking: cannot rebuild null tree
        if (node == null) {
            return;
        }

        TreeNode p = node.parent;
        TreeNode[] nodeList = enumerateNodes(node);
        TreeNode newRoot = buildTree(nodeList);

        if (p == null) {
            root = newRoot;
        } else if (node == p.left) {
            p.left = newRoot;
        } else {
            p.right = newRoot;
        }

        newRoot.parent = p;
    }

    /**
     * Inserts a key into the tree
     *
     * @param key the key to insert
     */
    public void insert(int key) {
        if (root == null) {
            root = new TreeNode(key);
            return;
        }

        insert(key, root);
        TreeNode highestUnbalancedNode = getHighestUnbalancedNode(key, root);
        if (highestUnbalancedNode != null) {
            rebuild(highestUnbalancedNode);
        }

    }

    public TreeNode getHighestUnbalancedNode(int key, TreeNode node) {
        if (!checkBalance(node)) {
            return node;
        }

        TreeNode highestUnbalancedNode = null;
        if (key <= node.key) {
            if (node.left != null) {
                highestUnbalancedNode = getHighestUnbalancedNode(key, node.left);
            }
        } else {
            if (node.right != null) {
                highestUnbalancedNode = getHighestUnbalancedNode(key, node.right);
            }
        }

        return highestUnbalancedNode;
    }

    // Helper method to insert a key into the tree
    private void insert(int key, TreeNode node) {
        node.weight++;
        if (key <= node.key) {
            if (node.left == null) {
                node.left = new TreeNode(key);
                node.left.parent = node;
            } else {
                insert(key, node.left);
            }
        } else {
            if (node.right == null) {
                node.right = new TreeNode(key);
                node.right.parent = node;
            } else {
                insert(key, node.right);
            }
        }
    }

    // Simple main function for debugging purposes
    public static void main(String[] args) {
        SGTree tree = new SGTree();
        for (int i = 0; i < 100; i++) {
            tree.insert(i);
        }
        tree.rebuild(tree.root);

    }
}
