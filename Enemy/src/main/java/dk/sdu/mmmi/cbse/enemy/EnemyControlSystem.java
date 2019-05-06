package dk.sdu.mmmi.cbse.enemy;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;


@ServiceProviders(value = {
    @ServiceProvider(service = IEntityProcessingService.class)
    
    })
public class EnemyControlSystem implements IEntityProcessingService{



    @Override
    public void process(GameData gameData, World world) {
        for (Entity player : world.getEntities(Enemy.class)) {
            PositionPart positionPart = player.getPart(PositionPart.class);
            MovingPart move = player.getPart(MovingPart.class);

            move.process(gameData, player);
            positionPart.process(gameData, player);
        }
    }



}
