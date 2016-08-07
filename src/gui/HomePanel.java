package gui;

import logic.User;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class HomePanel extends JPanel {
    private JLabel yourPhoto;
    private JLabel nickLabel;
    private JLabel personaInfo;
    private JPanel topPanel, bottomPanel;
    private JScrollPane scrollPane;

    public JPanel getBottomPanel() {
        return bottomPanel;
    }

    public HomePanel(User user) {
        this.setLayout(null);
        this.setOpaque(false);
        this.setBackground(new Color(0, 0, 0, 0));

        BufferedImage scaled = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB);

        Graphics2D g = scaled.createGraphics();
        g.drawImage(user.getAvatarAsBufImage(), 0, 0, 256, 256, null);
        g.dispose();

        yourPhoto = new JLabel(new ImageIcon(scaled));
        yourPhoto.setBorder(new LineBorder(Color.WHITE));
        nickLabel = new JLabel(user.getNickname()); //TODO: сделать подсчет оптимального размера шрифта, чтобы он влез в компонент
        nickLabel.setFont(Fonts.nickFont);
        nickLabel.setForeground(Color.WHITE);
        nickLabel.setHorizontalAlignment(SwingConstants.CENTER);

        topPanel = new JPanel(null);
        topPanel.setBackground(new Color(0, 0, 0, 150));

        personaInfo = new JLabel("Personal Info");
        personaInfo.setFont(Fonts.nickFont);
        personaInfo.setForeground(Color.WHITE);
        personaInfo.setHorizontalAlignment(SwingConstants.CENTER);
        personaInfo.setBorder(new LineBorder(Color.WHITE, 5));

        JLabel namePanel = new JLabel("Name: ");
        JLabel surnamePanel = new JLabel("Surname: ");
        JLabel dateOfBirthPanel = new JLabel("Date of birth: ");
        JLabel sexPanel = new JLabel("Sex: ");
        namePanel.setHorizontalAlignment(SwingConstants.LEFT);
        surnamePanel.setHorizontalAlignment(SwingConstants.LEFT);
        dateOfBirthPanel.setHorizontalAlignment(SwingConstants.LEFT);
        sexPanel.setHorizontalAlignment(SwingConstants.LEFT);
        namePanel.setForeground(Color.WHITE);
        surnamePanel.setForeground(Color.WHITE);
        dateOfBirthPanel.setForeground(Color.WHITE);
        sexPanel.setForeground(Color.WHITE);

        JLabel namePanelInfo = new JLabel(user.getName());
        JLabel surnamePanelInfo = new JLabel(user.getSurname());
        JLabel dateOfBirthPanelInfo = new JLabel(user.getDateOfBirth());
        JLabel sexPanelInfo;
        if (user.getSex() == null) {
            sexPanelInfo = new JLabel("-");
        } else {
            sexPanelInfo = new JLabel(user.getSex().toString());
        }
        namePanelInfo.setForeground(Color.RED);
        surnamePanelInfo.setForeground(Color.RED);
        dateOfBirthPanelInfo.setForeground(Color.RED);
        sexPanelInfo.setForeground(Color.RED);

        namePanelInfo.setHorizontalAlignment(SwingConstants.CENTER);
        surnamePanelInfo.setHorizontalAlignment(SwingConstants.CENTER);
        dateOfBirthPanelInfo.setHorizontalAlignment(SwingConstants.CENTER);
        sexPanelInfo.setHorizontalAlignment(SwingConstants.CENTER);

        namePanel.setFont(Fonts.nickFont);
        surnamePanel.setFont(Fonts.nickFont);
        dateOfBirthPanel.setFont(Fonts.nickFont);
        sexPanel.setFont(Fonts.nickFont);
        namePanelInfo.setFont(Fonts.nickFont);
        surnamePanelInfo.setFont(Fonts.nickFont);
        dateOfBirthPanelInfo.setFont(Fonts.nickFont);
        sexPanelInfo.setFont(Fonts.nickFont);

        bottomPanel = new JPanel(new GridBagLayout());
        bottomPanel.setBackground(new Color(0, 0, 0, 150));

        scrollPane = new JScrollPane(bottomPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);

        if (user.getFriendsList().size() > 0) {
            for (int i = 0; i < (user.getFriendsList().size() / 5) + 1; i++) {
                for (int j = 0; j < 5; j++) {
                    if (i * 5 + j >= user.getFriendsList().size()) break;
                    FriendLook friend = new FriendLook(user.getFriendsList().get(i * 5 + j));
                    bottomPanel.add(friend, new GridBagConstraints(j, i, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
                }
            }
        }


        topPanel.setBounds(30, 10, 900, 320);
        scrollPane.setBounds(170, 350, 620, 150);
        yourPhoto.setBounds(20, 15, 256, 256);
        nickLabel.setBounds(20, 281, 256, 29);
        personaInfo.setBounds(301, 15, 599, 70);
        namePanel.setBounds(311, 90, 240, 70);
        surnamePanel.setBounds(311, 140, 240, 70);
        dateOfBirthPanel.setBounds(311, 190, 240, 70);
        sexPanel.setBounds(311, 240, 240, 70);
        namePanelInfo.setBounds(551, 90, 359, 70);
        surnamePanelInfo.setBounds(551, 140, 359, 70);
        dateOfBirthPanelInfo.setBounds(551, 190, 359, 70);
        sexPanelInfo.setBounds(551, 240, 359, 70);

        this.add(topPanel);
        this.add(scrollPane);
        topPanel.add(yourPhoto);
        topPanel.add(nickLabel);
        topPanel.add(personaInfo);
        topPanel.add(namePanel);
        topPanel.add(surnamePanel);
        topPanel.add(dateOfBirthPanel);
        topPanel.add(sexPanel);
        topPanel.add(namePanelInfo);
        topPanel.add(surnamePanelInfo);
        topPanel.add(dateOfBirthPanelInfo);
        topPanel.add(sexPanelInfo);

        RepaintPanel repaintPanel = new RepaintPanel(this);
        Thread thread = new Thread(repaintPanel);
        thread.start();
    }
}
