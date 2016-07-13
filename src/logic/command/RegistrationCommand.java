package logic.command;

import logic.RegistrationModel;

public class RegistrationCommand extends Command {

    private RegistrationModel regModel;

    public RegistrationCommand(String nick, String password) {
        regModel = new RegistrationModel(nick, password);
    }

    public RegistrationCommand(RegistrationModel regModel) {
        this.regModel = regModel;
    }

    public void setRegModel(RegistrationModel regModel) {
        this.regModel = regModel;
    }

    public RegistrationModel getRegModel() {
        return regModel;
    }
}
