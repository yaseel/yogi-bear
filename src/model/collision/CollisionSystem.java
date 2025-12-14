package model.collision;

import model.level.Level;
import model.entity.yogi.YogiBear;

public class CollisionSystem {
    private TileCollisionHandler tileHandler;
    private AgentCollisionHandler agentHandler;
    private BoundaryHandler boundaryHandler;
    private Level level;

    public CollisionSystem(YogiBear yogi, Level level) {
        this.level = level;
        this.tileHandler = new TileCollisionHandler(yogi, level);
        this.agentHandler = new AgentCollisionHandler(yogi, level);
        this.boundaryHandler = new BoundaryHandler(yogi);
    }

    public CollisionResult checkAll() {
        tileHandler.checkTileCollisions();

        if (agentHandler.checkAgentCollisions()) {
            return CollisionResult.CAUGHT;
        }

        BoundaryHandler.BoundaryResult boundaryResult = boundaryHandler.checkBoundaries(level);

        if (boundaryResult == BoundaryHandler.BoundaryResult.FELL) {
            return CollisionResult.FELL;
        } else if (boundaryResult == BoundaryHandler.BoundaryResult.NEXT_LEVEL) {
            return CollisionResult.LEVEL_COMPLETE;
        } else if (boundaryResult == BoundaryHandler.BoundaryResult.BLOCKED) {
            return CollisionResult.BLOCKED;
        }

        return CollisionResult.NONE;
    }

    public boolean canStandUp() {
        return tileHandler.canStandUp();
    }

    public enum CollisionResult {
        NONE,
        CAUGHT,
        FELL,
        LEVEL_COMPLETE,
        BLOCKED
    }
}
