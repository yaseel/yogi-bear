package controller;

import model.entity.yogi.YogiBear;
import model.collision.CollisionSystem;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public class InputHandler extends KeyAdapter {
    private YogiBear yogi;
    private CollisionSystem collisionSystem;
    private Set<Integer> pressedKeys;

    public InputHandler(YogiBear yogi, CollisionSystem collisionSystem) {
        this.yogi = yogi;
        this.collisionSystem = collisionSystem;
        this.pressedKeys = new HashSet<>();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        pressedKeys.add(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressedKeys.remove(e.getKeyCode());
    }

    public void update() {
        boolean moving = false;

        if (pressedKeys.contains(KeyEvent.VK_A)) {
            yogi.moveLeft();
            moving = true;
        }
        if (pressedKeys.contains(KeyEvent.VK_D)) {
            yogi.moveRight();
            moving = true;
        }

        if (pressedKeys.contains(KeyEvent.VK_W)) {
            yogi.jump();
        }

        if (pressedKeys.contains(KeyEvent.VK_S)) {
            yogi.crouch();
            yogi.requestDropThrough();
        } else {
            yogi.standUp(collisionSystem.canStandUp());
        }

        if (!moving) {
            yogi.stopMoving();
        }
    }

    public void setCollisionSystem(CollisionSystem collisionSystem) {
        this.collisionSystem = collisionSystem;
    }

    public void clearAllKeys() {
        pressedKeys.clear();
    }
}
