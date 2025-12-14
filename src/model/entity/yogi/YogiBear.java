package model.entity.yogi;

import model.GameConfig;
import model.entity.Entity;

public class YogiBear extends Entity {
    private boolean onGround;
    private boolean crouching;
    private boolean dropThroughPlatform;

    public YogiBear(int x, int y) {
        super(x, y, GameConfig.TILE_SIZE, GameConfig.TILE_SIZE * 2);
        this.onGround = false;
        this.crouching = false;
    }

    public void crouch() {
        if (onGround && !crouching) {
            crouching = true;
            int prevHeight = height;
            height /= 2;
            y += (prevHeight - height);
        }
    }

    public void standUp(boolean canStand) {
        if (crouching && canStand) {
            crouching = false;
            int prevHeight = height;
            height *= 2;
            y -= (height - prevHeight);
        }
    }

    public void moveLeft() {
        if (crouching) {
            velocityX = -GameConfig.MOVE_SPEED / 2;
        } else {
            velocityX = -GameConfig.MOVE_SPEED;
        }
    }

    public void moveRight() {
        if (crouching) {
            velocityX = GameConfig.MOVE_SPEED / 2;
        } else {
            velocityX = GameConfig.MOVE_SPEED;
        }
    }

    public void stopMoving() {
        velocityX = 0;
    }

    public void jump() {
        if (onGround) {
            if (crouching) {
                velocityY = (int) (GameConfig.JUMP_STRENGTH * 0.6);
            } else {
                velocityY = GameConfig.JUMP_STRENGTH;
            }
            onGround = false;
        }
    }

    @Override
    public void update() {
        x += velocityX;

        // keep yogi within level bounds
        if (x < 0)
            x = 0;
        if (x > GameConfig.LEVEL_WIDTH - width)
            x = GameConfig.LEVEL_WIDTH - width;

        velocityY += GameConfig.GRAVITY;
        y += velocityY;
    }

    public boolean isCrouching() {
        return crouching;
    }

    public void requestDropThrough() {
        dropThroughPlatform = true;
    }

    public boolean isDropThroughRequested() {
        return dropThroughPlatform;
    }

    public void clearDropThrough() {
        dropThroughPlatform = false;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public boolean isJumping() {
        return (this.velocityY < 0);
    }

    public boolean isFalling() {
        return (this.velocityY > 0);
    }
}
