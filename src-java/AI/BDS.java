package AI;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Map.Entry;

import model.ACTION_TYPE;
import model.Board;
import model.Cell;
import model.Node;

public class BDS {

    private boolean print = false;
    private static int target = 0;
    
    static Hashtable<String, Node> forwardVisited = new Hashtable<>();
    static Hashtable<String, Node> backwardVisited = new Hashtable<>();

    static Hashtable<Node, Node> backwardVisitedHash = new Hashtable<>();
    static Hashtable<Node, Node> forwardVisitedHash = new Hashtable<>();
    
    static Queue<Node> forwardQueue = new LinkedList<Node>();
    static Queue<Node> backwardQueue = new LinkedList<Node>();

    public void search(Node forward) {

        Cell startBackward = new Cell(forward.board.getGoal().getRow(), forward.board.getGoal().getCol(), forward.currentCell.getValue(), forward.currentCell.op);
        Cell goalBackward = new Cell(forward.currentCell.getRow(), forward.currentCell.getCol(), forward.board.getGoal().getValue(), forward.board.getGoal().op);
        Cell[][] cells = forward.board.copy().getCells();

        cells[startBackward.getRow()][startBackward.getCol()] = startBackward;
        cells[goalBackward.getRow()][goalBackward.getCol()] = goalBackward;

        Board backwardBoard = new Board(forward.board.getRow(), forward.board.getCol(), cells);

        Hashtable<String, Boolean> backwardHash = new Hashtable<>();
        backwardHash.put(startBackward.toString(), true);

        Node backward = new Node(startBackward, startBackward.getValue(), goalBackward.getValue(), backwardBoard, null, backwardHash, ACTION_TYPE.RIGHT);

        forwardQueue.add(forward);
        forwardVisitedHash.put(forward, forward);
        forwardVisited.put(forward.toString(), forward);
        
        backwardQueue.add(backward);
        backwardVisitedHash.put(backward, backward);
        backwardVisited.put(backward.toString(), backward);


        while(!forwardQueue.isEmpty() && !backwardQueue.isEmpty()){
            bfs(forwardQueue, forwardVisited, forwardVisitedHash);

            bfs(backwardQueue, backwardVisited, backwardVisitedHash);

            Node[] interNode = interSection(forwardVisited, backwardVisited);
           
            if (interNode != null) {
                printResult(interNode[1], 0);
                printResult(interNode[0], 0);
                System.out.println(target);
                return;
            }
        }
    }

    private static void bfs(Queue<Node> queue, Hashtable<String, Node> visited, Hashtable<Node, Node> visitedHash) {
        Queue<Node> tempQueue = new LinkedList<Node>();
        while(!queue.isEmpty()){
            Node current = queue.poll();
            tempQueue.add(current);
        }

        while (!tempQueue.isEmpty()) {
            ArrayList<Node> children = tempQueue.poll().successor();
            for(Node child:children){
                if (!visitedHash.containsKey(child)) {
                    queue.add(child);
                    visitedHash.put(child, child);
                    visited.put(child.toString(), child); 
                }
            }
        }
    }

    private static Node[] interSection(Hashtable<String, Node> fVisited, Hashtable<String, Node> bVisited) {
        Node[] arr = new Node[2];
        for (Entry<String, Node> node: fVisited.entrySet()){
            if (bVisited.containsKey(node.getKey())) {

                arr[1] = bVisited.get(node.getKey());
                arr[0] = node.getValue();

                if (checkConflict(arr[0].copy(), arr[1].copy().parent)) {
                    target = check(arr[0].sum, arr[1].parent);
                    if (target >= node.getValue().board.getGoal().getValue() ) {
                        return arr; 
                    }
                }
            }
        }
        return null;
    }

    private static boolean checkConflict(Node start,Node goal) {
        
    Hashtable<String, Node> startVisited = new Hashtable<>();
    Hashtable<String, Node> goalVisited = new Hashtable<>();
        while(start != null){
            startVisited.put(start.currentCell.toString(), start);
            start = start.parent;
        }
        while(goal != null){
            goalVisited.put(goal.currentCell.toString(), goal);
            goal = goal.parent;
        }
        for(Entry<String, Node> node: startVisited.entrySet()){
            if (goalVisited.containsKey(node.getKey())) {
                return false;
            }
        }
        return true;
    }

    private static int check(int sum, Node node) {
        if (node !=null) {
            if (node.parent == null) {
                return sum;
            }
            sum = check(node.customCalculate(node.currentCell, sum), node.parent);
        }
        return sum;
       
        
    }

    private void printResult(Node node, int depthCounter) {
        if (node.parent == null && print == true) {
            System.out.println("BDS problem solved at a depth of  : " + depthCounter*2);
            return;
        }else if(node.parent == null && print == false){
            print = true;
            System.out.println(node.toString());
            return;
        }
        if (print) {
            System.out.println(node.toString());
            printResult(node.parent, depthCounter + 1);
        } else {
            printResult(node.parent, depthCounter + 1);
            System.out.println(node.toString());
        }
    }
}
