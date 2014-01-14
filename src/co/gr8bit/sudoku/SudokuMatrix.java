package co.gr8bit.sudoku;

import java.util.LinkedList;
import java.util.List;

public class SudokuMatrix extends DLXMatrix {
    private static final int constraints = 324;

    public SudokuMatrix() {
        super(constraints);

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                for (int num = 0; num < 9; num++) {
                    addRow(encodeConstraint(row, col, num));
                }
            }
        }
    }

    public void assign(int row, int col, int num) {
        int offset = 0;

        cover(columnHeaders.get(row * 9 + col));
        offset += 81;
        cover(columnHeaders.get(offset + row * 9 + num));
        offset += 81;
        cover(columnHeaders.get(offset + col * 9 + num));
        offset += 81;
        cover(columnHeaders.get(offset + getBox(row, col) * 9 + num));
    }

    private static List<Integer> encodeConstraint(int row, int col, int num) {
        List<Integer> assignment = new LinkedList<>();
        int offset = 0;

        assignment.add(row * 9 + col);
        offset += 81;
        assignment.add(offset + row * 9 + num);
        offset += 81;
        assignment.add(offset + col * 9 + num);
        offset += 81;
        assignment.add(offset + getBox(row, col) * 9 + num);

        return assignment;
    }


    private static int getBox(int row, int col) {
        return (row - (row % 3)) + (col / 3);
    }
}
