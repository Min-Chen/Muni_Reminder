import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

//Origional from https://github.com/cs212/lectures/blob/fall2014/DynamicHTML/src/HelloServer.java

/**
 * Demonstrates the danger of using user-input in a web application, especially
 * regarding cross-site scripting (XSS) attacks.
 */
public class WebServer {
    private static String seed;
    private static int threadAmount;
    public static Map<String,String[]> query;

    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);

        ServletHandler handler = new ServletHandler();
        handler.addServletWithMapping(HomePage.class, "/index");
        handler.addServletWithMapping(SearchPage.class, "/submit");
        server.setHandler(handler);
        server.start();
        server.join();
    }

    public WebServer(int port, String seed, int threadAmount) throws Exception {
        Server server = new Server(port);
        this.seed = seed;
        this.threadAmount = threadAmount;

        ServletHandler handler = new ServletHandler();
        handler.addServletWithMapping(HomePage.class, "/index");
        server.setHandler(handler);
        server.start();
        server.join();
    }

    @SuppressWarnings("serial")
    public static class HomePage extends HttpServlet {
        @Override
        protected void doGet(
                HttpServletRequest request,
                HttpServletResponse response)
                throws ServletException, IOException {

            response.setContentType("text/html");
            response.setStatus(HttpServletResponse.SC_OK);

            PrintWriter out = response.getWriter();
            printHeader(out, "Min Chen Search", request);

            response.setIntHeader("X-XSS-Protection", 0);

            BufferedReader br = new BufferedReader(new FileReader("/Users/Artemis/Documents/USF/CS212/Muni_Reminder/src/HomePage.html"));
            String str = br.readLine();
            while (str != null) {
                out.printf(str);
                str = br.readLine();
            }
            br.close();
        }
    }

    public static class SearchPage extends HttpServlet {
        protected void doGet (HttpServletRequest request,
                HttpServletResponse response)
                throws ServletException, IOException {
            response.setContentType("text/html");
            response.setStatus(HttpServletResponse.SC_OK);

            PrintWriter out = response.getWriter();
            out.println("<html>");
            out.println("<head><title>searchpage</title></head>");
            out.println("<body>");
            query = request.getParameterMap();


            try {
                Driver driver = new Driver();
                String route = query.get("route")[0];
                String direction = query.get("direction")[0];
                int StopNum = Integer.parseInt(query.get("stopnumber")[0]);

                driver.url = driver.getURL(route, direction, StopNum);
                driver.urlSource = driver.convertURLToText(driver.url);
                driver.getRemainingTime(driver.urlSource);
                out.println(driver.remainingTime);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private static void printHeader(PrintWriter out, String title, HttpServletRequest request) {
        out.printf("<html>%n");
        out.printf("<head><title>%s</title></head>%n", title);
        out.printf("<body style=\"width:1100px;margin-left:auto;margin-right:auto\" >");
        out.printf("<div style = \"height:25px\" >");
        out.printf("<span style=\"float:left;\">");
        out.printf("<a href = \"index\" style=\"margin-right:15px\">Home Page</a>");
        out.printf("<a href = \"SearchHistory\" style=\"margin-right:15px\">Search History</a>");
        out.printf("<a href = \"VisitHistory\" style=\"margin-right:15px\">Visit History</a>");
        out.printf("</span>%n");

        out.printf("<span style=\"float:right\">");

        String name = WebUtil.getCookieValue(request,"name");
        if (name == null) {
            out.printf("<a href = \"Register\" style=\"margin-right:15px\">Register</a>");
            out.printf("<a href = \"Login\" style=\"margin-right:15px\">Sign In</a>");
        }
        else {
            long time = 0;
            try {
                ResultSet rs = null;
                rs = WebUtil.executeQuery("SELECT LAST_LOGIN_TIME FROM USER WHERE NAME = '" + name + "' ");
                if (rs.next()) {
                    time = rs.getLong(1);
                }
                WebUtil.closeDB();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            SimpleDateFormat sd = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            sd.setTimeZone(TimeZone.getTimeZone(TimeZone.getDefault().toZoneId()));
            String lastLoginTime = sd.format(new Date(time));

            out.printf("<span style=\"margin-right:20px;\">Hello, " + name + ", Last login time: " + lastLoginTime + "</span>");
            out.printf("<a href = \"ChangePwd\" style=\"margin-right:15px\">Change Password</a>");
            out.printf("<a href = \"Logout\" style=\"margin-right:15px\">Log out</a>");
        }
        out.printf("</span>");
        out.printf("</div>");
    }
}