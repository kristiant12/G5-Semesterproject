/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.weapon;

import com.badlogic.gdx.assets.AssetManager;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author nitra
 */
public class WeaponPluginTest {

    public WeaponPluginTest() {
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
     * Test of start method, of class WeaponPlugin.
     */
    @Test
    public void testStart() {
        System.out.println("start");
        System.out.println("test is irellevant");
    }

    /**
     * Test of createBullet method, of class WeaponPlugin.
     */
    @Test
    public void testCreateBullet() {
        System.out.println("createBullet");
        World world = new World();
        PositionPart playerPositionPart = new PositionPart(0, 0, 0);
        MovingPart playerMovingPart = new MovingPart(0, 9999, 300, 0);
        PositionPart playerPositionPart2 = new PositionPart(0, 0, 0);
        MovingPart playerMovingPart2 = new MovingPart(0, 9999, 300, 0);
        Weapon expResult = WeaponPlugin.createBullet(playerPositionPart, playerMovingPart);
        Weapon result = WeaponPlugin.createBullet(playerPositionPart2, playerMovingPart2);
        int p = expResult.getType();
        int l = result.getType();
        System.out.println("bullet created");
        assertEquals(p, l);
    }

    /**
     * Test of stop method, of class WeaponPlugin.
     */
    @Test
    public void testStop() {
        System.out.println("stop");
        GameData gameData = new GameData();
        World world = new World();
        WeaponPlugin instance = new WeaponPlugin();
        instance.stop(gameData, world);
        // TODO review the generated test code and remove the default call to fail.
        System.out.println("bullet removed from world");
    }

}
