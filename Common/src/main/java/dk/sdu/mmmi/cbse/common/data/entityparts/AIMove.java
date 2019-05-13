/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.data.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IPlayer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author tfvg-pc11
 */
public class AIMove implements EntityPart {

    private float speed;
    private World world;
    private List<Node> path = new ArrayList();
    private float dx, dy;
    private int time = 0;
    private int xa, ya;
    private int testX = 0;
    private int testY = 0;

    private Comparator<Node> nodeSorter = new Comparator<Node>() {
        @Override
        public int compare(Node n0, Node n1) {
            if (n1.fCost < n0.fCost) {
                return +1;
            }
            if (n1.fCost > n0.fCost) {
                return -1;
            }
            return 0;
        }
    };

    public AIMove(float speed, World world) {
        this.speed = speed;
        this.world = world;
    }

    public List<Node> findPath(Vector2i start, Vector2i goal) {
        List<Node> openList = new ArrayList<Node>();
        List<Node> closedList = new ArrayList<Node>();
        Node current = new Node(start, null, 0, getDistance(start, goal));
        openList.add(current);
        while (openList.size() > 0) {
            Collections.sort(openList, nodeSorter);
            current = openList.get(0);
            if (current.tile.equals(goal)) {
                List<Node> path = new ArrayList<Node>();
                while (current.parent != null) {
                    path.add(current);
                    current = current.parent;
                }
                openList.clear();
                if (closedList.size() <= 10) {
                    closedList.add(new Node(goal, null, 0, 0));
                    return closedList;
                } else {
                    for (int i = 0; i < 10; i++) {
                        path.add(closedList.get(i));
                    }
                    System.out.println("noget");
                }
                return path;
            }
            openList.remove(current);
            closedList.add(current);
            for (int i = 0; i < 9; i++) {
                if (i == 4) {
                    continue;
                }
                int x = current.tile.getX();
                int y = current.tile.getY();
                int xi = (i % 3) - 1;
                int yi = (i / 3) - 1;
                Vector2i a = new Vector2i(x + xi, y + yi);
                double gCost = current.gCost + getDistance(current.tile, a);
                double hCost = getDistance(a, goal);
                Node node = new Node(a, current, gCost, hCost);
                if (vecInList(closedList, a) && gCost >= node.gCost) {
                    continue;
                }
                if (!vecInList(openList, a) || gCost < node.gCost) {
                    openList.add(node);
                }
            }

        }
        closedList.clear();
        return null;
    }

    private boolean vecInList(List<Node> list, Vector2i vector2i) {
        for (Node n : list) {
            if (n.tile.equals(vector2i)) {
                return true;
            }
        }
        return false;

    }

    private double getDistance(Vector2i tile, Vector2i goal) {
        double dx = tile.getX() - goal.getX();
        double dy = tile.getY() - goal.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public Vector2i getPlayerLocation() {
        Vector2i position = new Vector2i();
        for (Entity player : world.getEntities()) {
            if (player instanceof IPlayer) {
                PositionPart poPlayer = player.getPart(PositionPart.class);
                position.set((int) poPlayer.getX() / 32, (int) poPlayer.getY() / 32);
            }
        }
        return position;
    }

    @Override
    public void process(GameData gameData, Entity entity) {

        PositionPart positionPart = entity.getPart(PositionPart.class);
        Vector2i start = new Vector2i((int) positionPart.getX() / 32, (int) positionPart.getY() / 32);
        System.out.println("Start: " + start);
        Vector2i destination = getPlayerLocation();
        System.out.println("destination: " + destination);
        System.out.println("player position: " + destination.getX() + "," + destination.getY());
        int x = (int) (positionPart.getX() / 32);
        int y = (int) (positionPart.getY() / 32);
        float dt = gameData.getDelta();
        float radins = 4;
        path = findPath(start, destination);
       
        
        if (path != null) {
            
            System.out.println("ting");
            System.out.println(path.size());
            if (path.size() > 0) {
                Vector2i vec = path.get(path.size() - 1).tile;
                radins = (float) Math.atan2(destination.getY() - y, destination.getX() - x);
                if (x < vec.getX()) {
                    testX += ((vec.getX() + speed) * dt);
                    System.out.println("x1");
                }
                if (x > vec.getX()) {
                    testX -= ((vec.getX() + speed) * dt);
                    System.out.println("x2");

                }
                if (y < vec.getY()) {
                    testY += ((vec.getY() + speed) * dt);
                    System.out.println("y1");

                }
                if (y > vec.getY()) {
                    testY -= ((vec.getY() + speed) * dt);
                    System.out.println("y2");

                }

            }
        }
        positionPart.setX(testX);
        positionPart.setY(testY);
        positionPart.setRadians(radins);
        path.clear();

        // time++;
    }

}
