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
            MovingPart move = player.getPart(MovingPart.class);

            move.process(gameData, player);
            positionPart.process(gameData, player);
        }
    }

    @Override
    public void start(GameData gameData, World world, AssetManager manager) {
        enemy = createNormalEnemy(gameData, world, manager);
        runner = createRunnerEnemy(gameData, world, manager);
        fatty = createFattyEnemy(gameData, world, manager);
        boss = createBossEnemy(gameData, world, manager);
        world.addEntity(enemy);
        world.addEntity(runner);
        world.addEntity(fatty);
        world.addEntity(boss);
    }

    private Entity createNormalEnemy(GameData gameData, World world, AssetManager manager) {

        float speed = 50;
        int life = 50;

        Entity enemyEntity = new Enemy();
        enemyEntity.setImage(manager.get("assets/images/Enemies.png", Texture.class));
        enemyEntity.setWidth(enemyEntity.getImage().getWidth());
        enemyEntity.setHeight(enemyEntity.getImage().getHeight());

        enemyEntity.setType(1);
        enemyEntity.add(new MovingPart(speed, world));
        enemyEntity.add(new PositionPart(300, 300, 3));
        enemyEntity.add(new LifePart(life));

        return enemyEntity;

    }

    private Entity createRunnerEnemy(GameData gameData, World world, AssetManager manager) {

        float speed = 130;
        int life = 25;

        Entity enemyEntity = new Enemy();
        enemyEntity.setImage(manager.get("assets/images/Runner.png", Texture.class));
        enemyEntity.setWidth(enemyEntity.getImage().getWidth());
        enemyEntity.setHeight(enemyEntity.getImage().getHeight());

        enemyEntity.setType(2);
        enemyEntity.add(new MovingPart(speed, world));
        enemyEntity.add(new PositionPart(300, 300, 3));
        enemyEntity.add(new LifePart(life));

        return enemyEntity;
    }

    private Entity createFattyEnemy(GameData gameData, World world, AssetManager manager) {

        float speed = 35;
        int life = 100;

        Entity enemyEntity = new Enemy();
        enemyEntity.setImage(manager.get("assets/images/Fatties.png", Texture.class));
        enemyEntity.setWidth(enemyEntity.getImage().getWidth());
        enemyEntity.setHeight(enemyEntity.getImage().getHeight());

        enemyEntity.setType(3);
        enemyEntity.add(new MovingPart(speed, world));
        enemyEntity.add(new PositionPart(300, 300, 3));
        enemyEntity.add(new LifePart(life));

        return enemyEntity;
    }

    private Entity createBossEnemy(GameData gameData, World world, AssetManager manager) {

        float speed = 25;
        int life = 200;

        Entity enemyEntity = new Enemy();
        enemyEntity.setImage(manager.get("assets/images/Boss.png", Texture.class));
        enemyEntity.setWidth(enemyEntity.getImage().getWidth());
        enemyEntity.setHeight(enemyEntity.getImage().getHeight());
        
        enemyEntity.setType(4);
        enemyEntity.add(new MovingPart(speed, world));
        enemyEntity.add(new PositionPart(300, 300, 3));
        enemyEntity.add(new LifePart(life));

        return enemyEntity;
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(enemy);
    }

}
