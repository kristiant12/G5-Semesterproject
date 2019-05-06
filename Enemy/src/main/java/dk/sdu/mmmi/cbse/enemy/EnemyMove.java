package dk.sdu.mmmi.cbse.enemy;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.EntityPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IPlayer;

/**
 *
 * @author tfvg-pc11
 */
public class EnemyMove implements EntityPart {

    private float dx, dy;
    private float speed;
    private World world;
    private float radians;

    public EnemyMove(float speed, World world) {
        this.speed = speed;
        this.world = world;
        this.radians = 4;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();
        float dt = gameData.getDelta();

        for (Entity player : world.getEntities()) {
            if (player instanceof IPlayer) {
                PositionPart poPlayer = player.getPart(PositionPart.class);
                float playerX = poPlayer.getX();
                float playerY = poPlayer.getY();
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

                positionPart.setX(x);
                positionPart.setY(y);
                positionPart.setRadians(radians);
            }

        }

    }

}
