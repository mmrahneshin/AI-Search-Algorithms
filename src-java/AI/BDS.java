package AI;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Map.Entry;

import model.Board;
import model.Cell;
import model.Node;

public class BDS {

    private boolean print = false;
    private static int target = 0;
    
    static Hashtable<String, Node> forwardVisited = new Hashtable<>();
    static Hashtable<String, Node> backwardVisited = new Hashtable<>();

    static Hashtable<String, Node> backwardVisitedHash = new Hashtable<>();
    static Hashtable<String, Node> forwardVisitedHash = new Hashtable<>();
    
    static Queue<Node> forwardQueue = new LinkedList<Node>();
    static Queue<Node> backwardQueue = new LinkedList<Node>();

    public void search(Node forward) {
        Cell startBackward = new Cell(forward.currentCell.getGoal().getRow(), forward.currentCell.getGoal().getCol(), forward.currentCell.getValue(), forward.currentCell.op);
        Cell goalBackward = new Cell(forward.currentCell.getRow(), forward.currentCell.getCol(), forward.currentCell.getGoal().getValue(), forward.currentCell.getGoal().op);
        Cell[][] cells = forward.board.copy().getCells();

        cells[startBackward.getRow()][startBackward.getCol()] = startBackward;
        cells[goalBackward.getRow()][goalBackward.getCol()] = goalBackward;

        Board backwardBoard = new Board(forward.board.getRow(), forward.board.getCol(), cells);

        Hashtable<String, Boolean> backwardHash = new Hashtable<>();
        backwardHash.put(startBackward.toString(), true);

        Node backward = new Node(startBackward, startBackward.getValue(), goalBackward.getValue(), backwardBoard, null, backwardHash);

        forwardQueue.add(forward);
        forwardVisitedHash.put(forward.hash(), forward);
        forwardVisited.put(forward.toString(), forward);
        
        backwardQueue.add(backward);
        backwardVisitedHash.put(backward.hash(), backward);
        backwardVisited.put(backward.toString(), backward);


        while(!forwardQueue.isEmpty() && !backwardQueue.isEmpty()){
            bfs(forwardQueue, forwardVisited, forwardVisitedHash);
            // System.out.println("sdfsd");
            // Node[] interNode = interSection(forwardVisited, backwardVisited);
            bfs(backwardQueue, backwardVisited, backwardVisitedHash);
            // if (interNode == null) {
            // interNode = interSection(forwardVisited, backwardVisited);
            // }
            Node[] interNode = interSection(forwardVisited, backwardVisited);

            if (interNode != null) {
                printResult(interNode[1], 0);
                printResult(interNode[0], 0);
                System.out.println(target);
                return;
            }
        }
    }

    private static void bfs(Queue<Node> queue, Hashtable<String, Node> visited, Hashtable<String, Node> visitedHash) {
        Node current = queue.poll();
        // visited.clear();
        ArrayList<Node> children = current.successor();
        for(Node child:children){
            if (!visitedHash.containsKey(child.hash())) {
                queue.add(child);
                visitedHash.put(child.hash(), child);
                visited.put(child.toString(), child); 
            }
        }
    }

    private static Node[] interSection(Hashtable<String, Node> fVisited, Hashtable<String, Node> bVisited) {
        Node[] arr = new Node[2];
        for (Entry<String, Node> node: fVisited.entrySet()){
            if (bVisited.containsKey(node.getKey())) {

                arr[1] = bVisited.get(node.getKey());
                arr[0] = node.getValue();
                // System.out.println(arr[1]);
                // boolean conflict = subscription(arr[0].copy(), arr[1].parent.copy());
                // System.out.println(conflict);
                if (checkGoal(arr[0].copy())) {
                    target = check(arr[0].sum, arr[1].parent);
                    if (target >= node.getValue().currentCell.getGoal().getValue() ) {
                        return arr; 
                    }
                }
            }
        }
        return null;
    }

    private static boolean checkGoal(Node start) {
        while(start != null){
            if (start.currentCell.toString().equals(start.currentCell.getGoal().toString())) {
                // System.out.println("ss");
                return false;
            }
            // System.out.println(start);
            // System.out.println(start.currentCell.getGoal());
            start = start.parent;
        }
        return true;
    }

    private static int check(int sum, Node node) {
        if (node.parent == null) {
            return sum;
        }
        sum = check(node.customCalculate(node.currentCell, sum), node.parent);
        return sum;
    }

    private void printResult(Node node, int depthCounter) {
        if (node.parent == null && print == true) {
            System.out.println("BDS problem solved at a depth of  : " + depthCounter*2);
            return;
        }else if(node.parent == null && print == false){
            print = true;
            System.out.println(node.toString());
            // node.drawState();
            return;
        }
        if (print) {
            System.out.println(node.toString());
            // node.drawState();
            printResult(node.parent, depthCounter + 1);
        } else {
            printResult(node.parent, depthCounter + 1);
            System.out.println(node.toString());
            // node.drawState();
        }
    }
}
