package model;

public class YogiBear {
    private int x,y;
    private int width, height;
    private int velocityX;

    public YogiBear(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = GameConfig.TILE_SIZE;
        this.height = GameConfig.TILE_SIZE * 2;
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

    public void update() {
        x += velocityX;

        if (x < 0) x = 0;
        if (x > GameConfig.WINDOW_WIDTH - width) x = GameConfig.WINDOW_WIDTH - width;
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
