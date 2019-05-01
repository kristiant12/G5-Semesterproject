/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.astar;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.Point;
import dk.sdu.mmmi.cbse.common.data.World;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Set;

/**
 *
 * @author kaspe
 */
public class AStar {

    private float normal = 1f;
    private float diagonal = (float) Math.sqrt(2);
    private boolean[] grid;
    private PriorityQueue<Node> openList;
    private Set<Node> closedList;
    private int cellSize = 32;
    private int gridWidth = 100;
    private int gridHeight = 100;

    AStar() {
        openList = new PriorityQueue();
        closedList = new HashSet<>();
        grid = new boolean[gridHeight * gridHeight];
    }

    public LinkedList<Point> calculateRoute(Point start, Point end) {
        openList.clear();
        closedList.clear();
        LinkedList<Point> route = new LinkedList<>();

        if (start.getX() == end.getX() && start.getY() == end.getY()) {
            route.add(end);
            return route;
        }

        Node startNode = new Node(start, calculateHeuristic(start, end), 0, null);
        Node endNode = new Node(end, 0, 0, null);
        findNeighbours(startNode, endNode);
        Node nextNode = openList.poll();

        while (!openList.isEmpty() && !nextNode.equals(endNode)) {
            findNeighbours(nextNode, endNode);
            closedList.add(nextNode);
            nextNode = openList.poll();
        }

        if (nextNode.equals(endNode)) {
            route.addFirst(end);
            nextNode = nextNode.getParent();
            // while the next node has a parent, keep adding the next nodes parent to the route towards the player
            while (nextNode.getParent() != null) {
                route.addFirst(new Point(nextNode.getPoint().getX() * cellSize, nextNode.getPoint().getY() * cellSize));
                nextNode = nextNode.getParent();
            }
        }

        return route;

    }

    private float calculateHeuristic(Point start, Point end) {
//        return(float) Math.sqrt(((end.getX() - start.getX()) * (end.getX() - start.getX())) + (end.getY() - start.getY()) * (end.getY() - start.getY()));
        float dx = end.getX() - start.getX();
        float dy = end.getY() - start.getY();
        return (float) Math.sqrt((dx * dx) + (dy * dy));
    }

    private void findNeighbours(Node parent, Node end) {
        ArrayList<Node> neighbors = new ArrayList<>();
        int index = gridWidth * (int) parent.getPoint().getY()%(cellSize/2) + (int) parent.getPoint().getX()%(cellSize/2);

        //Linked if-statements for finding neighbours of a given node, based on the math of the grid
        //the AI uses on top of the map
        if (index % gridWidth != gridWidth - 1 && !grid[index + 1]) {
            neighbors.add(createNode(1, 0, parent, end, normal));
        }
        if (index % gridWidth != 0 && !grid[index - 1]) {
            neighbors.add(createNode(-1, 0, parent, end, normal));
        }
        if (index - gridWidth + 1 >= 0 && index % gridWidth != gridWidth - 1 && !grid[index - gridWidth + 1]) {
            neighbors.add(createNode(1, -1, parent, end, diagonal));
        }
        if (index - gridWidth >= 0 && !grid[index - gridWidth]) {
            neighbors.add(createNode(0, -1, parent, end, normal));
        }
        if (index % gridWidth != 0 && index - gridWidth >= 0 && !grid[index - gridWidth - 1]) {
            neighbors.add(createNode(-1, -1, parent, end, diagonal));
        }
        if (index % gridWidth != 0 && index + gridWidth <= gridWidth * gridHeight - 1 && !grid[index + gridWidth - 1]) {
            neighbors.add(createNode(-1, 1, parent, end, diagonal));
        }
        if (index + gridWidth <= gridWidth * gridHeight - 1 && !grid[index + gridWidth]) {
            neighbors.add(createNode(0, 1, parent, end, normal));
        }
        if (index % gridWidth != gridWidth - 1 && index + gridWidth <= gridWidth * gridHeight - 1 && !grid[index + gridWidth]) {
            neighbors.add(createNode(1, 1, parent, end, diagonal));
        }

        //Loop for checking wether a node is previously walked on
        for (Node node : neighbors) {
            //If it is not in the previously marked nodes, and is not in the fringe, it is added to the fringe
            if (closedList.contains(node)) {
                continue;
            }
            if (!openList.contains(node)) {
                openList.add(node);
            //checks for each node in the fringe if there is another node in the fringe that is better
                //compared to the one we are standing on, based on the cost and heuristic of the pathfinding
            } else {
                for (Node temp : openList) {
                    if (temp.equals(node)) {
                        if (temp.compareTo(node) > 0) {
                            temp.updateNode(node);
                            break;
                        }
                    }
                }
            }
        }
    }

    private Node createNode(int x, int y, Node parent, Node end, float cost) {
        Point newPoint = new Point(parent.getPoint().getX() + x, parent.getPoint().getY() + y);
        return new Node(newPoint, calculateHeuristic(newPoint, end.getPoint()), cost + parent.getCost(), parent);
    }

    void updateGrid(World world, GameData gameData, AssetManager manager) {
        Arrays.fill(grid, false);
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                float x = (i+1) * (cellSize/2);
                float y = (j+1) * (cellSize/2);
                x %= cellSize;
                y %= cellSize;
                
                if (isCellBlocked(x, y, gameData)) {
                    grid[gridWidth * (int) y + (int) x] = true;
                }
            }
        }
    }
    
    private boolean isCellBlocked(float x, float y, GameData gameData) {
        //long preTime = System.currentTimeMillis();
        for (int i = 0; i < gameData.getMapList().size(); i++) {
            TiledMapTileLayer.Cell cell = gameData.getMapList().get(i).getCell((int) (x / gameData.getMapList().get(i).getTileWidth()), (int) (y / gameData.getMapList().get(i).getTileHeight()));
            if (cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("blocked")) {
                return true;
            }
        }
        return false;
    }

}
