package model.entity;

import model.GameConfig;

import java.awt.Rectangle;

public abstract class Entity {
    protected int x, y;
    protected int width, height;
    protected int velocityX, velocityY;
    protected String spritePath;
    protected int action;

    protected int animationTick = 0;
    protected int animationIndex = 0;

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

    protected abstract void updateAction();

    protected abstract int getActionFrames(int action);

    protected void updateAnimationTick() {
        animationTick++;

        if (animationTick >= GameConfig.ANIMATION_SPEED) {
            animationTick = 0;
            animationIndex++;

            if (animationIndex >= getActionFrames(action)) {
                animationIndex = 0;
            }
        }
    }

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

    public int getAction() {
        return action;
    }

    public int getAnimationIndex() {
        return animationIndex;
    }

    public String getSpritePath() {
        return spritePath;
    }

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
