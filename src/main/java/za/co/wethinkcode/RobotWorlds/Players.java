package za.co.wethinkcode.RobotWorlds;

import java.util.ArrayList;
import java.util.HashMap;

public class Players {
    private static HashMap<String, Robot> robots = new HashMap<String, Robot>();
    private static ArrayList<Robot> playerRobots = new ArrayList<>();


    /**
     * Adds players robots with unique names
     * @param name is the name of the robots
     */
//    public static void checkRobot(String name){
//        if(!robots.containsKey(name)){
//            Robot robot = new Robot(name);
//            robots.put(name, robot);
//            playerRobots.add(robot);
//        }
//    }


    /**
     * returns the intsance of the robot specified by the string parameter
     *
     * @param name s the name of the robot
     * @return instance of the Robot
     */
    public static Robot getRobot(String name){
//        checkRobot(name);
        return robots.get(name);
    }


    /**
     * Returns all the robots that have been launched
     * @return launched robots
     */
    public static ArrayList<Robot> getPlayers(){
        return playerRobots;
    }
}
