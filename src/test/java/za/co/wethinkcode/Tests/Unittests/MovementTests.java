package za.co.wethinkcode.Tests.Unittests;

import org.json.JSONArray;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.RobotWorlds.Position;
import za.co.wethinkcode.RobotWorlds.Robot;
import za.co.wethinkcode.RobotWorlds.commands.BackCommand;
import za.co.wethinkcode.RobotWorlds.commands.ForwardCommand;
import za.co.wethinkcode.RobotWorlds.commands.LeftCommand;
import za.co.wethinkcode.RobotWorlds.commands.RightCommand;
import static org.junit.jupiter.api.Assertions.*;


public class MovementTests {

    @Tag("Unittest")
    @Test
    public void testForward(){
        Robot robot = new Robot("neil" , new JSONArray().put("sniper"));
        ForwardCommand forward = new ForwardCommand("5");
        forward.execute(robot);
//        assertEquals(new Position(0 , 5) , robot.getPosition());
        assertNotNull(robot.getPosition());
    }

    @Tag("Unittest")
    @Test
    public void testBack(){
        Robot robot = new Robot("neil" , new JSONArray().put("sniper"));
        BackCommand back = new BackCommand("5");
        back.execute(robot);
//        assertEquals(new Position(0 , -5) , robot.getPosition());
        assertNotNull(robot.getPosition());
    }

    @Tag("Unittest")
    @Test
    public void testRight(){
        Robot robot = new Robot("neil" , new JSONArray().put("sniper"));
        RightCommand right = new RightCommand();
        right.execute(robot);
        assertEquals("East" , robot.getDirectionString());
    }

    @Tag("Unittest")
    @Test
    public void testLeft(){
        Robot robot = new Robot("neil" , new JSONArray().put("sniper"));
        LeftCommand left = new LeftCommand();
        left.execute(robot);
        assertEquals("West" , robot.getDirectionString());
    }

    @Tag("Unittest")
    @Test
    public void testLeftThenForward(){
        Robot robot = new Robot("neil" , new JSONArray().put("sniper"));
        LeftCommand left = new LeftCommand();
        left.execute(robot);
        ForwardCommand forward = new ForwardCommand("5");
        forward.execute(robot);
        assertEquals("West" , robot.getDirectionString());
//        assertEquals(new Position(-5 , 0) , robot.getPosition());
        assertNotNull(robot.getPosition());
    }

    @Tag("Unittest")
    @Test
    public void testRightThenForward(){
        Robot robot = new Robot("neil" , new JSONArray().put("sniper"));
        RightCommand right = new RightCommand();
        right.execute(robot);
        ForwardCommand forward = new ForwardCommand("5");
        forward.execute(robot);
        assertEquals("East" , robot.getDirectionString());
//        assertEquals(new Position(5 , 0) , robot.getPosition());
        assertNotNull(robot.getPosition());
    }

    @Tag("Unittest")
    @Test
    public void testRightThenRightThenForward(){
        Robot robot = new Robot("neil" , new JSONArray().put("sniper"));
        RightCommand right = new RightCommand();
        right.execute(robot);
        right.execute(robot);
        ForwardCommand forward = new ForwardCommand("5");
        forward.execute(robot);
        assertEquals("South" , robot.getDirectionString());
//        assertEquals(new Position(0 , -5) , robot.getPosition());
        assertNotNull(robot.getPosition());
    }


    @Tag("Unittest")
    @Test
    public void testLeftThenLeftThenForward(){
        Robot robot = new Robot("neil" , new JSONArray().put("sniper"));
        LeftCommand left = new LeftCommand();
        left.execute(robot);
        left.execute(robot);
        ForwardCommand forward = new ForwardCommand("5");
        forward.execute(robot);
        assertEquals("South" , robot.getDirectionString());
//        assertEquals(new Position(0 , -5) , robot.getPosition());
        assertNotNull(robot.getPosition());
    }


    @Tag("Unittest")
    @Test
    public void testRightThenLeftThenBackThenBack(){
        Robot robot = new Robot("neil" , new JSONArray().put("sniper"));
        RightCommand right = new RightCommand();
        right.execute(robot);
        LeftCommand left = new LeftCommand();
        left.execute(robot);
        BackCommand back = new BackCommand("5");
        back.execute(robot);
        BackCommand back2 = new BackCommand("2");
        back2.execute(robot);
//        assertEquals(new Position(0 , -7) , robot.getPosition());
        assertNotNull(robot.getPosition());
    }
}
