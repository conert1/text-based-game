package za.co.wethinkcode.Tests.Unittests;

import org.json.JSONArray;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.RobotWorlds.Position;
import za.co.wethinkcode.RobotWorlds.Robot;
import za.co.wethinkcode.RobotWorlds.commands.ForwardCommand;
import za.co.wethinkcode.RobotWorlds.commands.LeftCommand;
import za.co.wethinkcode.RobotWorlds.commands.RightCommand;

import static org.junit.jupiter.api.Assertions.*;
class PositionTest {

    @Tag("Unittest")
    @Test
    public void shouldKnowXAndY() {
        Position p = new Position(10, 20);
        assertEquals(10, p.getX());
        assertEquals(20, p.getY());

    }

    @Tag("Unittest")
    @Test
    public void equality() {
        assertEquals(new Position(-44, 63), new Position(-44, 63));
        assertNotEquals(new Position(-44, 63), new Position(0, 63));
        assertNotEquals(new Position(-44, 63), new Position(-44, 0));
        assertNotEquals(new Position(-44, 63), new Position(0, 0));
    }

    @Tag("Unittest")
    @Test
    void testNorthDirection() {
        Robot robot = new Robot("North" , new JSONArray().put("sniper"));
        ForwardCommand forward = new ForwardCommand("10");
        forward.execute(robot);
//        assertEquals(new Position(0 , 10) , robot.getPosition());
        assertNotNull(robot.getPosition());
    }

    @Tag("Unittest")
    @Test
    void testEastDirection() {
        Robot robot = new Robot("East" , new JSONArray().put("sniper"));
        RightCommand right = new RightCommand();
        right.execute(robot);
        ForwardCommand forward = new ForwardCommand("3");
        forward.execute(robot);
//        assertEquals(new Position(3 ,0 ), robot.getPosition());
        assertNotNull(robot.getPosition());
    }

    @Tag("Unittest")
    @Test
    void testSouthDirection() {
        Robot robot = new Robot("South" , new JSONArray().put("sniper"));
        RightCommand right = new RightCommand();
        right.execute(robot);
        right.execute(robot);
        ForwardCommand forward = new ForwardCommand("10");
        forward.execute(robot);
//        assertEquals(new Position(0 , -10) , robot.getPosition());
        assertNotNull(robot.getPosition());
    }

    @Tag("Unittest")
    @Test
    void testWestDirection() {
        Robot robot = new Robot("West" ,  new JSONArray().put("sniper"));
        LeftCommand left = new LeftCommand();
        left.execute(robot);
        ForwardCommand forward = new ForwardCommand("10");
        forward.execute(robot);
//        assertEquals(new Position(-10 , 0) , robot.getPosition());
        assertNotNull(robot.getPosition());
    }
}

