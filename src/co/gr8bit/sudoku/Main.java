package co.gr8bit.sudoku;

class Main {
    public static void main(String[] args) {
        SudokuMatrix matrix = new SudokuMatrix();
        int[][] startingGrid = {
                {0, 0, 0, 0, 0, 0, 0, 1, 0},
                {4, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 2, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 5, 0, 4, 0, 7},
                {0, 0, 8, 0, 0, 0, 3, 0, 0},
                {0, 0, 1, 0, 9, 0, 0, 0, 0},
                {3, 0, 0, 4, 0, 0, 2, 0, 0},
                {0, 5, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 8, 0, 6, 0, 0, 0},
        };
        DLXSolver solver = new SudokuSolver(matrix, startingGrid);
        System.out.println(solver.hasMultipleSolutions());
        if (solver.solve() != null) {
            solver.printSolution();
        } else {
            System.out.println("No solution.");
        }
    }
}
