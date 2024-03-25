package za.co.wethinkcode.RobotWorlds.ServerCommands;

public class InvalidCommand extends ServerCommand {
    public InvalidCommand(String name) {
        super(name);
    }

    @Override
    public boolean runCommand() {
        return false;
    }

    @Override
    public boolean runCommand(String instruction) {
        System.out.println("Unsupported server command: " + instruction + "\nUse 'help' to view all valid commands");
        return true;
    }
}
