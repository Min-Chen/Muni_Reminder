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
    public static String getStops(String route) {
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
}


    for route in get_departures(stop_id):
    name = route.get("Name")
    direction = route[0][0].get("Name")
    times = muni_times(route)

    # store the route info at MUNI stop
    routes[name] = (direction, times)

            return routes


    def muni_stop(name):
            """ Normalize SF-MUNI stop name with regex. """
    name = re.sub("  and  ", " & ", name)
    name = re.sub("Street", "St", name)
    name = re.sub(" Of ", " of ", name)
    name = re.sub("C Chavez", "Cesar Chavez", name)
            return name


    def muni_stops(route_id, direction, stops):
            """ Get XML of SF-MUNI list of stops for each route. """
            for stop in get_stops(route_id, direction):
    stop_id = stop.get("StopCode")

            # store each stop in dictionary
    stops[stop_id] = muni_stop(stop.get("name"))

            return stops


    def muni_routes(agency, stops):
            """ Get XML of SF-MUNI stops from all the routes for specific agency. """
            for route in get_routes(agency):
    route_id = route.get("Code")

            # grab inbound stops
            stops = muni_stops(route_id, direction[0], stops)

    # grab outbound stops, except 81X and 80X don't have outbound stops
            if route_id != "81X" and route_id != "80X":
    stops = muni_stops(route_id, direction[1], stops)

    return stops


    def muni():
            """ Get SF-MUNI data on stops for all of the routes. """
            return muni_routes(agency[5], {})
