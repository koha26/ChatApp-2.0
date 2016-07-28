package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.*;

public class TestFileChooser extends JFrame {
    JFrame thisFrame;

    private static String getFileExtension(File file) {
        String fileName = file.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        else return "";
    }

    public TestFileChooser() {
        thisFrame = this;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setUndecorated(true);
        setLayout(new GridBagLayout());
        setBackground(Color.BLACK);

        JButton button = new JButton("Open...");
        button.setBackground(Color.BLACK);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setForeground(Color.WHITE);
        button.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.BLACK);
        panel.add(button, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));

        add(panel, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));

        final JFileChooser fileopen = new JFileChooser();

        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileopen.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileopen.setCurrentDirectory(new File("/"));
                fileopen.showOpenDialog(null);
                fileopen.setMultiSelectionEnabled(false);
                if (!(fileopen.getSelectedFile() == null)) {
                    final File file = fileopen.getSelectedFile();
                    if (getFileExtension(file).toLowerCase().equals("jpg")) {
                        ImageIcon img = new ImageIcon(file.getAbsolutePath());
                        if (img.getIconHeight() >= 250 || img.getIconWidth() >= 250) {
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        new AvatarEditor(file);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        } else {
                            JOptionPane.showMessageDialog(null, "Very small picture. Choose 250x250px or bigger!");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Choose JPEG File please!");
                    }
                }
            }
        });

        setSize(new Dimension(100, 100));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TestFileChooser();
            }
        });
    }
}