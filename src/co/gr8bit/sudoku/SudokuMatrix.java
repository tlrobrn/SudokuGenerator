package co.gr8bit.sudoku;

class SudokuMatrix extends DLXMatrix {
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

    public static int[] encodeConstraint(int row, int col, int num) {
        int[] assignment = new int[4];
        int offset = 0;
        int i = 0;

        assignment[i++] = row * 9 + col;
        offset += 81;
        assignment[i++] = offset + row * 9 + num;
        offset += 81;
        assignment[i++] = offset + col * 9 + num;
        offset += 81;
        assignment[i] = offset + getBox(row, col) * 9 + num;

        return assignment;
    }


    private static int getBox(int row, int col) {
        return (row - (row % 3)) + (col / 3);
    }
}
