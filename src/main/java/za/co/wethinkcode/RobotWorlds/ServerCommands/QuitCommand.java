package za.co.wethinkcode.RobotWorlds.ServerCommands;

public class QuitCommand extends ServerCommand{


    public QuitCommand(){
        super("quit");
    }

    @Override
    public boolean runCommand() {
        System.exit(0);
        return true;
    }

    @Override
    public boolean runCommand(String instruction) {
        return false;
    }
}
