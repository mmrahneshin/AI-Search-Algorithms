package AI;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;

import model.Cell;
import model.Node;

public class BDS {
    public void search(Node forward) {
        Node backward = forward.copy();
        Cell[][] cells = backward.board.getCells();
        cells[forward.currentCell.getStart().getRow()][forward.currentCell.getStart().getCol()] = backward.currentCell.getGoal();
        cells[forward.currentCell.getGoal().getRow()][forward.currentCell.getGoal().getCol()] = backward.currentCell.getStart();

        System.out.println(backward.board);
        System.out.println(forward.board);

        Queue<Node> forwardQueue = new LinkedList<Node>();
        Queue<Node> backwardQueue = new LinkedList<Node>();

        Hashtable<String, Node> forwardParents = new Hashtable<>();
        Hashtable<String, Node> backwardParents = new Hashtable<>();

        Hashtable<String, Node> forwardVisited = new Hashtable<>();
        Hashtable<String, Node> backwardVisited = new Hashtable<>();

        // Node backward = (Node) forward.clone();
        // Cell[][] kir = backward.board.getCells();
        // kir[forward.currentCell.getStart().getRow()][forward.currentCell.getStart().getCol()] = backward.currentCell.getGoal();
        // kir[forward.currentCell.getGoal().getRow()][forward.currentCell.getGoal().getCol()] = backward.currentCell.getStart();

        // Board tempBoard = new Board(forward.board.getRow(), forward.board.getCol(), kir);
        // System.out.println(tempBoard.toString());
        // System.out.println(forward.board.toString());
    }
}
