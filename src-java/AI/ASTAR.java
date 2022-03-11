package AI;

import java.util.ArrayList;
import java.util.Hashtable;


import model.Node;

public class ASTAR {
    
    public void search(Node startNode) {
        Hashtable<String, Node> inFrontier = new Hashtable<>();
        Hashtable<String, Boolean> explored = new Hashtable<>();
        if (startNode.isGoal()) {
            System.out.println("score : " + startNode.sum);
            printResult(startNode, 0);
            return;
        }
        startNode.FN = startNode.heuristic(startNode.currentCell);
        inFrontier.put(startNode.hash(), startNode);

        while (!inFrontier.isEmpty()) {
            Node temp =  minFinder(inFrontier);
            inFrontier.remove(temp.hash());
            explored.put(temp.hash(), true);
            ArrayList<Node> children = temp.successor();
            for (Node child : children) {
                if (!(inFrontier.containsKey(child.hash())) && !(explored.containsKey(child.hash()))) {
                    if (child.isGoal()) {
                        if (minFinder(inFrontier) == null) {
                            child.GN = child.pathCost() + temp.GN;
                            child.FN = child.heuristic(child.currentCell) + child.GN;
                            printResult(child, 0);
                            System.out.println(child.sum);
                            return;
                        }
                        if (child.FN <= minFinder(inFrontier).FN) {
                            child.GN = child.pathCost() + temp.GN;
                            child.FN = child.heuristic(child.currentCell) + child.GN;
                            printResult(child, 0);
                            System.out.println(child.sum);
                            return;
                        }
                    }
                    child.GN = child.pathCost() + temp.GN;
                    child.FN = child.heuristic(child.currentCell) + child.GN;
                    inFrontier.put(child.hash(), child);
                }
            }
        }

        System.out.println("no solution");

    }

    private Node minFinder(Hashtable<String, Node> inFrontier) {
        int min = Integer.MAX_VALUE;
        Node minNode = null;
        for(java.util.Map.Entry<String, Node> node : inFrontier.entrySet()){
            if (node.getValue().FN < min) {
                minNode = node.getValue();
                min = node.getValue().FN;
            }
        }
        return minNode;
    }

    public void printResult(Node node, int depthCounter) {
        if (node.parent == null) {
            System.out.println("A* problem solved at a depth of  : " + depthCounter);
            return;
        }
        System.out.println(node.toString()+" "+ node.GN);
        printResult(node.parent, depthCounter + 1);
    }

}
