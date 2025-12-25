package model.entity.agent;

import model.GameConfig;
import model.entity.Entity;
import model.entity.yogi.YogiBear;

public class Agent extends Entity {
    public enum Direction {
        LEFT, RIGHT
    }

    private int startX, startY;
    private Direction direction;

    private int patrolStartCol;
    private int patrolEndCol;

    private int targetX;
    private boolean movingRight;
    private int pauseTimer;

    public Agent(int x, int y, int patrolStartCol, int patrolEndCol) {
        super(x, y, GameConfig.TILE_SIZE, GameConfig.TILE_SIZE * 2);
        this.startX = x;
        this.startY = y;

        this.patrolStartCol = patrolStartCol;
        this.patrolEndCol = patrolEndCol;

        this.movingRight = true;
        this.targetX = patrolEndCol * GameConfig.TILE_SIZE;
        this.direction = Direction.RIGHT;
        this.pauseTimer = 0;
    }

    @Override
    public void update() {
        if (pauseTimer > 0) {
            pauseTimer--;
            return;
        }

        if (movingRight) {
            x += GameConfig.AGENT_SPEED;
            direction = Direction.RIGHT;
            if (x >= targetX) {
                x = targetX;
                movingRight = false;
                targetX = patrolStartCol * GameConfig.TILE_SIZE;
                pauseTimer = GameConfig.AGENT_PAUSE_FRAMES;
            }
        } else {
            x -= GameConfig.AGENT_SPEED;
            direction = Direction.LEFT;

            if (x <= targetX) {
                x = targetX;
                movingRight = true;
                targetX = patrolEndCol * GameConfig.TILE_SIZE;
                pauseTimer = GameConfig.AGENT_PAUSE_FRAMES;
            }
        }
    }

    @Override
    protected void updateAction() {

    }

    @Override
    protected int getActionFrames(int action) {
        return 1;
    }

    public boolean canSeeYogi(YogiBear yogi) {
        int visionRange = GameConfig.AGENT_VISION_RANGE * GameConfig.TILE_SIZE;

        int yogiBottom = yogi.getY() + yogi.getHeight();
        int agentBottom = y + height;

        boolean yOverlap = !(yogiBottom < y || yogi.getY() > agentBottom);

        if (!yOverlap) {
            return false;
        }

        if (direction == Direction.RIGHT) {
            int visionStart = x + width;
            int visionEnd = visionStart + visionRange;
            return yogi.getX() >= visionStart && yogi.getX() < visionEnd;
        } else {
            int visionEnd = x;
            int visionStart = visionEnd - visionRange;
            return yogi.getX() + yogi.getWidth() > visionStart && yogi.getX() + yogi.getWidth() <= visionEnd;
        }
    }

    public void reset() {
        x = startX;
        y = startY;
        movingRight = true;
        targetX = patrolEndCol * GameConfig.TILE_SIZE;
        direction = Direction.RIGHT;
        pauseTimer = 0;
    }
}
