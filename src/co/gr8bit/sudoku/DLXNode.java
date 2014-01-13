package co.gr8bit.sudoku;

public class DLXNode {
    public DLXNode left, right, up, down, head;
    public int id, nodes;

    public DLXNode() {
        left = this;
        right = this;
        up = this;
        down = this;
        head = this;
        id = -1;
        nodes = 0;
    }

    public DLXNode(DLXNode left, DLXNode right) {
        this.left = left;
        this.right = right;
        up = this;
        down = this;
        head = this;
        id = -1;
        nodes = 0;
    }

    @Override
    public String toString() {
        if (id == -1) {
            return "H";
        }

        if (head == this) {
            return "C" + id;
        }

        return "C" + head.id + ":R" + id;
    }
}
