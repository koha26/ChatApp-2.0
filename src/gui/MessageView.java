package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Date;

public class MessageView extends JPanel {
    private int currentPos = 0;
    private JScrollPane scrollPane;
    private JPanel messagesPanel; // панель для отображения сообщений
    private RectangleForMessageArea r = new RectangleForMessageArea();

    public MessageView() {
        this.setBorder(new LineBorder(Color.BLACK, 1));
        setLayout(new BorderLayout());
        messagesPanel = new JPanel();
        scrollPane = new JScrollPane(messagesPanel);
        messagesPanel.setBorder(null);
        messagesPanel.setLayout(new GridBagLayout());
        this.add(scrollPane);
    }


    public void sendMessage(String message) {
        JPanel outMsgPanel = new JPanel();
        outMsgPanel.setLayout(new BorderLayout());
        TitledBorder msgBorder = BorderFactory.createTitledBorder(new LineBorder(Color.GREEN, 2), new Date().getHours() + ":" +
                new Date().getMinutes() + ":" + new Date().getSeconds());
        msgBorder.setTitleFont(new Font("Century Gothic", Font.PLAIN, 12));
        msgBorder.setTitlePosition(TitledBorder.BOTTOM);
        JTextArea text = new JTextArea(message);
        text.setBackground(new Color(0, 0, 0, 0));
        text.setFont(new Font("Century Gothic", Font.PLAIN, 16));
        text.setEditable(false);
        text.setBorder(msgBorder);
        outMsgPanel.add(text, BorderLayout.WEST);
        outMsgPanel.setBorder(null);
        JPanel panel = new JPanel();
        panel.setBorder(null);
        messagesPanel.add(outMsgPanel, new GridBagConstraints(0, currentPos, 1, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        messagesPanel.add(panel, new GridBagConstraints(1, currentPos, 1, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        currentPos++;
        updatePanel(messagesPanel);
    }

    public void getMessage(String message) {
        JPanel inMsgPanel = new JPanel();
        inMsgPanel.setLayout(new BorderLayout());
        JTextArea text = new JTextArea(message);
        TitledBorder msgBorder = BorderFactory.createTitledBorder(new LineBorder(Color.BLUE, 2), new Date().getHours() + ":" +
                new Date().getMinutes() + ":" + new Date().getSeconds());
        msgBorder.setTitleFont(new Font("Century Gothic", Font.PLAIN, 12));
        msgBorder.setTitlePosition(TitledBorder.BOTTOM);
        text.setBackground(new Color(0, 0, 0, 0));
        text.setFont(new Font("Century Gothic", Font.PLAIN, 16));
        text.setEditable(false);
        text.setBorder(msgBorder);
        inMsgPanel.add(text, BorderLayout.EAST);
        inMsgPanel.setBorder(null);
        JPanel panel = new JPanel();
        panel.setBorder(null);
        messagesPanel.add(inMsgPanel, new GridBagConstraints(1, currentPos, 1, 1, 1, 0, GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        messagesPanel.add(panel, new GridBagConstraints(0, currentPos, 1, 1, 1, 0, GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        currentPos++;
        updatePanel(messagesPanel);
    }

    public Dimension messageToPixels(String message) { // метод, который возвращает из строки пиксели
        AffineTransform affineTransform = new AffineTransform();
        FontRenderContext frc = new FontRenderContext(affineTransform, true, true);
        Font font = new Font("Century Gothic", Font.PLAIN, 16);
        int msgWidth = (int) (font.getStringBounds(message, frc).getWidth());
        int msgHeight = (int) (font.getStringBounds(message, frc).getHeight());
        return new Dimension(msgWidth, msgHeight);
    }

    void updatePanel(JPanel panel) {
        panel.revalidate();
        panel.updateUI();
    }

    class RectangleForMessageArea {

        private Rectangle r;

        public Rectangle getR() {
            return r;
        }

        public void setR(Rectangle r) {
            this.r = r;
        }
    }
}
