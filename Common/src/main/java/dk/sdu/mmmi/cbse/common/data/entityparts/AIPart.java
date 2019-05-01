/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.data.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.Point;
import java.util.LinkedList;
import org.lwjgl.util.vector.Vector2f;

/**
 *
 * @author kaspe
 */
public class AIPart implements EntityPart{
    private final float UPDATE_TIME = 0.5f;

    private float speed;
    private LinkedList<Point> route = new LinkedList<>();
    private boolean update = true;

    private float nextUpdate = UPDATE_TIME;

    public boolean needUpdate() {
        return update;
    }

    public void setRoute(LinkedList<Point> route) {
        update = false;
        this.route = route;
    }

    public AIPart(float maxSpeed) {
        speed = maxSpeed;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        if (!update) {
            nextUpdate -= gameData.getDelta();
            if (nextUpdate <= 0) {
                nextUpdate = UPDATE_TIME;
                update = true;
            }
        }


        if (route.isEmpty()) {
            return;
        }

        float deltaSpeed = speed * gameData.getDelta();

        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();

        Vector2f to = new Vector2f(route.getFirst().getX(), route.getFirst().getY());

        while (Math.abs(x - to.getX()) <= deltaSpeed && Math.abs(y - to.getY()) <= deltaSpeed) {
            route.removeFirst();
            if (route.isEmpty()) return;
            to = new Vector2f(route.getFirst().getX(), route.getFirst().getY());
        }

        Vector2f pos = new Vector2f(x, y);
        //Subtraction of the two vectors
        Vector2f delta = new Vector2f(to.x-pos.x, to.y-pos.x);
        
        //Length of the vector
        float length = (float) Math.sqrt(delta.x * delta.x + delta.y * delta.y);
        //Normalization of the vector, setting the length to 1
        delta = new Vector2f(delta.x/length, delta.y/length);
        //Multiplying the vector with the float deltaSpeed
        delta = new Vector2f(delta.x*deltaSpeed, delta.y*deltaSpeed);
        

        x += delta.getX();
        y += delta.getY();

        positionPart.setRadians((float)Math.atan2(delta.getY(), delta.getX()));

        positionPart.setPosition(x, y);
    }
    
}
