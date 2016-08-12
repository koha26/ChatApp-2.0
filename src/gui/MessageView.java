package gui;

import logic.Message;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MessageView extends JPanel {
    private int currentPos = 0;
    private JScrollPane scrollPane;
    private JPanel messagesPanel; // панель для отображения сообщений

    public MessageView() {
        setLayout(new GridBagLayout());
        messagesPanel = new JPanel();
        messagesPanel.setBackground(new Color(0, 0, 0, 0));
        scrollPane = new JScrollPane(messagesPanel);
        messagesPanel.setBorder(null);
        messagesPanel.setLayout(new GridBagLayout());
        this.setBackground(new Color(0, 0, 0, 150));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        this.add(scrollPane, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
    }

    public void showOutcomingMessage(Message message) {
        JPanel outMsgPanel = new JPanel();
        AbstractBorder brdrRight = new MyBorder(Color.PINK, 1, 16, 16);

        outMsgPanel.setBackground(new Color(0, 0, 0, 0));
        outMsgPanel.setLayout(new BorderLayout(15, 0));
        TitledBorder msgBorder = BorderFactory.createTitledBorder(new LineBorder(Color.GREEN, 2), getMessageTime(new Date()),
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, Fonts.typingFont, Color.GREEN);
        msgBorder.setTitleFont(new Font("Century Gothic", Font.PLAIN, 12));
        msgBorder.setTitlePosition(TitledBorder.BOTTOM);
        JTextArea text = new JTextArea(message.getMessageText());
        text.setBackground(new Color(0, 0, 0, 0));
        text.setFont(new Font("Century Gothic", Font.PLAIN, 16));
        text.setEditable(false);
        text.setBorder(null);
        text.setForeground(Color.WHITE);
        text.setLineWrap(true);
        outMsgPanel.add(text, BorderLayout.CENTER);
        JLabel msgTime = new JLabel(getMessageTime(new Date()));
        msgTime.setForeground(Color.PINK);
        msgTime.setFont(Fonts.smallFont);
        outMsgPanel.add(msgTime, BorderLayout.EAST);
        outMsgPanel.setBorder(brdrRight);
        messagesPanel.add(outMsgPanel, new GridBagConstraints(0, currentPos, 1, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(2, 2, 2, 200), 0, 0));
        currentPos++;
        updatePanel(messagesPanel);
    }

    public void showIncomingMessage(Message message) {
        AbstractBorder brdr = new MyBorder(Color.GREEN, 1, 16, 16);
        JPanel inMsgPanel = new JPanel();
        inMsgPanel.setLayout(new BorderLayout());
        inMsgPanel.setBackground(new Color(0, 0, 0, 0));
        JTextArea text = new JTextArea(message.getMessageText());
        TitledBorder msgBorder = BorderFactory.createTitledBorder(new LineBorder(Color.BLUE, 2), getMessageTime(message.getDate())
                , TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, Fonts.typingFont, Color.BLUE);
        msgBorder.setTitleFont(new Font("Century Gothic", Font.PLAIN, 12));
        msgBorder.setTitlePosition(TitledBorder.BOTTOM);
        text.setBackground(new Color(0, 0, 0, 0));
        text.setFont(new Font("Century Gothic", Font.PLAIN, 16));
        text.setEditable(false);
        text.setForeground(Color.WHITE);
        text.setBorder(brdr);
        JLabel msgTime = new JLabel(" " + getMessageTime(new Date()));
        msgTime.setForeground(Color.GREEN);
        msgTime.setFont(Fonts.smallFont);
        inMsgPanel.add(msgTime, BorderLayout.WEST);
        inMsgPanel.add(text, BorderLayout.EAST);
        inMsgPanel.setBorder(null);
        messagesPanel.add(inMsgPanel, new GridBagConstraints(0, currentPos, 1, 1, 1, 0, GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(2, 200, 2, 2), 0, 0));
        currentPos++;
        updatePanel(messagesPanel);
    }

    public static Dimension messageToPixels(String message) { // метод, который возвращает из строки пиксели
        AffineTransform affineTransform = new AffineTransform();
        FontRenderContext frc = new FontRenderContext(affineTransform, true, true);
        Font font = new Font("Century Gothic", Font.PLAIN, 16);
        int msgWidth = (int) (font.getStringBounds(message, frc).getWidth());
        int msgHeight = (int) (font.getStringBounds(message, frc).getHeight());
        return new Dimension(msgWidth, msgHeight);
    }

    public static String getMessageTime(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        //  dateFormat.setTimeZone(TimeZone.);
        return dateFormat.format(date);
    }

    void updatePanel(JPanel panel) {
        panel.revalidate();
        panel.updateUI();
    }
}