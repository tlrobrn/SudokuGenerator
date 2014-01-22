package co.gr8bit.sudoku;

import java.util.*;

class DLXSolver {
    private final DLXMatrix matrix;
    Deque<DLXNode> solution;

    DLXSolver(DLXMatrix matrix) {
        this.matrix = matrix;
        solution = null;
    }

    List<DLXNode> solve() {
        if (solution != null) {
            return new ArrayList<>(solution);
        }

        solution = new ArrayDeque<>();
        return singleSolve(solution) ? new ArrayList<>(solution) : null;
    }

    List<DLXNode> solveWithContext(List<DLXNode> context) {
        context = new ArrayList<>(context);
        solution = new ArrayDeque<>(context);

        for (DLXNode node : context) {
            matrix.cover(node.head);
            pruneMatrix(node);
        }

        boolean foundSolution = singleSolve(solution);

        Collections.reverse(context);
        for (DLXNode node : context) {
            unpruneMatrix(node);
            matrix.uncover(node.head);
        }

        return foundSolution ? new ArrayList<>(solution) : null;
    }

    boolean singleSolve(Deque<DLXNode> solution) {
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

            foundSolution = singleSolve(solution);

            unpruneMatrix(row);
            if (foundSolution) {
                break;
            }

            solution.pop();
        }
        matrix.uncover(column);

        return foundSolution;
    }

    List<DLXNode> uniqueDescription() {
        List<DLXNode> solutionRows = solve();
        if (solutionRows == null) {
            return null;
        }

        List<DLXNode> uniqueGrid = new ArrayList<>();
        Deque<DLXNode> givenSolution = new ArrayDeque<>(solutionRows);
        boolean foundSecondSolution;
        DLXNode row;

        while (!givenSolution.isEmpty()) {
            row = givenSolution.pop();
            removeRow(row);
            prepareMatrix(givenSolution);
            prepareMatrix(uniqueGrid);

            foundSecondSolution = singleSolve(new ArrayDeque<>(givenSolution));
            restoreMatrix(uniqueGrid);
            restoreMatrix(givenSolution);
            restoreRow(row);

            if (foundSecondSolution) {
                uniqueGrid.add(row);
            }
        }

        return uniqueGrid;
    }

    void prepareMatrix(Collection<DLXNode> collection) {
        for (DLXNode node : collection) {
            matrix.cover(node.head);
            pruneMatrix(node);
        }
    }

    void restoreMatrix(Collection<DLXNode> collection) {
        List<DLXNode> list = new ArrayList<>(collection);
        Collections.reverse(list);
        for (DLXNode node : list) {
            unpruneMatrix(node);
            matrix.uncover(node.head);
        }
    }

    void restoreRow(DLXNode row) {
        DLXNode node = row;
        do {
            node.up.down = node;
            node.down.up = node;
            node = node.right;
        } while (node != row);
    }

    void removeRow(DLXNode row) {
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

}
