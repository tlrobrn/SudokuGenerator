package co.gr8bit.sudoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class Main {
    public static void main(String[] args) {
        SudokuMatrix matrix = new SudokuMatrix();
        List<Integer> firstRow = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8));
        Collections.shuffle(firstRow);

        DLXSolver solver = new SudokuSolver(matrix, firstRow);
        if (solver.solve()) {
            solver.printSolution();
        } else {
            System.out.println("No solution.");
        }
    }
}
