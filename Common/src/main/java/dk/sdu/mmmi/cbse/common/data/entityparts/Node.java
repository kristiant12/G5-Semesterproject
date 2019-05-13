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
public class Node {
    public Vector2i tile;
    public Node parent;
    public double fCost,gCost,hCost;
    
    public Node(Vector2i tile,Node parent,double gCost,double hCost){
        this.tile = tile;
        this.gCost = gCost;
        this.hCost = hCost;
        this.fCost = this.gCost+this.hCost;
    }
    
    public String toString(){
        return "Vector: "+tile.getX()+","+tile.getY()+" gCost: "+gCost+" hCost :"+hCost;
    }
    
    
}
