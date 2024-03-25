package za.co.wethinkcode.RobotWorlds.WebApi;

import io.javalin.Javalin;
import org.json.JSONArray;
import org.json.JSONObject;
import kong.unirest.HttpResponse;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class JavalinServer {
    private final Javalin server;
    public static JSONObject dataa = new JSONObject();
    public JavalinServer() {

        server = Javalin.create(config -> {

//            config.defaultContentType = "application/json";

        });

        this.server.get("obstacles", context -> context.json(dataa.get("object").toString()));
        this.server.get("pits", context -> context.json(dataa.get("pits").toString()));
        this.server.get("mine", context -> context.json(dataa.get("mm").toString()));

    }

    public static void main(String[] args) {
        Scanner key = new Scanner(System.in);
        JavalinServer ser = new JavalinServer();

        ser.start(4000);
        System.out.println("Enter the database you would like to restore to the game:");
        String data = key.nextLine();
        Data(data);
    }

    public static JSONObject Data(String data){
        ArrayList<String> lisObs= new ArrayList<>();
        ArrayList<String> lisPit = new ArrayList<>();
        ArrayList<String> lisMines = new ArrayList<>();
        String url = "jdbc:sqlite:"+data+".sqlite";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            try {
                ResultSet rs = stmt.executeQuery("select * from "+data);
                while (rs.next()) {
                    String obstacles = rs.getString("obstacles");
                    lisObs.add(0, obstacles);

                    String pits = rs.getString("pits");
                    lisPit.add(pits);

                    String mines = rs.getString("mine");
                    lisMines.add(mines);
                    System.out.println(mines);
                }
                dataa.put("pits", lisPit);
                dataa.put("object", lisObs);
                dataa.put("mm", lisMines);
                System.out.println(dataa.get("mm"));


            } catch (SQLException e ) {
                throw new Error("Problem", e);
            }

        } catch (SQLException e) {
            throw new Error("Problem", e);
        }
        return dataa;
    }

    public void start(int port) {
        this.server.start(port);
    }

    public void stop() {
        this.server.stop();
    }
}

