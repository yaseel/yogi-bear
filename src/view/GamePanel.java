package view;

import model.GameConfig;
import model.YogiBear;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private YogiBear yogi;

    public GamePanel() {
        setPreferredSize(new Dimension(GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT));
        setBackground(new Color(135, 206, 235));

        yogi = new YogiBear(100, GameConfig.GROUND_Y - GameConfig.TILE_SIZE * 2);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(new Color(101, 67, 33));
        g.fillRect(0, GameConfig.GROUND_Y, GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT - GameConfig.GROUND_Y);

        g.setColor(new Color(139, 90, 43));
        g.fillRect(yogi.getX(), yogi.getY(), yogi.getWidth(), yogi.getHeight());
    }
}
