/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.astar;

import com.badlogic.gdx.assets.AssetManager;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.Point;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.AIPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEnemy;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import java.util.LinkedList;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

/**
 *
 * @author kaspe
 */
@ServiceProviders(value = {
    @ServiceProvider(service = IPostEntityProcessingService.class)
    
    })
public class AIProcess implements IPostEntityProcessingService{
    private AStar aStar;
    
    public AIProcess(){
        aStar = new AStar();
    }

    @Override
    public void process(GameData gameData, World world, AssetManager manager) {
        aStar.updateGrid(world, gameData, manager);
        Entity player = null;
        PositionPart playerPos = null;
        // Finds the player, and the position of the player on the map
        for (Entity p : world.getEntities()) {
            if (p.getType() == 5) {
                player = p;
                playerPos = player.getPart(PositionPart.class);
                break;
            }
        }
        
        if (player == null) 
            return;

        // Finds all enemies, and get a hold of their movingpart
        for (Entity enemy : world.getEntities()) {
            if (enemy.getType() == 2 ) {
                AIPart AIPart = enemy.getPart(AIPart.class);

                // Checks if the timelimit for updating the path has been exceeded
                // Updates the list of points to walk to get to the player
                if (AIPart.needUpdate()) {
                    PositionPart positionPart = enemy.getPart(PositionPart.class);
                    LinkedList<Point> route = aStar.calculateRoute(new Point(positionPart.getX(), positionPart.getY()), new Point(playerPos.getX(), playerPos.getY()));
                    AIPart.setRoute(route);
                }
            }

        }
    }
    
}
