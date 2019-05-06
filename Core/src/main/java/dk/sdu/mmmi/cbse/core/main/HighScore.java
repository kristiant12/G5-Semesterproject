package dk.sdu.mmmi.cbse.core.main;

import dk.sdu.mmmi.cbse.common.services.IHighScore;


/**
 *
 * @author tfvg-pc11
 */
public class HighScore implements IHighScore{
    
    private int wave;
    private String name;
    
    public HighScore(int wave, String name){
        this.name = name;
        this.wave = wave;
    }

    @Override
    public int getWave() {
        return wave;
    }

    @Override
    public void setWave(int wave) {
        this.wave = wave;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
    
    
    
    @Override
    public String toString(){
        return getName() + " wave: "+getWave();
    }
    
    
}
