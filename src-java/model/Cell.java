package model;

public class Cell {
    int i;
    int j;
    private int value;
    private OPERATION_TYPE operationType;
    public String op;

    public Cell(int i, int j, int value, String op) {
        this.i = i;
        this.j = j;
        this.op = op;
        this.value = value;
        this.operationType = OPERATION_TYPE.getOperation(op);
        
    }

    public Cell copy() {
        Cell copyCell = new Cell(this.i, this.j, this.value, this.op);
        return copyCell;
    }

    public int getRow() {
        return i;
    }

    public int getCol() {
        return j;
    }

    public int getValue() {
        return value;
    }

    public OPERATION_TYPE getOperationType() {
        return this.operationType;
    }

    @Override
    public String toString() {
        return "(" + this.i + "," + this.j + ")";
    }
}
