package za.co.wethinkcode.RobotWorlds;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class SimpleServer implements Runnable {
    public static ArrayList<Robot> listRobots = new ArrayList<>();
    public static final int PORT = 5000;
    /**
     * from Server --> client.
     */
    private final BufferedReader in;
    /**
     * from client --> server
     */
    private final PrintStream out;
    private final String clientMachine;

    World world;
    Robot robot;
    String requestFromClient;
    String message;


    public SimpleServer(Socket socket , World world) throws IOException {
        clientMachine = socket.getInetAddress().getHostName();
        out = new PrintStream(socket.getOutputStream());
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.world = world;
    }

    /**
     * Add new loaded robots
     *
     */
    public void addRobots(Robot robot) {
        listRobots.add(robot);
    }

    /**
     * Initiate the server
     */
    public void run() {
        try {
            /**launching robot loop*/
            while (true){
                requestFromClient = in.readLine();
                JSONObject request = receiveRequest(requestFromClient);
                JSONObject response = checkIfNameIsValid(request);
                if (response.get("result").equals("ERROR")) {
                    robot = new Robot(request.getString("robot"), request.getJSONArray("arguments"));
                    JSONObject state = getState(robot);
                    JSONObject data = getData(robot , false);
                    response.put("data" , data);
                    response.put("state" , state);
                    out.println(response);
                } else {
                    robot = new Robot(request.getString("robot"), request.getJSONArray("arguments"));
                    JSONObject state = getState(robot);
                    JSONObject data = getData(robot , false);
                    response.put("data" , data);
                    response.put("state" , state);
                    world.addRobots(robot);
                    out.println(response);
                    break;
                }

            }

            /**Maiin game loop*/
            while (true) {
                requestFromClient = in.readLine();
                JSONObject response = sendCommandResponse(requestFromClient);
                if (response.get("result").equals("error")){
                    response.put("data", getData(robot , true));
                    response.put("state" , getState(robot));
                    out.println(response);
                } else {
                    JSONObject request = new JSONObject(requestFromClient);
                    robot.handleCommand(request.getString("command") , request.getJSONArray("arguments"));
                    JSONObject state = getState(robot);
                    JSONObject data = getData(robot , true);
                    response.put("data", data);
                    response.put("state" , state);
                    out.println(response);
                }
            }
        } catch (SocketException | NullPointerException e) {
            System.out.println(robot.getName() + " left game.");
            World.robots.remove(robot);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public JSONObject checkIfNameIsValid(JSONObject request) {
        JSONObject response = new JSONObject();
        String name = request.getString("robot");
        message = "Robot successfully launched in the world.";
        if (request.get("command").equals("launch") && !request.get("arguments").equals("")) {
            if (World.robots.isEmpty()){
                response.put("result" , "OK");
            } else  for (Robot robot : World.robots)
                if (name.equalsIgnoreCase(robot.getName())){
                    response.put("result" , "ERROR");
                    message = "Unable to launch robot due to its name already existing in the world.";
                } else {
                    response.put("result", "OK");
                }
        } else {
            response.put("result", "ERROR");
            message = "Unsupported command";
        }
        return response;
    }

    public JSONObject getState(Robot robot) {
        /**THIS IS ACTUALLY THE STATE*/
        JSONObject values = new JSONObject();
        JSONArray position = new JSONArray();
        position.put(robot.getPosition().getX());
        position.put(robot.getPosition().getY());
        values.put("position" , position);
        values.put("direction" , robot.getDirectionString());
        values.put("shields" , robot.getShields());
        values.put("shots", robot.getShots());
        values.put("status" , robot.getOperationalStatus());
        return values;
    }

    public JSONObject getData(Robot target , boolean launched) {
        JSONObject values = new JSONObject();
        JSONArray dimensions = new JSONArray();
        dimensions.put(World.horizontalLength);
        dimensions.put(World.verticalLength);
        values.put("visibility" , 10);
        JSONArray position = new JSONArray();
        position.put(robot.getPosition().getX());
        position.put(robot.getPosition().getY());
        values.put("position" , position);
        values.put("reload" , robot.getReloadTime());
        values.put("repair" , robot.getRepairTime());
        values.put("mine" , robot.getMines());
        values.put("max-shields" , robot.getMaxShields());
        values.put("max-shots" , robot.getMaxShots());
        values.put("dimensions" , dimensions);
        if (launched)
            message = target.getStatus();
        if (robot.GotShot()) {
            message = robot.getGotShotMessage();
            robot.setGotShotFalse();
        } if (robot.getPurged()) {
            message = "You got purged by the man in charge:(";
        }
        values.put("message" , message);
        return values;
    }




    private Boolean IsValid(JSONObject request){
        return (request.has("arguments") && request.has("command"));
    }

    /**sends request to client*/
    public JSONObject sendCommandResponse(String input){
        JSONObject request = new JSONObject(input);
        JSONObject response = new JSONObject();
        if (IsValid(request)) {
            response.put("result", "OK");
        } else {
            response.put("result" , "error");
            message = "Invalid command";
        }
        return response;
    }

    /**receives request from client*/
    public static JSONObject receiveRequest(String input){
        return new JSONObject(input);
    }
}

