package co.gr8bit.sudoku;

import java.util.*;

class DLXSolver {
    private final DLXMatrix matrix;
    protected final Deque<DLXNode> solution;
    protected long startTime, endTime;

    public DLXSolver(DLXMatrix matrix) {
        this.matrix = matrix;
        solution = new LinkedList<DLXNode>();
        startTime = System.nanoTime();
    }

    public boolean solve() {
        boolean foundSolution;
        DLXNode column;

        if (matrix.head.right == matrix.head) { // All columns have been covered.
            endTime = System.nanoTime();
            return true;
        }
        if ((column = matrix.sparsestColumn()) == null) { // Remaining columns cannot be covered.
            endTime = System.nanoTime();
            return false;
        }

        matrix.cover(column);
        foundSolution = attemptRows(column); // Recursively cover rows and attempt to solve.
        matrix.uncover(column);

        return foundSolution;
    }

    private boolean attemptRows(DLXNode column) {
        boolean foundSolution = false;
        DLXNode node;

        for (DLXNode row : shuffleRows(column)) {
            for (node = row.right; node != row; node = node.right) {
                matrix.cover(node.head);
            }
            solution.push(node);

            foundSolution = solve();

            for (node = row.right; node != row; node = node.right) {
                matrix.uncover(node.head);
            }

            if (foundSolution) {
                break;
            }

            solution.pop();
        }

        return foundSolution;
    }

    private List<DLXNode> shuffleRows(DLXNode column) {
        List<DLXNode> rows = new ArrayList<DLXNode>(column.nodes);
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
        System.out.println("Solved in " + (endTime - startTime) / 1000000000.0 + " seconds.");
    }
}
