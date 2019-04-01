/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.services;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import java.util.ArrayList;

/**
 *
 * @author tfvg-pc11
 */
public interface IMap {
    
   void process(GameData gameData, World world, ArrayList<TiledMapTileLayer> map);

    
}
