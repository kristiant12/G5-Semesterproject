/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.data.entityparts;

/**
 *
 * @author tfvg-pc11
 */
public class Vector2i {

    private int x, y;

    public Vector2i() {
        set(0, 0);
    }

    public Vector2i(Vector2i vector) {
        set(vector.x, vector.y);
    }
    public Vector2i(int x,int y){
        this.x = x;
        this.y = y;
    }

    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Vector2i add(Vector2i vector){
        this.x += vector.x;
        this.y += vector.y;
        return this;
    }    
    public Vector2i subtract(Vector2i vector){
        this.x -=vector.x;
        this.y -=vector.y;
        return this;
    }


    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Vector2i)){
            return false;
        }
        Vector2i vec = (Vector2i) obj;
        if(vec.getX() == this.getX() && vec.getY() == this.getY()){
            return true;
        }
        else{
            return false;
        }
    }
 
    @Override
    public String toString(){
        return "x: "+x+" y: "+y;  
    }
}
