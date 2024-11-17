package dictionary;

import java.util.ArrayList;
import java.util.List;

public class BPlusTree {
    private Node root;
    private int order;

    public BPlusTree(int order) {
        this.root = new Node(true);
        this.order = order;
    }

    public void insert(String key, String value) {
        Node root = this.root;
        if (root.keys.size() == order - 1) {
            Node newNode = new Node(false);
            newNode.children.add(root);
            splitChild(newNode, 0, root);
            root = newNode;
            this.root = newNode;
        }
        insertNonFull(root, key, value);
    }

    private void insertNonFull(Node node, String key, String value) {
        int i = node.keys.size() - 1;
        if (node.isLeaf) {
            node.keys.add("");
            node.values.add("");
            while (i >= 0 && key.compareTo(node.keys.get(i)) < 0) {
                node.keys.set(i + 1, node.keys.get(i));
                node.values.set(i + 1, node.values.get(i));
                i--;
            }
            node.keys.set(i + 1, key);
            node.values.set(i + 1, value);
        } else {
            while (i >= 0 && key.compareTo(node.keys.get(i)) < 0) {
                i--;
            }
            i++;
            Node child = node.children.get(i);
            if (child.keys.size() == order - 1) {
                splitChild(node, i, child);
                if (key.compareTo(node.keys.get(i)) > 0) {
                    i++;
                }
            }
            insertNonFull(node.children.get(i), key, value);
        }
    }

    private void splitChild(Node parent, int index, Node child) {
        int mid = order / 2;
        Node newNode = new Node(child.isLeaf);
        parent.keys.add(index, child.keys.get(mid));
        parent.children.add(index + 1, newNode);
        for (int i = mid + 1; i < child.keys.size(); i++) {
            newNode.keys.add(child.keys.get(i));
        }
        if (!child.isLeaf) {
            for (int i = mid + 1; i < child.children.size(); i++) {
                newNode.children.add(child.children.get(i));
            }
        } else {
            for (int i = mid + 1; i < child.values.size(); i++) {
                newNode.values.add(child.values.get(i));
            }
        }
        child.keys.subList(mid, child.keys.size()).clear();
        if (!child.isLeaf) {
            child.children.subList(mid + 1, child.children.size()).clear();
        } else {
            child.values.subList(mid, child.values.size()).clear();
        }
    }

    public String search(String key) {
        return search(root, key);
    }

    private String search(Node node, String key) {
        int i = 0;
        while (i < node.keys.size() && key.compareTo(node.keys.get(i)) > 0) {
            i++;
        }
        if (i < node.keys.size() && key.compareTo(node.keys.get(i)) == 0) {
            return node.isLeaf ? node.values.get(i) : search(node.children.get(i + 1), key);
        } else {
            return node.isLeaf ? null : search(node.children.get(i), key);
        }
    }
}

