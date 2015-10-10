import java.io.InputStream;
import java.util.HashMap;

/**
 * Created by artemischen on 10/10/15.
 */
public class Stops {
    public static HashMap<Integer, String> stopsMap;

    public Stops() {
        stopsMap = new HashMap<>();
    }

    public String getStopName(int stopId) {
        return stopsMap.get(stopId);
    }
}
