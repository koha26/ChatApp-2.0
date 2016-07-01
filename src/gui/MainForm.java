package gui;

import logic.Constants;
import sun.applet.Main;

import javax.swing.*;


public class MainForm extends JFrame {

    private LoginPanel loginPanel;
    private PopUpMenu popUpMenu;

    private final ImageIcon backgroundImage = new ImageIcon("images/bg.png");
    private final ImageIcon appIcon = new ImageIcon("images/icon.png");

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
        super("ChatApp - Login");
        setSize(1000, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        setContentPane(new JLabel(backgroundImage));
        //setUndecorated(true);
        setIconImage(appIcon.getImage());
        setLayout(null);

        loginPanel = new LoginPanel();
        add(loginPanel);

        popUpMenu = new PopUpMenu();
        popUpMenu.setPopUpMenu();
    }
}


