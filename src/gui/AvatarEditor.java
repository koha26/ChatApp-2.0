package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class AvatarEditor extends JFrame {
    private JFrame thisFrame;
    private JPanel image;
    private JButton saveButton, cancelButton, plusButton, minusButton;
    private JLabel logo = new JLabel(new ImageIcon("images/popup.png"));
    private final ImageIcon saveButIcon = new ImageIcon("images/avatarform/pl.png");
    private final ImageIcon saveButIconEntered = new ImageIcon("images/avatarform/pl_e.png");
    private final ImageIcon cancelButIcon = new ImageIcon("images/avatarform/cancel.png");
    private final ImageIcon cancelButIconEntered = new ImageIcon("images/avatarform/cancel_e.png");
    private final ImageIcon plusButIcon = new ImageIcon("images/avatarform/z1.png");
    private final ImageIcon plusButIconEntered = new ImageIcon("images/avatarform/z1_e.png");
    private final ImageIcon minusButIcon = new ImageIcon("images/avatarform/z2.png");
    private final ImageIcon minusButIconEntered = new ImageIcon("images/avatarform/z2_e.png");

    private Dimension calculateSize(ImageIcon img, int width, int height) {
        int maxsize;
        double x;
        double newWidth = 0;
        double newHeight = 0;
        if ((img.getIconWidth() >= img.getIconHeight()) & (img.getIconWidth() >= width / 2)) {
            maxsize = width / 2;
            x = ((double) img.getIconWidth() / (double) maxsize);
            newWidth = (double) img.getIconWidth() / x;
            newHeight = (double) img.getIconHeight() / x;
        } else if ((img.getIconWidth() < img.getIconHeight()) & (img.getIconHeight() >= height / 2)) {
            maxsize = height;
            x = ((double) img.getIconHeight() / maxsize);
            newWidth = (double) img.getIconWidth() / x;
            newHeight = (double) img.getIconHeight() / x;
        } else {
            return new Dimension(img.getIconWidth(), img.getIconHeight());
        }

        return new Dimension((int) (newWidth / 1.5), (int) (newHeight / 1.5));
    }

    private BufferedImage cutImage(BufferedImage image, int x, int y, int size) {
        BufferedImage newImage = image.getSubimage(x, y, size, size);
        return newImage;
    }

    public AvatarEditor(File file) throws IOException {
        thisFrame = this;
        this.setResizable(false);
        this.setUndecorated(true);
        this.setLayout(new GridBagLayout());
        ImageIcon img = new ImageIcon(file.getAbsolutePath());
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int x = screenSize.width;
        int y = screenSize.height;
        setBounds(y / 4, x / 4, y / 2, x / 2);
        final Dimension d = calculateSize(img, x, y);
        final BufferedImage bufImage = LoadImage.resizeImage(file.getAbsolutePath(), (int) d.getWidth(), (int) d.getHeight());

        image = new ImagePanel(bufImage);
        image.setSize(new Dimension((int) d.getWidth(), (int) d.getHeight()));
        image.setLayout(new GridBagLayout());
        saveButton = GUIStandartOperations.ButtonStartOperations(saveButIcon, saveButIconEntered, true);
        cancelButton = GUIStandartOperations.ButtonStartOperations(cancelButIcon, cancelButIconEntered, true);
        plusButton = GUIStandartOperations.ButtonStartOperations(plusButIcon, plusButIconEntered, true);
        minusButton = GUIStandartOperations.ButtonStartOperations(minusButIcon, minusButIconEntered, true);
        minusButton.setEnabled(false);

        final MyPanel myPanel = new MyPanel(d);

        plusButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                minusButton.setEnabled(true);
                if (myPanel.getX() + myPanel.getSizeOfSquare() + 10 > (d.getWidth())) {
                    myPanel.setLocation(myPanel.getX() - 10, myPanel.getY());
                }
                if (myPanel.getY() + myPanel.getSizeOfSquare() + 10 > (d.getHeight())) {
                    myPanel.setLocation(myPanel.getX(), myPanel.getY() - 10);
                } else {
                    myPanel.setLocation(myPanel.getX(), myPanel.getY());
                }
                myPanel.setSizeOfSquare(myPanel.getSizeOfSquare() + 10);
                myPanel.setSize(myPanel.getWidth() + 10, myPanel.getHeight() + 10);
                if (myPanel.getSizeOfSquare() + 20 > d.getHeight() || myPanel.getSizeOfSquare() + 20 > d.getWidth()) {
                    plusButton.setEnabled(false);
                }
                image.repaint();
                image.revalidate();
            }
        });

        minusButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                plusButton.setEnabled(true);
                myPanel.setSize(myPanel.getWidth() - 10, myPanel.getHeight() - 10);
                myPanel.setSizeOfSquare(myPanel.getSizeOfSquare() - 10);
                if (myPanel.getSizeOfSquare() - 10 < 250) {
                    minusButton.setEnabled(false);
                }
                image.repaint();
                image.revalidate();
            }
        });

        cancelButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegPanel.changeEnableOfButtons();
                thisFrame.dispose();
            }
        });

        saveButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BufferedImage newImage = cutImage(bufImage, myPanel.getX(), myPanel.getY(), myPanel.getSizeOfSquare());

                BufferedImage scaled = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB);

                Graphics2D g = scaled.createGraphics();
                g.drawImage(newImage, 0, 0, 256, 256, null);
                g.dispose();

                RegPanel.setBufImage(scaled);
                RegPanel.changeEnableOfButtons();
                thisFrame.dispose();
            }
        });

        JPanel bottomPanel = new JPanel(new GridBagLayout());
        bottomPanel.setBackground(Color.BLACK);
        bottomPanel.add(saveButton, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        bottomPanel.add(cancelButton, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 0), 0, 0));
        bottomPanel.add(logo, new GridBagConstraints(2, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, (int) d.getWidth() - 32 - 32 - 2 - 2 - 2, 2, 2), 0, 0));

        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(Color.BLACK);
        rightPanel.add(plusButton, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        rightPanel.add(minusButton, new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));

        image.setLayout(null);
        image.add(myPanel);

        this.add(image, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        this.add(rightPanel, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        this.add(bottomPanel, new GridBagConstraints(0, 1, 2, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

        this.setSize((int) ((int) d.getWidth() + bottomPanel.getPreferredSize().getHeight()), (int) ((int) d.getHeight() + rightPanel.getPreferredSize().getWidth()));

        setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
