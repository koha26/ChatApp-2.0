package logic.command;

import logic.RegistrationModel;
import logic.User;

/**
 * Created by demo on 06.07.16.
 */
public class RegistrationCommand extends Command {

    private RegistrationModel regModel;

    public RegistrationCommand(String nick, String password) {
        regModel = new RegistrationModel(nick, password);
    }
}
