import java.net.MalformedURLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Artemis on 8/17/15.
 */
public class Driver {
    public static String url = "";
    public static String urlSource;
    public static String remainingTime;
    /*inputRoute();
    inputDirection();
    inputStopNum();
*/

    //https://www.nextbus.com/#!/sf-muni/31/31___I_F00/3066/6728
    //https://www.nextbus.com/#!/sf-muni/31/31___I_F00/3066



    public String getURL(String route, String direction, int StopNum) {
        StringBuilder sb = new StringBuilder();
        sb.append("https://www.nextbus.com/#!/sf-muni/");

        // get route
        if (route.equals("31")) {
            sb.append("31");
        }

        // get direction
        if (direction.substring(0,10).equals("38R__I_F00")) {
            sb.append("/31___I_F00/");
        }
        else {
            sb.append("/31___O_F00/");
        }

        // get Stop Number
        if (StopNum == 13066) {
            sb.append(3066);
        }

        url = sb.toString();
        return url;
    }

    public String convertURLToText(String url) throws Exception {
        try {
            url = "https://www.nextbus.com/#!/sf-muni/31/31___I_F00/3066/3064";
            System.out.println(url);
            HTTPRequest htmlRequest = new HTTPRequest(url);
            urlSource = htmlRequest.getURLSource(htmlRequest.url);
            System.out.println(urlSource);

        } catch (MalformedURLException e) {
            System.out.println("....");
        }
        return urlSource;
    }

    public String getRemainingTime(String urlSource) {
        //System.out.println(urlSource);
        String regex = "(?<=<tr><td>)\\d+(?=<\\/td><td>)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(urlSource);
        String group = null;
        if (matcher.find()) {
            remainingTime = matcher.group(0);
            group = matcher.group(0);
            System.out.println("xxxx: " + group);
            System.out.println(urlSource);
            System.out.printf("I found the text \"%s\" starting at index %d " +
                            "and ending at index %d.%n",
                    matcher.group(), matcher.start(), matcher.end());
        }else {
            System.out.println("no matches!!");
        }
        return remainingTime;
    }

//    public static void main(String[] args) throws Exception {
//        Driver driver = new Driver();
//        String route = WebServer.query.get("route")[0];
//        String direction = WebServer.query.get("direction")[0];
//        int StopNum = Integer.parseInt(WebServer.query.get("stopnumber")[0]);
//
//        url = driver.getURL(route, direction, StopNum);
//        urlSource = driver.convertURLToText(url);
//
//        driver.getRemainingTime(urlSource);
//        System.out.println(remainingTime);
//    }
}
