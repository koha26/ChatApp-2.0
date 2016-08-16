package gui;

import logic.Constants;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PopUpMenu extends PopupMenu {

    private final ImageIcon icon = new ImageIcon("images/popup.png");

    private Menu menu;
    private MenuItem exitItem;
    private static TrayIcon trayIcon;
    public void setPopUpMenu() {
        trayIcon = new TrayIcon(icon.getImage(), Constants.VERSION_ID, new PopUpMenu());
        trayIcon.setImageAutoSize(true);
        SystemTray tray = SystemTray.getSystemTray();

        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            e.printStackTrace();
        }

        menu = new Menu();
        add(menu);

        exitItem = new MenuItem("Exit");
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(-1);
            }
        });
        menu.add(exitItem);


        trayIcon.displayMessage(Constants.VERSION_ID, "ChatApp started.", TrayIcon.MessageType.INFO);
    }

    public static void displayMessage(String message) {
        trayIcon.displayMessage(Constants.VERSION_ID, message, TrayIcon.MessageType.INFO);
    }
}
