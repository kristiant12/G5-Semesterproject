/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.enemy;

/**
 *
 * @author nitra
 */
public enum EnemyType {
    BOSS("BOSS"),
    NORMAL("NORMAL"),
    RUNNERS("RUNNERS"),
    FATTIES("FATTIES");
    
    private String type;
    
    private EnemyType(String type){
        this.type = type;
    }
    
    public String getType(){
        return type;
    }

}
