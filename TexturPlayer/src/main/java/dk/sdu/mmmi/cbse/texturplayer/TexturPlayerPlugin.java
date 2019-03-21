/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.texturplayer;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

/**
 *
 * @author tfvg-pc11
 */

public class TexturPlayerPlugin implements IGamePluginService{
    
    private Entity player;

    @Override
    public void start(GameData gameData, World world) {
        player = createPlayerShip(gameData);
        world.addEntity(player);
    }
    
    private Entity createPlayerShip(GameData gameData){
        float deacceleration = 10;
        float acceleration = 200;
        float maxSpeed = 200;
        float rotationSpeed = 5;
        float x = gameData.getDisplayWidth() / 2;
        float y = gameData.getDisplayHeight() / 2;
        float radians = 3.1415f / 2;
        
        Entity playerShip = new TexturPlayer();
        playerShip.add(new MovingPart(deacceleration,acceleration,maxSpeed,rotationSpeed));
        playerShip.add(new PositionPart(x,y,radians));
        
        return playerShip;
    }
    
    

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(player);
    }
    
}
