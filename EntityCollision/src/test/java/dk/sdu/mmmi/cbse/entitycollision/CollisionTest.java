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
import dk.sdu.mmmi.cbse.texturplayer.TexturPlayer;
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
        GameData gameData = null;
        World world = null;
        AssetManager manager = null;
        Collision instance = new Collision();
        instance.process(gameData, world, manager);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of Collides method, of class Collision.
     */
    @Test
    public void testCollides() {
        System.out.println("Collides");
        Entity entity = null;
        Entity entity2 = null;
        Collision instance = new Collision();
        Boolean expResult = null;
        Boolean result = instance.Collides(entity, entity2);
        assertEquals(expResult, result);
       
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
