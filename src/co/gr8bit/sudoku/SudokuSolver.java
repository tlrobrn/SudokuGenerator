package co.gr8bit.sudoku;

import java.util.List;

public class SudokuSolver extends DLXSolver {
    private final int[][] grid;

    public SudokuSolver(SudokuMatrix matrix, List<Integer> startingRow) {
        super(matrix);

        grid = new int[9][9];
        int col = 0;
        for (Integer val : startingRow) {
            grid[0][col] = val;
            matrix.assign(0, col++, val);
        }
    }

    public SudokuSolver(SudokuMatrix matrix, int[][] startingGrid) {
        super(matrix);
        grid = startingGrid;
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (grid[row][col] != 0) {
                    matrix.assign(row, col, --grid[row][col]);
                }
            }
        }
    }

    @Override
    public void printSolution() {
        int[] rowColNum;
        for (DLXNode rowNode : solution) {
            rowColNum = base9(rowNode.id);
            grid[rowColNum[0]][rowColNum[1]] = rowColNum[2];
        }

        StringBuilder sb = new StringBuilder();
        int rowBorder = 3, colBorder = 3;
        for (int[] row : grid) {
            for (int val : row) {
                sb.append(val + 1);
                if (--colBorder > 0) {
                    sb.append(' ');
                } else {
                    sb.append('|');
                    colBorder = 3;
                }
            }
            sb.append('\n');
            if (--rowBorder == 0) {
                sb.append("----- ----- -----").append('\n');
                rowBorder = 3;
            }
        }

        System.out.println(sb.toString());
        System.out.println("Solved in " + (endTime - startTime) / 1000000000.0 + " seconds.");
    }

    public static int[] base9(int number) {
        int[] digits = new int[3];
        for (int i = 2; i >= 0; number = number / 9, i--) {
            digits[i] = number % 9;
        }

        return digits;
    }
}
