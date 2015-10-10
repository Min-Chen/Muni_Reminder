import com.sun.deploy.xml.XMLParser;

import javax.xml.ws.Response;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;

/**
 * Created by artemischen on 10/10/15.
 */
public class TransitAPI {
    Stops stops = new Stops();
    private static String secretToken;
    private static String HOST;
    private static String[] services = {
            "GetAgencies.aspx",
            "GetRoutesForAgencies.aspx",
            "GetRoutesForAgency.aspx",
            "GetStopsForRoute.aspx",
            "GetStopsForRoutes.aspx",
            "GetNextDeparturesByStopName.aspx",
            "GetNextDeparturesByStopCode.aspx"};
    private static String[] agencies = {
            "AC Transit",
            "BART",
            "Caltrain",
            "Dumbarton Express",
            "SamTrans",
            "SF-MUNI",
            "VTA",
            "WESTCAT"};
    private static String[] direction = {"~Inbound", "Outbound"};

    /**
     * Default constructor
     */
    public TransitAPI() {
        secretToken = "e6ac96ee-25ee-4b8e-b1ca-c121312994e3";
        HOST = "http://services.my511.org/Transit2.0/";
    }

    /**
     * Uses the 511transit API to download information about the agencies, routes, and directions information.
     * Return the response as a in xml type.
     *
     * @param
     * @return
     */
    public static String getAgencies() {
        String agenciesResponse = "";
        try {
            HTTPRequest httpRequest = new HTTPRequest(HOST + services[0]);
            agenciesResponse = httpRequest.getURLSource();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return agenciesResponse;
    }

    /**
     * Return XML of routes from a specific agency.
     * @param agency
     * @return
     */
    public static String getRoutes(String agency) {
        String agenciesResponse = "";
        try {
            HTTPRequest httpRequest = new HTTPRequest(HOST + services[2]);
            agenciesResponse = httpRequest.getURLSource();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return agenciesResponse;
    }

    /**
     * Return XML of Stops for a given route
     * @param route
     * @return
     */
    public static String getStops(String route, String direction) {
        String agenciesResponse = "";
        try {
            HTTPRequest httpRequest = new HTTPRequest(HOST + services[3]);
            agenciesResponse = httpRequest.getURLSource();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return agenciesResponse;
    }

    public static String getMuniDepartures(String stopName) {
        String agenciesResponse = "";
        try {
            HTTPRequest httpRequest = new HTTPRequest(HOST + services[5]);
            agenciesResponse = httpRequest.getURLSource();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return agenciesResponse;
    }

    public static String getMuniDepartures(int stopId) {
        String agenciesResponse = "";
        try {
            HTTPRequest httpRequest = new HTTPRequest(HOST + services[6]);
            agenciesResponse = httpRequest.getURLSource();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return agenciesResponse;
    }

    /**
     * Get XML of SF-MUNI list of stops for each route.
     * @param route
     * @param direction
     * @param stops
     * @return
     */
    public static String getMuniStops(String route, String direction, String stops) {
        return null;
    }

}


