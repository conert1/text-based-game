package za.co.wethinkcode.Tests.Unittests;

import org.json.JSONArray;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.RobotWorlds.Robot;
import za.co.wethinkcode.RobotWorlds.World;
import za.co.wethinkcode.RobotWorlds.commands.FireCommand;
import za.co.wethinkcode.RobotWorlds.commands.ForwardCommand;
import za.co.wethinkcode.RobotWorlds.commands.RightCommand;
import za.co.wethinkcode.RobotWorlds.commands.StateCommand;
import static org.junit.jupiter.api.Assertions.*;


public class StateCommandTest {

    @Test
    public void testState(){
        World.setDimensions(300 , 300 , 0 , 0);
        Robot robot = new Robot("robot" , new JSONArray().put("bomber"));
        StateCommand state = new StateCommand();
        state.execute(robot);
        assertEquals("\n" +
                "Position  : [0,0]\n" +
                "Direction : NORTH\n" +
                "Shields   : 5\\5\n" +
                "Shots     : 0\\0\n" +
                "Mines     : 2\\2" , StateCommand.string.toString());

    }


    @Test
    public void testStateAfterMovingForward(){
        World.setDimensions(300 , 300 , 0 , 0);
        Robot Jack = new Robot("Jack" , new JSONArray().put("bomber"));
        ForwardCommand forward = new ForwardCommand("10");
        forward.execute(Jack);
        StateCommand state = new StateCommand();
        state.execute(Jack);
        assertEquals("\n" +
                "Position  : [0,10]\n" +
                "Direction : NORTH\n" +
                "Shields   : 5\\5\n" +
                "Shots     : 0\\0\n" +
                "Mines     : 2\\2" , StateCommand.string.toString());
    }


    @Test
    public void testStateAfterTurningRight(){
        World.setDimensions(300 , 300 , 0 , 0);
        Robot Jock = new Robot("Jock" , new JSONArray().put("bomber"));
        RightCommand right = new RightCommand();
        right.execute(Jock);
        StateCommand state = new StateCommand();
        state.execute(Jock);
        assertEquals("\n" +
                "Position  : [0,0]\n" +
                "Direction : EAST\n" +
                "Shields   : 5\\5\n" +
                "Shots     : 0\\0\n" +
                "Mines     : 2\\2" , StateCommand.string.toString());
    }


    @Test
    public void testStateAfterTurningAndMoving(){
        World.setDimensions(300 , 300 , 0 , 0);
        Robot Jock = new Robot("Jock" , new JSONArray().put("bomber"));
        RightCommand right = new RightCommand();
        right.execute(Jock);
        ForwardCommand forward = new ForwardCommand("10");
        forward.execute(Jock);
        StateCommand state = new StateCommand();
        state.execute(Jock);
        assertEquals("\n" +
                "Position  : [10,0]\n" +
                "Direction : EAST\n" +
                "Shields   : 5\\5\n" +
                "Shots     : 0\\0\n" +
                "Mines     : 2\\2" , StateCommand.string.toString());
    }

    @Test
    public void testStateAfterFiring(){
        World.setDimensions(300 , 300 , 0 , 0);
        Robot Jock = new Robot("Jock" , new JSONArray().put("sniper"));
        FireCommand fire = new FireCommand();
        fire.execute(Jock);
        StateCommand state = new StateCommand();
        state.execute(Jock);
        assertEquals("\n" +
                "Position  : [0,0]\n" +
                "Direction : NORTH\n" +
                "Shields   : 3\\3\n" +
                "Shots     : 0\\1\n" +
                "Mines     : 0\\0" , StateCommand.string.toString());
    }
}
