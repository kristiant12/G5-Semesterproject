/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.data.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

/**
 *
 * @author tfvg-pc11
 */
public class ShootingPart implements EntityPart{
    
    private boolean shoot = false;
    
    public boolean getShoot(){
        return shoot;
    }

    public void setShoot(boolean shoot) {
        this.shoot = shoot;
    }
    

    @Override
    public void process(GameData gameData, Entity entity) {
    }
    
}
