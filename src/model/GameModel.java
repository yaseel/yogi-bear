package model;

public class GameModel {
    private int score;
    private int lives;

    public GameModel() {
        this.score = 0;
        this.lives = 3;
    }

    public void addScore(int points) {
        score += points;
    }

    public void loseLife() {
        lives--;
    }

    public int getScore() {
        return score;
    }

    public int getLives() {
        return lives;

    }

    public void reset() {
        score = 0;
        lives = 3;
    }
}
