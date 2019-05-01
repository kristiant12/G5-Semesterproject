/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.astar;

import dk.sdu.mmmi.cbse.common.data.Point;

/**
 *
 * @author kaspe
 */
public class Node implements Comparable {

    private Point point;
    /**
     * The path cost for this node
     */
    private float cost;
    /**
     * The parent of this node, how we reached it in the search
     */
    private Node parent;
    /**
     * The heuristic cost of this node
     */
    private float heuristic;

    /**
     * Create a new node
     *
     * @param x The x coordinate of the node
     * @param y The y coordinate of the node
     */
    public Node(Point point, float cost, float heuristic, Node parent) {
        this.point = point;
        this.cost = cost;
        this.heuristic = heuristic;
        this.parent = parent;
    }

    /**
     * Set the parent of this node
     *
     * @param parent The parent node which lead us to this node
     * @return The depth we have no reached in searching
     */
    public void setParent(Node parent) {
        this.parent = parent;
    }

    public float getX() {
        return point.getX();
    }

    public float getY() {
        return point.getY();
    }

    public float getCost() {
        return cost;
    }

    public Node getParent() {
        return parent;
    }

    public float getHeuristic() {
        return heuristic;
    }

    public Point getPoint() {
        return point;
    }
    
    

    /**
     * @see Comparable#compareTo(Object)
     */
    public int compareTo(Object other) {
        Node o = (Node) other;

        float f = heuristic + cost;
        float of = o.heuristic + o.cost;

        if (f < of) {
            return -1;
        } else if (f > of) {
            return 1;
        } else {
            return 0;
        }
    }
    
    public void updateNode(Node node) {
        cost = node.cost;
        heuristic = node.heuristic;
        parent = node.parent;
    }
}
