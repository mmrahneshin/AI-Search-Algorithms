package model;

import core.Constants;

public class Board {
    private int row;
    private int col;
    private Cell[][] cells;
    private Cell start;
    private Cell goal;

    public Board(int row, int col, Cell[][] cells) {
        this.row = row;
        this.col = col;
        this.cells = cells;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (cells[i][j].op.equals("g")) {
                    this.goal = cells[i][j]; 
                }else if(cells[i][j].op.equals("s")){
                    this.start = cells[i][j];
                }
            }
        }
    }

    public Board copy() {
        Cell[][] copyCells = new Cell[this.row][this.col]; 
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                copyCells[i][j] = new Cell(i, j, this.cells[i][j].getValue(), this.cells[i][j].op);
            }
        }
        Board copyBoard = new Board(this.row, this.col, copyCells);
        return copyBoard;
    }

    public Cell getGoal() {
        return this.goal;
    }

    public Cell getStart() {
        return this.start;
    }

    public int getCol() {
        return this.col;
    }

    public int getRow() {
        return this.row;
    }

    public Cell[][] getCells() {
        return this.cells;
    }

    @Override
    public String toString() {
        //todo needs a refactor
        StringBuilder map = new StringBuilder();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                map.append(Constants.ANSI_BRIGHT_BLUE)
                        .append(OPERATION_TYPE.getOperationTag(cells[i][j].getOperationType()))
                        .append(cells[i][j].getValue())
                        .append("\t");

            }
            map.append("\n");
        }
        return map.toString();
    }

}
