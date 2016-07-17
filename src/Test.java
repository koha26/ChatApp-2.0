import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import logic.User;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by demo on 17.07.16.
 */
public class Test {
    public static void main(String[] args) throws java.net.UnknownHostException {
        User u1 = new User("Kostya", "qwery", InetAddress.getLocalHost(), 1);
        User u2 = new User("BobMorcos", "h", InetAddress.getLocalHost(), 2);
        User u3 = new User("Mrglton", "qwe", InetAddress.getLocalHost(), 3);

        XStream xStream = new XStream(new DomDriver());
        xStream.alias("user", User.class);


        Map<String, User> map = new HashMap<>();
        map.put(u1.getNickname(),u1);
        map.put(u2.getNickname(),u2);
        map.put(u3.getNickname(),u3);

        try {
            FileOutputStream fos = new FileOutputStream("outMap.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(map);
            oos.close();
            fos.close();
        } catch (IOException e){

        }

        try{
            FileOutputStream fis = new FileOutputStream("out.xml");
            xStream.toXML(map, fis);
        } catch (IOException e){

        }
    }
    /*public static void main(String[] args) {
        User u = new User();
        XStream stream = new XStream(new DomDriver());

        try{
            FileInputStream fis = new FileInputStream("out.xml");
            stream.fromXML(fis,u);
            System.out.println(u.toString());
        }catch(IOException e ){

        }
    }*/
}
