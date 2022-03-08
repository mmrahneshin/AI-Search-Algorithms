package AI;

import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.Hashtable;

import model.Node;

public class IDASTAR {
    
    public void search(Node startNode) {
        Hashtable<String, Node> inFrontier = new Hashtable<>();
        if (startNode.isGoal()) {
            System.out.println("score : " + startNode.sum);
            printResult(startNode, 0);
            return;
        }

        inFrontier.put(startNode.hash(), startNode);
        int cutoff = startNode.heuristic(startNode.currentCell);
        startNode.FN = startNode.heuristic(startNode.currentCell);

        while (!inFrontier.isEmpty()) {
            inFrontier.clear();
            Node temp = DLS(startNode, cutoff, inFrontier);

            if (temp == null) {
                cutoff = serchInFrontier(inFrontier);
                // System.out.println(cutoff);
            } else {
                printResult(temp, 0);
                System.out.println(temp.sum);
                return;
            }
        }
        System.out.println("no solution");
}

    private int serchInFrontier(Hashtable<String, Node> inFrontier) {
        int min = Integer.MAX_VALUE;
        for(java.util.Map.Entry<String, Node> node : inFrontier.entrySet()){
            if (node.getValue().FN < min) {
                min = node.getValue().FN;
            }
        }
        return min;
    }

    private Node DLS(Node node, int cutoff,Hashtable<String, Node> inFrontier) {
        if(node.isGoal()){
            return node;
        }
        if (node.FN > cutoff) {
            return null;
        }
        inFrontier.remove(node.hash());
        ArrayList<Node> children = node.successor();
        for(Node child: children){
            child.GN = child.pathCost() + node.GN;
            child.FN = child.heuristic(child.currentCell) + child.GN;
            inFrontier.put(child.hash(), child);
            Node temp = DLS(child, cutoff, inFrontier);
            if (temp != null) {
                return temp;
            } 
        }
        return null;
    }

    public void printResult(Node node, int depthCounter) {
        if (node.parent == null) {
            System.out.println("IDA* problem solved at a depth of  : " + depthCounter);
            return;
        }
        System.out.println(node.toString()+" "+ node.GN);
        printResult(node.parent, depthCounter + 1);
    }
}
