package dk.sdu.mmmi.cbse.common.data;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import dk.sdu.mmmi.cbse.common.data.entityparts.EntityPart;
import java.io.Serializable;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Entity extends Sprite implements Serializable {

    private final UUID ID = UUID.randomUUID();

    private float[] shapeX = new float[4];
    private float[] shapeY = new float[4];
    private float radius;
    private float height, width;
    private int type;
    private Texture image;
    private int damage;
    private Map<Class, EntityPart> parts;

    public Entity() {
        parts = new ConcurrentHashMap<>();
    }

    public Entity(float height, float width) {
        this.width = width;
        this.height = height;
        parts = new ConcurrentHashMap<>();
    }

    public void add(EntityPart part) {
        parts.put(part.getClass(), part);
    }

    public void remove(Class partClass) {
        parts.remove(partClass);
    }

    public <E extends EntityPart> E getPart(Class partClass) {
        return (E) parts.get(partClass);
    }

    public void setRadius(float r) {
        this.radius = r;
    }

    public float getRadius() {
        return radius;
    }

    public String getID() {
        return ID.toString();
    }

    public float[] getShapeX() {
        return shapeX;
    }

    public void setShapeX(float[] shapeX) {
        this.shapeX = shapeX;
    }

    public float[] getShapeY() {
        return shapeY;
    }

    public void setShapeY(float[] shapeY) {
        this.shapeY = shapeY;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    @Override
    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    @Override
    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public Texture getImage() {
        return image;
    }

    public void setImage(Texture image) {
        this.image = image;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
    
}
