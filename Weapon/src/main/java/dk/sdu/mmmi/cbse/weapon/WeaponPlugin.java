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
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

/**
 *
 * @author tfvg-pc11
 */


@ServiceProviders(value = {
    
    @ServiceProvider(service = IGamePluginService.class)})
public class WeaponPlugin implements IGamePluginService{

    @Override
    public void start(GameData gameData, World world) {
    }
       public static Weapon createBullet(PositionPart playerPositionPart, MovingPart playerMovingPart) {

        float deacceleration = 0;
        float acceleration = 350;
        float maxSpeed = 350;
        float rotationSpeed = 0;
        float x = playerPositionPart.getX();
        float y = playerPositionPart.getY();
        float radians = playerPositionPart.getRadians();

        
        Entity bullet = new Weapon();
        bullet.add(new PositionPart(x, y, radians));
        bullet.setRadius(1);
        MovingPart movingPart = new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed);
        movingPart.setUp(true);
        bullet.add(movingPart);
      
        bullet.add(new LifePart(1,3));
        
        return (Weapon)bullet;
    }
    

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity bullet : world.getEntities(Weapon.class)) {
            world.removeEntity(bullet);
        }
    }
    
}
