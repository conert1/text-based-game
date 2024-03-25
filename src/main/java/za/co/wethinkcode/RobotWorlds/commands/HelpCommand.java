package za.co.wethinkcode.RobotWorlds.commands;

import org.json.JSONObject;
import za.co.wethinkcode.RobotWorlds.Robot;

public class HelpCommand extends Command {

    public static StringBuilder string;

    public HelpCommand() {
        super("help");
    }

    @Override
    public boolean execute(Robot target) {
        string = new StringBuilder();
        string.append("\nquit    - Shut down robot.\nhelp    - Display all valid robot commands.\nforward - Move forward by a given amount of steps.\nback    - Move back by a given amount of steps.\nright   - Turn right by 90 degrees.\nleft    - Turn left by 90 degrees.\nfire    - Fire your weapon.\nmine    - Set a mine.\nlook    - Display all objects in line of sight.");
        target.setStatus(string.toString());
        return true;
    }
}
