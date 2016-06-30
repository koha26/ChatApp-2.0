package gui;

import javax.swing.*;

public class MainForm extends JFrame {

    private LoginPanel loginPanel;

    private final ImageIcon backgroundImage = new ImageIcon("images/bg.png");

    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainForm().setVisible(true);
            }
        });
    }

    public MainForm() {
        setSize(1000, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        setContentPane(new JLabel(backgroundImage));
        setUndecorated(true);
        setLayout(null);

        loginPanel = new LoginPanel();
        add(loginPanel);
    }
}
