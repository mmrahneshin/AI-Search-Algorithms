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
        forwardVisited.put(forward.hash(), forward);
        // forwardParents.put(forward.hash(), null);

        
        backwardQueue.add(backward);
        backwardVisited.put(backward.hash(), backward);
        // backwardParents.put(backward.hash(), null);

        while(!forwardQueue.isEmpty() && !backwardQueue.isEmpty()){
            bfs(forwardQueue, forwardVisited);
            bfs(backwardQueue, backwardVisited);

            Node[] interNode = interSection(copyHash(forwardVisited), copyHash(backwardVisited));
            if (interNode != null) {
                printResult(interNode[1], 0);
                printResult(interNode[0], 0);
                System.out.println(target);
                return;
            }
        }
    }

    private Hashtable<String, Node> copyHash(Hashtable<String, Node> repeated) {
        Hashtable<String, Node> copyHash = new Hashtable<String, Node>(repeated);

        for (Entry<String, Node> node: repeated.entrySet()){
            copyHash.put(node.getKey(), node.getValue());
        }
        return copyHash;
    }

    private static void bfs(Queue<Node> queue, Hashtable<String, Node> visited) {
        Node current = queue.poll();
        // visited.clear();
        ArrayList<Node> children = current.successor();
        for(Node child:children){
            if (!visited.containsKey(child.hash())) {
                queue.add(child);
                visited.put(child.hash(), child);
            }
        }
    }

    private static Node[] interSection(Hashtable<String, Node> fVisited, Hashtable<String, Node> bVisited) {
        Node[] arr = new Node[2];
        for (Entry<String, Node> node: fVisited.entrySet()){
            if (bVisited.containsKey(node.getKey())) {
                arr[1] = bVisited.get(node.getKey());
                arr[0] = node.getValue();
                target = check(arr[0].sum, arr[1].parent);
                // remove(node.getValue(), forwardQueue);
                // remove(node.getValue(), backwardQueue);
                if (target >= arr[0].currentCell.getGoal().getValue() ) {
                    return arr; 
                }
                // return arr;
            }
        }
        // forwardVisited.clear();
        // backwardVisited.clear();
        return null;
    }

    static void remove(Node t,Queue<Node> q)
    {
     
        // Helper queue to store the elements
        // temporarily.
        Queue<Node> ref = new LinkedList<>();
        int s = q.size();
        int cnt = 0;
     
        // Finding the value to be removed
        while (!q.isEmpty() && q.peek() != t) {
            ref.add(q.peek());
            q.remove();
            cnt++;
        }
     
        // If element is not found
        if (q.isEmpty()) {
            System.out.print("element not found!!" +"\n");
            while (!ref.isEmpty()) {
     
                // Pushing all the elements back into q
                q.add(ref.peek());
                ref.remove();
            }
        }
     
        // If element is found
        if(!q.isEmpty()) {
            q.remove();
            while (!ref.isEmpty()) {
     
                // Pushing all the elements back into q
                q.add(ref.peek());
                ref.remove();
            }
            int k = s - cnt - 1;
            while (k-- >0) {
     
                // Pushing elements from front of q to its back
                Node p = q.peek();
                q.remove();
                q.add(p);
            }
        }
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
            node.drawState();
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
