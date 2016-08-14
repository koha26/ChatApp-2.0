import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
    public static void main(String[] args) throws IOException {
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

        Map<String, String > map = new HashMap<>();
        map.put("one", "Maxxx");
        map.put("two", "BigMax");
        map.put("three", "asdasMaXxx");
        map.put("four", "Maox");
        map.put("five", "koatys");

        String nickname = ".*Max.*";
        Pattern pattern = Pattern.compile(nickname.toLowerCase());
        Matcher matcher;
        for (String nick:map.values()) {
            matcher = pattern.matcher(nick.toLowerCase());
            if (matcher.find()){
                System.out.println(nick);
            }
        }
    }

}
