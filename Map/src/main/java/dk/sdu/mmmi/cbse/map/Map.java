package dk.sdu.mmmi.cbse.map;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

/**
 *
 * @author tfvg-pc11
 */
public class Map{
    private TiledMap tileMap;
    
   public Map(){
       createMap();
   } 
   
    
  public void createMap(){
      tileMap = new TmxMapLoader().load("assets\\images\\NewMap.tmx");
  }
    
  public TiledMap getMap(){
      return tileMap;
  }  
  
    
    
       
}
