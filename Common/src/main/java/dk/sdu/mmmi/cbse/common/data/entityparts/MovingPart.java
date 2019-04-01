/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.data.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

/**
 *
 * @author Alexander
 */
public class MovingPart implements EntityPart {
    private float dx, dy;
    private float acceleration, vDeceleration, hDeceleration;
    private float maxSpeed, rotationSpeed;
    private boolean left, right, up, down;

    public MovingPart(float deceleration, float acceleration, float maxSpeed, float rotationSpeed) {
        this.vDeceleration = deceleration;
        this.hDeceleration = deceleration;
        this.acceleration = acceleration;
        this.maxSpeed = maxSpeed;
        this.rotationSpeed = rotationSpeed;
    }

    public float getDx() {
        return dx;
    }

    public float getDy() {
        return dy;
    }
    
    

    public void setDeceleration(float deceleration) {
        this.vDeceleration = deceleration;
        this.hDeceleration = deceleration;
    }

    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }

    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }
    
    public void setSpeed(float speed) {
        this.acceleration = speed;
        this.maxSpeed = speed;
    }

    public void setRotationSpeed(float rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setUp(boolean up) {
        this.up = up;
    }
    
    public void setDown(boolean down) {
        this.down = down;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = (float) Math.atan2(gameData.getMouseY(), gameData.getMouseX());
        float dt = gameData.getDelta();

        
        
        
        // turning
        if (left) {
            dx -= acceleration * dt;
        } else if (right) {
            dx += acceleration * dt;
        } else {
            hDeceleration = 200;
        }

        // accelerating            
        if (up) {
            dy += acceleration * dt;
        } else if(down) {
            dy -= acceleration * dt;
        } else {
            vDeceleration = 200;
        }

        // deccelerating
        float vec = (float) sqrt(dx * dx + dy * dy);
        if (vec > 0) {
            dx -= (dx / vec) * vDeceleration * dt;
            dy -= (dy / vec) * hDeceleration * dt;
        }
        if (vec > maxSpeed) {
            dx = (dx / vec) * maxSpeed;
            dy = (dy / vec) * maxSpeed;
        }
        
        setDeceleration(10);

        // set position
        x += dx * dt;
        if (x > gameData.getDisplayWidth()) {
//            x = gameData.getDisplayWidth();
        }
        else if (x < 0) {
//            x = 0;
        }

        y += dy * dt;
        if (y > gameData.getDisplayHeight()) {
//            y  = gameData.getDisplayHeight();
        }
        else if (y < 0) {
//            y = 0;
        }
        
        positionPart.setX(x);
        positionPart.setY(y);

        positionPart.setRadians(radians);
    }

}
