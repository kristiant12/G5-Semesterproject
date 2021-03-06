package dk.sdu.mmmi.cbse.common.data.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEnemy;
import dk.sdu.mmmi.cbse.common.services.IPlayer;
import dk.sdu.mmmi.cbse.common.services.IWeapon;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

public class MovingPart implements EntityPart {

    private World world;
    private float dx, dy;
    private float acceleration, vDeceleration, hDeceleration;
    private float maxSpeed, rotationSpeed, speed;
    private boolean left, right, up, down, space;
    private boolean collisionX = false, collisionY = false;

    public MovingPart(float deceleration, float acceleration, float maxSpeed, float rotationSpeed) {
        this.vDeceleration = deceleration;
        this.hDeceleration = deceleration;
        this.acceleration = acceleration;
        this.maxSpeed = maxSpeed;
        this.rotationSpeed = rotationSpeed;
    }

    public MovingPart(float speed, World world) {
        this.speed = speed;
        this.world = world;
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

    public void setCollisionX(boolean b) {
        collisionX = b;
    }

    public void setCollisionY(boolean b) {
        collisionY = b;
    }

    public void setSpace(boolean space) {
        this.space = space;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        PositionPart positionPart = entity.getPart(PositionPart.class);
        ShootingPart shootingPart = entity.getPart(ShootingPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = (float) Math.atan2(gameData.getMouseY(), gameData.getMouseX());
        float dt = gameData.getDelta();
        if (entity instanceof IWeapon) {
            radians = positionPart.getRadians();
            dx += cos(radians) * acceleration * dt;
            dy += sin(radians) * acceleration * dt;
        } else if (entity instanceof IEnemy) {
            float playerX = 0;
            float playerY = 0;

            for (Entity player : world.getEntities()) {
                if (player instanceof IPlayer) {
                    PositionPart poPlayer = player.getPart(PositionPart.class);
                    playerX = poPlayer.getX();
                    playerY = poPlayer.getY();

                    radians = (float) Math.atan2(playerY - y, playerX - x);

                    if (x < playerX) {
                        x += speed * dt;
                    } else if (x > playerX) {
                        x -= speed * dt;
                    }

                    if (y < playerY) {
                        y += speed * dt;
                    } else if (y > playerY) {
                        y -= speed * dt;

                    }

                }
            }

        } else {
            if (collisionX) {
                hDeceleration = 5000;
            } else if (collisionY) {
                vDeceleration = 5000;
            }

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
            } else if (down) {
                dy -= acceleration * dt;
            } else {
                vDeceleration = 200;
            }

            // deccelerating
            float vec = (float) sqrt(dx * dx + dy * dy);
            if (vec > 0) {
                dx -= (dx / vec) * hDeceleration * dt;
                dy -= (dy / vec) * vDeceleration * dt;
            }
            if (vec > maxSpeed) {
                dx = (dx / vec) * maxSpeed;
                dy = (dy / vec) * maxSpeed;
            }
            setDeceleration(10);

        }
        // set position
        x += dx * dt;
        y += dy * dt;

        positionPart.setX(x);
        positionPart.setY(y);

        positionPart.setRadians(radians);
    }

}
