package gui.Notifications;

import gui.GUIStandartOperations;
import logic.command.FriendshipRequestCommand;

import javax.swing.*;
import java.awt.*;

public class FriendshipRequestNotification extends JPanel implements Notification {
    private JLabel nickname, text;
    private JButton acceptButton, rejectButton;
    private final ImageIcon acceptButIcon = new ImageIcon("images/mainform/accept.png");
    private final ImageIcon acceptButIconEntered = new ImageIcon("images/mainform/accept_e.png");
    private final ImageIcon rejectutIcon = new ImageIcon("images/mainform/cancel.png");
    private final ImageIcon rejectButIconEntered = new ImageIcon("images/mainform/cancel_e.png");

    public FriendshipRequestNotification(){
        this.setLayout(new GridBagLayout());
        this.setOpaque(false);
        this.setBackground(new Color(0,0,0,0));

        nickname = new JLabel();
        nickname.setBackground(new Color(0,0,0,0));
        nickname.setForeground(Color.RED);
        nickname.setFont(new Font("Berlin Sans FB Demi", Font.PLAIN, 20));
        text = new JLabel("wants to add you as a friend!");
        text.setBackground(new Color(0,0,0,0));
        text.setForeground(Color.WHITE);
        text.setFont(new Font("Berlin Sans FB Demi", Font.PLAIN, 20));

        acceptButton = GUIStandartOperations.ButtonStartOperations(acceptButIcon, acceptButIconEntered, true);
        rejectButton = GUIStandartOperations.ButtonStartOperations(rejectutIcon, rejectButIconEntered, true);

        this.add(nickname, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 10, 2, 2), 0, 0));
        this.add(text, new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 100, 2, 2), 0, 0));
        this.add(acceptButton, new GridBagConstraints(1, 0, 1, 2, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        this.add(rejectButton, new GridBagConstraints(2, 0, 1, 2, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 20), 0, 0));
    }

    public void updateInfo(FriendshipRequestCommand command){
        nickname.setText(command.getNickname_From());
        repaint();
        revalidate();
        updateUI();
    }

    public JButton getAcceptButton() {
        return acceptButton;
    }

    public JButton getRejectButton() {
        return rejectButton;
    }

    public JLabel getNickname() {
        return nickname;
    }
}
