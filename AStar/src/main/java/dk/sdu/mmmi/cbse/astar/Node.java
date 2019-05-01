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
public class Node implements Comparable<Node> {

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
    public int compareTo(Node o) {
        return Double.compare(cost + heuristic, o.cost + o.heuristic);
    }
    
    public void updateNode(Node node) {
        cost = node.cost;
        heuristic = node.heuristic;
        parent = node.parent;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof Node) {
            Node other = ((Node) o);
            return point.getX() == other.getPoint().getX() && point.getY() == other.getPoint().getY();
        }
        return false;
    }
}
