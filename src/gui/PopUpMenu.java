package gui;

import logic.Constants;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PopUpMenu extends PopupMenu {

    private final ImageIcon icon = new ImageIcon("images/popup.png");

    private MenuItem exitItem;

    public void setPopUpMenu() {
        TrayIcon trayIcon = new TrayIcon(icon.getImage(), Constants.VERSION_ID, new PopUpMenu());
        trayIcon.setImageAutoSize(true);
        SystemTray tray = SystemTray.getSystemTray();

        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            e.printStackTrace();
        }

        exitItem = new MenuItem("Exit");
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        add(exitItem);

        trayIcon.displayMessage(Constants.VERSION_ID, "ChatApp started.", TrayIcon.MessageType.INFO);
    }
}
