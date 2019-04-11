/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.enemy;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import java.util.Random;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

/**
 *
 * @author tfvg-pc11
 */
//@ServiceProviders(value = {
//    @ServiceProvider(service = IEntityProcessingService.class)
//    ,
//    @ServiceProvider(service = IGamePluginService.class)})
//public class EnemyControlSystem implements IEntityProcessingService, IGamePluginService {
@ServiceProviders(value = {
    @ServiceProvider(service = IEntityProcessingService.class)
    
    })
public class EnemyControlSystem implements IEntityProcessingService{

//    private Entity enemy;
//    private Entity runner;
//    private Entity runner1;
//    private Entity runner2;
//    private Entity runner3;
//    private Entity runner4;
//    private Entity runner5;
//    private Entity runner6;
//    private Entity fatty;
//    private Entity boss;
//    private Random rand = new Random();

    @Override
    public void process(GameData gameData, World world) {
        for (Entity player : world.getEntities(Enemy.class)) {
            PositionPart positionPart = player.getPart(PositionPart.class);
            MovingPart move = player.getPart(MovingPart.class);

            move.process(gameData, player);
            positionPart.process(gameData, player);
        }
    }

//    @Override
//    public void start(GameData gameData, World world, AssetManager manager) {
//        enemy = createNormalEnemy(gameData, world, manager);
//        runner = createRunnerEnemy(gameData, world, manager);
//        runner1 = createRunnerEnemy(gameData, world, manager);
//        runner2 = createRunnerEnemy(gameData, world, manager);
//        runner3 = createRunnerEnemy(gameData, world, manager);
//        runner4 = createRunnerEnemy(gameData, world, manager);
//        runner5 = createRunnerEnemy(gameData, world, manager);
//        runner6 = createRunnerEnemy(gameData, world, manager);
//
//        fatty = createFattyEnemy(gameData, world, manager);
//        boss = createBossEnemy(gameData, world, manager);
//        world.addEntity(enemy);
//        world.addEntity(runner);
//        world.addEntity(runner1);
//        world.addEntity(runner2);
//        world.addEntity(runner3);
//        world.addEntity(runner4);
//        world.addEntity(runner5);
//        world.addEntity(runner6);
//
//        world.addEntity(fatty);
//        world.addEntity(boss);
//    }
//
//    private Entity createNormalEnemy(GameData gameData, World world, AssetManager manager) {
//
//        float speed = 50;
//        int life = 50;
//
//        Entity enemyEntity = new Enemy();
//        enemyEntity.setDamage(10);
//        enemyEntity.setImage(manager.get("assets/images/Enemies.png", Texture.class));
//        enemyEntity.setWidth(enemyEntity.getImage().getWidth());
//        enemyEntity.setHeight(enemyEntity.getImage().getHeight());
//
//        enemyEntity.setType(1);
//        enemyEntity.add(new MovingPart(speed, world));
//        enemyEntity.add(new PositionPart(rand.nextInt(400) + 150, rand.nextInt(355) + 275, 3));
//        enemyEntity.add(new LifePart(life));
//
//        return enemyEntity;
//
//    }
//
//    private Entity createRunnerEnemy(GameData gameData, World world, AssetManager manager) {
//
//        float speed = 130;
//        int life = 25;
//
//        Entity enemyEntity = new Enemy();
//        enemyEntity.setDamage(5);
//        enemyEntity.setImage(manager.get("assets/images/Runner.png", Texture.class));
//        enemyEntity.setWidth(enemyEntity.getImage().getWidth());
//        enemyEntity.setHeight(enemyEntity.getImage().getHeight());
//
//        enemyEntity.setType(2);
//        enemyEntity.add(new MovingPart(speed, world));
//        enemyEntity.add(new PositionPart(rand.nextInt(400) + 150, rand.nextInt(355) + 275, 3));
//        enemyEntity.add(new LifePart(life));
//
//        return enemyEntity;
//    }
//
//    private Entity createFattyEnemy(GameData gameData, World world, AssetManager manager) {
//
//        float speed = 35;
//        int life = 100;
//
//        Entity enemyEntity = new Enemy();
//        enemyEntity.setDamage(25);
//        enemyEntity.setImage(manager.get("assets/images/Fatties.png", Texture.class));
//        enemyEntity.setWidth(enemyEntity.getImage().getWidth());
//        enemyEntity.setHeight(enemyEntity.getImage().getHeight());
//
//        enemyEntity.setType(3);
//        enemyEntity.add(new MovingPart(speed, world));
//        enemyEntity.add(new PositionPart(rand.nextInt(400) + 150, rand.nextInt(355) + 275, 3));
//
//        enemyEntity.add(new LifePart(life));
//
//        return enemyEntity;
//    }
//
//    private Entity createBossEnemy(GameData gameData, World world, AssetManager manager) {
//
//        float speed = 150;
//        int life = 200;
//
//        Entity enemyEntity = new Enemy();
//        enemyEntity.setDamage(50);
//        enemyEntity.setImage(manager.get("assets/images/Boss.png", Texture.class));
//        enemyEntity.setWidth(enemyEntity.getImage().getWidth());
//        enemyEntity.setHeight(enemyEntity.getImage().getHeight());
//
//        enemyEntity.setType(4);
//        enemyEntity.add(new MovingPart(speed, world));
//        enemyEntity.add(new PositionPart(rand.nextInt(400) + 150, rand.nextInt(355) + 275, 3));
//
//        enemyEntity.add(new LifePart(life));
//
//        return enemyEntity;
//    }
//
//    @Override
//    public void stop(GameData gameData, World world) {
//        world.removeEntity(enemy);
//    }

}
