package view.collision;

import model.*;
import model.level.Level;
import model.level.Tile;
import model.yogi.YogiBear;

import java.awt.Rectangle;

public class CollisionHandler {
    private static final int WALL_PUSH_DISTANCE = 10;

    private YogiBear yogi;
    private Level level;

    public CollisionHandler(YogiBear yogi, Level level) {
        this.yogi = yogi;
        this.level = level;
    }

    public void checkCollisions() {
        Rectangle yogiBounds = yogi.getBounds();
        boolean touchingGround = false;

        // check yogi against every solid tile
        for (Tile tile : level.getTiles()) {
            if (!tile.isSolid())
                continue;

            Rectangle tileBounds = new Rectangle(
                    tile.getX(),
                    tile.getY(),
                    tile.getSize(),
                    tile.getSize());

            if (yogiBounds.intersects(tileBounds)) {
                // check if yogi landed on top of this tile
                if (handleGroundCollision(tile)) {
                    touchingGround = true;
                }

                // walls and ground block from all sides, platforms only from top
                if (tile.getType() != Tile.Type.PLATFORM) {
                    handleWallCollision(tile);
                    handleCeilingCollision(tile);
                }
            }
        }

        // if not touching anything solid, yogi is airborne
        if (!touchingGround && yogi.getY() < GameConfig.LEVEL_HEIGHT - yogi.getHeight()) {
            yogi.setOnGround(false);
        }
    }

    // checks if yogi can stand up from crouch without hitting ceiling
    public boolean canStandUp() {
        int standingHeight = yogi.getHeight() * 2;
        int headSpace = standingHeight - yogi.getHeight();
        int headCheckY = yogi.getY() - headSpace;

        // check if there's a solid tile where yogi's head would be
        for (Tile tile : level.getTiles()) {
            if (tile.getType() == Tile.Type.WALL || tile.getType() == Tile.Type.GROUND) {
                Rectangle tileRect = new Rectangle(
                        tile.getX(),
                        tile.getY(),
                        tile.getSize(),
                        tile.getSize());
                Rectangle headRect = new Rectangle(
                        yogi.getX(),
                        headCheckY,
                        yogi.getWidth(),
                        headSpace);

                if (tileRect.intersects(headRect)) {
                    return false;
                }
            }
        }

        return true;
    }

    // handles landing on platforms and ground
    private boolean handleGroundCollision(Tile tile) {
        // only care about this when falling down
        if (!yogi.isFalling())
            return false;

        int yogiBottom = yogi.getY() + yogi.getHeight();
        int tileTop = tile.getY();
        int prevYogiBottom = yogiBottom - yogi.getVelocityY();

        // skip platforms when player is crouching and holding down
        boolean droppingThrough = tile.getType() == Tile.Type.PLATFORM
                && yogi.isDropThroughRequested()
                && yogi.isCrouching();

        // check if yogi just crossed the top surface of the tile
        if (!droppingThrough && prevYogiBottom <= tileTop && yogiBottom > tileTop) {
            // snap yogi to top of tile
            yogi.setY(tileTop - yogi.getHeight());
            yogi.setVelocityY(0);
            yogi.setOnGround(true);
            yogi.clearDropThrough();
            return true;
        }

        return false;
    }

    // handles bumping into walls from the side
    private void handleWallCollision(Tile tile) {
        int yogiLeft = yogi.getX();
        int yogiRight = yogi.getX() + yogi.getWidth();
        int tileLeft = tile.getX();
        int tileRight = tile.getX() + tile.getSize();

        int yogiCenterY = yogi.getY() + yogi.getHeight() / 2;
        int tileCenterY = tile.getY() + tile.getSize() / 2;

        // only push yogi if vertically aligned with the tile
        if (Math.abs(yogiCenterY - tileCenterY) < tile.getSize()) {
            // pushing into left side of tile
            if (yogiRight > tileLeft && yogiRight < tileLeft + WALL_PUSH_DISTANCE && yogiLeft < tileLeft) {
                yogi.setX(tileLeft - yogi.getWidth());
            }
            // pushing into right side of tile
            else if (yogiLeft < tileRight && yogiLeft > tileRight - WALL_PUSH_DISTANCE && yogiRight > tileRight) {
                yogi.setX(tileRight);
            }
        }
    }

    // handles hitting head on ceiling while jumping
    private void handleCeilingCollision(Tile tile) {
        // only matters when moving upward
        if (!yogi.isJumping())
            return;

        int yogiTop = yogi.getY();
        int tileBottom = tile.getY() + tile.getSize();
        int prevYogiTop = yogiTop - yogi.getVelocityY();

        // check if yogi's head just hit the bottom of the tile
        if (prevYogiTop >= tileBottom && yogiTop < tileBottom) {
            // snap yogi below the tile and stop upward movement
            yogi.setY(tileBottom);
            yogi.setVelocityY(0);
            yogi.setOnGround(false);
        }
    }
}
