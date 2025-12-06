package model;

import java.util.List;

public class Level {
    private List<Tile> tiles;
    private List<BrownBag> bags;
    private int yogiStartX;
    private int yogiStartY;
    private int width;
    private int height;

    public Level(List<Tile> tiles, List<BrownBag> bags, int yogiStartX, int yogiStartY, int width, int height) {
        this.tiles = tiles;
        this.bags = bags;
        this.yogiStartX = yogiStartX;
        this.yogiStartY = yogiStartY;
        this.width = width;
        this.height = height;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public List<BrownBag> getBags() {
        return bags;
    }

    public int getYogiStartX() {
        return yogiStartX;
    }

    public int getYogiStartY() {
        return yogiStartY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
