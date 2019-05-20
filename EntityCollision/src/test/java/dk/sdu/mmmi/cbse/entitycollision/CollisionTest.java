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
//        System.out.println("process");
//        GameData gameData = new GameData();
//        World world = new World();
//        AssetManager manager = new AssetManager();
//        Collision instance = new Collision();
//        Entity p = new Entity();
//        Entity e = new Entity();
//        world.addEntity(e);
//        world.addEntity(p);
//        instance.process(gameData, world, manager);
//        // TODO review the generated test code and remove the default call to fail.
//        //fail("The test case is a prototype.");
    }

    /**
     * Test of Collides method, of class Collision.
     */
    @Test
    public void testCollides() {
        System.out.println("Collides");
        Entity entity = new Entity();
        PositionPart part = new PositionPart(0, 0, 0);
        entity.add(part);
        entity.setWidth(10);
        entity.setHeight(10);
        Entity entity2 = new Entity();
        entity2.add(part);
        entity2.setWidth(10);
        entity2.setHeight(10);
        Collision instance = new Collision();
        Boolean expResult = true;
        Boolean result = instance.Collides(entity, entity2);
        assertEquals(expResult, result);
       
        
        // TODO review the generated test code and remove the default call to fail.
    }
    
}
