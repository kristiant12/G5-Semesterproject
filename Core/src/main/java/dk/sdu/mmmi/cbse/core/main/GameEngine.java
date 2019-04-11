package dk.sdu.mmmi.cbse.core.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
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
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEnemy;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IMap;
import dk.sdu.mmmi.cbse.common.services.IPlayer;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.core.managers.Assets;
import dk.sdu.mmmi.cbse.core.managers.GameInputProcessor;
import dk.sdu.mmmi.cbse.map.Map;
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
    // private TiledMap tileMap;
    private TiledMap tileMapNew;
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
    private Texture Fatties;
    private Texture Boss;
    // prøver Map collision
    private ArrayList<TiledMapTileLayer> mapList;
    private String blockedKey = "blocked";
    private Vector2 velocity;

    @Override
    public void create() {
        // tileMap = new TmxMapLoader().load("assets\\images\\NewMap.tmx");
        Map map = new Map();
        tileMapNew = map.getMap();
        tmr = new OrthogonalTiledMapRenderer(tileMapNew);
        gameData.setDisplayWidth(Gdx.graphics.getWidth());
        gameData.setDisplayHeight(Gdx.graphics.getHeight());
        cam.translate(gameData.getDisplayWidth() / 2, gameData.getDisplayHeight() / 2);
        cam.update();
        this.mapList = new ArrayList<>();
        getLayer();
        sr = new ShapeRenderer();
        ab = new SpriteBatch();

        System.out.println(Assets.getInstance().getManger().getAssetNames());

//        Testplayer = (Assets.getInstance().getManger().get("assets/images/player5.png", Texture.class));
//        Enemy = (Assets.getInstance().getManger().get("assets/images/Enemies.png", Texture.class));
//        Runner = (Assets.getInstance().getManger().get("assets/images/Runner.png", Texture.class));
//        Fatties = (Assets.getInstance().getManger().get("assets/images/Fatties.png", Texture.class));
//        Boss = (Assets.getInstance().getManger().get("assets/images/Boss.png", Texture.class));
        Gdx.input.setInputProcessor(new GameInputProcessor(gameData));

        result = lookup.lookupResult(IGamePluginService.class);
        result.addLookupListener(lookupListener);
        result.allItems();
        //   getLayer();
        for (IGamePluginService plugin : result.allInstances()) {
            plugin.start(gameData, world, Assets.getInstance().getManger());
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
        gameData.setDelta(Gdx.graphics.getDeltaTime());

        gameData.getKeys().update();
        //renderer.setView(cam);
        update();
        draw();
        drawTextur();
        //    mapCollision(world);

        //tmr.setView(cam);
        gameData.setMouseX(Gdx.input.getX() - Gdx.graphics.getWidth() / 2);
        gameData.setMouseY(Gdx.graphics.getHeight() / 2 - Gdx.input.getY());
        gameData.setMouseClicked(Gdx.input.isTouched());

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

        for (IMap mapCollision : getMapCollisonServices()) {
            mapCollision.process(gameData, world, mapList);
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
                ab.draw(entity.getImage(), positionPart.getX() - entity.getWidth() / 2, positionPart.getY() - entity.getHeight() / 2, entity.getWidth() / 2, entity.getHeight() / 2, entity.getWidth(), entity.getHeight(), 1, 1, (float) Math.toDegrees(positionPart.getRadians()), 0, 0, (int) entity.getWidth(), (int) entity.getHeight(), false, false);
                LifePart life = entity.getPart(LifePart.class);
                cam.position.x = positionPart.getX();
                cam.position.y = positionPart.getY();
                sr.setProjectionMatrix(cam.combined);
                //  ab.draw(new Texture("assets\\images\\player1.png"),positionPart.getX() , positionPart.getY());
                ab.end();
                sr.begin(ShapeType.Filled);
                sr.setColor(Color.GREEN);
                sr.rect(positionPart.getX() - (Gdx.graphics.getWidth() / 2), positionPart.getY() + (Gdx.graphics.getHeight() / 2) - 25, life.getLife() * 2, 25);
                sr.end();
                cam.update();
            } else if (entity instanceof IEnemy) {
                if (entity.getType() == 1) {
                    ab.begin();
                    PositionPart positionPart = entity.getPart(PositionPart.class);
                    LifePart life = entity.getPart(LifePart.class);
                    ab.draw(entity.getImage(), positionPart.getX() - entity.getWidth() / 2, positionPart.getY() - entity.getHeight() / 2, entity.getWidth() / 2, entity.getHeight() / 2, entity.getWidth(), entity.getHeight(), 1, 1, (float) Math.toDegrees(positionPart.getRadians()), 0, 0, (int) entity.getWidth(), (int) entity.getHeight(), false, false);

                    ab.end();
                    float x = 17;
                    float y = 60;
                    float width = 8;
                //    drawLife(x, y, width, positionPart, life);
                } else if (entity.getType() == 2) {
                    ab.begin();
                    PositionPart positionPart = entity.getPart(PositionPart.class);
                    LifePart life = entity.getPart(LifePart.class);
                    float x = 12.5f;
                    float y = 40;
                    float width = 4;
                    ab.draw(entity.getImage(), positionPart.getX() - entity.getWidth() / 2, positionPart.getY() - entity.getHeight() / 2, entity.getWidth() / 2, entity.getHeight() / 2, entity.getWidth(), entity.getHeight(), 1, 1, (float) Math.toDegrees(positionPart.getRadians()), 0, 0, (int) entity.getWidth(), (int) entity.getHeight(), false, false);
                    ab.end();
                  //  drawLife(x, y, width, positionPart, life);
                } else if (entity.getType() == 3) {
                    ab.begin();
                    PositionPart positionPart = entity.getPart(PositionPart.class);
                    LifePart life = entity.getPart(LifePart.class);
                    float x = 10;
                    float y = 75;
                    float width = 8;
                    ab.draw(entity.getImage(), positionPart.getX() - entity.getWidth() / 2, positionPart.getY() - entity.getHeight() / 2, entity.getWidth() / 2, entity.getHeight() / 2, entity.getWidth(), entity.getHeight(), 1, 1, (float) Math.toDegrees(positionPart.getRadians()), 0, 0, (int) entity.getWidth(), (int) entity.getHeight(), false, false);

                    ab.end();
                  //  drawLife(x, y, width, positionPart, life);
                } else if (entity.getType() == 4) {
                    ab.begin();
                    PositionPart positionPart = entity.getPart(PositionPart.class);
                    LifePart life = entity.getPart(LifePart.class);
                    float x = 70;
                    float y = 230;
                    float width = 8;
                    ab.draw(entity.getImage(), positionPart.getX() - entity.getWidth() / 2, positionPart.getY() - entity.getHeight() / 2, entity.getWidth() / 2, entity.getHeight() / 2, entity.getWidth(), entity.getHeight(), 1, 1, (float) Math.toDegrees(positionPart.getRadians()), 0, 0, (int) entity.getWidth(), (int) entity.getHeight(), false, false);
                    ab.end();
                //    drawLife(x, y, width, positionPart, life);
                }
            }

        }

    }

    private void drawLife(float xPosition, float yPosition, float width, PositionPart positionPart, LifePart life) {
        sr.begin(ShapeType.Filled);
        sr.setColor(Color.GREEN);
        sr.rect(positionPart.getX() + xPosition, positionPart.getY() + yPosition, life.getLife(), width);
        sr.end();
    }

    public void getLayer() {
        for (int i = 0; i < 5; i++) {
            this.mapList.add((TiledMapTileLayer) tileMapNew.getLayers().get(i));
        }
    }

    @Override
    public void resize(int width, int height) {
        cam.viewportWidth = width;
        cam.viewportHeight = height;
        cam.update();
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

    private Collection<? extends IMap> getMapCollisonServices() {
        return lookup.lookupAll(IMap.class);
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
                    us.start(gameData, world, Assets.getInstance().getManger());
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
