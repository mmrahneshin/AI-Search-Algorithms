package AI;

import java.util.ArrayList;
import java.util.Stack;

import model.Node;

public class IDS {

    public void search(Node startNode){
       
        int depth = 0;
        int maxDepth = startNode.board.getCol() * startNode.board.getRow(); 
        if (startNode.isGoal()) {
            System.out.println("score : " + startNode.sum);
            printResult(startNode, 0);
            return;
        }
        for (depth = 0; depth <= maxDepth; depth++) {
            Node isValid = DLS(startNode, depth);
            if (isValid != null){
                printResult(isValid, 0);
                System.out.println(isValid.sum);
                return;
            }
        }
        System.out.println("no solution");
    }

    public Node DLS(Node startNode, int limit){
        if (startNode.isGoal()) {
            return startNode;
        }
        if (limit <= 0) {
            return null;
        }
        ArrayList<Node> children = startNode.successor();
        for(Node child : children){
            Node temp = DLS(child, limit-1);
            if (temp != null) {
                return temp;
            }
        }
        return null;
    }

    public void printResult(Node node, int depthCounter) {
        if (node.parent == null) {
            System.out.println("IDS problem solved at a depth of  : " + depthCounter);
            return;
        }

        System.out.println(node.toString());
        // node.drawState();
        printResult(node.parent, depthCounter + 1);
    }
    
}
