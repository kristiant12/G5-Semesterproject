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
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tfvg-pc11
 */
public class CollisionTest {
    
    public CollisionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of process method, of class Collision.
     */
    @Test
    public void testProcess() {
        System.out.println("process");
        GameData gameData = new GameData();
        World world = new World();
        AssetManager manager = new AssetManager();
        Collision instance = new Collision();
        
        Entity player = new Entity();
        player.add(new PositionPart(0, 0, 0));
        player.setHeight(10);
        player.setWidth(10);
        player.setType(5);
        player.setDamage(1000);
        player.add(new LifePart(100));
        Entity enemy = new Entity();
        enemy.add(new PositionPart(0, 0, 0));
        enemy.setHeight(10);
        enemy.setWidth(10);
        enemy.setType(1);
        enemy.setDamage(1);
        enemy.add(new LifePart(100));
        world.addEntity(enemy);
        world.addEntity(player);
        System.out.println(world.getEntities().size());
        instance.process(gameData, world, manager);
        int expSize = 1;
        int result = world.getEntities().size();
        
        assertEquals(expSize, result);
        
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of Collides method, of class Collision.
     */
    @Test
    public void testCollides() {
        System.out.println("Collides");
        Entity entity = new Entity();

        entity.add(new PositionPart(0, 0, 0));
        entity.setWidth(10);
        entity.setHeight(10);
        
        Entity entity2 = new Entity();
        entity2.add(new PositionPart(0, 0, 0));
        entity2.setWidth(10);
        entity2.setHeight(10);

        Collision instance = new Collision();
        
        PositionPart s = entity2.getPart(PositionPart.class);
        PositionPart d = entity.getPart(PositionPart.class);
        
        System.out.println("Entity1: x "+s.getX()+" y "+s.getY());
        System.out.println("Entity2: x "+d.getX()+" y "+d.getY());

        Boolean expResult = true;
        Boolean result = instance.Collides(entity, entity2);
        assertEquals(expResult, result);
        s.setX(100);
        s.setY(100);
        
        System.out.println("Entity1: x "+s.getX()+" y "+s.getY());
        System.out.println("Entity2: x "+d.getX()+" y "+d.getY());
        expResult = false;
        result = instance.Collides(entity, entity2);
        assertEquals(expResult, result);
       
        
        
        
        
        // TODO review the generated test code and remove the default call to fail.
    }
    
}
