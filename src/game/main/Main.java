package game.main;

import game.main.MenuPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Main {

    public static void main(String[] args) {

        JFrame window = new JFrame("Pixel Farmer");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        CardLayout cardLayout = new CardLayout();
        JPanel container = new JPanel(cardLayout);
        GamePanel gp = new GamePanel();

        BufferedImage menuBg = gp.tileM.renderScreenBackground(5, 0);

        PauseMenuPanel pauseMenu = new PauseMenuPanel(
                // Resume
                () -> {
                    gp.paused = false;
                    cardLayout.show(container, "GAME");
                    gp.requestFocusInWindow();
                },
                // Back to Menu
                () -> {
                    gp.paused = false;
                    gp.stopGameThread();
                    cardLayout.show(container, "MENU");
                }
        );

        MenuPanel menu = new MenuPanel(menuBg, () -> {
            cardLayout.show(container, "GAME");
            gp.startGameThread();
            gp.requestFocusInWindow();
        });
        gp.setOnPause(() -> {
            cardLayout.show(container, "PAUSE");
        });

        container.add(menu, "MENU");
        container.add(gp, "GAME");
        container.add(pauseMenu, "PAUSE");

        window.setContentPane(container);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        cardLayout.show(container, "MENU");
    }
}
