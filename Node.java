package dictionary;

import java.util.ArrayList;
import java.util.List;

public class Node {
    boolean isLeaf;
    List<String> keys;
    List<Node> children;
    List<String> values;

    public Node(boolean isLeaf) {
        this.isLeaf = isLeaf;
        this.keys = new ArrayList<>();
        this.children = new ArrayList<>();
        this.values = new ArrayList<>();
    }
}
