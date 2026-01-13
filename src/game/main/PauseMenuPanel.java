package game.main;

import javax.swing.*;
import java.awt.*;

public class PauseMenuPanel extends JPanel {

    public PauseMenuPanel(Runnable onResume, Runnable onBackToMenu) {

        setLayout(new GridBagLayout());
        setOpaque(false);

        JButton resumeBtn = new JButton("Resume");
        JButton backBtn   = new JButton("Back to Menu");

        styleBtn(resumeBtn);
        styleBtn(backBtn);

        resumeBtn.addActionListener(e -> onResume.run());
        backBtn.addActionListener(e -> onBackToMenu.run());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridy = 0;
        add(resumeBtn, gbc);

        gbc.gridy = 1;
        add(backBtn, gbc);
    }

    private void styleBtn(JButton btn) {
        btn.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setForeground(Color.WHITE);

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setForeground(new Color(255, 230, 150));
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setForeground(Color.WHITE);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // nền tối mờ
        g.setColor(new Color(0, 0, 0, 140));
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
