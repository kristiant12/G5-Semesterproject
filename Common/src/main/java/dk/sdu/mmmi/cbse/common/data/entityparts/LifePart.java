package dk.sdu.mmmi.cbse.common.data.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

public class LifePart implements EntityPart {
    private boolean dead = false;
    private int life;
    private boolean isHit = false;
    private float expiration;

    

    public LifePart(int life,float expiration) {
        this.life = life;
        this.expiration = expiration;
    }
    
    public LifePart(int life){
        this.life = life;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public boolean isHit() {
        return isHit;
    }

    public void setIsHit(boolean isHit) {
        this.isHit = isHit;
    }
    
    public boolean isDead() {
        return dead;
    }

    public float getExpiration() {
        return expiration;
    }

    public void setExpiration(float expiration) {
         this.expiration = expiration;
    }
    
    public void reduceExpiration(float delta){
        this.expiration -= delta;
    }
   

    
    
    @Override
    public void process(GameData gameData, Entity entity) {
    }
}
