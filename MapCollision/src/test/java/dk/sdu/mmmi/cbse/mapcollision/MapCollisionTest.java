/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.mapcollision;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.core.managers.Assets;
import dk.sdu.mmmi.cbse.map.Map;
import dk.sdu.mmmi.cbse.texturplayer.TextPlayerControl;
import dk.sdu.mmmi.cbse.texturplayer.TexturPlayer;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kaspe
 */
public class MapCollisionTest {
    
    public MapCollisionTest() {
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
    
    public ArrayList<TiledMapTileLayer> getLayer(ArrayList<TiledMapTileLayer> mapList, TiledMap tileMapNew) {
        for (int i = 0; i < 5; i++) {
            mapList.add((TiledMapTileLayer) tileMapNew.getLayers().get(i));
        }
        return mapList;
    }

    /**
     * Test of process method, of class MapCollision.
     */
    @Test
    public void testProcess() {
        
        System.out.println("process");
        GameData gameData = new GameData();
        World world = new World();
        ArrayList<TiledMapTileLayer> map = new ArrayList<>();
        System.out.println("Creating map");
        Map mapObject = new Map();
        TiledMap mapTiles = mapObject.getMap();
        map = getLayer(map, mapTiles);
        MapCollision instance = new MapCollision();
        TextPlayerControl pControl = new TextPlayerControl();
        pControl.start(gameData, world, Assets.getInstance().getManger());
        instance.process(gameData, world, map);
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of isCellBlocked method, of class MapCollision.
     */
    @Test
    public void testIsCellBlocked() {
        ArrayList<TiledMapTileLayer> map = new ArrayList<>();
        Map mapObject = new Map();
        TiledMap mapTiles = mapObject.getMap();
        map = getLayer(map, mapTiles);
        
        
        System.out.println("isCellBlocked");
        float x = 0.0F;
        float y = 0.0F;
        MapCollision instance = new MapCollision();
        boolean expResult = false;
        boolean result = instance.isCellBlocked(x, y);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of mapCollision method, of class MapCollision.
     */
    @Test
    public void testMapCollision() {
        System.out.println("mapCollision");
        World world = null;
        GameData gameData = null;
        MapCollision instance = new MapCollision();
        instance.mapCollision(world, gameData);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
