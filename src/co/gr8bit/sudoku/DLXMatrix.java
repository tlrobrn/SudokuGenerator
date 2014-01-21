package co.gr8bit.sudoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class DLXMatrix {
    final DLXNode head;
    protected int numRows, numColumns;

    public DLXMatrix(int columns) {
        head = new DLXNode();
        for (; columns > 0; columns--) {
            addColumn();
        }
    }

    void addColumn() {
        head.left.right = new DLXNode(head.left, head);
        head.left.right.id = numColumns++;
        head.left = head.left.right;
    }

    public void addRow(int[] columns) {
        DLXNode curr, prev, first;
        curr = new DLXNode();
        first = prev = curr;

        for (DLXNode header : getColumnsByIDs(columns)) {
            curr.down = header;
            curr.up = header.up;
            curr.left = prev;
            curr.right = first;
            curr.head = header;
            curr.id = numRows;

            prev.right = curr;

            header.up.down = curr;
            header.up = curr;
            header.nodes++;

            prev = curr;
            curr = new DLXNode();
        }

        numRows++;
    }

    DLXNode getColumnByID(int id) throws IndexOutOfBoundsException {
        for (DLXNode node = head.right; node != head && node.id <= id; node = node.right) {
            if (node.id == id) {
                return node;
            }
        }

        throw new IndexOutOfBoundsException("Column " + id + " is not reachable or does not exist.");
    }

    List<DLXNode> getColumnsByIDs(int[] ids) throws IndexOutOfBoundsException {
        List<DLXNode> columns = new ArrayList<>(ids.length);
        Arrays.sort(ids);
        int index = 0;

        for (DLXNode node = head.right; node != head && index < ids.length; node = node.right) {
            if (node.id == ids[index]) {
                columns.add(node);
                index++;
            }
        }

        if (index < ids.length) {
            throw new IndexOutOfBoundsException("Column " + ids[index] + " is not reachable or does not exist.");
        }

        return columns;
    }

    public void cover(DLXNode node) {
        // Remove the Column
        node.right.left = node.left;
        node.left.right = node.right;

        // Remove the Rows
        DLXNode other;
        for (DLXNode rowNode = node.down; rowNode != node; rowNode = rowNode.down) {
            for (other = rowNode.right; other != rowNode; other = other.right) {
                other.up.down = other.down;
                other.down.up = other.up;
                other.head.nodes--;
            }
        }
    }

    public void uncover(DLXNode node) {
        // Add the Row
        DLXNode other;
        for (DLXNode rowNode = node.down; rowNode != node; rowNode = rowNode.down) {
            for (other = rowNode.right; other != rowNode; other = other.right) {
                other.up.down = other;
                other.down.up = other;
                other.head.nodes++;
            }
        }

        // Add the Column
        node.right.left = node;
        node.left.right = node;
    }

    public DLXNode sparsestColumn() {
        DLXNode node = head;
        DLXNode sparsest = null;

        while ((node = node.right) != head) {
            if (node.nodes > 0 && (sparsest == null || node.nodes < sparsest.nodes)) {
                sparsest = node;
            }
        }

        return sparsest;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        DLXNode columnNode, rowNode;
        for (int row = 0; row < numRows; row++) {
            for (columnNode = head.right; columnNode != head; columnNode = columnNode.right) {
                rowNode = columnNode.down;
                while (rowNode != columnNode && rowNode.id < row) {
                    rowNode = rowNode.down;
                }

                sb.append((rowNode != columnNode && rowNode.id == row) ? 1 : 0);
                sb.append(' ');
            }
            sb.append('\n');
        }

        return sb.toString();
    }
}
