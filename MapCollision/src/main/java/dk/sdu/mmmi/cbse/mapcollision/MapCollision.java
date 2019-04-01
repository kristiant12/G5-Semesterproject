/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.mapcollision;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;

import dk.sdu.mmmi.cbse.common.services.IMap;
import java.util.ArrayList;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;


@ServiceProviders(value = {
    @ServiceProvider(service = IMap.class) } )

public class MapCollision implements IMap{
    
   private ArrayList<TiledMapTileLayer> mapList;
   private String blockedKey = "blocked";
   
   
   
   
    @Override
    public void process(GameData gameData, World world, ArrayList<TiledMapTileLayer> map) {
        mapList = map;
        System.out.println("test");
        mapCollision(world);
        
    }
     private boolean isCellBlocked(float x, float y){
         for (int i = 0; i < mapList.size(); i++) {
             TiledMapTileLayer.Cell cell = mapList.get(i).getCell((int)(x/mapList.get(i).getTileWidth()), (int)(y/mapList.get(i).getTileHeight()));
             if(cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey(blockedKey)){
                 return true;
             }

         }
               
        return false;
   }
      public void mapCollision(World world){
       for(Entity entity : world.getEntities()){
           PositionPart positionPart = entity.getPart(PositionPart.class);
       float oldX = positionPart.getX(),oldY = positionPart.getY(),tiledWith = mapList.get(0).getTileWidth(),tiledHeight = mapList.get(0).getTileHeight();
       boolean collisoinX = false;
       boolean CollisionY = false;
       // move on x
       
       if(positionPart.getX() < 0){
           collisoinX = isCellBlocked(positionPart.getX(),positionPart.getY()+entity.getHeight());
           
           if(!collisoinX){
           collisoinX = isCellBlocked(positionPart.getX(), positionPart.getY() + entity.getHeight()/2);
           }
           if(!collisoinX){
           collisoinX = isCellBlocked(positionPart.getX(), positionPart.getY());
           }
       }else if(positionPart.getX() > 0){
         collisoinX = isCellBlocked(positionPart.getX()+entity.getWidth(), positionPart.getY()+entity.getHeight());
       
         if(!collisoinX){
             collisoinX = isCellBlocked(positionPart.getX()+ entity.getWidth(),positionPart.getY()+ entity.getHeight()/2);
         }
         if(!collisoinX){
            collisoinX = isCellBlocked(positionPart.getX() + entity.getWidth(), positionPart.getY());
         }
       }
       
       if(collisoinX){
          // positionPart.setX(positionPart.getX());
//           setX(oldX);
//           velocity.x = 0;
        System.out.println("det virker");
       }
       
    //   setY(getY()+ velocity.y * delta);
       if(positionPart.getY() < 0){
           
             CollisionY = isCellBlocked(positionPart.getX(), positionPart.getY());
             
            if(!CollisionY){
             CollisionY = isCellBlocked(positionPart.getX() +entity.getWidth()/2, positionPart.getY());
            }
            if(!CollisionY){
            CollisionY = isCellBlocked(positionPart.getX()+ entity.getWidth(),positionPart.getY());
            }
             
       }else if(positionPart.getY() > 0){
                CollisionY = isCellBlocked(positionPart.getX(), positionPart.getY()+entity.getHeight());
                
                if(!CollisionY){
                    CollisionY = isCellBlocked(positionPart.getX()+entity.getWidth()/2, positionPart.getY()+entity.getHeight());
                }
                
                if(!CollisionY){
                 CollisionY = isCellBlocked(positionPart.getX()+entity.getWidth(), positionPart.getY()+entity.getHeight());

                }

            
       }
       if(CollisionY){
           System.out.println("pls virk");
          // positionPart.setY(positionPart.getY());
//                setY(oldY);
//                velocity.y = 0;
            }
       }
    }
//        public void getLayer(TiledMap map){
//        for (int i = 0; i < 18; i++) {
//            this.mapList.add((TiledMapTileLayer) map.getLayers().get(i));
//        }
//    }
}
