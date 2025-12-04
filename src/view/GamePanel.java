package view;

import model.GameConfig;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    public GamePanel() {
        setPreferredSize(new Dimension(GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT));
        setBackground(new Color(135, 206, 235));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(new Color(101, 67, 33));
        g.fillRect(0, GameConfig.GROUND_Y, GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT - GameConfig.GROUND_Y);
    }
}
