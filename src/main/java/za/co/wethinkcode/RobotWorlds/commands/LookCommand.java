package za.co.wethinkcode.RobotWorlds.commands;

import za.co.wethinkcode.RobotWorlds.*;


import org.json.JSONArray;
import org.json.JSONObject;
//import org.turtle.StdDraw;
import za.co.wethinkcode.RobotWorlds.Robot;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LookCommand extends Command{

    int obstacleSize = 4;

    int visibility = 10;

    String obstacleDirection;

    public static StringBuilder string;

    public LookCommand() {
        super("look");
    }

    @Override
    public boolean execute(Robot target) {
        ArrayList<SquareObstacle> visibleObstacles = visibleObstacles(target);
        ArrayList<SquareObstacle> visiblePits = visiblePits(target);
        ArrayList<Robot> visibleRobots = visibleRobots(target);
        ArrayList<Mine> visibleMines = visibleMines(target);
        visibleRobots.remove(target);
        string = new StringBuilder();
        if (visibleObstacles.size() == 0) {
            string.append("There are no visible obstacles.\n");
        } else {
            string.append("There is an obstacle : \n");
            for (SquareObstacle obstacle : visibleObstacles) {
                getObstacleDirection(obstacle, target);
                if (obstacleDirection != null) {
                    string.append("               To the ").append(obstacleDirection).append(" of my position. (").append(obstacle.getBottomLeftX()).append(",").append(obstacle.getBottomLeftY()).append(") to (").append(obstacle.getBottomLeftX() + 4).append(",").append(obstacle.getBottomLeftY() + obstacleSize).append(")\n");
                }
            }
        }
        if (visiblePits.size() == 0) {
            string.append("           There are no visible bottomless pits.\n");
        } else {
            string.append("           There is a bottomless pit : \n");
            for (SquareObstacle pit : visiblePits) {
                getObstacleDirection(pit , target);
                if (obstacleDirection != null) {
                    string.append("               To the ").append(obstacleDirection).append(" of my position. (").append(pit.getBottomLeftX()).append(",").append(pit.getBottomLeftY()).append(") to (").append(pit.getBottomLeftX() + 4).append(",").append(pit.getBottomLeftY() + obstacleSize).append(")\n");
                }
            }
        }
        if (visibleRobots.size() == 0) {
            string.append("           There are no visible robots.\n");
        } else {
            string.append("           There is a robot : \n");
            for (Robot robot : visibleRobots) {
                getRobotDirection(target , robot);
                if (obstacleDirection != null && !Objects.equals(target.getName(), robot.getName())) {
                    string.append("               (").append(robot.getName()).append(") To the ").append(obstacleDirection).append(" of my position. ").append("[").append(robot.getPosition().getX()).append(",").append(robot.getPosition().getY()).append("]\n");
                }
            }
        }
        if (visibleMines.size() == 0) {
            string.append("           There are no visible mines.");
        } else {
            string.append("           There is a mine : \n");
            for (Mine mine : visibleMines) {
                getMineDirection(target , mine);
                if (obstacleDirection != null) {
                    string.append("               To the ").append(obstacleDirection).append(" of my position. ").append("[").append(mine.getX()).append(",").append(mine.getY()).append("]\n");
                }
            }
        }
        target.setStatus(String.valueOf(string));
        return true;
    }

    private ArrayList<Mine> visibleMines(Robot target) {
        ArrayList<Mine> visibleMines = new ArrayList<>();
        for (Mine mine : World.mines) {
            if ((sameXAsMine(target, mine) || sameYAsMine(target, mine)) && ((belowMineY(target, mine) || aboveMineY(target, mine)) || (belowMineX(target, mine) || aboveMineX(target, mine))))
                visibleMines.add(mine);
        }
        return visibleMines;
    }


    /**Creates a list of all the Obstacles in range of the robots field of vision.*/
    private ArrayList<SquareObstacle> visibleObstacles(Robot target) {
        ArrayList<SquareObstacle> visibleObstacles = new ArrayList<>();
        for (SquareObstacle obstacle : World.obstacles)
            if ((xRange(target , obstacle) || yRange(target , obstacle)) && ((belowY(target , obstacle) || aboveY(target , obstacle)) || (belowX(target , obstacle) || aboveX(target , obstacle)))){
                visibleObstacles.add(obstacle);
            }
        return visibleObstacles;
    }

    /**Creates a list of all the Pits in range of the robots field of vision.*/
    private ArrayList<SquareObstacle> visiblePits(Robot target) {
        ArrayList<SquareObstacle> visiblePits = new ArrayList<>();
        for (SquareObstacle obstacle : World.pits)
            if ((xRange(target , obstacle) || yRange(target , obstacle)) && ((belowY(target , obstacle) || aboveY(target , obstacle)) || (belowX(target , obstacle) || aboveX(target , obstacle)))){
                visiblePits.add(obstacle);
            }
        return visiblePits;
    }


    /**Creates a list of all the Robots in range of the robots field of vision.*/
    private ArrayList<Robot> visibleRobots(Robot target) {
        ArrayList<Robot> visibleRobots = new ArrayList<>();
        for (Robot robot : World.robots)
            if ((sameXAsRobot(target , robot) || sameYAsRobot(target , robot)) && (((belowRobotY(target , robot)) || (aboveRobotY(target , robot))) || ((belowRobotX(target , robot) || aboveRobotX(target , robot))))) {
                visibleRobots.add(robot);
            }
        return visibleRobots;
    }



    /**Checks if robot is between the two x coordinates of the obstacle*/
    public boolean xRange(Robot target , SquareObstacle obstacle) {
        return ((target.getPosition().getX() >= obstacle.getBottomLeftX()) && (target.getPosition().getX() <= (obstacle.getBottomLeftX() + obstacleSize)));
    }

    /**Checks if robot is between the two y coordinates of the obstacle*/
    public boolean yRange(Robot target , SquareObstacle obstacle) {
        return ((target.getPosition().getY() >= obstacle.getBottomLeftY()) && (target.getPosition().getY() <= (obstacle.getBottomLeftY() + obstacleSize)));
    }

    /**Checks if Obstacle is North of the Robot*/
    private boolean belowY(Robot target , SquareObstacle obstacle){
        return (target.getPosition().getY() >= (obstacle.getBottomLeftY() - visibility)) && (target.getPosition().getY() < obstacle.getBottomLeftY());
    }

    /**Checks if the obstacle is South of the Robot*/
    private boolean aboveY(Robot target , SquareObstacle obstacle){
        return target.getPosition().getY() <= (obstacle.getBottomLeftY()+ obstacleSize+visibility) && (target.getPosition().getY() > obstacle.getBottomLeftY()+obstacleSize);
    }

    /**Returns boolean if Obstacle is West of the Robot*/
    private boolean aboveX(Robot target , SquareObstacle obstacle) {
        return target.getPosition().getX() > (obstacle.getBottomLeftX()+visibility) && target.getPosition().getX() <= (obstacle.getBottomLeftX() + obstacleSize+visibility);
    }

    /**Returns boolean if Obstacle is East of the Robot*/
    private boolean belowX(Robot target , SquareObstacle obstacle) {
        return target.getPosition().getX() >= (obstacle.getBottomLeftX() - visibility) && target.getPosition().getX() < obstacle.getBottomLeftX();
    }

    private boolean sameXAsRobot(Robot target , Robot robot){
        return (target.getPosition().getX() == robot.getPosition().getX());
    }

    private boolean sameYAsRobot(Robot target , Robot robot){
        return (target.getPosition().getY() == robot.getPosition().getY());
    }

    private boolean aboveRobotX(Robot target , Robot robot){
        return (target.getPosition().getX() > robot.getPosition().getX()) && (target.getPosition().getX() <= (robot.getPosition().getX() + visibility));
    }


    private boolean belowRobotX(Robot target , Robot robot){
        return (target.getPosition().getX() < robot.getPosition().getX()) && (target.getPosition().getX() >= (robot.getPosition().getX() - visibility));
    }

    private boolean aboveRobotY(Robot target , Robot robot) {
        return (target.getPosition().getY() > robot.getPosition().getY()) && (target.getPosition().getY() <= (robot.getPosition().getY() + visibility));
    }

    private boolean belowRobotY (Robot target , Robot robot) {
        return (target.getPosition().getY() < robot.getPosition().getY()) && (target.getPosition().getY() >= (robot.getPosition().getY() - visibility));
    }


    private boolean sameXAsMine(Robot target , Mine mine){
        return (target.getPosition().getX() == mine.getX());
    }

    private boolean sameYAsMine(Robot target , Mine mine){
        return (target.getPosition().getY() == mine.getY());
    }

    private boolean aboveMineX(Robot target , Mine mine){
        return (target.getPosition().getX() > mine.getX()) && (target.getPosition().getX() <= (mine.getX() + visibility));
    }


    private boolean belowMineX(Robot target , Mine mine){
        return (target.getPosition().getX() < mine.getX()) && (target.getPosition().getX() >= (mine.getX() - visibility));
    }

    private boolean aboveMineY(Robot target , Mine mine) {
        return (target.getPosition().getY() > mine.getY()) && (target.getPosition().getY() <= (mine.getY() + visibility));
    }

    private boolean belowMineY (Robot target , Mine mine) {
        return (target.getPosition().getY() < mine.getY()) && (target.getPosition().getY() >= (mine.getY() - visibility));
    }

    /**Checks the obstacle compass direction relative to the robots position*/
    private void getObstacleDirection(SquareObstacle obstacle, Robot target){
        if ((xRange(target ,obstacle) || yRange(target ,obstacle)))
            if (belowY(target , obstacle))
                this.obstacleDirection = "North";
            else if (aboveY(target , obstacle))
                this.obstacleDirection = "South";
            else if (aboveX(target , obstacle))
                this.obstacleDirection = "West";
            else if (belowX(target , obstacle))
                this.obstacleDirection = "East";
    }

    private void getRobotDirection(Robot target, Robot robot) {
        if (sameXAsRobot(target , robot) || sameYAsRobot(target , robot))
            if (belowRobotX(target , robot))
                this.obstacleDirection = "North";
            else if (aboveRobotX(target , robot))
                this.obstacleDirection = "West";
            else if (aboveRobotY(target , robot))
                this.obstacleDirection = "South";
            else if (belowRobotY(target , robot))
                this.obstacleDirection = "North";

    }

    private void getMineDirection(Robot target , Mine mine) {
        if (sameXAsMine(target , mine) || sameYAsMine(target , mine))
            if (belowMineX(target , mine))
                this.obstacleDirection = "North";
            else if (aboveMineX(target , mine))
                this.obstacleDirection = "West";
            else if (aboveMineY(target , mine))
                this.obstacleDirection = "South";
            else if (belowMineY(target , mine))
                this.obstacleDirection = "North";
    }
}
