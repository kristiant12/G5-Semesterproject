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
    private List<Node> path = null;
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
        //System.out.println(start.toString() +" "+goal.toString());
        List<Node> openList = new ArrayList<Node>();
        List<Node> closedList = new ArrayList<Node>();
        Node current = new Node(start, null, 0, getDistance(start, goal));
       // System.out.println(getDistance(start, goal));
        openList.add(current);
        while (openList.size() > 0) {
            Collections.sort(openList, nodeSorter);
            current = openList.get(0);
            if (current.tile.equals(goal)) {
                System.out.println("negger");
                List<Node> path = new ArrayList<Node>();
                while (current.parent != null) {
                    path.add(current);
                    current = current.parent;
                }
                openList.clear();
                closedList.clear();
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
        Vector2i destination = getPlayerLocation();
        int x = (int) (positionPart.getX() / 32);
        int y = (int) (positionPart.getY() / 32);
        float dt = gameData.getDelta();
        float radins = 4;
        path = findPath(start, destination);
        for (Node node : path) {
            System.out.println(node.tile);
        }
        if (path != null) {
            System.out.println("ting");
            System.out.println(path.size());
            if (path.size() > 0) {
                Vector2i vec = path.get(path.size() - 1).tile;
                if (x < vec.getX() / 32) {
                    testX = (int) ((int)(vec.getX() + speed) * dt);
                    System.out.println("x");
                }
                if (x > vec.getX() / 32) {
                    testX = (int) ((int) (vec.getX() - speed)*dt);
                    System.out.println("x");

                }
                if (y < vec.getY() / 32) {
                    testY = (int) ((int) (vec.getY() + speed)* dt);
                    System.out.println("x");

                }
                if (y > vec.getY() / 32) {
                    testY = (int) ((int) (vec.getY() - speed)* dt);
                    System.out.println("x");

                }

            }
        }

        positionPart.setX(testX);
        positionPart.setY(testY);
        positionPart.setRadians(radins);
        // time++;
    }

}
