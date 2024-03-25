package za.co.wethinkcode.Tests.Unittests;

import org.json.JSONArray;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.RobotWorlds.Robot;
import za.co.wethinkcode.RobotWorlds.World;
import za.co.wethinkcode.RobotWorlds.commands.ForwardCommand;
import za.co.wethinkcode.RobotWorlds.commands.LookCommand;
import za.co.wethinkcode.RobotWorlds.commands.MineCommand;

import static org.junit.jupiter.api.Assertions.*;


public class LookCommandTest {

    World world = new World();


    @Test
    public void testObstacles(){
        World.setDimensions(300 , 300 , 0 , 0);
        world.setObstacle(0 , 1);
        Robot Hal = new Robot("Hal" , new JSONArray().put("sniper"));
        LookCommand Look = new LookCommand();
        Look.execute(Hal);
        assertEquals("There is an obstacle : \n" +
                "               To the North of my position. (0,1) to (4,5)\n" +
                "           There are no visible bottomless pits.\n" +
                "           There are no visible robots.\n" +
                "           There are no visible mines." , String.valueOf(LookCommand.string));
        world.clearObstacles();
    }

    @Test
    public void testPits(){
        World.setDimensions(300 , 300 , 0 , 0);
        world.setPits(-1 , 1);
        Robot Hal = new Robot("Hal" , new JSONArray().put("sniper"));
        LookCommand Look = new LookCommand();
        Look.execute(Hal);
        assertEquals("There are no visible obstacles.\n" +
                "           There is a bottomless pit : \n" +
                "               To the North of my position. (-1,1) to (3,5)\n" +
                "           There are no visible robots.\n" +
                "           There are no visible mines.", String.valueOf(LookCommand.string));
        world.clearPits();
    }

    @Test
    public void testRobots(){
        World.setDimensions(300 , 300 , 0 , 0);
        Robot Hal = new Robot("Hal" , new JSONArray().put("sniper"));
        Robot Gap = new Robot("Gap" , new JSONArray().put("bomber"));
        World.robots.add(Gap);
        ForwardCommand forward = new ForwardCommand("5");
        forward.execute(Gap);
        LookCommand Look = new LookCommand();
        Look.execute(Hal);
        assertEquals("There are no visible obstacles.\n" +
                "           There are no visible bottomless pits.\n" +
                "           There is a robot : \n" +
                "               (Gap) To the North of my position. [0,5]\n" +
                "           There are no visible mines." , String.valueOf(LookCommand.string));
        World.robots.remove(Gap);
    }

    @Test
    public void testMines(){
        World.setDimensions(300 , 300 , 0 , 0);
        Robot Gap = new Robot("Gap" , new JSONArray().put("bomber"));
        World.robots.add(Gap);
        MineCommand mine = new MineCommand();
        mine.execute(Gap);
        LookCommand Look = new LookCommand();
        Look.execute(Gap);
        System.out.println(LookCommand.string);
        assertEquals("There are no visible obstacles.\n" +
                "           There are no visible bottomless pits.\n" +
                "           There are no visible robots.\n" +
                "           There is a mine : \n" +
                "               To the South of my position. [0,0]\n" , String.valueOf(LookCommand.string));
        World.robots.remove(Gap);
    }




}
