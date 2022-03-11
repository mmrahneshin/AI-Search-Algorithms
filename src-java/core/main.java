package core;

import AI.ASTAR;
import AI.BDS;
import AI.BFS;
import AI.IDASTAR;
import AI.IDS;
import model.Board;
import model.Cell;
import model.Node;

import java.util.Hashtable;
import java.util.Scanner;
import model.ACTION_TYPE;

public class main {

    public static void main(String[] args) {
        System.out.println(" pls enter rows and columns of your board : \n");
        Scanner sc = new Scanner(System.in);
        String mn = sc.nextLine();
        int rows = Integer.parseInt(mn.split(" ")[0]);
        int columns = Integer.parseInt(mn.split(" ")[1]);
        String[][] board = new String[rows][columns];
        String[] lines = new String[rows];
        for (int i = 0; i < rows; i++) {
            lines[i] = sc.nextLine();
            String[] line = lines[i].split(" ");
            if (columns >= 0) System.arraycopy(line, 0, board[i], 0, columns);
        }
        Mapper mapper = new Mapper();
        Cell[][] cells = mapper.createCells(board, rows, columns);
        Board gameBoard = mapper.createBoard(cells, rows, columns);
        Hashtable<String, Boolean> initHash = new Hashtable<>();
        initHash.put(gameBoard.getStart().toString(), true);

        Node startBFS = new Node(gameBoard.getStart(), gameBoard.getStart().getValue(), gameBoard.getGoal().getValue(), gameBoard, null, initHash, ACTION_TYPE.RIGHT);
        BFS bfs = new BFS();
        bfs.search(startBFS);

        Node startIDS = new Node(gameBoard.getStart(), gameBoard.getStart().getValue(), gameBoard.getGoal().getValue(), gameBoard, null, initHash, ACTION_TYPE.RIGHT);
        IDS ids = new IDS();
        ids.search(startIDS);

        Node startBDS = new Node(gameBoard.getStart(), gameBoard.getStart().getValue(), gameBoard.getGoal().getValue(), gameBoard, null, initHash, ACTION_TYPE.RIGHT);
        BDS bds = new BDS();
        bds.search(startBDS);

        Node startASTAR = new Node(gameBoard.getStart(), gameBoard.getStart().getValue(), gameBoard.getGoal().getValue(), gameBoard, null, initHash, ACTION_TYPE.RIGHT);
        ASTAR astar = new ASTAR();
        astar.search(startASTAR);

        Node startIDA = new Node(gameBoard.getStart(), gameBoard.getStart().getValue(), gameBoard.getGoal().getValue(), gameBoard, null, initHash, ACTION_TYPE.RIGHT);
        IDASTAR idastar = new IDASTAR();
        idastar.search(startIDA);
    }
}
