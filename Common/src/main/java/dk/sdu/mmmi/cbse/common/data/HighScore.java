/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.data;

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

    public int getWave() {
        return wave;
    }

    public void setWave(int wave) {
        this.wave = wave;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
    
}
