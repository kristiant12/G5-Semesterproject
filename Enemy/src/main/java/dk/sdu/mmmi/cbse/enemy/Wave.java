package dk.sdu.mmmi.cbse.enemy;

import com.badlogic.gdx.assets.AssetManager;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import java.util.Random;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

/**
 *
 * @author tfvg-pc11
 */
@ServiceProviders(value = {
    @ServiceProvider(service = IPostEntityProcessingService.class)
    ,
    @ServiceProvider(service = IGamePluginService.class)
})
public class Wave implements IPostEntityProcessingService, IGamePluginService {

    private int points = 0;
    private Random rand = new Random();
    private EnemyPlugin a = new EnemyPlugin();

    @Override
    public void process(GameData gameData, World world, AssetManager manager) {
        if (world.getEntities(Enemy.class).isEmpty()) {
            createWave(gameData, world, manager);
        }

    }

    private void createWave(GameData gameData, World world, AssetManager manager) {

        gameData.increaseWave();
        points = 3 + gameData.getWave() * 5;
        
        while (points > 0) {
            int limitter = 1;

            if (points > 9) {
                limitter = 4;
            } else if (points > 4) {
                limitter = 3;
            } else if (points > 2) {
                limitter = 2;
            }
            
            int s = rand.nextInt(limitter);
            switch (s) {
                case 0:
                    world.addEntity(a.createNormalEnemy(gameData, world, manager));
                    points -= 1;
                    break;
                case 1:
                    world.addEntity(a.createRunnerEnemy(gameData, world, manager));
                    points -= 3;
                    break;
                case 2:
                    world.addEntity(a.createFattyEnemy(gameData, world, manager));
                    points -= 5;
                    break;
                case 3:
                    world.addEntity(a.createBossEnemy(gameData, world, manager));
                    points -= 10;
                    break;
            }
        }
    }

    @Override
    public void start(GameData gameData, World world, AssetManager manger) {

    }

    @Override
    public void stop(GameData gameData, World world) {
        a.stop(gameData, world);
    }
}
