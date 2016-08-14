package logic.command;

import logic.PotentialFriend;

import java.util.List;

public class SearchStatusCommand extends Command {
    private List<PotentialFriend> resultList;

    public SearchStatusCommand(List<PotentialFriend> resultList){
        this.resultList = resultList;
    }

    public List<PotentialFriend> getResultList() {
        return resultList;
    }

    public void setResultList(List<PotentialFriend> resultList) {
        this.resultList = resultList;
    }
}
