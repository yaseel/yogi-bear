package model.entity;

import model.GameConfig;

import java.awt.Rectangle;

public abstract class Entity {
    protected int x, y;
    protected int width, height;
    protected int velocityX, velocityY;
    protected String spritePath;

    public Entity(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.velocityX = 0;
        this.velocityY = 0;
        this.spritePath = GameConfig.BASE_SPRITE_PATH;
    }

    public abstract void update();

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getVelocityY() {
        return velocityY;
    }

    public String getSpritePath() { return spritePath; }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setVelocityY(int velocityY) {
        this.velocityY = velocityY;
    }
}
