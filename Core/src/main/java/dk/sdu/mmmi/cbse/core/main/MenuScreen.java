/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.core.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 *
 * @author tfvg-pc11
 */
public class MenuScreen implements Screen {

    private Main main;
    private Stage stage;
    
    
    public MenuScreen(Main main) {
        this.main = main;
        
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

//        Skin skin = new Skin(Gdx.files.internal("assets\\images\\buttonSkin.json"));
//
//        TextButton startButton = new TextButton("Start", skin);
//        startButton.setPosition(300, 300);
//        startButton.setSize(300, 60);

     //   stage.addActor(startButton);

    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }

}
