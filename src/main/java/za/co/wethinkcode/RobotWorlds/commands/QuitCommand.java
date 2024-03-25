package za.co.wethinkcode.RobotWorlds.commands;

import za.co.wethinkcode.RobotWorlds.Robot;

public class QuitCommand extends Command{

    public QuitCommand() {
        super("quit");
    }

    public boolean execute(Robot target) {
        target.setStatus("Shutting down...");
        return true;
    }

}
