package dk.sdu.mmmi.cbse.core.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEnemy;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IPlayer;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.core.managers.Assets;
import dk.sdu.mmmi.cbse.core.managers.GameInputProcessor;
import java.util.ArrayList;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

public class GameEngine implements ApplicationListener {
    private int tingRamtPåX = 0;
    private int tingRampPåY = 0;
    private TiledMap tileMap;
    private OrthogonalTiledMapRenderer tmr;
    private static OrthographicCamera cam = new OrthographicCamera();
    private ShapeRenderer sr;
    private final Lookup lookup = Lookup.getDefault();
    private final GameData gameData = new GameData();
    private World world = new World();
    private List<IGamePluginService> gamePlugins = new CopyOnWriteArrayList<>();
    private Lookup.Result<IGamePluginService> result;
    private SpriteBatch ab;
    private Texture Testplayer;
    private Texture Enemy;
    private Texture Runner;
    // prøver Map collision
    private ArrayList<TiledMapTileLayer> mapList;
    private String blockedKey = "blocked";
    private Vector2 velocity;

    @Override
    public void create() {
        tileMap = new TmxMapLoader().load("assets\\images\\Map.tmx");
        tmr = new OrthogonalTiledMapRenderer(tileMap);

        gameData.setDisplayWidth(Gdx.graphics.getWidth());
        gameData.setDisplayHeight(Gdx.graphics.getHeight());
        cam.translate(gameData.getDisplayWidth() / 2, gameData.getDisplayHeight() / 2);
        cam.update();

        sr = new ShapeRenderer();
        ab = new SpriteBatch();
        this.mapList = new ArrayList<>();
        System.out.println(Assets.getInstance().getManger().getAssetNames());
        Testplayer = (Assets.getInstance().getManger().get("assets/images/player5.png", Texture.class));
        Enemy = (Assets.getInstance().getManger().get("assets/images/Enemies.png", Texture.class));
        Runner = (Assets.getInstance().getManger().get("assets/images/Enemies.png", Texture.class));
        Gdx.input.setInputProcessor(new GameInputProcessor(gameData));

        result = lookup.lookupResult(IGamePluginService.class);
        result.addLookupListener(lookupListener);
        result.allItems();
        getLayer();
        for (IGamePluginService plugin : result.allInstances()) {
            plugin.start(gameData, world);
            gamePlugins.add(plugin);
        }
    }

    @Override
    public void render() {
        // clear screen to black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        tmr.setView(cam);
        tmr.render();
        update();
        //  draw();
        drawTextur();
        mapCollision(world);


        //tmr.setView(cam);
        gameData.setDelta(Gdx.graphics.getDeltaTime());

        gameData.getKeys().update();
        //renderer.setView(cam);

    }

    private void update() {
        // Update
        for (IEntityProcessingService entityProcessorService : getEntityProcessingServices()) {
            entityProcessorService.process(gameData, world);
        }

        // Post Update
        for (IPostEntityProcessingService postEntityProcessorService : getPostEntityProcessingServices()) {
            postEntityProcessorService.process(gameData, world);
        }
    }

    private void draw() {
        for (Entity entity : world.getEntities()) {
            sr.setColor(1, 1, 1, 1);

            sr.begin(ShapeRenderer.ShapeType.Line);

            float[] shapex = entity.getShapeX();
            float[] shapey = entity.getShapeY();

            for (int i = 0, j = shapex.length - 1;
                    i < shapex.length;
                    j = i++) {

                sr.line(shapex[i], shapey[i], shapex[j], shapey[j]);
            }

            sr.end();
        }
    }

    private void drawTextur() {
        for (Entity entity : world.getEntities()) {
            if (entity instanceof IPlayer) {
                ab.setProjectionMatrix(cam.combined);
                ab.begin();
                PositionPart positionPart = entity.getPart(PositionPart.class);
                ab.draw(Testplayer, positionPart.getX(), positionPart.getY(), 32.5f, 27.5f, 65, 55, 1, 1, (float) Math.toDegrees(positionPart.getRadians()), 0, 0, 65, 55, false, false);
                cam.position.x = positionPart.getX();
                cam.position.y = positionPart.getY();
                sr.setProjectionMatrix(cam.combined);
                //  ab.draw(new Texture("assets\\images\\player1.png"),positionPart.getX() , positionPart.getY());
                ab.end();
                cam.update();
            } else if (entity instanceof IEnemy) {
                ab.begin();
                PositionPart positionPart = entity.getPart(PositionPart.class);
                ab.draw(Enemy, positionPart.getX(), positionPart.getY(), 40f, 37f, 80, 74, 1, 1, (float) Math.toDegrees(positionPart.getRadians()), 0, 0, 80, 74, false, false);
                ab.draw(Runner, positionPart.getX(), positionPart.getY(), 40f, 37f, 80, 74, 1, 1, (float) Math.toDegrees(positionPart.getRadians()), 0, 0, 80, 74, false, false);
                ab.end();
            }
            // SpriteBatch ab = new SpriteBatch();

        }

    }
    public void getLayer(){
        for (int i = 0; i < 18; i++) {
            this.mapList.add((TiledMapTileLayer) tileMap.getLayers().get(i));
        }
    }

    @Override
    public void resize(int width, int height) {
        cam.viewportWidth = width;
        cam.viewportHeight = height;
        cam.update();
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
        System.out.println("det virker"+ tingRamtPåX++);
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
           System.out.println("pls virk"+ tingRampPåY++);
          // positionPart.setY(positionPart.getY());
//                setY(oldY);
//                velocity.y = 0;
            }
       }
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

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }

    private Collection<? extends IEntityProcessingService> getEntityProcessingServices() {
        return lookup.lookupAll(IEntityProcessingService.class);
    }

    private Collection<? extends IPostEntityProcessingService> getPostEntityProcessingServices() {
        return lookup.lookupAll(IPostEntityProcessingService.class);
    }

    private final LookupListener lookupListener = new LookupListener() {
        @Override
        public void resultChanged(LookupEvent le) {

            Collection<? extends IGamePluginService> updated = result.allInstances();

            for (IGamePluginService us : updated) {
                // Newly installed modules
                if (!gamePlugins.contains(us)) {
                    us.start(gameData, world);
                    gamePlugins.add(us);
                }
            }

            // Stop and remove module
            for (IGamePluginService gs : gamePlugins) {
                if (!updated.contains(gs)) {
                    gs.stop(gameData, world);
                    gamePlugins.remove(gs);
                }
            }
        }

    };
}
