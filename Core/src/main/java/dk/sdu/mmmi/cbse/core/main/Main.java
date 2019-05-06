package dk.sdu.mmmi.cbse.core.main;

import com.badlogic.gdx.Game;


/**
 *
 * @author tfvg-pc11
 */
public class Main extends Game{

    
    @Override
    public void create() {
        this.setScreen(new MenuScreen(this));
 
    }
    @Override
    public void render(){
        super.render();
    }
}
