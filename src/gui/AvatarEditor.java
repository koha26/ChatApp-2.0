package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class AvatarEditor extends JFrame {
    private JPanel image;
    private JButton saveButton, cancelButton, plusButton, minusButton;
    private JLabel logo = new JLabel(new ImageIcon("images/popup.png"));
    private Point point = new Point();

    private Dimension calculateSize(ImageIcon img, int width, int height) {
        int maxsize;
        double x;
        double newWidth = 0;
        double newHeight = 0;
        if (img.getIconWidth() > img.getIconHeight()) {
            maxsize = width / 2;
            x = ((double) img.getIconWidth() / (double) maxsize);
            newWidth = (double) img.getIconWidth() / x;
            newHeight = (double) img.getIconHeight() / x;
        } else {
            maxsize = height;
            x = ((double) img.getIconHeight() / maxsize);
            newWidth = (double) img.getIconWidth() / x;
            newHeight = (double) img.getIconHeight() / x;
        }

        return new Dimension((int) (newWidth / 1.5), (int) (newHeight / 1.5));
    }

    public AvatarEditor() throws IOException {
        this.setResizable(false);
        this.setUndecorated(true);
        this.setLayout(new GridBagLayout());
        ImageIcon img = new ImageIcon("lambo.jpg");
        System.out.println(img.getIconHeight() + ";" + img.getIconWidth());
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int x = screenSize.width;
        int y = screenSize.height;
        setBounds(y / 4, x / 4, y / 2, x / 2);
        Dimension d = calculateSize(img, x, y);
        LoadImage.resizeImage("lambo.jpg", (int) d.getWidth(), (int) d.getHeight());

        System.out.println(d);

        image = new ImagePanel("1.jpg");
        image.setSize(d);
        image.setBackground(new Color(0xd0d0ff));
        image.setLayout(new GridBagLayout());
        plusButton = new JButton("+");
        minusButton = new JButton("-");
        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");

        final MyPanel myPanel = new MyPanel();

        plusButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myPanel.setSize(myPanel.getWidth() + 10, myPanel.getHeight() + 10);
                image.repaint();
                image.revalidate();
            }
        });

        minusButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myPanel.setSize(myPanel.getWidth() - 10, myPanel.getHeight() - 10);
                image.repaint();
                image.revalidate();
            }
        });

        cancelButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(-1);
            }
        });

        JPanel bottomPanel = new JPanel(new GridBagLayout());
        bottomPanel.add(saveButton, new GridBagConstraints(0, 0, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        bottomPanel.add(cancelButton, new GridBagConstraints(1, 0, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        bottomPanel.add(logo, new GridBagConstraints(2, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 7, 2, 7), 0, 0));

        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.add(plusButton, new GridBagConstraints(0, 0, 1, 1, 0, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        rightPanel.add(minusButton, new GridBagConstraints(0, 1, 1, 1, 0, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));

        System.out.println(point);

        image.setLayout(null);
        image.add(myPanel);


        this.add(image, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        this.add(rightPanel, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        this.add(bottomPanel, new GridBagConstraints(0, 1, 2, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));

        this.setSize((int) ((int) d.getWidth() + bottomPanel.getPreferredSize().getHeight()), (int) ((int) d.getHeight() + rightPanel.getPreferredSize().getWidth()));

        setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new AvatarEditor();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
