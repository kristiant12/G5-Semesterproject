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
 * @author patri
 */
public enum EntityTypePart implements EntityPart {
    PLAYER("PLAYER"),
    BOSS("BOSS"),
    NORMAL("NORMAL"),
    RUNNERS("RUNNERS"),
    FATTIES("FATTIES"),;
    
    private String type;
    
    private EntityTypePart(String type){
        this.type = type;
    }
    
    public String getType(){
        return type;
    }
 

    @Override
    public void process(GameData gameData, Entity entity) {
         
    }

   
    
}
