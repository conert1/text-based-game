package za.co.wethinkcode.RobotWorlds;
import org.json.JSONArray;
import org.json.JSONObject;
//import org.turtle.*;
import za.co.wethinkcode.RobotWorlds.commands.*;
//import za.wethinkcode.RobotWorlds.configuration.Configuration;
//import za.wethinkcode.RobotWorlds.Turtle;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Robot {

    private final String kind;
    public String gotShotMessage;

    public Boolean Purged = false;

    private int maxShots;
    private int shots;
    private int shields;
    private int maxShields;
    private int shotDistance;
    private int reloadTime;
    private int repairTime;
    private int maxMines;
    private int mines;
    public boolean gotShot = false;
    private Position position;
    private Direction currentDirection;
    public String name;
    private String status;
    //    private String statusType = "NORMAL";
    private String operationalStatus = "Normal";

    private Boolean isDead = false;



    public String getKind() {
        return kind;
    }

    public enum Direction{
        NORTH , SOUTH , EAST , WEST
    }

    /**
     * Condition for moving the robot
     */
    public enum Conditions {

        SUCCESS, FAILED_OBSTACLE_DETECTED, FAILED_OUTSIDE_ROBOT_WORLD , IN_MINE , COLLIDED_WITH_ROBOT , IN_PIT
    }


    /**
     * set up the robot
     * @param name is the name given to the robot
     */
    public Robot(String name , JSONArray kind){
        this.position = new Position(0, 0);
        this.currentDirection = Direction.NORTH;
        this.name = name;
        this.kind = kind.getString(0);
        setSpecs(this.kind);
    }


    private void setSpecs(String kind){
        /* sniper specs */
        int maxSniperShots = 1;
        int maxSniperShields = 3;
        int sniperShotDistance = 10;
        int sniperReloadTime = 1;/*seconds*/
        int sniperRepairTime = 3;/*seconds*/
        int sniperMines = 0;
        /* shooter specs */
        int maxShooterShots = 5;
        int maxShooterShields = 2;
        int shooterShotDistance = 5;
        int shooterReloadTime = 5;/*seconds*/
        int shooterRepairTime = 2;/*seconds*/
        int shooterMines = 0;
        /* bomber specs */
        int maxBomberShots = 0;
        int maxBomberShields = 5;
        int bomberShotDistance = 0;
        int bomberReloadTime = 10;/*seconds*/
        int bomberRepairTime = 5;/*seconds*/
        int bomberMines = 2;
        switch (kind){
            case "sniper":
                maxShots = maxSniperShots;
                maxShields = maxSniperShields;
                shotDistance = sniperShotDistance;
                reloadTime = sniperReloadTime;
                repairTime = sniperRepairTime;
                maxMines = sniperMines;
                break;
            case "shooter":
                maxShots = maxShooterShots;
                maxShields = maxShooterShields;
                shotDistance = shooterShotDistance;
                reloadTime = shooterReloadTime;
                repairTime = shooterRepairTime;
                maxMines = shooterMines;
                break;
            case "bomber":
                maxShots = maxBomberShots;
                maxShields = maxBomberShields;
                shotDistance = bomberShotDistance;
                reloadTime = bomberReloadTime;
                repairTime = bomberRepairTime;
                maxMines = bomberMines;
                break;
        }
        mines = maxMines;
        shots = maxShots;
        shields = maxShields;
    }



    /**
     * sets the status of the robot
     * @param status is the status of the robot
     */
    public void setStatus(String status){
        this.status = status;
    }

    /**
     * gets the status of the robot
     * @return status of the robot
     */
    public String getStatus(){
        return this.status;
    }

    /**
     * handles the commands sent to the robots
     * @param com instruction sent to the robot
     * @param args arguments of the command
     */
    void handleCommand(String com, JSONArray args){
        Command command = Command.createCommand(com, args);
        if (command == null){
            setStatus("Sorry, I did not understand '" + com + "'.");
        }
        else{
            command.execute(this);
        }
    }

    /**
     * sets the position of the robot
     * @param pos position of the robot
     */
    public void setPosition(Position pos){
        this.position = pos;
    }

    /**
     * returns the position of the robot
     * @return position of the robot
     */
    public Position getPosition(){
        return this.position;
    }

    /**
     * get the current direction of the robot
     * @return robot direction
     */
    public Direction getCurrentDirection(){
        return this.currentDirection;
    }

    /**
     * gets the name of the robot
     * @return name of the robot
     */
    public String getName(){
        return this.name;
    }



    /**Function changes the current direction of the robot depending
     * on if it is told to move left or right
     *
     * @param turnRight boolean containing if the robot turns left or right
     */
    public void updateDirection(boolean turnRight) {
        switch(this.currentDirection){
            case NORTH:
                if(turnRight){
                    this.currentDirection = Direction.EAST;
                }
                else{
                    this.currentDirection = Direction.WEST;
                }
                break;
            case EAST:
                if(turnRight){
                    this.currentDirection = Direction.SOUTH;
                }
                else{
                    this.currentDirection = Direction.NORTH;
                }
                break;
            case SOUTH:
                if(turnRight){
                    this.currentDirection = Direction.WEST;
                }
                else{
                    this.currentDirection = Direction.EAST;
                }
                break;
            case WEST:
                if(turnRight){
                    this.currentDirection = Direction.NORTH;
                }
                else{
                    this.currentDirection = Direction.SOUTH;
                }
                break;

        }
    }

    /**Function gets current direction to check if the new Position is in
     * an unblocked position and if it is, move to the new position and draw
     * a line to the next position with the robotTurtle
     *
     * @param nrSteps int value of the number of steps to the next Position
     * @return UpdateResponse of the check to see if it moves or not
     */
    public Conditions updatePosition(int nrSteps) {
        int newX = this.position.getX();
        int newY = this.position.getY();
        if (Direction.NORTH.equals(this.currentDirection)) {
            newY = newY + nrSteps;
        } else if (Direction.SOUTH.equals(this.currentDirection)){
            newY = newY - nrSteps;
        } else if (Direction.EAST.equals(this.currentDirection)){
            newX = newX + nrSteps;
        } else if (Direction.WEST.equals(this.currentDirection)){
            newX = newX - nrSteps;
        }
        Position newPosition = new Position(newX, newY);
        if (isNewPositionAllowed(newPosition)) {
            if (Obstacles.blocksPosition(newPosition)) {
                return Conditions.FAILED_OBSTACLE_DETECTED;
            } else if (Obstacles.blocksPath(this.position, newPosition)) {
                return Conditions.FAILED_OBSTACLE_DETECTED;
            } else if (Mine.inMine(newPosition)) {
                this.position = newPosition;
                return Conditions.IN_MINE;
            } else if (Mine.overMine(this.position, newPosition)) {
                this.position = newPosition;
                return Conditions.IN_MINE;
            } else if (Position.collideWithRobot(newPosition)) {
                newX = newPosition.getX();
                newY = newPosition.getY();
                if (Direction.NORTH.equals(this.currentDirection)) {
                    newY -= 1;
                } else if (Direction.SOUTH.equals(this.currentDirection)) {
                    newY += 1;
                } else if (Direction.EAST.equals(this.currentDirection)) {
                    newX -= 11;
                } else if (Direction.WEST.equals(this.currentDirection)) {
                    newX += 1;
                }
                this.position = new Position(newX, newY);
                return Conditions.COLLIDED_WITH_ROBOT;
            } else if (Position.inRobotPath(this.position, newPosition)){
                this.position = newPosition;
                return Conditions.COLLIDED_WITH_ROBOT;
            } else if (Pits.inPit(newPosition)){
                this.position = newPosition;
                return Conditions.IN_PIT;
            } else if (Pits.overPit(this.position , newPosition)) {

                return Conditions.IN_PIT;
            }
            this.position = newPosition;
            return Conditions.SUCCESS;
        }
        return Conditions.FAILED_OUTSIDE_ROBOT_WORLD;
    }

    /** Function checks to see if Position isIn the specified area
     *
     * @param position the Position of the new position of the robot
     * @return boolean if the Position is in the area
     */
    public boolean isNewPositionAllowed(Position position) {
        return position.isIn(new Position(-1*(World.horizontalLength/2), World.verticalLength/2), new Position(World.horizontalLength/2, -1*(World.verticalLength/2)));
    }


    /**
     * Record the damages dealt to the robot
     */
    public void setPurged(){
        Purged = true;
    }

    public boolean getPurged(){
        return Purged;
    }

    /**
     * records the health of the robot
     * @return remaining health of the robot
     */
    public int getShields() {
        return shields;
    }

    /**
     * sets the health of the robot
     */
    public void setShields(){
        this.shields = maxShields;
    }

    public void setMines(){
        this.mines = maxMines;
    }

    public void plantMine() {
        this.mines -= 1;
    }

    public int getMines(){
        return mines;
    }

    public int getMaxMines() {
        return this.maxMines;
    }

    /**
     * shoot bullets
     */
    public void fire(){
        this.shots -= 1;
    }

    /**
     * gets the robot bullets
     * @return gun bullets
     */
    public int getShots(){
        return this.shots;
    }

    /**
     * loads the bullets
     */
    public void setShots(){
        this.shots = maxShots;
    }

    public int getMaxShots() {
        return maxShots;
    }

    public int getShotDistance() {
        return shotDistance;
    }

    public int getMaxShields() {
        return maxShields;
    }

    public int getReloadTime() {
        return reloadTime;
    }

    public int getRepairTime() {
        return repairTime;
    }

    public int convertSecondsToMilliseconds(int seconds){
        return seconds*1000;
    }

    public void collidedWithRobot(){
        shields -= 1;
    }

    public void setGotShotTrue(){
        gotShot = true;
    }

    public void setGotShotFalse(){
        gotShot = false;
    }

    public void shotByBullet(){
        shields -= 1;
    }

    public String getGotShotMessage(){
        return gotShotMessage;
    }

    public void setGotShotMessage(String name){
        gotShotMessage = name + " shot you.";
    }

    public boolean GotShot(){
        return gotShot;
    }

    public void inMine(){
        shields -= 2;
        if (shields <= 0) {
            setDead();
        }
    }


    /**
     * get robot position update
     * @return Json array
     */
    public JSONArray getPositionState(){
        JSONArray positionState = new JSONArray();
        positionState.put(getPosition().getX());
        positionState.put(getPosition().getY());
        return positionState;
    }

    /**
     * gets the direction of the robot
     * @return robot direction
     */
    public String getDirectionString(){
        switch(getCurrentDirection()){
            case NORTH:
                return "North";
            case SOUTH:
                return "South";
            case EAST:
                return "East";
            case WEST:
                return "West";
        }
        return  null;
    }

    /**
     * gets the reply from the server
     * @return reply JSON Object
     */



    /**
     * sets the status type
     * @param statusType the status type string
     */
//    public void setStatusType(String statusType){
//        this.statusType = statusType;
//    }

    /**
     * report status type
     * @return status types
     */
//    public String getStatusType(){
//        return this.statusType;
//    }


    public void createRobot(){
//        Turtle turtle = new Turtle();
    }

    public String getOperationalStatus(){
        setOperationalStatus();
        return operationalStatus;
    }

    public void setOperationalStatus(){
        if (shields == maxShields){
            operationalStatus = "Normal";
        } else if (shields == 1){
            operationalStatus = "Critical";
        } else if (shields <= 0){
            operationalStatus = "Dead";
        }
    }

    public void setDead() {
        shields = 0;
        isDead = true;
    }
}



