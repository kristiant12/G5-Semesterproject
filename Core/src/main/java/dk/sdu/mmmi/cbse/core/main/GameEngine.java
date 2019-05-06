package dk.sdu.mmmi.cbse.core.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEnemy;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IHighScore;
import dk.sdu.mmmi.cbse.common.services.IMap;
import dk.sdu.mmmi.cbse.common.services.IPlayer;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.core.managers.Assets;
import dk.sdu.mmmi.cbse.core.managers.GameInputProcessor;
import dk.sdu.mmmi.cbse.map.Map;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

public class GameEngine extends JPanel implements ApplicationListener, ActionListener {

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
    private TextButton startButton;
    // prøver Map collision
    private ArrayList<TiledMapTileLayer> mapList;
    private String blockedKey = "blocked";
    private Vector2 velocity;
    private boolean GameScreen = false;
    private JFrame MainMenu;
    private JFrame highScoreScreen;
    private JFrame playScreen;

    private JButton playButton;
    private JButton playScreenButton;
    private JButton highscoreButton;
    private JButton highscoreBackButton;
    private JButton playBackButton;

    private JLabel nameLabel;

    private JTextField nameField;

    private JList<HighScore> scoreList;

    private BitmapFont font;
    private Music sound;
    private Music gameSound;
    
    private SortingAlgorithm sort;
    

    @Override

    public void create() {
        // tileMap = new TmxMapLoader().load("assets\\images\\NewMap.tmx");
        Map map = new Map();
        tileMapNew = map.getMap();
        sound = Gdx.audio.newMusic(Gdx.files.internal("assets\\images\\Soundtrack.mp3"));
        gameSound = Gdx.audio.newMusic(Gdx.files.internal("assets\\images\\Doom.mp3"));

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
        
        sort = new SortingAlgorithm();
        addPersonToHighScore();
        createJPlayScreen();
        createJMainScreen();
        createJHighscoreScreen();
        font = new BitmapFont();

        Gdx.input.setInputProcessor(new GameInputProcessor(gameData));

        result = lookup.lookupResult(IGamePluginService.class);
        result.addLookupListener(lookupListener);
        result.allItems();
        
        createAllIGamePluginService();
    }

    public void createAllIGamePluginService() {
        for (IGamePluginService plugin : result.allInstances()) {
            plugin.start(gameData, world, Assets.getInstance().getManger());
            gamePlugins.add(plugin);
        }
    }

