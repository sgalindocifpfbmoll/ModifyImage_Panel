package Helper;

public class Matrix2D {
    // Attributes
    private int[][] matrix;
    private int rows, columns;

    //<editor-fold desc="CONSTRUCTORS">
    public Matrix2D(int rows, int columns) {
        this.setRows(rows);
        this.setColumns(columns);
        this.setMatrix(new int[rows][columns]);
    }

    public Matrix2D(int[][] matrix) {
        this.setRows(matrix.length);
        this.setColumns(matrix[0].length);
        this.setMatrix(new int[this.getRows()][this.getColumns()]);
        this.fill(matrix);
    }
    //</editor-fold>

    //<editor-fold desc="GETTERS">
    public int[][] getMatrix() {
        return matrix;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getDivisor() {
        int sum = 0;
        for (int i = 0; i < this.getRows(); i++) {
            for (int j = 0; j < this.getColumns(); j++) {
                sum += this.getValue(i, j);
            }
        }
        if (sum == 0) {
            sum = 1;
        }
        return sum;
    }

    public int getValue(int row, int column) {
        return this.getMatrix()[row][column];
    }
    //</editor-fold>

    //<editor-fold desc="SETTERS">
    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public void setRows(int fila) {
        this.rows = fila;
    }

    public void setColumns(int columna) {
        this.columns = columna;
    }

    public void setBox(int fila, int columna, int value) {
        this.matrix[fila][columna] = value;
    }
    //</editor-fold>

    public void fill(int[][] matrix) {
        //divider=0;
        for (int i = 0; i < this.getRows(); i++) {
            for (int j = 0; j < this.getColumns(); j++) {
                this.setBox(i, j, matrix[i][j]);
                //divider+=matrix[i][j];
            }
        }
    }

}
