package view.renderer;

import model.*;
import model.bag.BrownBag;
import model.bag.GunBag;
import model.bag.MethBag;
import model.bag.MoneyBag;
import model.entity.agent.Agent;
import model.level.Level;
import model.level.Tile;
import model.entity.yogi.YogiBear;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameRenderer {

    private YogiBear yogi;
    private BufferedImage yogiSprite;
    private BufferedImage[] yogiIdle;

    private int animationTick, animationIndex;

    public GameRenderer(YogiBear yogi) {
        this.yogi = yogi;
        loadSprites();
        loadAnimations();
    }

    public void render(Graphics g, Level level, GameModel gameModel) {
        updateAnimationTick();

        renderTiles(g, level);
        renderBags(g, level);
        renderYogi(g);
        renderAgents(g, level);
        renderUI(g, gameModel);
    }

    private void loadSprites() {
        yogiSprite = loadSprite(yogi.getSpritePath());
    }

    private BufferedImage loadSprite(String path) {
        try {
            File spriteFile = new File(path);
            if (spriteFile.exists()) {
                return ImageIO.read(spriteFile);
            } else {
                System.err.println("Sprite not found: " + spriteFile.getAbsolutePath());
                return null;
            }
        } catch (IOException e) {
            System.err.println("Failed to load sprite: " + e.getMessage());
            return null;
        }
    }

    private void loadAnimations() {
        yogiIdle = new BufferedImage[YogiBear.IDLE_FRAMES];

        for (int i = 0; i < yogiIdle.length; i++) {
            yogiIdle[i] = yogiSprite.getSubimage(i * YogiBear.SPRITE_WIDTH, 0, YogiBear.SPRITE_WIDTH, YogiBear.SPRITE_HEIGHT);
        }
    }

    private void updateAnimationTick() {
        animationTick++;

        if (animationTick >= GameConfig.ANIMATION_SPEED) {
            animationTick = 0;
            animationIndex++;

            if (animationIndex >= yogiIdle.length) animationIndex = 0;
        }
    }

    private void renderTiles(Graphics g, Level level) {
        for (Tile tile : level.getTiles()) {
            if (tile.getType() == Tile.Type.WALL) {
                g.setColor(new Color(101, 67, 33));
                g.fillRect(tile.getX(), tile.getY(), tile.getSize(), tile.getSize());
            } else if (tile.getType() == Tile.Type.PLATFORM) {
                g.setColor(new Color(139, 69, 19));
                g.fillRect(tile.getX(), tile.getY(), tile.getSize(), tile.getSize());
            } else if (tile.getType() == Tile.Type.GROUND) {
                g.setColor(new Color(90, 60, 30));
                g.fillRect(tile.getX(), tile.getY(), tile.getSize(), tile.getSize());
            }
        }
    }

    private void renderBags(Graphics g, Level level) {
        for (BrownBag bag : level.getBags()) {
            if (!bag.isCollected()) {
                if (bag instanceof MethBag) g.setColor(Color.CYAN);
                else if (bag instanceof GunBag) g.setColor(Color.DARK_GRAY);
                else if (bag instanceof MoneyBag) g.setColor(Color.GREEN);
                g.fillRect(bag.getX(), bag.getY(), bag.getSize(), bag.getSize());
            }
        }
    }

    private void renderYogi(Graphics g) {
        double scale = (double) yogi.getHeight() / YogiBear.SPRITE_HEIGHT;
        int scaledWidth = (int) (YogiBear.SPRITE_WIDTH * scale);
        int scaledHeight = yogi.getHeight();

        g.drawImage(yogiIdle[animationIndex], yogi.getX(), yogi.getY(), scaledWidth, scaledHeight, null);
    }

    private void renderAgents(Graphics g, Level level) {
        g.setColor(new Color(139, 0, 0));
        for (Agent agent : level.getAgents()) {
            g.fillRect(agent.getX(), agent.getY(), agent.getWidth(), agent.getHeight());
        }
    }

    private void renderUI(Graphics g, GameModel gameModel) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + gameModel.getScore(), 10, 25);
        g.drawString("Lives: " + gameModel.getLives(), 10, 50);
    }

    public void renderMessage(Graphics g, String message, int alpha, int width, int height) {
        if (message != null && alpha > 0) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha / 255f));
            g2d.setColor(Color.RED);
            g2d.setFont(new Font("Arial", Font.BOLD, 60));

            FontMetrics fm = g2d.getFontMetrics();
            int msgWidth = fm.stringWidth(message);
            int msgX = (width - msgWidth) / 2;
            int msgY = height / 2;

            g2d.drawString(message, msgX, msgY);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        }
    }
}
