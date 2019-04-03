/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.enemy;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
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
public class EnemyControlSystem implements IEntityProcessingService, IGamePluginService {

    private Entity enemy;
    private Entity runner;
    private Entity fatty;
    private Entity boss;

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
        enemy = createNormalEnemy(gameData, world);
        runner = createRunnerEnemy(gameData, world);
        fatty = createFattyEnemy(gameData, world);
        boss = createBossEnemy(gameData, world);
        world.addEntity(enemy);
        world.addEntity(runner);
        world.addEntity(fatty);
        world.addEntity(boss);
    }

    private Entity createNormalEnemy(GameData gameData, World world) {

        float speed = 50;
        int life = 50;

        Entity enemyEntity = new Enemy();
        enemyEntity.setType(1);
        enemyEntity.add(new EnemyMove(speed, world));
        enemyEntity.add(new PositionPart(100, 100, 3));
        enemyEntity.add(new LifePart(life));

        return enemyEntity;

    }

    private Entity createRunnerEnemy(GameData gameData, World world) {

        float speed = 130;
        int life = 25;
        
        Entity enemyEntity = new Enemy();
        enemyEntity.setType(2);
        enemyEntity.add(new EnemyMove(speed, world));
        enemyEntity.add(new PositionPart(100, 100, 3));
        enemyEntity.add(new LifePart(life));

        return enemyEntity;
    }
    private Entity createFattyEnemy(GameData gameData, World world) {

        float speed = 35;
        int life = 100;

        Entity enemyEntity = new Enemy();
        enemyEntity.setType(3);
        enemyEntity.add(new EnemyMove(speed, world));
        enemyEntity.add(new PositionPart(100, 100, 3));
        enemyEntity.add(new LifePart(life));


        return enemyEntity;
    }
    private Entity createBossEnemy(GameData gameData, World world) {

        float speed = 25;
        int life = 200;

        Entity enemyEntity = new Enemy();
        enemyEntity.setType(4);
        enemyEntity.add(new EnemyMove(speed, world));
        enemyEntity.add(new PositionPart(100, 100, 3));
        enemyEntity.add(new LifePart(life));


        return enemyEntity;
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(enemy);
    }

}
