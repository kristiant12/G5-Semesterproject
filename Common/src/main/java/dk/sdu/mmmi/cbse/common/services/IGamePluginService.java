package dk.sdu.mmmi.cbse.common.services;

import com.badlogic.gdx.assets.AssetManager;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

public interface IGamePluginService {
    void start(GameData gameData, World world,AssetManager manger);

    void stop(GameData gameData, World world);
}
