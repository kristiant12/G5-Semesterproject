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
