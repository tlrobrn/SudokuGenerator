package co.gr8bit.sudoku;

import java.util.*;

class SudokuSolver extends DLXSolver {
    private final List<DLXNode> context;

    public SudokuSolver(SudokuMatrix matrix) {
        super(matrix);
        List<Integer> fullRow = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8));
        Collections.shuffle(fullRow);

        context = new ArrayList<>(9);
        int value, rowID, colID;
        for (int col = 0; col < fullRow.size(); col++) {
            value = fullRow.get(col);
            rowID = col * 9 + value;
            colID = SudokuMatrix.encodeConstraint(0, col, value)[0];
            context.add(matrix.get(colID, rowID));
        }
    }

    public SudokuSolver(SudokuMatrix matrix, String startingGrid) {
        super(matrix);
        context = new ArrayList<>();
        int value, row = 0, col = 0, rowID, colID;
        for (char valChar : startingGrid.toCharArray()) {
            value = Character.getNumericValue(valChar) - 1;
            if (value >= 0) {
                rowID = row * 81 + col * 9 + value;
                colID = SudokuMatrix.encodeConstraint(row, col, value)[0];
                context.add(matrix.get(colID, rowID));
            }
            col++;
            if (col == 9) {
                col = 0;
                row++;
            }
        }
    }

    @Override
    public List<DLXNode> solve() {
        if (solution == null) {
            return solveWithContext(context);
        }

        return super.solve();
    }

    public int[][] uniqueGrid() {
        List<DLXNode> rows = uniqueDescription();
        if (rows == null) {
            return null;
        }

        return buildBoard(rows);
    }

    private int[][] buildBoard(Collection<DLXNode> rows) {
        int[][] board = new int[9][9];

        int[] rowColNum;
        for (DLXNode rowNode : rows) {
            rowColNum = base9(rowNode.id);
            board[rowColNum[0]][rowColNum[1]] = rowColNum[2] + 1;
        }
        return board;
    }

    public void printSolution() {
        printGrid(buildBoard(solution));
    }

    void printGrid(int[][] grid) {
        StringBuilder sb = new StringBuilder();
        int rowBorder = 3, colBorder = 3;
        for (int[] row : grid) {
            for (int val : row) {
                sb.append(val);
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
    }

    private static int[] base9(int number) {
        int[] digits = new int[3];
        for (int i = 2; i >= 0; number = number / 9, i--) {
            digits[i] = number % 9;
        }

        return digits;
    }
}
