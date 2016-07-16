package gui;

import logic.Constants;
import logic.RegistrationModel;
import logic.User;
import logic.command.LoginStatusCommand;
import logic.command.RegistrationStatusCommand;
import server.Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;


public class MainForm extends JFrame implements Observer {

    private Client client;
    private StartForm startForm;
    private User user;
    // CHAT FORM HERE

    public MainForm() {
        startForm = new StartForm();

        try {
            client = new Client(Constants.HOST, Constants.PORT);
            client.start();

            //client = new Client("localhost", 8621);
        } catch (IOException e) {
            //TODO
        }
        client.addObserver(this);


        startForm.getLoginButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (startForm.isFieldFilled()) {
                    RegistrationModel regModel = startForm.getRegistrationModel();
                    if (regModel != null) {
                        client.sendLoginCommand(regModel.getNick(), regModel.getPassword());
                    }
                } else {
                    showInfoMessage("Заполните главные поля!");
                }
            }
        });

        startForm.getRegRegistrationButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (startForm.isFieldFilled()) {
                    RegistrationModel regModel = startForm.getRegistrationModel();
                    if (regModel != null) {
                        client.sendRegistrationCommand(regModel);
                    }
                } else {
                    showInfoMessage("Заполните главные поля!");
                }
            }
        });

    }

    public void showInfoMessage(String text) {
        JOptionPane.showMessageDialog(startForm, text);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof LoginStatusCommand) {

            LoginStatusCommand lsCommand = (LoginStatusCommand) arg;
            if (lsCommand.getUser() != null) {
                user = lsCommand.getUser();
                showInfoMessage("Вы выполнили вход! Ваш ид " + user.getUniqueID());
            } else {
                showInfoMessage(lsCommand.getExceptionDescription());
            }

        } else if (arg instanceof RegistrationStatusCommand) {

            RegistrationStatusCommand rsCommand = (RegistrationStatusCommand) arg;
            if (rsCommand.isRegistered() && rsCommand.getUser() != null) {
                user = rsCommand.getUser();
                showInfoMessage("Вы выполнили вход! Ваш ид " + user.getUniqueID());
            } else {
                showInfoMessage(rsCommand.getExceptionDescription());
            }
        }

    }

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainForm().setVisible(true);
            }
        });
    }
}


