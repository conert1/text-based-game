package za.co.wethinkcode.RobotWorlds;

import org.json.JSONArray;
import org.json.JSONObject;
//import org.turtle.*;
//import za.wethinkcode.RobotWorlds.configuration.Configuration;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class ResponseReader {

    private Position currentPosition;
    private Position currentRobotPosition;
    private Robot.Direction currentDirection;
    private int shields;
    private int shots;
    private String status;
//    private final Configuration configuration = new Configuration();
    private int angle;

    private int directionIndex = 0;

    /**
     * Sets the Graphics and the robot
     */
    public ResponseReader(){
        //code for the turtle and the border
        this.currentRobotPosition = new Position(0,0);
        this.angle = 90;
        terminator(currentRobotPosition.getX(),currentRobotPosition.getY(),angle);



    }

    /**
     * Handle the replay messages from the server
     * @param command is the command JSON string with the status of requests execution
     * @param request is the request JSON string with instructions sent to the sever to move the robot
     */
    public void handleReply(JSONObject command, JSONObject request) {
        if (command.getString("result").equalsIgnoreCase("OK")) {
            handleStatus(command.getJSONObject("state"));
            if (request.getString("command").equalsIgnoreCase("forward") || request.getString("command").equalsIgnoreCase("back")) {
                if (command.getJSONObject("data").getString("message").equalsIgnoreCase("Done")) {
                    System.out.println(request.getString("robot") + " > " + "[" + currentPosition.getX() +
                            " , " + currentPosition.getY() + "] Moved " + request.getString("command") +
                            " by " + request.getJSONArray("arguments").getInt(0) + " steps.");
                } else if (command.getJSONObject("data").getString("message").equalsIgnoreCase("Obstructed")) {
                    System.out.println(request.getString("robot") + " > " + "[" + currentPosition.getX() +
                            " , " + currentPosition.getY() + "] Sorry, I have encountered an obstacle.");
                } else if (command.getJSONObject("data").getString("message").equalsIgnoreCase("Border")) {
                    System.out.println(request.getString("robot") + " > " + "[" + currentPosition.getX() +
                            " , " + currentPosition.getY() + "] Sorry, I cannot go outside my safe zone.");
                }
            }
            else if (request.getString("command").equalsIgnoreCase("left") || request.getString("command").equalsIgnoreCase("right")) {
                if (command.getJSONObject("data").getString("message").equalsIgnoreCase("Done")) {
                    System.out.println(request.getString("robot") + " > " + "[" + currentPosition.getX() +
                            " , " + currentPosition.getY() + "] Turned " + request.getString("command"));
                }
            }
            else if (request.getString("command").equalsIgnoreCase("repair") || request.getString("command").equalsIgnoreCase("reload")) {
                checkStatus();
            }
            else if (request.getString("command").equalsIgnoreCase("state")) {
                System.out.println("Position : [" + currentPosition.getX() +
                        " , " + currentPosition.getY() + "] Bullets : " + this.shots + " Shield : " + this.shields);
            }
            else if (request.getString("command").equalsIgnoreCase("shoot")) {
                if (command.getJSONObject("data").getString("message").equalsIgnoreCase("Hit")) {
                    System.out.println(request.getString("robot") + " > " + "[" + currentPosition.getX() +
                            " , " + currentPosition.getY() + "] Bullet Hit " +
                            command.getJSONObject("data").getString("robot"));
                } else if (command.getJSONObject("data").getString("message").equalsIgnoreCase("Missed")) {
                    System.out.println(request.getString("robot") + " > " + "[" + currentPosition.getX() +
                            " , " + currentPosition.getY() + "] Bullet Missed");
                }

            }
            else if (request.getString("command").equalsIgnoreCase("look")) {
                for (int i = 0; i < command.getJSONObject("data").getJSONArray("objects").length(); i++) {
                    if(command.getJSONObject("data").getJSONArray("objects").getJSONObject(i).getString("type"). equalsIgnoreCase("ROBOT")) {
                        System.out.println(command.getJSONObject("data").getJSONArray("objects").getJSONObject(i).getString("type")
                                + "   " + command.getJSONObject("data").getJSONArray("objects").getJSONObject(i).getString("direction")
                                + "   " + command.getJSONObject("data").getJSONArray("objects").getJSONObject(i).getJSONArray("position"));
                    }
                }
                drawLook(command.getJSONObject("data").getJSONArray("objects"));
            }
            else if (request.getString("command").equalsIgnoreCase("dump")) {
                System.out.println(command.getJSONObject("data").getString("message"));
            }
            else if (request.getString("command").equalsIgnoreCase("robots")) {
                System.out.println(command.getJSONObject("data").getString("message"));
            }
            else if (request.getString("command").equalsIgnoreCase("help")) {
                System.out.println(command.getJSONObject("data").getString("message"));
            }
        }
    }

    /**
     * Sets the robots weapons, shields and postion
     * @param state JSON Object
     */
    public void handleStatus(JSONObject state){
        int x = state.getJSONArray("position").getInt(0);
        int y = state.getJSONArray("position").getInt(1);
        this.currentDirection = getDirection(state.getString("direction"));
        newDirection(this.currentDirection);

        this.currentPosition = new Position(x, y);
        newPosition(this.currentPosition);

        this.shields = state.getInt("shields");

        this.shots = state.getInt("shots");

        this.status = state.getString("status");

    }

    /**
     * returns the orientation of the robot
     * @param direction the direction in which the robot is facing
     * @return Robot direction
     */
    public Robot.Direction getDirection(String direction){
//        switch(direction){
//
//            case "DOWN":
//
//                return Robot.Direction.DOWN;
//
//            case "LEFT":
//
//                return Robot.Direction.LEFT;
//
//            case "RIGHT":
//
//                return Robot.Direction.RIGHT;
//
//            case "UP":
//            default:
                return Robot.Direction.NORTH;
    }

    public void newPosition(Position position){
        //code to move turtle to new position
//        StdDraw.clear();
//        StdDraw.enableDoubleBuffering();

        // the angle needs to be updated
        terminator(position.getX(), position.getY(), this.angle);



    }

    /**
     * sets the new direction of the robot based on its previous direction
     * @param direction is the previous direction of the robot
     */
    public void newDirection(Robot.Direction direction) {
//
//        switch (direction) {
//
//            case UP:
//                this.angle = 90;
//                break;
//
//            case RIGHT:
//                this.angle = 0;
//                break;
//
//            case DOWN:
//                this.angle = 270;
//                break;
//
//            case LEFT:
//                this.angle = 180;
//                break;
//        }

    }

    /**
     * Draws all obstacles that the robot can see within its visibility range
     * @param objects JSON array with the position of the obstacles and other robots
     */
    public void drawLook(JSONArray objects){
        //code to draw the obstacles and enemy players
//
//        StdDraw.clear();
//        StdDraw.enableDoubleBuffering();

        for (int i = 0; i < objects.length(); i++) {
            int x = objects.getJSONObject(i).getJSONArray("position").getInt(0);
            int y = objects.getJSONObject(i).getJSONArray("position").getInt(1);
            if(objects.getJSONObject(i).getString("type").equalsIgnoreCase("OBSTACLE")){
                //set line colour to black
                //Turtle box drawing code

//                StdDraw.setPenColor(StdDraw.BLACK);
//                terminator(this.currentPosition.getX(),this.currentPosition.getY(),this.angle);
//                StdDraw.filledSquare(x + 2.5, y + 2.5, 2.5);
            }
            else if(objects.getJSONObject(i).getString("type").equalsIgnoreCase("ROBOT")){
                //set line colour to red
                //Turtle box drawing code

//                StdDraw.setPenColor(StdDraw.RED);
//                terminator(this.currentPosition.getX(),this.currentPosition.getY(),this.angle);
//                StdDraw.filledSquare(x , y, 2);
            }


        }
    }


    /**
     * checks the status of the robot
     */
    public void checkStatus(){
        if(Objects.equals(this.status, "RELOAD")){
            System.out.println("Reloading...");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Reloaded");
            this.status = "NORMAL";
        }
        else if(Objects.equals(this.status, "REPAIR")){
            System.out.println("Repairing...");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Repaired");
            this.status = "NORMAL";
        }
        else if (Objects.equals(this.status, "DEAD")){
            System.out.println("Sorry, You are already Dead");
        }
    }

    /**
     * Robot Design
     * @param x coordinate of the robot
     * @param y coordinate of the robot
     * @param a the angle it makes with the horizontal
     */
    public void terminator(int x, int y, int a) {

//        StdDraw.setScale(configuration.getWorldSize().getX(), configuration.getWorldSize().getY());
//        StdDraw.setPenColor(StdDraw.BLACK);
//        StdDraw.rectangle(0,0,256,256);
//
//        Turtle robot = new Turtle(x, y, a);
//        StdDraw.setPenColor(StdDraw.BLUE);
//        robot.left(150);
//        robot.forward(5);
//        robot.left(120);
//        robot.forward(5);
//        robot.left(120);
//        robot.forward(5);
//        StdDraw.setPenColor(StdDraw.BLACK);
    }



}

