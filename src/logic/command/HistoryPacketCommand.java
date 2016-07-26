package logic.command;

import logic.Message;

import java.util.List;

public class HistoryPacketCommand extends Command {
    private List<Message> historyPart;
    private String nickname_host;
    private String nickname_companion;
    private boolean unreadHas;

    public HistoryPacketCommand(){
    }

    public String getNickname_host() {
        return nickname_host;
    }

    public void setNickname_host(String nickname_host) {
        this.nickname_host = nickname_host;
    }

    public String getNickname_companion() {
        return nickname_companion;
    }

    public void setNickname_companion(String nickname_companion) {
        this.nickname_companion = nickname_companion;
    }

    public void setHistoryPart(List<Message> historyPart) {
        this.historyPart = historyPart;
    }

    public List<Message> getHistoryPart() {
        return historyPart;
    }

    public boolean isUnreadHas() {
        return unreadHas;
    }

    public void setUnreadHas(boolean unreadHas) {
        this.unreadHas = unreadHas;
    }
}
