package server;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import logic.Message;
import logic.PotentialFriend;
import logic.RegistrationModel;
import logic.User;
import logic.command.Command;
import logic.command.HistoryPacketCommand;

import javax.imageio.ImageIO;
import java.io.*;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Database {

    private Map<String, User> userMap; // все пользователи
    private Map<String, Connection> userOnline; // пользователи, которые онлайн

    private File dataPath;
    private File clientsDataFile;

    public Database() {
        this.userMap = Collections.synchronizedMap(new HashMap<String, User>());
        this.userOnline = Collections.synchronizedMap(new HashMap<String, Connection>());
        this.dataPath = new File(Config.DATA_PATH);
        this.clientsDataFile = new File(dataPath + "/" + Config.CLIENTS_DATA_FILENAME);

        loadData();
    }

    public void goOnline(String nickname, Connection connection) {
        if (connection.isOpen()) {
            this.userOnline.put(nickname, connection);
        }
    }

    public void goOffline(String nickname) {
        this.userOnline.remove(nickname);
    }

    public User checkUser(String nickname, String password) {
        for (User u : userMap.values()) {
            if (u != null && u.getNickname().equals(nickname) && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }

    public boolean isExist(String nickname) {
        return userMap.containsKey(nickname);
    }

    public boolean isOnline(String nickname) {
        if (userOnline.get(nickname) != null) {
            return true;
        } else
            return false;
    }

    public void deleteUser(String nickname) {
        this.userMap.remove(nickname);
        this.userOnline.remove(nickname);
    }

    public User updateUser(String nickname) {
        return new User(userMap.get(nickname));
    }

    public User registerUser(RegistrationModel regModel, Connection connection) throws UnknownHostException {
        if (isNickAvailable(regModel.getNick())) { // FIXME by Koha: 10.07.2016
            int uniqueID = getLastUserId() + 1;
            Config.changeID(uniqueID);
            User newUser = new User(regModel, connection.getSocket().getInetAddress(), uniqueID);
            userMap.put(regModel.getNick(), newUser);

            createDirectoryForRegisteredUser(newUser);
            updateData();

            return newUser;
        } else
            return null;
    }

    public User modificationUser(User changedUser, boolean isInfoChanged, boolean isAvatarChanged) {
        if (changedUser != null && userMap.containsKey(changedUser.getNickname())) {
            if (isAvatarChanged) {
                File avatarDir = new File(dataPath + "/user_" + changedUser.getUniqueID() + "/avatar");
                File avatarFile = new File(avatarDir.getPath() + "/avatar.jpg");
                if (avatarFile.exists() && avatarFile.isFile()) {
                    File[] list = avatarDir.listFiles(new FilenameFilter() {
                        @Override
                        public boolean accept(File dir, String name) {
                            return name.endsWith(".jpg");
                        }
                    });
                    avatarFile.renameTo(new File(avatarDir.getPath() + "/avatar_" + list.length + ".jpg"));
                }
                int tries = 0;

                while (tries < 20) {
                    try {
                        if (ImageIO.write(changedUser.getAvatarAsBufImage(), "jpg", avatarFile)) {
                            break;
                        } else {
                            tries++;
                        }
                    } catch (IOException e) {
                        tries++;
                    }
                }

            }

            if (isInfoChanged) {
                userMap.put(changedUser.getNickname(), changedUser);

                updateData();
                return userMap.get(changedUser.getNickname());
            } else {

                return userMap.get(changedUser.getNickname());
            }
        } else {
            return null;
        }
    }

    public boolean isNickAvailable(String nickname) {
        for (String nick : userMap.keySet()) {
            if (nick.toLowerCase().equals(nickname.toLowerCase())) {
                return false;
            }
        }
        return true;
        //return !userMap.containsKey(nickname);
    }

    public int getLastUserId() {
        /*if (userMap.isEmpty()) {
            return 0;
        } else {
            int lastID = userMap.size();
            return lastID;
        }*/
        return Config.UNIQUE_ID;
    }

    public User getUserById(int uniqueID) {
        for (Map.Entry entry : userMap.entrySet()) {
            if (((User) entry.getValue()).getUniqueID() == uniqueID) {
                return (User) entry.getValue();
            }
        }
        return null;
    }

    public ArrayList<User> getUsersOnline() {
        return (ArrayList<User>) userMap.values();
    }

    public void addFriend(String nickname_host, String nickname_friend) {
        User editedUser_host = userMap.get(nickname_host);
        User editedUser_friend = userMap.get(nickname_friend);

        editedUser_host.addFriend(editedUser_friend.toFriendObject());
        userMap.put(nickname_host, editedUser_host);

        editedUser_friend.addFriend(editedUser_host.toFriendObject());
        userMap.put(nickname_friend, editedUser_friend);

        updateData();
    }

    public boolean deleteFriend(String nickname_host, String nickname_friend) {
        User editedUser_host = userMap.get(nickname_host);
        User editedUser_friend = userMap.get(nickname_friend);

        boolean isHostFriend = editedUser_host.deleteFriend(nickname_friend);
        boolean isFriendFriend = editedUser_friend.deleteFriend(nickname_host);

        if (isHostFriend && isFriendFriend) {
            userMap.put(nickname_host, editedUser_host);
            userMap.put(nickname_friend, editedUser_friend);
            updateData();
            return true;
        } else
            return false;
    }

    public Set<String> retainAllFriendOnline(Set<String> comparatedSet) { // возврат друзей онлайн
        comparatedSet.retainAll(this.userOnline.keySet());
        return comparatedSet;
    }

    public List<PotentialFriend> getResultByGlobalSearch(User host, String nicknamePattern) {
        ArrayList<PotentialFriend> list = new ArrayList<>();
        //Pattern pattern = Pattern.compile(".*"+nicknamePattern+".*");
        Pattern pattern = Pattern.compile(nicknamePattern);
        Matcher matcher;
        for (User user : userMap.values()) {
            matcher = pattern.matcher(user.getNickname().toLowerCase());
            if (matcher.find() && !host.hasFriend(user.getNickname()) && !host.getNickname().equals(user.getNickname())) {
                list.add(new PotentialFriend(user));
            }
        }
        return list;
    }

    // ДЛЯ РАБОТЫ С ДИРИКТОРИЯМИ

    public void loadData() { //загрузка с файла в мэп

        if (checkDataPath() && checkClientsDataFile() && clientsDataFile.isFile() && clientsDataFile.length() > 0) { //если файл не пустой

            XStream xmlStream = new XStream(new DomDriver()); //поток для управлением сериалации/десереализации объектов в .xml
            xmlStream.alias("user", User.class);

            try {
                FileInputStream inputStream = new FileInputStream(clientsDataFile);

                userMap = (Map<String, User>) xmlStream.fromXML(inputStream);
            } catch (FileNotFoundException e) {
                // ex
            }

            File avatarFile;
            File defaultImageFile = new File("images/avatar/default_avatarBIG.jpg");

            for (User u : userMap.values()) {
                avatarFile = new File(dataPath + "/user_" + u.getUniqueID() + "/avatar/avatar.jpg");
                if (avatarFile.exists() && avatarFile.isFile()) {
                    try {
                        u.setAvatar(ImageIO.read(avatarFile));
                    } catch (IOException e) {
                        //u.setAvatar(new ImageSerializable(null));
                    }
                } else {
                    try {
                        u.setAvatar(ImageIO.read(defaultImageFile));
                    } catch (IOException e) {
                        //u.setAvatar(new ImageSerializable(null));
                    }
                }
            }
        }

    }

    public void updateData() {
        if (clientsDataFile.setWritable(true)) { // установить возможность редактирования

            if (checkDataPath() && checkClientsDataFile() && clientsDataFile.isFile()) {
                XStream xmlStream = new XStream(new DomDriver());
                xmlStream.alias("user", User.class); // класс User будет в тегах писать как user, а не logic.User
                xmlStream.omitField(User.class, "avatar");

                try {
                    FileOutputStream outputStream = new FileOutputStream(clientsDataFile);

                    xmlStream.toXML(userMap, outputStream);
                } catch (FileNotFoundException e) {
                    // ex
                }
            }
            clientsDataFile.setReadOnly(); // установить возможность - только для чтения

        }

    }

    public boolean checkDataPath() {
        if (!dataPath.exists()) {
            while (true) {
                if (dataPath.mkdirs()) {
                    return true;
                }
            }
        }
        return true;
    }

    public boolean checkClientsDataFile() {
        if (!clientsDataFile.exists()) {
            while (true) {
                try {
                    if (clientsDataFile.createNewFile()) {
                        return true;
                    }
                } catch (IOException e) {
                    return false;
                }
            }
        }
        return true;
    }

    public void createDirectoryForRegisteredUser(User u) {
        final String dirName = "user_" + u.getUniqueID();

        FilenameFilter filter = new FilenameFilter() { //фильтр поиска папки с даным названием
            @Override
            public boolean accept(File dir, String name) {
                File dirNameCompared = new File(dir + "/" + name);
                if (dir.isDirectory() && name.equals(dirName) && dirNameCompared.isDirectory()) {
                    return true;
                } else
                    return false;
            }
        };

        String[] files = dataPath.list(filter);

        if (files.length == 0) { // если не нашло такой папки, то создаем ее
            File newHistoryDir = new File(dataPath + "/" + dirName + "/history");
            File newAvatarDir = new File(dataPath + "/" + dirName + "/avatar");
            File newAwaitingCommandDir = new File(dataPath + "/" + dirName + "/awaiting_command");

            int count = 0;
            while (count < 10) {
                if (newHistoryDir.mkdirs()) {
                    break;
                }
                count++;
            }
            count = 0;
            while (count < 10) {
                if (newAvatarDir.mkdirs()) {
                    break;
                }
                count++;
            }
            count = 0;
            while (count < 10) {
                if (newAwaitingCommandDir.mkdirs()) {
                    break;
                }
                count++;
            }
        }

    }

    public Set<String> getSetOfFriendUnreadMes(String nickname_host) { // возврат друзей от которых несколько непрочитанных
        File file = new File(dataPath + "/user_" + userMap.get(nickname_host).getUniqueID() + "/unread_messages_from.dat");
        Set<String> unreadMesFrom = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            if (file.exists() && file.isFile()) {
                String nickFriend = "";
                while ((nickFriend = reader.readLine()) != null) {
                    if (isExist(nickFriend))
                        unreadMesFrom.add(nickFriend);
                }
            }
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
        return unreadMesFrom;
    }

    public boolean addFriendAsUnreadMesFrom(String nickname_host, String nickname_friend) { //добавление ника друга, от которого пропущено смс
        Set<String> unreadMesFrom = getSetOfFriendUnreadMes(nickname_host);
        if (unreadMesFrom == null || unreadMesFrom.size() == 0 || !unreadMesFrom.contains(nickname_friend)) {
            String dirPath = dataPath + "/user_" + userMap.get(nickname_host).getUniqueID();
            String filePath = dirPath + "/unread_messages_from.dat";
            File dir = new File(dirPath);
            File file = new File(filePath);

            if (!dir.isDirectory() && !dir.exists()) {
                int count = 0;
                while (count < 10) {
                    if (dir.mkdirs()) {
                        break;
                    }
                    count++;
                }
            }
            if (!file.isFile() && !file.exists()) {
                int count = 0;
                while (count < 10) {
                    try {
                        if (file.createNewFile()) {
                            break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    count++;
                }
            }
            try {
                FileWriter fw = new FileWriter(file, true);
                fw.append(nickname_friend + "\n");
                fw.flush();
                fw.close();
                return true;
            } catch (IOException e) {
                System.out.println("Не получилось записать в файл \"" + file.getName() + "\" нового непрочитанного юзера!");
                return false;
            }
        } else
            return true;
    }

    public boolean deleteAndCreateUnreadMesFile(String nickname_host) {
        File file = new File(dataPath + "/user_" + userMap.get(nickname_host).getUniqueID() + "/unread_messages_from.dat");
        if (file.delete()) {
            try {
                int count = 0;
                while (count < 10) {
                    if (file.createNewFile()) {
                        break;
                    }
                    count++;
                }
            } catch (IOException e) {
                System.out.println("Неудалось создать файл \"" + file.getName() + "\"");
                return false;
            }
            return true;
        } else {
            System.out.println("Неудалось удалить файл \"" + file.getName() + "\"");
            return false;
        }
    }

    public synchronized void saveMessageInSenderHistory(Message message, String nickname_sender, String nickname_receiver) throws IOException {
        User user_To = userMap.get(nickname_receiver);                                                //КОМУ - тот, кому отослали
        User user_From = userMap.get(nickname_sender);                                              //ОТ - тот, кто отослал

        String history_dirPath = Config.DATA_PATH + "/user_" + user_From.getUniqueID() + "/history/user_" + user_To.getUniqueID();
        String history_filePath = history_dirPath + "/messages.dat";
        File historyDir_From = new File(history_dirPath);
        File historyFile_From = new File(history_filePath);//папка истории переписки ОТ с КОМУ

        if (!historyDir_From.isDirectory() && !historyDir_From.exists()) {
            int count = 0;
            while (count < 10) {
                if (historyDir_From.mkdirs()) {
                    break;
                }
                count++;
            }
        }
        if (!historyFile_From.isFile() && !historyFile_From.exists()) {
            int count = 0;
            while (count < 10) {
                if (historyFile_From.createNewFile()) {
                    break;
                }
                count++;
            }
        }

        saveMessageInHistory(historyFile_From, message);
    }

    public synchronized void saveMessageInReceiverHistory(Message message, String nickname_sender, String nickname_receiver, boolean isRead) throws IOException {
        User user_To = userMap.get(nickname_receiver);                                                //КОМУ - тот, кому отослали
        User user_From = userMap.get(nickname_sender);                                           //ОТ - тот, кто отослал

        String history_dirPath = Config.DATA_PATH + "/user_" + user_To.getUniqueID() + "/history/user_" + user_From.getUniqueID();
        String history_filePath = history_dirPath + "/messages.dat";
        String unreadMessages_dirPath = Config.DATA_PATH + "/user_" + user_To.getUniqueID() + "/history/user_" + user_From.getUniqueID();
        String unreadMessages_filePath = unreadMessages_dirPath + "/unread.dat";

        if (isRead) { // если прочтено, то в историю смс записываем
            File dir_To = new File(history_dirPath);
            File file_To = new File(history_filePath);//папка истории переписки КОМУ с ОТ

            if (!dir_To.isDirectory() && !dir_To.exists()) {
                int count = 0;
                while (count < 10) {
                    if (dir_To.mkdirs()) {
                        break;
                    }
                    count++;
                }
            }

            if (!file_To.isFile() && !file_To.exists()) {
                int count = 0;
                while (count < 10) {
                    if (file_To.createNewFile()) {
                        break;
                    }
                    count++;
                }
            }
            saveMessageInHistory(file_To, message);
        } else { //если не прочтено, то в пропущенные
            File dir_To = new File(unreadMessages_dirPath);
            File file_To = new File(unreadMessages_filePath);

            if (!dir_To.isDirectory() && !dir_To.exists()) {
                int count = 0;
                while (count < 10) {
                    if (dir_To.mkdirs()) {
                        break;
                    }
                    count++;
                }
            }

            if (!file_To.isFile() && !file_To.exists()) {
                int count = 0;
                while (count < 10) {
                    if (file_To.createNewFile()) {
                        break;
                    }
                    count++;
                }
            }
            saveMessageInHistory(file_To, message);
            addFriendAsUnreadMesFrom(nickname_receiver, nickname_sender);
        }
    }

    public synchronized void saveMessageInHistory(File file, Message message) throws IOException {
        /*User user_To = userMap.get(message.getNickname_To());                                                //ОТ - тот, кто отослал
        User user_From = userMap.get(message.getNickname_From());                                            //КОМУ - тот, кому отослали

        String dirPath_From = Config.DATA_PATH + "/user_" + user_From.getUniqueID() + "/history/user_" + user_To.getUniqueID() + "/messages.dat";
        String dirPath_To = Config.DATA_PATH + "/user_" + user_To.getUniqueID() + "/history/user_" + user_From.getUniqueID() + "/messages.dat";
        File file_From = new File(dirPath_From);//папка истории переписки ОТ с КОМУ
        File file_To = new File(dirPath_To);
        if (!file_From.isFile() && !file_From.exists()) {
            file_From.createNewFile();
        }
        if (!file_To.isFile() && !file_To.exists()) {
            file_To.createNewFile();
        }*/

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("<NICK>");
        stringBuilder.append(message.getNickname_From());
        stringBuilder.append("</NICK>");

        stringBuilder.append("<TEXT>");
        stringBuilder.append(message.getMessageText());
        stringBuilder.append("</TEXT>");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+00"));
        String dateText = dateFormat.format(message.getDate());
        String[] arg = dateText.split(" ");

        stringBuilder.append("<DATE>");
        stringBuilder.append(arg[0]);
        stringBuilder.append("</DATE>");

        stringBuilder.append("<TIME>");
        stringBuilder.append(arg[1]);
        stringBuilder.append("</TIME>");

        stringBuilder.append("\n");

        //PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream("test.txt", true), "UTF-8"));
        FileWriter writer = new FileWriter(file, true);
        writer.append(stringBuilder.toString());
        writer.flush();
        writer.close();
    }

    public synchronized Stack<HistoryPacketCommand> loadHistory(String nickname_asker, String nickname_companion) throws IOException {
        //id_asker - тот, кто запрашивает историю / id_companion - тот, с кем он общался
        Stack<HistoryPacketCommand> history = new Stack<>(); //стэк со всей историей
        ArrayList<Message> messageList; //определенное к-во истории
        HistoryPacketCommand packet = new HistoryPacketCommand();

        User user_asker = userMap.get(nickname_asker);
            User user_companion = userMap.get(nickname_companion);

            if (user_asker != null && user_companion != null) {

                String history_filePath = Config.DATA_PATH + "/user_" + user_asker.getUniqueID() + "/history/user_" + user_companion.getUniqueID() + "/messages.dat";//or unread_messagges.dat
                File historyFile = new File(history_filePath);
                String unreadMessages_filePath = Config.DATA_PATH + "/user_" + user_asker.getUniqueID() + "/history/user_" + user_companion.getUniqueID() + "/unread.dat";
                File unreadMessagesFile = new File(unreadMessages_filePath);

            int numFiles = 1;

            if (unreadMessagesFile.isFile() && unreadMessagesFile.exists() || unreadMessagesFile.length() != 0) {
                numFiles = 2; //если есть файл с пропущенными, то читаем его
            }

            Pattern pattern = Pattern.compile(Config.REGEX_HISTORY); // мой паттерн по которому я разберу каждое смс
            Matcher matcher;
            BufferedReader reader;
            messageList = new ArrayList<>();
            Message message = new Message(); // модель смс, которое я заполню и впихну в массив
            String inputString;

            for (int i = 0; i < numFiles; i++) { //0 - history; 1 - unread messages
                if (i == 0) {
                    if (!(historyFile.isFile() && historyFile.exists()) || historyFile.length() == 0) {
                        continue;
                    } else {
                        reader = new BufferedReader(new FileReader(historyFile));
                    }
                } else {
                    reader = new BufferedReader(new FileReader(unreadMessagesFile));
                }

                while ((inputString = reader.readLine()) != null) {
                    matcher = pattern.matcher(inputString);
                    int tagNumber = 1; //1 - <NICK>; 2 - <TEXT>; 3 - <DATE> & <TIME>

                    String date = "2016.01.01";
                    String time = "00:00:01";
                    String[] datePart = date.split("\\.");
                    String[] timePart = time.split(":");

                    while (matcher.find()) { //проверка есть ли во входящей строке такая строка == моему патерну

                        switch (tagNumber) {
                            case 1:
                                message = new Message();
                                String nickname = matcher.group();
                                if (nickname.equals(user_asker.getNickname())) { //в истории записывается ник того, чье смс, тоесть ОТ кого
                                    message.setNickname_From(nickname);
                                    message.setNickname_To(user_companion.getNickname());
                                } else if (nickname.equals(user_companion.getNickname())) {
                                    message.setNickname_From(nickname);
                                    message.setNickname_To(user_asker.getNickname());
                                }
                                tagNumber = 2;
                                break;
                            case 2:
                                message.setMessageText(matcher.group()); //текст
                                tagNumber = 3;
                                break;
                            case 3:
                                date = matcher.group();
                                datePart = date.split("\\."); //0 - year; 1 - month; 2 - day;
                                tagNumber = 4;
                                break;
                            case 4:
                                time = matcher.group();
                                timePart = time.split(":"); //0 - hour, 1 - min, 2 - sec;

                                GregorianCalendar calendar = new GregorianCalendar(); //буду всоввывать в конструктор по формату yyyy.MM.dd Hh:mm:ss
                                if (datePart.length == 3 && timePart.length == 3) {
                                    calendar = new GregorianCalendar(Integer.parseInt(datePart[0]), Integer.parseInt(datePart[1]) - 1,
                                            Integer.parseInt(datePart[2]), Integer.parseInt(timePart[0]), Integer.parseInt(timePart[1]),
                                            Integer.parseInt(datePart[2]));
                                    message.setDate(calendar.getTime());
                                } else {
                                    message.setDate(new Date());
                                }



                                tagNumber = 1;

                                break;
                        }
                    } // закончился разбор строки

                    messageList.add(message); // добавил смс в массив

                    if (messageList.size() == Config.BUFFER_HISTORY_SIZE) { // если массив достиг максимального значения по вместимости,
                        packet.setNickname_host(nickname_asker);
                        packet.setNickname_companion(nickname_companion);
                        packet.setHistoryPart(messageList);
                        packet.setUnreadHas(i == 1 ? true : false);
                        history.add(packet); // то готвим его как пакет
                        messageList = new ArrayList<>();
                        packet = new HistoryPacketCommand();
                    }
                }

                reader.close();

                if (messageList.size() > 0) { // если не собрался пакет последний и закончились строки
                    packet.setNickname_host(nickname_asker);
                    packet.setNickname_companion(nickname_companion);
                    packet.setHistoryPart(messageList);
                    packet.setUnreadHas(i == 1 ? true : false);
                    history.add(packet);
                    messageList = new ArrayList<>();
                    packet = new HistoryPacketCommand();
                }
            }
        }
        return history;
    }

    public boolean deleteFileWithUnreadMessage(String nickname_asker, String nickname_companion){
        User user_asker = userMap.get(nickname_asker);
        User user_companion = userMap.get(nickname_companion);

        String unreadMessages_filePath = Config.DATA_PATH + "/user_" + user_asker.getUniqueID() + "/history/user_" + user_companion.getUniqueID() + "/unread.dat";
        File unreadMessagesFile = new File(unreadMessages_filePath);

        if (unreadMessagesFile.isFile() && unreadMessagesFile.exists() || unreadMessagesFile.length() != 0) {
            int count = 0;
            while (count < 10) {
                if (unreadMessagesFile.delete()) {
                    return true;
                }
                count++;
            }
            return false;
        }
        return true;
    }

    //ДЛЯ РАБОТЫ С ОФФ-ЛАЙН запросами

    public LinkedList<Command> loadAwaitingCommands(String nickname) {
        User user = userMap.get(nickname);

        File awaitingCommandFile = new File(dataPath + "/user_" + user.getUniqueID() + "/awaiting_command/commands.xml");

        if (awaitingCommandFile.exists() && awaitingCommandFile.isFile() && awaitingCommandFile.length() > 0) {
            XStream xStream = new XStream(new DomDriver());
            LinkedList<Command> commands = new LinkedList<>();
            try {
                InputStream inputStream = new FileInputStream(awaitingCommandFile);
                commands = (LinkedList<Command>) xStream.fromXML(inputStream);
                awaitingCommandFile.delete();
                return commands;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            return new LinkedList<Command>();
        }
        return new LinkedList<Command>();
    }

    public void addAwaitingCommand(String nickname, Command command) {
        User user = userMap.get(nickname);

        File awaitingCommandFile = new File(dataPath + "/user_" + user.getUniqueID() + "/awaiting_command/commands.xml");
        LinkedList<Command> commands = loadAwaitingCommands(nickname);
        commands.push(command);

        try (FileOutputStream outputStream = new FileOutputStream(awaitingCommandFile)) {

            XStream xStream = new XStream(new DomDriver());
            xStream.toXML(commands, outputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws IOException {

        /*Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        System.out.println(sdf.format(date));

        GregorianCalendar calendar = new GregorianCalendar(2016, 6, 1, 1, 6, 8);
        System.out.println(calendar.getTime());
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+00"));
        System.out.println(calendar.getTime());


        System.out.println();
        SimpleDateFormat dateFormat = new SimpleDateFormat("z dd.MM.yy HH:mm");
        System.out.println(dateFormat.format(date));

        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+06"));//"GMT+00"

        System.out.println(dateFormat.format(date));

        System.out.println();
        System.out.println(dateFormat.format(calendar.getTime()));
*/

    }
}