    public void removeAllIGamePluginService() {
        for (IGamePluginService plugin : result.allInstances()) {
            plugin.stop(gameData, world);
            gamePlugins.remove(plugin);
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

        if (GameScreen == true) {
            sound.dispose();
            gameSound.play();
            gameSound.setVolume(0.2f);
            update();
            draw();
            drawTextur();
        } else {
            sound.play();
        }
        boolean checkIfAlive = false;
        for (Entity entity : world.getEntities()) {
            if (entity instanceof IPlayer) {
                checkIfAlive = true;
            }
        }
        if (checkIfAlive == false && MainMenu.isVisible() == false && playScreen.isVisible() == false && highScoreScreen.isVisible() == false) {
            GameScreen = false;
            gameSound.stop();
            removeAllIGamePluginService();
            MainMenu.setVisible(true);
            sort.addPlayerToNewArray(new HighScore(gameData.getWave(), gameData.getPlayerName()));
         //   gameData.addPlayerToNewArray(new HighScore(gameData.getWave(), gameData.getPlayerName()));
            highScoreScreen.remove(scoreList);
            
            scoreList = new JList(sort.getNewHighScoreArray());
            scoreList.setBounds(70, 10, 200, 250);
            highScoreScreen.add(scoreList);
        }

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
            postEntityProcessorService.process(gameData, world, Assets.getInstance().getManger());
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
                ab.end();
                sr.begin(ShapeType.Filled);
                sr.setColor(Color.GREEN);
                sr.rect(positionPart.getX() - (Gdx.graphics.getWidth() / 2), positionPart.getY() + (Gdx.graphics.getHeight() / 2) - 25, life.getLife() * 2, 25);
                sr.end();
                ab.begin();
                font.draw(ab, "Wave: " + gameData.getWave(), positionPart.getX() - (Gdx.graphics.getWidth() / 2), positionPart.getY() + (Gdx.graphics.getHeight() / 2) - 40);
                ab.end();
                cam.update();
            } else if (entity instanceof IEnemy) {
                if (entity.getType() == 1) {
                    ab.begin();
                    PositionPart positionPart = entity.getPart(PositionPart.class);
                    LifePart life = entity.getPart(LifePart.class);
                    ab.draw(entity.getImage(), positionPart.getX() - entity.getWidth() / 2, positionPart.getY() - entity.getHeight() / 2, entity.getWidth() / 2, entity.getHeight() / 2, entity.getWidth(), entity.getHeight(), 1, 1, (float) Math.toDegrees(positionPart.getRadians()), 0, 0, (int) entity.getWidth(), (int) entity.getHeight(), false, false);
                    ab.end();
                    float x = -entity.getWidth() / 2;
                    float y = 25;
                    float width = 8;
                    drawLife(x, y, width, positionPart, life);
                } else if (entity.getType() == 2) {
                    ab.begin();
                    PositionPart positionPart = entity.getPart(PositionPart.class);
                    LifePart life = entity.getPart(LifePart.class);
                    float x = -entity.getWidth() / 2 + 12.5f;
                    float y = 20;
                    float width = 4;
                    ab.draw(entity.getImage(), positionPart.getX() - entity.getWidth() / 2, positionPart.getY() - entity.getHeight() / 2, entity.getWidth() / 2, entity.getHeight() / 2, entity.getWidth(), entity.getHeight(), 1, 1, (float) Math.toDegrees(positionPart.getRadians()), 0, 0, (int) entity.getWidth(), (int) entity.getHeight(), false, false);
                    ab.end();
                    drawLife(x, y, width, positionPart, life);
                } else if (entity.getType() == 3) {
                    ab.begin();
                    PositionPart positionPart = entity.getPart(PositionPart.class);
                    LifePart life = entity.getPart(LifePart.class);
                    float x = -entity.getWidth() / 2;
                    float y = 50;
                    float width = 8;
                    ab.draw(entity.getImage(), positionPart.getX() - entity.getWidth() / 2, positionPart.getY() - entity.getHeight() / 2, entity.getWidth() / 2, entity.getHeight() / 2, entity.getWidth(), entity.getHeight(), 1, 1, (float) Math.toDegrees(positionPart.getRadians()), 0, 0, (int) entity.getWidth(), (int) entity.getHeight(), false, false);
                    ab.end();
                    drawLife(x, y, width, positionPart, life);
                } else if (entity.getType() == 4) {
                    ab.begin();
                    PositionPart positionPart = entity.getPart(PositionPart.class);
                    LifePart life = entity.getPart(LifePart.class);
                    float x = -entity.getWidth() / 2;
                    float y = 100;
                    float width = 8;
                    ab.draw(entity.getImage(), positionPart.getX() - entity.getWidth() / 2, positionPart.getY() - entity.getHeight() / 2, entity.getWidth() / 2, entity.getHeight() / 2, entity.getWidth(), entity.getHeight(), 1, 1, (float) Math.toDegrees(positionPart.getRadians()), 0, 0, (int) entity.getWidth(), (int) entity.getHeight(), false, false);
                    ab.end();
                    drawLife(x, y, width, positionPart, life);
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

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == playScreenButton) {
            // GameScreen = true;
            playScreen.setVisible(true);
            MainMenu.setVisible(false);
        }
        if (ae.getSource() == highscoreButton) {
            MainMenu.setVisible(false);
            highScoreScreen.setVisible(true);
        }
        if (ae.getSource() == highscoreBackButton) {
            MainMenu.setVisible(true);
            highScoreScreen.setVisible(false);
        }
        if (ae.getSource() == playBackButton) {
            playScreen.setVisible(false);
            MainMenu.setVisible(true);
        }
        if (ae.getSource() == playButton) {
            playScreen.setVisible(false);
            gameData.setPlayerName(nameField.getText());
            gameData.newWave();
            if (world.getEntities().isEmpty()) {
                createAllIGamePluginService();
            }
            GameScreen = true;
        }
    }

    public void createJMainScreen() {
        MainMenu = new JFrame();
        playScreenButton = new JButton("Start Game");
        playScreenButton.setBounds(100, 100, 140, 40);
        highscoreButton = new JButton("Highscore");
        highscoreButton.setBounds(100, 200, 140, 40);
        MainMenu.add(playScreenButton);
        MainMenu.add(highscoreButton);
        MainMenu.setSize(350, 400);
        MainMenu.setLayout(null);
        MainMenu.setVisible(true);

        MainMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        playScreenButton.addActionListener(this);
        highscoreButton.addActionListener(this);
    }

    public void createJHighscoreScreen() {
        highScoreScreen = new JFrame();
        highscoreBackButton = new JButton("Back");
       // sort.sort();
        scoreList = new JList(sort.getNewHighScoreArray());

        scoreList.setBounds(70, 10, 200, 250);

        highScoreScreen.setSize(350, 400);
        highScoreScreen.setLayout(null);
        highScoreScreen.setVisible(false);

        highscoreBackButton.setBounds(25, 295, 70, 40);
        highScoreScreen.add(highscoreBackButton);
        highScoreScreen.add(scoreList);
        highScoreScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        highscoreBackButton.addActionListener(this);

    }

    public void createJPlayScreen() {
        playScreen = new JFrame();
        playScreen.setSize(350, 400);
        playScreen.setLayout(null);
        playScreen.setVisible(false);

        playBackButton = new JButton("Back");
        playBackButton.setBounds(25, 295, 70, 40);

        playButton = new JButton("Play");
        playButton.setBounds(100, 200, 140, 40);

        nameLabel = new JLabel("Enter name:");
        nameLabel.setBounds(100, 100, 140, 40);

        nameField = new JTextField();
        nameField.setBounds(100, 150, 140, 40);

        playScreen.add(playBackButton);
        playScreen.add(playButton);
        playScreen.add(nameLabel);
        playScreen.add(nameField);

        playScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        playBackButton.addActionListener(this);
        playButton.addActionListener(this);
    }

    

    public void addPersonToHighScore() {
        sort.addPlayerToNewArray(new HighScore(1, "kristian"));
        sort.addPlayerToNewArray(new HighScore(4, "ahmet"));
        sort.addPlayerToNewArray(new HighScore(4000, "nicolai"));
        
       
       // sotering.sort(gameData.getNewHighScoreArray());
    }
}
