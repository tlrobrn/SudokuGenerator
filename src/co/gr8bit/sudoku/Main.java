package co.gr8bit.sudoku;

class Main {
    public static void main(String[] args) {
        SudokuMatrix matrix = new SudokuMatrix();
        SudokuSolver solver = new SudokuSolver(matrix);
        String gridString = buildGridString(solver.uniqueGrid());
        System.out.println(gridString);

        solver = new SudokuSolver(matrix, gridString);
        if (solver.solve() != null) {
            solver.printSolution();
        } else {
            System.out.println("No solution.");
        }
    }

    private static String buildGridString(int[][] grid) {
        StringBuilder sb = new StringBuilder();
        for (int[] row : grid) {
            for (int val : row) {
                sb.append(val);
            }
        }

        return sb.toString();
    }
}
