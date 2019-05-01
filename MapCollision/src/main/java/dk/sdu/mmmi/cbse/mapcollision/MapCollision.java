package dk.sdu.mmmi.cbse.mapcollision;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;

import dk.sdu.mmmi.cbse.common.services.IMap;
import dk.sdu.mmmi.cbse.common.services.IPlayer;
import java.util.ArrayList;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

@ServiceProviders(value = {
    @ServiceProvider(service = IMap.class)})

public class MapCollision implements IMap {

    private ArrayList<TiledMapTileLayer> mapList;
    private String blockedKey = "blocked";

    @Override
    public void process(GameData gameData, World world, ArrayList<TiledMapTileLayer> map) {
        mapList = map;
        //       System.out.println("test");
        mapCollision(world, gameData);

    }

    private boolean isCellBlocked(float x, float y) {
        //long preTime = System.currentTimeMillis();
        for (int i = 0; i < mapList.size(); i++) {
            TiledMapTileLayer.Cell cell = mapList.get(i).getCell((int) (x / mapList.get(i).getTileWidth()), (int) (y / mapList.get(i).getTileHeight()));
            if (cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey(blockedKey)) {
                return true;
            }

        }
        //System.out.println("Time to run isCellBlocked" + (System.currentTimeMillis() - preTime));

        return false;
    }

    public void mapCollision(World world, GameData gameData) {
        //long preTime = System.currentTimeMillis();
        for (Entity entity : world.getEntities()) {

            if (entity instanceof IPlayer) {

                PositionPart positionPart = entity.getPart(PositionPart.class);
                MovingPart mp = entity.getPart(MovingPart.class);
                mp.setCollisionX(false);
                mp.setCollisionY(false);

                float oldX = positionPart.getX(), oldY = positionPart.getY(), tiledWith = mapList.get(0).getTileWidth(), tiledHeight = mapList.get(0).getTileHeight();
                boolean collisionX = false;
                boolean collisionY = false;
                boolean left = false;
                boolean right = false;
                boolean up = false;
                boolean down = false;
            // move on x

                if (isCellBlocked(positionPart.getX() - entity.getWidth() / 2, positionPart.getY())) {
                    left = true;
                    mp.setCollisionX(left);
                } else if (isCellBlocked(positionPart.getX() + entity.getWidth() / 2, positionPart.getY())) {
                    right = true;
                    mp.setCollisionX(right);
                } else if (isCellBlocked(positionPart.getX(), positionPart.getY() - entity.getHeight() / 2)) {
                    down = true;
                    mp.setCollisionY(true);
                } else if (isCellBlocked(positionPart.getX(), positionPart.getY() + entity.getHeight() / 2)) {
                    up = true;
                    mp.setCollisionY(true);
                }

                if (left) {
                    positionPart.setX(positionPart.getX() + 1);
                }
                if (right) {
                    positionPart.setX(positionPart.getX() - 1);
                }
                if (down) {
                    positionPart.setY(positionPart.getY() + 1);
                }
                if (up) {
                    positionPart.setY(positionPart.getY() - 1);
                }

//           if (positionPart.getX() > 0) {
//                collisionX = isCellBlocked(positionPart.getX() + 65, positionPart.getY() + 55);
//                
//                if (!collisionX) {
//                    collisionX = isCellBlocked(positionPart.getX() + 65, positionPart.getY() + 55 / 2);
//                }
//                if (!collisionX) {
//                    collisionX = isCellBlocked(positionPart.getX() + 65, positionPart.getY());
//                }
//            }
//
//            if (collisionX) {
//                System.out.println(positionPart.getX() + " XXX " + oldX);
//                positionPart.setX(oldX+2);
//                if (entity instanceof IPlayer) {
//                    movingPart.setSpeed(0);
//                    movingPart.process(gameData, entity);
//                    movingPart.setSpeed(200);
//                }
//            }
//
//            if (positionPart.getY() > 0) {
//                collisionY = isCellBlocked(positionPart.getX(), positionPart.getY() + 55);
//
//                if (!collisionY) {
//                    collisionY = isCellBlocked(positionPart.getX() + 65 / 2, positionPart.getY() + 55);
//                }
//
//                if (!collisionY) {
//                    collisionY = isCellBlocked(positionPart.getX() + 65, positionPart.getY() + 55);
//
//                }
//
//            }
//            if (collisionY) {
//                System.out.println(positionPart.getY() + " YYY " + oldY);
//                positionPart.setY(oldY);
//                if (entity instanceof IPlayer) {
//                    movingPart.setSpeed(0);
//                    movingPart.process(gameData, entity);
//                    movingPart.setSpeed(200);
//                }
//            }
            }
            //System.out.println("Time to run collision " + (System.currentTimeMillis()-preTime) );
        }
    }
}
