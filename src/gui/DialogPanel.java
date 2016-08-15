package gui;

import logic.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class DialogPanel extends JPanel {
    private JTextArea messageArea = new JTextArea();
    private JScrollPane messageScrollPane = new JScrollPane(messageArea);
    private MessageView messageView = new MessageView();
    private JLabel myPhotoLabel, friendPhotoLabel;

    public DialogPanel() {
        this.setLayout(null);
        this.setOpaque(false);
        this.setBackground(new Color(0, 0, 0, 0));

        messageArea.setFont(Fonts.typingFont);
        messageArea.setLineWrap(true);
        messageArea.setBackground(Color.LIGHT_GRAY);
        messageArea.setWrapStyleWord(true);
        messageArea.setBackground(new Color(0, 0, 0, 150));
        messageArea.setForeground(Color.WHITE);

        messageScrollPane = new JScrollPane(messageArea);
        messageScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        messageScrollPane.setOpaque(false);
        messageScrollPane.getViewport().setOpaque(false);
        messageScrollPane.setBorder(null);
        messageScrollPane.getVerticalScrollBar().setUI(new MyScrollBar());
        messageScrollPane.getVerticalScrollBar().setUnitIncrement(10);

        messageView = new MessageView();

        messageView.setBounds(100, 70, 760, 300);
        messageScrollPane.setBounds(210, 390, 540, 100);

        this.add(messageView);
        this.add(messageScrollPane);

        RepaintPanel repaintPanel = new RepaintPanel(this);
        Thread thread = new Thread(repaintPanel);
        thread.start();
    }

    public MessageView getMessageView() {
        return messageView;
    }

    public JTextArea getMessageArea() {
        return messageArea;
    }

    public void showOutcomingMessage(Message message) {
        messageView.showOutcomingMessage(message);
    }

    public void showIncomingMessage(Message message) {
        messageView.showIncomingMessage(message);
        Sound.playSound(Sound.Sounds.NEW_MESSAGE);
    }

    public void updateInfo(BufferedImage myPhoto, BufferedImage friendPhoto) throws IOException {
        BufferedImage scaled1 = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);

        Graphics2D g1 = scaled1.createGraphics();
        g1.drawImage(myPhoto, 0, 0, 100, 100, null);
        g1.dispose();

        BufferedImage scaled2 = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2 = scaled2.createGraphics();
        g2.drawImage(friendPhoto, 0, 0, 100, 100, null);
        g2.dispose();

        myPhotoLabel = CircleLabelClass.CircleLabel(scaled1);
        friendPhotoLabel = CircleLabelClass.CircleLabel(scaled2);

        myPhotoLabel.setBounds(100, 390, 100, 100);
        friendPhotoLabel.setBounds(760, 390, 100, 100);

        this.add(myPhotoLabel);
        this.add(friendPhotoLabel);

        repaint();
        revalidate();
        updateUI();
    }
}
