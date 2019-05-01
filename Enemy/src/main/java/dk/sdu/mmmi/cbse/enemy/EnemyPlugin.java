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
import dk.sdu.mmmi.cbse.common.data.entityparts.AIPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import java.util.Random;


/**
 *
 * @author tfvg-pc11
 */

public class EnemyPlugin {

    private Entity enemy;
    private Entity runner;
    private Entity fatty;
    private Entity boss;
    private Random rand = new Random();
    private int type;


    

    public Entity createNormalEnemy(GameData gameData, World world, AssetManager manager) {
        System.out.println("Creating normal enemy");
        float speed = 110;
        int life = 50;

        Entity enemyEntity = new Enemy();
        enemyEntity.setDamage(10);
        enemyEntity.setImage(manager.get("assets/images/Enemies.png", Texture.class));
        enemyEntity.setWidth(enemyEntity.getImage().getWidth());
        enemyEntity.setHeight(enemyEntity.getImage().getHeight());

        enemyEntity.setType(1);
        System.out.println("Pre ai");
        enemyEntity.add(new AIPart(speed));
        System.out.println("Post AI");
        enemyEntity.add(new PositionPart(rand.nextInt(400) + 150, rand.nextInt(355) + 275, 3));
        enemyEntity.add(new LifePart(life));

        return enemyEntity;

    }

    public Entity createRunnerEnemy(GameData gameData, World world, AssetManager manager) {
        System.out.println("Creating runner enemy");
        float speed = 170;
        int life = 25;

        Entity enemyEntity = new Enemy();
        enemyEntity.setDamage(5);
        enemyEntity.setImage(manager.get("assets/images/Runner.png", Texture.class));
        enemyEntity.setWidth(enemyEntity.getImage().getWidth());
        enemyEntity.setHeight(enemyEntity.getImage().getHeight());

        enemyEntity.setType(2);
        System.out.println("Pre ai");
        enemyEntity.add(new AIPart(speed));
        System.out.println("Post AI");
        enemyEntity.add(new PositionPart(rand.nextInt(400) + 150, rand.nextInt(355) + 275, 3));
        enemyEntity.add(new LifePart(life));

        return enemyEntity;
    }

    public Entity createFattyEnemy(GameData gameData, World world, AssetManager manager) {
        System.out.println("fatty");
        float speed = 90;
        int life = 100;

        Entity enemyEntity = new Enemy();
        enemyEntity.setDamage(25);
        enemyEntity.setImage(manager.get("assets/images/Fatties.png", Texture.class));
        enemyEntity.setWidth(enemyEntity.getImage().getWidth());
        enemyEntity.setHeight(enemyEntity.getImage().getHeight());

        enemyEntity.setType(3);
        System.out.println("Pre ai");
        enemyEntity.add(new AIPart(speed));
        System.out.println("Post AI");
        enemyEntity.add(new PositionPart(rand.nextInt(400) + 150, rand.nextInt(355) + 275, 3));
        enemyEntity.add(new LifePart(life));

        return enemyEntity;
    }

    public Entity createBossEnemy(GameData gameData, World world, AssetManager manager) {
        System.out.println("boss");
        float speed = 100;
        int life = 200;

        Entity enemyEntity = new Enemy();
        enemyEntity.setDamage(50);
        enemyEntity.setImage(manager.get("assets/images/Boss.png", Texture.class));
        enemyEntity.setWidth(enemyEntity.getImage().getWidth());
        enemyEntity.setHeight(enemyEntity.getImage().getHeight());

        enemyEntity.setType(4);
        System.out.println("Pre ai");
        enemyEntity.add(new AIPart(speed));
        System.out.println("Post AI");
        enemyEntity.add(new PositionPart(rand.nextInt(400) + 150, rand.nextInt(355) + 275, 3));
        enemyEntity.add(new LifePart(life));

        return enemyEntity;
    }

   
    public void stop(GameData gameData, World world) {
        for(Entity enmey : world.getEntities(Enemy.class)){
            world.removeEntity(enmey);
        }
    }

}
