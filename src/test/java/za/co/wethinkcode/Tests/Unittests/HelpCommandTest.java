package za.co.wethinkcode.Tests.Unittests;

import org.json.JSONArray;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.RobotWorlds.Robot;
import za.co.wethinkcode.RobotWorlds.commands.HelpCommand;

import static org.junit.jupiter.api.Assertions.*;

public class HelpCommandTest {

    @Test
    public void testHelpCommand(){
        HelpCommand help = new HelpCommand();
        help.execute(new Robot("Hal" , new JSONArray().put("sniper")));
        assertEquals("\nquit    - Shut down robot.\nhelp    - Display all valid robot commands.\nforward - Move forward by a given amount of steps.\nback    - Move back by a given amount of steps.\nright   - Turn right by 90 degrees.\nleft    - Turn left by 90 degrees.\nfire    - Fire your weapon.\nmine    - Set a mine.\nlook    - Display all objects in line of sight.",HelpCommand.string.toString());
    }
}
