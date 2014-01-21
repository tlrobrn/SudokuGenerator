package co.gr8bit.sudoku;

import java.util.*;

class DLXSolver {
    private final DLXMatrix matrix;
    protected Deque<DLXNode> solution;

    public DLXSolver(DLXMatrix matrix) {
        this.matrix = matrix;
        solution = new ArrayDeque<>();
    }

    public List<DLXNode> solve() {
        if (solution.size() > 0 || singleSolve()) {
            return new ArrayList<>(solution);
        }

        return null;
    }

    protected boolean singleSolve() {
        boolean foundSolution = false;
        DLXNode column;

        if (matrix.head.right == matrix.head) { // All columns have been covered.
            return true;
        }
        if ((column = matrix.sparsestColumn()) == null) { // Remaining columns cannot be covered.
            return false;
        }

        matrix.cover(column);
        for (DLXNode row : shuffleRows(column)) {
            solution.push(row);
            pruneMatrix(row);

            foundSolution = singleSolve();

            unpruneMatrix(row);
            if (foundSolution) {
                break;
            }

            solution.pop();
        }
        matrix.uncover(column);

        return foundSolution;
    }

    public boolean hasMultipleSolutions() {
        if (solve() == null) {
            return false;
        }
        boolean foundSecondSolution = false;
        Deque<DLXNode> prevSolution = solution;
        Deque<DLXNode> original = new ArrayDeque<>(solution);
        DLXNode row;

        while (!foundSecondSolution && !prevSolution.isEmpty()) {
            row = prevSolution.pop();
            removeRow(row);
            solution = new ArrayDeque<>(prevSolution);
            foundSecondSolution = singleSolve();
            restoreRow(row);
        }
        solution = original;

        return foundSecondSolution;
    }

    protected void restoreRow(DLXNode row) {
        DLXNode node = row;
        do {
            node.up.down = node;
            node.down.up = node;
            node = node.right;
        } while (node != row);
    }

    protected void removeRow(DLXNode row) {
        DLXNode node = row;
        do {
            node.up.down = node.down;
            node.down.up = node.up;
            node = node.right;
        } while (node != row);
    }

    private void unpruneMatrix(DLXNode row) {
        for (DLXNode node = row.right; node != row; node = node.right) {
            matrix.uncover(node.head);
        }
    }

    private void pruneMatrix(DLXNode row) {
        for (DLXNode node = row.right; node != row; node = node.right) {
            matrix.cover(node.head);
        }
    }

    private static List<DLXNode> shuffleRows(DLXNode column) {
        List<DLXNode> rows = new ArrayList<>(column.nodes);
        for (DLXNode row = column.down; row != column; row = row.down) {
            rows.add(row);
        }
        Collections.shuffle(rows);

        return rows;
    }

    public void printSolution() {
        StringBuilder sb = new StringBuilder("Solution:\n");
        for (DLXNode node : solution) {
            sb.append("Row ").append(node.id).append('\n');
        }

        System.out.println(sb.toString());
    }
}
