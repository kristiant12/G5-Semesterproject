package dk.sdu.mmmi.cbse.core.managers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;

/**
 *
 * @author tfvg-pc11
 */
public class Assets {

    private static Assets instance = null;
    private AssetManager assetManager;

    private Assets() {
        this.assetManager = new AssetManager();
        load();
    }

    public static Assets getInstance() {
        if (instance == null) {
            instance = new Assets();
        }
        return instance;
    }

    public void load() {

        assetManager.load("assets\\images\\player.png", Texture.class);
        assetManager.load("assets\\images\\player5.png", Texture.class);
        assetManager.load("assets\\images\\Boss.png", Texture.class);
        assetManager.load("assets\\images\\Enemies.png", Texture.class);
        assetManager.load("assets\\images\\Runner.png", Texture.class);
        assetManager.load("assets\\images\\Fatties.png", Texture.class);
        assetManager.load("assets\\images\\Oof.wav", Music.class);
        assetManager.load("assets\\images\\Soundtrack.mp3", Music.class);
        
        assetManager.finishLoading();
    }

    public AssetManager getManger() {
        return this.assetManager;
    }
}
