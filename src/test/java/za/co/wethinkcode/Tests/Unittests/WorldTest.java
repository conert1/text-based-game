package za.co.wethinkcode.Tests.Unittests;

import org.json.JSONArray;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.RobotWorlds.*;


import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class WorldTest {

    World world = new World();

    @Test
    public void addRobots(){
        Robot Igni = new Robot("Igni" , new JSONArray().put("sniper"));
        world.addRobots(Igni);
        assertEquals(1 , World.robots.size());
        World.robots.remove(Igni);
    }

    @Test
    public void ObstaclesTest(){
        World.setDimensions(300 , 300 , 12 , 0);
        World.clearObstacles();
        world.generateObstacles(12);
        //number of obstacles generated
        assertEquals(12, World.obstacles.size());
        World.clearObstacles();
    }

    @Test
    public void testObstaclePositions(){
        World.clearObstacles();
        world.setObstacle(1 , 1);
        assertEquals(1 , World.obstacles.get(0).getBottomLeftX());
        assertEquals(1 , World.obstacles.get(0).getBottomLeftY());
        World.clearObstacles();
    }

    @Test
    public void PitsTest(){
        World.clearPits();
        World.setDimensions(300 , 300 , 0 , 12);
        world.generatePits(12);
        assertEquals( 12 ,World.pits.size());
        World.clearPits();
    }

    @Test
    public void testPitsPosition(){
        World.clearPits();
        world.setPits(4 , 9);
        assertEquals(4 , World.pits.get(0).getBottomLeftX());
        assertEquals(9 , World.pits.get(0).getBottomLeftY());
        World.clearPits();
    }

    @Test
    public void minesTest(){
        World.clearMines();
        world.setMine(1 , 7);
        assertEquals(1 , World.mines.size());
        World.clearMines();
    }

    @Test
    public void testMinesPosition(){
        World.clearMines();
        world.setMine(4 , 7);
        world.setMine(18 , 90);
        Mine mine1 = World.mines.get(0);
        Mine mine2 = World.mines.get(1);
        assertEquals(4 , mine1.getX());
        assertEquals(7 , mine1.getY());
        assertEquals(18 , mine2.getX());
        assertEquals(90 , mine2.getY());
    }

}
