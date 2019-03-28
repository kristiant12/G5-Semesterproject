/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.enemy;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

/**
 *
 * @author tfvg-pc11
 */

@ServiceProviders(value = {
    @ServiceProvider(service = IEntityProcessingService.class)
    ,
    @ServiceProvider(service = IGamePluginService.class)})
public class EnemyControlSystem implements IEntityProcessingService,IGamePluginService{

    private Entity enemy;
    private Entity runner;
    
    @Override
    public void process(GameData gameData, World world) {
          for (Entity player : world.getEntities(Enemy.class)) {
            PositionPart positionPart = player.getPart(PositionPart.class);
            EnemyMove move = player.getPart(EnemyMove.class);
            
            move.process(gameData, player);
            positionPart.process(gameData, player);
        }
    }

    @Override
    public void start(GameData gameData, World world) {
        enemy = createEnemy(gameData, world);
        runner = createRunnerEnemy(gameData, world);
        world.addEntity(enemy);
        world.addEntity(runner);
    }
    
    private Entity createEnemy(GameData gameData,World world){
        
        float speed = 50;
        
        Entity enemyEntity = new Enemy(EnemyType.NORMAL);
        enemyEntity.add(new EnemyMove(speed, world));
        enemyEntity.add(new PositionPart(100, 100,3));
        
        return enemyEntity;
        
        
    }
    
    
    private Entity createRunnerEnemy(GameData gameData, World world){
        
        float speed = 275;
        
        Entity runner = new Enemy(EnemyType.RUNNERS);
        runner.add(new EnemyMove(speed, world));
        runner.add(new PositionPart(100, 100, 3));
        
        return runner;
    }
    
    
    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(enemy);
    }
    
}
