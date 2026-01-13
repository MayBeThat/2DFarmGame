package game.main;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MenuPanel extends JPanel {

    private final BufferedImage bg;
    private final Runnable onStart;

    public MenuPanel(BufferedImage bg, Runnable onStart) {
        this.bg = bg;
        this.onStart = onStart;

        setLayout(new GridBagLayout());

        JButton startBtn = new JButton("Start");
        JButton exitBtn = new JButton("Exit");

        styleGameButton(startBtn);
        styleGameButton(exitBtn);


        startBtn.addActionListener(e -> onStart.run());
        exitBtn.addActionListener(e -> System.exit(0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridy = 0; add(startBtn, gbc);
        gbc.gridy = 1; add(exitBtn, gbc);
    }
    private void styleGameButton(JButton btn) {
        btn.setFont(new Font("Comic Sans MS", Font.BOLD, 18));


        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);


        btn.setFocusPainted(false);


        btn.setForeground(Color.WHITE);


        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setForeground(new Color(255, 230, 150));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setForeground(Color.WHITE);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // background
        if (bg != null) {
            g2.drawImage(bg, 0, 0, getWidth(), getHeight(), null);
            g2.setColor(new Color(0, 0, 0, 90));
            g2.fillRect(0, 0, getWidth(), getHeight());
        }

        //Title
        String title = "FARMER GAME";
        g2.setFont(new Font("Comic Sans MS", Font.BOLD, 48));

        //can tilte
        FontMetrics fm = g2.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(title)) / 2;
        int y = 120;

        g2.setColor(new Color(0, 0, 0, 180));
        g2.drawString(title, x + 3, y + 3);

        g2.setColor(Color.WHITE);
        g2.drawString(title, x, y);
    }
}
