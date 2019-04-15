/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.entitycollision;

import com.badlogic.gdx.assets.AssetManager;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEnemy;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

/**
 *
 * @author tfvg-pc11
 */
@ServiceProviders(value = {
    @ServiceProvider(service = IPostEntityProcessingService.class)
})
public class Collision implements IPostEntityProcessingService {

    @Override
    public void process(GameData gameData, World world,AssetManager manager) {
        for (Entity entity : world.getEntities()) {
            for (Entity collisionDetection : world.getEntities()) {
                if ((entity.getType() == 6 || entity.getType() == 5) && (collisionDetection.getType() == 6 || collisionDetection.getType() == 5)) {
                    continue;
                }
                // get life parts on all entities
                LifePart entityLife = entity.getPart(LifePart.class);
                LifePart collisionLife = collisionDetection.getPart(LifePart.class);

                // if the two entities are identical, skip the iteration
                if (entity instanceof IEnemy && collisionDetection instanceof IEnemy) {
                    continue;

                }
                // CollisionDetection
                if (entity.getType() != 6) {

                    if (this.Collides(entity, collisionDetection)) {
                        // if entity has been hit, and should have its life reduced
                        if (entityLife.getLife() > 0) {
                            entityLife.setLife(entityLife.getLife() - collisionDetection.getDamage());
                            if(collisionDetection.getType() == 6){
                                world.removeEntity(collisionDetection);
                            }
                            // if entity is out of life - remove
                            if (entityLife.getLife() <= 0) {
                                world.removeEntity(entity);
                            }
                        }
                    }
                }
            }
        }
    }

    public Boolean Collides(Entity entity, Entity entity2) {
        PositionPart entMov = entity.getPart(PositionPart.class);
        PositionPart entMov2 = entity2.getPart(PositionPart.class);
        float dx = (float) entMov.getX() - (float) entMov2.getX();
        float dy = (float) entMov.getY() - (float) entMov2.getY();
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        if (distance < (entity.getWidth() / 2 + entity2.getWidth() / 2)) {
            return true;
        }
        return false;
    }

}
