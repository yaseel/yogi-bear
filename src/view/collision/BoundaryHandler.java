package view.collision;

import model.GameConfig;
import model.level.Level;
import model.yogi.YogiBear;

public class BoundaryHandler {
    private YogiBear yogi;

    public BoundaryHandler(YogiBear yogi) {
        this.yogi = yogi;
    }

    // checks if yogi went out of bounds or reached the level exit
    public BoundaryResult checkBoundaries(Level level) {
        int yogiRight = yogi.getX() + yogi.getWidth();
        int yogiBottom = yogi.getY() + yogi.getHeight();

        // fell off the left edge
        if (yogiRight < 0) {
            return BoundaryResult.FAIL;
        }

        // fell off the bottom
        if (yogiBottom > GameConfig.LEVEL_HEIGHT) {
            return BoundaryResult.FAIL;
        }

        // reached the right edge (level exit)
        if (yogiRight >= GameConfig.LEVEL_WIDTH) {
            if (level.getRemainingBags() == 0) {
                return BoundaryResult.NEXT_LEVEL;
            } else {
                return BoundaryResult.BLOCKED;
            }
        }

        return BoundaryResult.NONE;
    }

    public enum BoundaryResult {
        NONE,
        FAIL,
        NEXT_LEVEL,
        BLOCKED
    }
}
