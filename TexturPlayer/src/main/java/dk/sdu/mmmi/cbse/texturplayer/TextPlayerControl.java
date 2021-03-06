package dk.sdu.mmmi.cbse.texturplayer;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.ShootingPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

/**
 *
 * @author tfvg-pc11
 */
@ServiceProviders(value = {
    @ServiceProvider(service = IEntityProcessingService.class)
    ,
    @ServiceProvider(service = IGamePluginService.class)})

public class TextPlayerControl implements IEntityProcessingService, IGamePluginService {

    private Entity player;

    public TextPlayerControl() {
    }

    @Override
    public void process(GameData gameData, World world) {

        for (Entity player : world.getEntities(TexturPlayer.class)) {
            PositionPart positionPart = player.getPart(PositionPart.class);
            MovingPart movingPart = player.getPart(MovingPart.class);
            ShootingPart shootingPart = player.getPart(ShootingPart.class);

            movingPart.setLeft(gameData.getKeys().isDown(GameKeys.A));
            movingPart.setRight(gameData.getKeys().isDown(GameKeys.D));
            movingPart.setUp(gameData.getKeys().isDown(GameKeys.W));
            movingPart.setDown(gameData.getKeys().isDown(GameKeys.S));
            shootingPart.setShoot(gameData.isMouseClicked());

            movingPart.process(gameData, player);
            positionPart.process(gameData, player);
        }

    }

    @Override
    public void start(GameData gameData, World world, AssetManager manager) {

        player = createPlayerShip(gameData, manager);
        world.addEntity(player);

    }

    private Entity createPlayerShip(GameData gameData, AssetManager manager) {
        float deacceleration = 10;
        float acceleration = 200;
        float maxSpeed = 200;
        float rotationSpeed = 5;
        float x = 1615;
        float y = 1875;
        float radians = 3.1415f / 2;
        int life = 100;

        Entity player = new TexturPlayer();
        player.setImage(manager.get("assets/images/player5.png", Texture.class));
        player.setWidth(player.getImage().getWidth());
        player.setHeight(player.getImage().getHeight());
        player.setType(5);
        player.setDamage(0);
        player.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
        player.add(new PositionPart(x, y, radians));
        player.add(new LifePart(life));
        player.add(new ShootingPart());

        return player;
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(player);
    }

}
