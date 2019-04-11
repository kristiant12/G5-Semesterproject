package dk.sdu.mmmi.cbse.core.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
/**
 * @author Monica
 */
public class GameScreen implements Screen {
    //Creates a reference to hold our "passed" game. Change "AdlermacGame" to you game class name.
    private AdlermacGame game;

    //Defining the camera to be used
    private OrthographicCamera camera;

    public GameScreen(AdlermacGame game){
        //assigns the game we passed to the placeholder
        this.game=game;
        //creates the camera
        camera = new OrthographicCamera();
        //Sets the screen to be 800 width by 480 height
        camera.setToOrtho(false,800,480);
    }

    //this is called every time the game is rendered
    @Override
    public void render(float deltaTime){
        //Sets the color to be applied after clearing the screen (R,G,B,A)
        Gdx.gl.glClearColor(0,0,255,1);
        //Clears the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //updates camera view
        camera.update();

        //makes the camera fit onto the available screen
        game.gameBatch.setProjectionMatrix(camera.combined);

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