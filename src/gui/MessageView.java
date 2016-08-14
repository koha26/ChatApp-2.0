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
        scrollPane.getVerticalScrollBar().setUI(new MyScrollBar());
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        this.add(scrollPane, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
    }

    public void showOutcomingMessage(Message message) {
        AbstractBorder msgBorder = new MyBorder(Color.PINK, 1, 16, 16);

        JPanel outMsgPanel = new JPanel();
        outMsgPanel.setBackground(new Color(0, 0, 0, 0));
        outMsgPanel.setLayout(new BorderLayout(15, 0));
        outMsgPanel.setBorder(msgBorder);

        JTextArea msgText = new JTextArea(message.getMessageText());
        msgText.setBackground(new Color(0, 0, 0, 0));
        msgText.setFont(new Font("Century Gothic", Font.PLAIN, 16));
        msgText.setEditable(false);
        msgText.setBorder(null);
        msgText.setForeground(Color.WHITE);
        msgText.setLineWrap(true);

        JLabel msgTime = new JLabel(getMessageTime(new Date()));
        msgTime.setForeground(Color.PINK);
        msgTime.setFont(Fonts.smallFont);

        outMsgPanel.add(msgText, BorderLayout.CENTER);
        outMsgPanel.add(msgTime, BorderLayout.EAST);
        messagesPanel.add(outMsgPanel, new GridBagConstraints(0, currentPos, 1, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(2, 2, 2, 200), 0, 0));

        currentPos++;
        updatePanel(messagesPanel);
    }

    public void showIncomingMessage(Message message) {
        AbstractBorder msgBorder = new MyBorder(Color.GREEN, 1, 16, 16);

        JPanel inMsgPanel = new JPanel();
        inMsgPanel.setLayout(new BorderLayout());
        inMsgPanel.setBackground(new Color(0, 0, 0, 0));
        inMsgPanel.setBorder(msgBorder);

        JTextArea msgText = new JTextArea(message.getMessageText());
        msgText.setBackground(new Color(0, 0, 0, 0));
        msgText.setFont(new Font("Century Gothic", Font.PLAIN, 16));
        msgText.setEditable(false);
        msgText.setForeground(Color.WHITE);
        msgText.setBorder(null);

        JLabel msgTime = new JLabel(getMessageTime(new Date()));

        msgTime.setForeground(Color.GREEN);
        msgTime.setFont(Fonts.smallFont);

        inMsgPanel.add(msgTime, BorderLayout.WEST);
        inMsgPanel.add(msgText, BorderLayout.EAST);

        messagesPanel.add(inMsgPanel, new GridBagConstraints(0, currentPos, 1, 1, 1, 0, GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(2, 200, 2, 2), 0, 0));
        currentPos++;
        updatePanel(messagesPanel);
    }


    public static String getMessageTime(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        //  dateFormat.setTimeZone(TimeZone.);
        return dateFormat.format(date);
    }

    void updatePanel(JPanel panel) {
        panel.repaint();
        panel.revalidate();
        panel.updateUI();
    }
}