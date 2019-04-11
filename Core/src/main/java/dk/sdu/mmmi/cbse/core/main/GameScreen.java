package dk.sdu.mmmi.cbse.core.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
/**
 * @author Monica
 */
public class GameScreen implements Screen {

    private OrthographicCamera camera;
    private GameEngine game;
    private SpriteBatch ab;

    public GameScreen(GameEngine game){
        this.game=game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false,800,480);
    }

    @Override
    public void render(float deltaTime){
        Gdx.gl.glClearColor(0,0,255,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        

        camera.update();

        ab.setProjectionMatrix(camera.combined);

    }

    @Override
    public void resize(int width, int height){

    }

    @Override
    public void show(){

    }

    @Override
    public void hide(){

    }

    @Override
    public void pause(){

    }

    @Override
    public void resume(){

    }

    @Override
    public void dispose(){

    }
}