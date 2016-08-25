package main.server;

import gui.*;
import javafx.scene.layout.Border;
import server.Server;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.StreamHandler;

public class ServerApplication extends JFrame implements Observer {
    private Server server;
    private JScrollPane scrollPane;
    private JTextArea textArea;
    private JButton startServerBut;
    private JButton stopServerBut;
    private JButton restartServerBut;

    private final String NEW_LINE = "\n";
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    public ServerApplication() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dim = toolkit.getScreenSize();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(dim.width / 2, dim.height / 2);
        setTitle("ChatApp 2.0 Server");
        setContentPane(new JLabel(Images.backgroundImage));
        setResizable(false);
        setIconImage(Images.appIcon.getImage());

        setLayout(null);

        textArea = new JTextArea();
        textArea.setBackground(new Color(0, 0, 0, 255));
        textArea.setFont(Fonts.typingFont);
        textArea.setForeground(Color.WHITE);
        scrollPane = new JScrollPane(textArea);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUI(new ChatAppVerticalScrollBarUI());
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getHorizontalScrollBar().setUI(new ChatAppHorizontalScrollBarUI());
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(50, 50, 580, 230);

        startServerBut = new JButton("Start");
        startServerBut.setFont(Fonts.typingFont);
        startServerBut.setBounds(150, 300, 100, 30);
        startServerBut.setBorder(null);

        stopServerBut = new JButton("Stop");
        stopServerBut.setFont(Fonts.typingFont);
        stopServerBut.setBounds(290, 300, 100, 30);
        stopServerBut.setBorder(null);
        stopServerBut.setEnabled(false);

        restartServerBut = new JButton("Restart");
        restartServerBut.setFont(Fonts.typingFont);
        restartServerBut.setBounds(440, 300, 100, 30);
        restartServerBut.setBorder(null);
        restartServerBut.setEnabled(false);

        add(scrollPane);
        add(startServerBut);
        add(stopServerBut);
        add(restartServerBut);
        /*this.server = new Server(8621);
        this.server.start();*/
        /*RepaintFrame repaintFrame = new RepaintFrame(this);
        Thread thread = new Thread(repaintFrame);
        thread.start();*/

        startServerBut.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                startServerBut.setEnabled(false);

                server = new Server(8621);
                server.addObserver(ServerApplication.this);
                server.start();

                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }

                stopServerBut.setEnabled(true);
                restartServerBut.setEnabled(true);

                textArea.append("CHATAPP@ADMIN = SERVER IS RUNNING!");
                textArea.append(NEW_LINE);
            }
        });

        stopServerBut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                stopServerBut.setEnabled(false);
                restartServerBut.setEnabled(false);

                server.interrupt();

                startServerBut.setEnabled(true);

                textArea.append("CHATAPP@ADMIN = SERVER WAS STOPPED!");
                textArea.append(NEW_LINE);
            }
        });

        restartServerBut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                startServerBut.setEnabled(false);
                stopServerBut.setEnabled(false);
                restartServerBut.setEnabled(false);
                textArea.append("CHATAPP@ADMIN = SERVER IS RERUNNING...");
                textArea.append(NEW_LINE);

                server.interrupt();
                textArea.append("CHATAPP@ADMIN == SERVER WAS STOPPED!");
                textArea.append(NEW_LINE);

                server = new Server(8621);
                server.addObserver(ServerApplication.this);
                server.start();

                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }

                startServerBut.setEnabled(false);
                stopServerBut.setEnabled(true);
                restartServerBut.setEnabled(true);

                textArea.append("CHATAPP@ADMIN == SERVER IS RUNNING!");
                textArea.append(NEW_LINE);
                textArea.append("CHATAPP@ADMIN = SERVER WAS RERUNNING!");
                textArea.append(NEW_LINE);
            }
        });
    }

    public static void main(String[] args) {
        ServerApplication serverApplication = new ServerApplication();
        serverApplication.setVisible(true);
    }

    @Override
    public void update(Observable o, Object arg) {
        final String text = (String) arg;
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

                textArea.append(text);
                textArea.append(NEW_LINE);
            }
        });
    }
}
