package main.server;

import gui.*;
import server.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class ServerApplication extends JFrame implements Observer{
    private Server server;
    private Thread serverThread;
    private JScrollPane scrollPane;
    private JTextArea textArea;
    private JButton startServerBut;
    private JButton stopServerBut;
    private JButton restartServerBut;

    public ServerApplication(){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dim = toolkit.getScreenSize();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(dim.width/2, dim.height/2);
        setTitle("ChatApp 2.0 Server");
        setContentPane(new JLabel(Images.backgroundImage));
        setResizable(false);
        setIconImage(Images.appIcon.getImage());

        setLayout(null);

        textArea = new JTextArea();
        textArea.setBackground(new Color(0,0,0,150));
        textArea.setFont(Fonts.typingFont);
        textArea.setForeground(Color.WHITE);
        scrollPane = new JScrollPane(textArea);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUI(new ChatAppVerticalScrollBarUI());
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(50, 50, 580, 230);

        startServerBut = new JButton("Start");
        startServerBut.setFont(Fonts.typingFont);
        startServerBut.setOpaque(false);
        startServerBut.setBounds(150,300,100,30);

        stopServerBut = new JButton("Stop");
        stopServerBut.setFont(Fonts.typingFont);
        stopServerBut.setOpaque(false);
        stopServerBut.setBounds(290,300,100,30);

        restartServerBut = new JButton("Restart");
        restartServerBut.setFont(Fonts.typingFont);
        restartServerBut.setOpaque(false);
        restartServerBut.setBounds(440,300,100,30);

        add(scrollPane);
        add(startServerBut);
        add(stopServerBut);
        add(restartServerBut);
        //this.server = new Server(8621);
        //this.server.run();
        RepaintFrame repaintFrame = new RepaintFrame(this);
        Thread thread = new Thread(repaintFrame);
        thread.start();


        startServerBut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                startServerBut.setEnabled(false);
                server = new Server(8621);
                serverThread = new Thread(server);
                serverThread.start();

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }

                stopServerBut.setEnabled(true);
                restartServerBut.setEnabled(true);

                textArea.append("Server is running\n");
            }
        });

        stopServerBut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                stopServerBut.setEnabled(false);
                restartServerBut.setEnabled(false);

                try {
                    server.stop();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                serverThread.interrupt();

                startServerBut.setEnabled(true);

                textArea.append("Server was stopped\n");
            }
        });

        restartServerBut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                startServerBut.setEnabled(false);
                stopServerBut.setEnabled(false);
                restartServerBut.setEnabled(false);

                try {
                    server.stop();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                serverThread.interrupt();
                textArea.append("*Server was stopped\n");

                server = new Server(8621);
                serverThread = new Thread(server);
                serverThread.start();

                startServerBut.setEnabled(false);
                stopServerBut.setEnabled(true);
                restartServerBut.setEnabled(true);

                textArea.append("*Server is running\n");
                textArea.append("Server was rerunning\n");

            }
        });
    }

    public static void main(String[] args) {
        ServerApplication serverApplication = new ServerApplication();
        serverApplication.setVisible(true);
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
