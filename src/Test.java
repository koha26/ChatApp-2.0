import logic.Message;
import logic.command.HistoryPacketCommand;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by demo on 17.07.16.
 */
public class Test {
    public static void main(String[] args) throws java.net.UnknownHostException {
        /*String patternDATE = "([0-2]\\d|3[01])\\.(0\\d|1[012])\\.(\\d{4})(([0,1][0-9])|(2[0-3])):[0-5][0-9]";

        String input = "<ID>1</ID><TEXT>askdnas</TEXT><DATE>24.05.2016</DATE>\n<ID>2</ID><TEXT>asasfsdfwdnas</TEXT><DATE>21.05.2016</DATE>";


        String REGEX_1 = "(?<=<TEXT>)(.*?)(?=(</TEXT>))";//"(<TEXT>).*(</TEXT>)";
        String REGEX_2 = "(?<=<DATE>)(.*?)(?=(</DATE>))";
        String REGEX_3 = "(?<=(<[A-Z]{1,4}>))(.*?)(?=(</[A-Z]{1,4}>))";

        Matcher matcher1 = Pattern.compile(REGEX_1).matcher(input);
        Matcher matcher2 = Pattern.compile(REGEX_2).matcher(input);
        Matcher matcher3 = Pattern.compile(REGEX_3).matcher(input);

        while (matcher1.find() && matcher2.find()){
            System.out.println(matcher1.group()+" "+matcher2.group());
        }
        System.out.println();
        System.out.println(matcher3.matches());
        while (matcher3.find()){
            System.out.println(matcher3.group());
        }*/

        Set<String> setNicks = new HashSet<>();
        setNicks.add("koha");
        setNicks.add("mrglton");
        setNicks.add("bobmorcos");
        setNicks.add("lingard");

        List<Stack<HistoryPacketCommand>> histories = Collections.synchronizedList(new ArrayList<Stack<HistoryPacketCommand>>());
        if (setNicks!= null) {

            for (String nicknameFriend : setNicks) {
                ArrayList<Message> messageArrayList = new ArrayList<>();
                Message mes = new Message();
                mes.setMessageText(nicknameFriend+1);
                messageArrayList.add(mes);

                mes = new Message();
                mes.setMessageText(nicknameFriend+2);
                messageArrayList.add(mes);

                mes = new Message();
                mes.setMessageText(nicknameFriend+3);
                messageArrayList.add(mes);

                HistoryPacketCommand packet =new HistoryPacketCommand();
                packet.setHistoryPart(messageArrayList);
                packet.setUnreadHas(true);

                Stack<HistoryPacketCommand> historyStack = new Stack<>();
                historyStack.push(packet);

                messageArrayList = new ArrayList<>();
                mes = new Message();
                mes.setMessageText(nicknameFriend+4);
                messageArrayList.add(mes);

                mes = new Message();
                mes.setMessageText(nicknameFriend+5);
                messageArrayList.add(mes);

                mes = new Message();
                mes.setMessageText(nicknameFriend+6);
                messageArrayList.add(mes);

                packet =new HistoryPacketCommand();
                packet.setHistoryPart(messageArrayList);
                packet.setUnreadHas(true);

                historyStack.push(packet);

                histories.add(historyStack);
            }
        }



        for (Stack<HistoryPacketCommand> historyPacketCommands : histories) {
            Stack<HistoryPacketCommand> tmp = new Stack<>();
            while (!historyPacketCommands.empty() && historyPacketCommands.peek().isUnreadHas() ){
                //send(historyPacketCommands.pop());
                HistoryPacketCommand packet = historyPacketCommands.pop();
                tmp.push(packet);
                System.out.println(packet.getHistoryPart().get(0).getMessageText());


            }

            final Stack<HistoryPacketCommand> toWritting = tmp;
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    while (!toWritting.empty()) {
                        HistoryPacketCommand packetCommand = toWritting.pop();
                        for (Message message : packetCommand.getHistoryPart()) {
                            //saveMessageInReceiverHistory(message, packetCommand.getNickname_host(), packetCommand.getNickname_companion(), true);
                            System.out.println("saving..."+message.getMessageText());
                        }
                    }

                }
            });
        }

    }

}
