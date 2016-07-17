package server;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import logic.RegistrationModel;
import logic.User;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Database {

    private Map<String, User> userMap; // все пользователи
    private Map<User, Connection> userOnline; // пользователи, которые онлайн

    private File dataPath;
    private File clientsDataFile;

    public Database() {
        this.userMap = new HashMap<String, User>();
        this.userOnline = new TreeMap<User, Connection>(new SortedByUniqueID());

        this.dataPath = new File(Config.DATA_PATH);
        this.clientsDataFile = new File(dataPath + "/" + Config.CLIENTS_DATA_FILENAME);

        loadData();
    }

    public void goOnline(User user, Connection connection) {
        if (connection.isOpen()) {
            this.userOnline.put(user, connection);
        }
    }

    public void goOffline(User user) {
        if (!userOnline.get(user).isOpen()) {
            this.userOnline.put(user, null);
        }
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
        if (userOnline.get(userMap.get(nickname)) != null) {
            return true;
        } else
            return false;
    }

    public void deleteUser(User user) {
        this.userMap.remove(user.getNickname());
        this.userOnline.remove(user);
    }

    public User registerUser(RegistrationModel regModel, Connection connection) throws UnknownHostException {
        if (isNickAvailable(regModel.getNick())) { // FIXME by Koha: 10.07.2016
            int uniqueID = getLastUserId() + 1;
            Config.changeID(uniqueID);
            User newUser = new User(regModel.getNick(), regModel.getPassword(), connection.getSocket().getInetAddress(), uniqueID);//+regModel
            userMap.put(regModel.getNick(), newUser);

            createDirectoryForRegisteredUser(newUser);
            updateData();

            return newUser;
        } else
            return null;
    }

    public boolean isNickAvailable(String nickname) {
        return !userMap.containsKey(nickname);
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

    // ДЛЯ РАБОТЫ С ДИРИКТОРИЯМИ

    public void loadData() { //загрузка с файла в мэп

        if (checkDataPath() && checkClientsDataFile() && clientsDataFile.isFile() && clientsDataFile.length() > 0) { //если файл не пустой

            XStream xmlStream = new XStream(new DomDriver()); //поток для управлением сериалации/десереализации объектов в .xml
            xmlStream.alias("user", User.class);

            try {
                FileInputStream inputStream = new FileInputStream(clientsDataFile);

                userMap = (HashMap<String, User>) xmlStream.fromXML(inputStream);
            } catch (FileNotFoundException e) {
                // ex
            }
        }
    }

    public void updateData() {
        if (clientsDataFile.setWritable(true)) { // установить возможность редактирования

            if (checkDataPath() && checkClientsDataFile() && clientsDataFile.isFile()) {
                XStream xmlStream = new XStream(new DomDriver());
                xmlStream.alias("user", User.class); // класс User будет в тегах писать как user, а не logic.User

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
            File newDirectory = new File(dataPath + "/" + dirName + "/history");

            while (true) {
                if (newDirectory.mkdirs()) {
                    break;
                }
            }
        }

    }

    public static void main(String[] args) throws UnknownHostException {
        /*RegistrationModel rm1 = new RegistrationModel("Bob", "morcos");
        RegistrationModel rm2 = new RegistrationModel("koha", "iloveNIGGERS");
        Database db = new Database();
        db.registerUser(rm1);
        db.registerUser(rm2);*/
        User u = new User("kostyq", "as", InetAddress.getLocalHost(), 1);
        final String dirName = "user_" + u.getUniqueID();
        String[] files = new File(Config.DATA_PATH).list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                File dirName = new File(dir + "/" + name);
                if (dir.isDirectory() && name.equals(dirName) && dirName.isDirectory()) {
                    return true;
                } else
                    return false;
            }
        });

        if (files.length == 0) { // если не нашло такой папки, то создаем ее
            File newDirectory = new File(Config.DATA_PATH + dirName + "/history");

            while (true) {
                if (newDirectory.mkdirs()) {
                    break;
                }
            }
        }

        //db.changeID(db.getLastUserId());

    }
}
