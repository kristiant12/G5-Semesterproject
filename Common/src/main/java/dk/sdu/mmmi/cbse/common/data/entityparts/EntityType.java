/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.data.entityparts;

/**
 *
 * @author nitra
 */
public enum EntityType {
    PLAYER ("PLAYER"),
    ENEMY ("ENEMY"),
    BULLET ("BULLET");
    
    private String type;
    
    private EntityType(String type){
        this.type = type;
    }
    
    public String getType(){
        return type;
    }
    
    
}
