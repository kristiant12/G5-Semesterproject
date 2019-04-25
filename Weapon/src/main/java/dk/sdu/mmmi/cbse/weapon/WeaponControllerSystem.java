/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.weapon;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.ShootingPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IPlayer;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

/**
 *
 * @author tfvg-pc11
 */


@ServiceProviders(value = {
    @ServiceProvider(service = IEntityProcessingService.class)})
public class WeaponControllerSystem implements IEntityProcessingService {

    private float delta = 0.03f;
    private float timeSinceLastShot;

    @Override
    public void process(GameData gameData, World world) {
        for(Entity player : world.getEntities()){
            if(player instanceof IPlayer){
                PositionPart positionPart = player.getPart(PositionPart.class);
                MovingPart movingPart = player.getPart(MovingPart.class);
                ShootingPart shootingPart = player.getPart(ShootingPart.class);
                if(shootingPart.getShoot() && timeSinceLastShot > 0.05f){
                    spawnBullet(movingPart, positionPart, shootingPart, gameData, world);
                    timeSinceLastShot = 0f;
                }
                timeSinceLastShot += gameData.getDelta();
            }
        }
        for (Entity bullet : world.getEntities(Weapon.class)) {
            PositionPart positionPart = bullet.getPart(PositionPart.class);
            MovingPart movingPart = bullet.getPart(MovingPart.class);
            LifePart lifePart = bullet.getPart(LifePart.class);
            movingPart.process(gameData, bullet);
            positionPart.process(gameData, bullet);
            
            lifePart.reduceExpiration(delta);
            updateShape(bullet);
            if(lifePart.getExpiration() < 0){
                world.removeEntity(bullet);
            }
        }
        
        
    }
    public void spawnBullet(MovingPart movingPart, PositionPart positionPart,ShootingPart shootingPart,GameData gameData, World world){
        Weapon bullet =  WeaponPlugin.createBullet(positionPart, movingPart);
        world.addEntity(bullet);
        shootingPart.setShoot(false);
    }
    
    private void updateShape(Entity bullet) {
        float[] shapex = bullet.getShapeX();
        float[] shapey = bullet.getShapeY();
        PositionPart positionPart = bullet.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float width = 1;
        float height = 1;
        float radians = positionPart.getRadians();

        shapex[0] = (float) (x + width);
        shapey[0] = (float) (y + height);

        shapex[1] = (float) (x + width);
        shapey[1] = (float) (y - height);

        shapex[2] = (float) (x - width);
        shapey[2] = (float) (y - height);

        shapex[3] = (float) (x - width);
        shapey[3] = (float) (y + height);

        bullet.setShapeX(shapex);
        bullet.setShapeY(shapey);
    }

}
