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

        Queue<Node> forwardQueue = new LinkedList<Node>();
        Queue<Node> backwardQueue = new LinkedList<Node>();

        Hashtable<String, Node> forwardParents = new Hashtable<>();
        Hashtable<String, Node> backwardParents = new Hashtable<>();

        Hashtable<String, Node> forwardVisited = new Hashtable<>();
        Hashtable<String, Node> backwardVisited = new Hashtable<>();

        forwardQueue.add(forward);
        forwardVisited.put(forward.hash(), forward);
        // forwardParents.put(forward.hash(), null);

        
        backwardQueue.add(backward);
        backwardVisited.put(backward.hash(), backward);
        // backwardParents.put(backward.hash(), null);

        while(!forwardQueue.isEmpty() && !backwardQueue.isEmpty()){
            bfs(forwardQueue, forwardVisited, forwardParents);
            bfs(backwardQueue, backwardVisited, backwardParents);

            Node[] interNode = interSection(forwardVisited, backwardVisited);
            if (interNode != null) {
                printResult(interNode[1], 0);
                printResult(interNode[0], 0);
                return;
            }
        }
    }

    private static void bfs(Queue<Node> queue, Hashtable<String, Node> visited, Hashtable<String, Node> parent) {
        Node current = queue.poll();
        ArrayList<Node> children = current.successor();
        for(Node child:children){
            if (!visited.containsKey(child.hash())) {
                queue.add(child);
                visited.put(child.hash(), child);
                parent.put(child.hash(), current);
            }
        }
    }

    private static Node[] interSection(Hashtable<String, Node> forwardVisited, Hashtable<String, Node> backwardVisited) {
        Node[] arr = new Node[2];
        for (Entry<String, Node> node: forwardVisited.entrySet()){
            if (backwardVisited.containsKey(node.getKey())) {
                arr[1] = backwardVisited.get(node.getKey());
                arr[0] = node.getValue();
                return arr;
            }
        }
        return null;
    }
    public void printResult(Node node, int depthCounter) {
        if (node.parent == null && print == true) {
            System.out.println("problem solved at a depth of  : " + depthCounter);
            return;
        }else if(node.parent == null && print == false){
            print = true;
            return;
        }
        if (print) {
            System.out.println(node.toString());
            node.drawState();
            printResult(node.parent, depthCounter + 1);
        } else {
            printResult(node.parent, depthCounter + 1);
            System.out.println(node.toString());
            node.drawState();
        }
       
    }
}
