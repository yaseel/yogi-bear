package model;

public class YogiBear {
    private int x,y;
    private int width, height;
    private int velocityX, velocityY;
    private boolean onGround;

    public YogiBear(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = GameConfig.TILE_SIZE;
        this.height = GameConfig.TILE_SIZE * 2;
        this.velocityX = 0;
        this.velocityY = 0;
        this.onGround = false;
    }

    public void moveLeft() {
        velocityX = -GameConfig.MOVE_SPEED;
    }

    public void moveRight() {
        velocityX = GameConfig.MOVE_SPEED;
    }

    public void stopMoving() {
        velocityX = 0;
    }

    public void jump() {
        if (onGround) {
            velocityY = GameConfig.JUMP_STRENGTH;
            onGround = false;
        }
    }

    public void update() {
        x += velocityX;

        if (x < 0) x = 0;
        if (x > GameConfig.WINDOW_WIDTH - width) x = GameConfig.WINDOW_WIDTH - width;

        velocityY += GameConfig.GRAVITY;
        y += velocityY;

        if (y >= GameConfig.GROUND_Y - height) {
            y = GameConfig.GROUND_Y - height;
            velocityY = 0;
            onGround = true;
        }
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
}
