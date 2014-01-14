package co.gr8bit.sudoku;

class Main {
    public static void main(String[] args) {
        SudokuMatrix matrix = new SudokuMatrix();
        DLXSolver solver = new SudokuSolver(matrix);
        if (solver.solve()) {
            solver.printSolution();
        } else {
            System.out.println("No solution.");
        }
    }
}
