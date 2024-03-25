package za.co.wethinkcode.RobotWorlds;

import org.json.JSONObject;

import java.util.ArrayList;

public class World {
    JSONObject ourWorld = new JSONObject();

    public static ArrayList<SquareObstacle> obstacles = new ArrayList<>();

    public static ArrayList<SquareObstacle> pits = new ArrayList<>();

    public static ArrayList<Robot> robots = new ArrayList<>();
    public static ArrayList<Mine> mines = new ArrayList<>();

    public static ArrayList<Position> edges = new ArrayList<>();

    static int northEdge;
    static int southEdge;
    static int westEdge;
    static int eastEdge;
    static int verticalLength;
    static int horizontalLength;
    static int obstacleNum;
    static int pitNum;


    public void addRobots(Robot robot){
        robots.add(robot);
    }

    public static void setDimensions(int XLength , int VLength , int obstacles ,int pits) {
        edges.add(new Position(XLength , VLength));
        horizontalLength = XLength;
        verticalLength = VLength;
        eastEdge = -1 * (horizontalLength/2);
        westEdge = horizontalLength/2;
        southEdge = -1 * (verticalLength/2);
        northEdge = (verticalLength/2);
        pitNum = pits;
        obstacleNum = obstacles;
    }

    public void generateObstacles(int obstacleNum){
        for (int i=0; i<obstacleNum; i++){
            obstacles.add(Obstacles.getObstacle(horizontalLength , verticalLength));
        }
    }


    public void generatePits(int pitNum) {
        for (int i = 0; i < pitNum; i++) {
            pits.add(Pits.getPit(horizontalLength, verticalLength));
        }
    }

    public void setMine(int x , int y){
        mines.add(new Mine(new Position(x , y)));
    }

    public static void clearObstacles(){
        obstacles = new ArrayList<>();
    }

    public static void clearPits(){
        pits = new ArrayList<>();
    }

    public static void clearMines(){
        mines = new ArrayList<>();
    }

    public void setObstacle(int x, int y){
        obstacles.add(new SquareObstacle(x , y));
    }

    public void setPits(int x , int y){
        pits.add(new SquareObstacle(x , y));
    }


}
