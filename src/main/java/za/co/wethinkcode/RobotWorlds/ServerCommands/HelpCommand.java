package za.co.wethinkcode.RobotWorlds.ServerCommands;

public class HelpCommand extends ServerCommand{


    public HelpCommand(){
        super("help");
    }

    @Override
    public boolean runCommand() {
        System.out.println("quit   - Disconnects all clients and shuts down the server.\ndump   - Displays the positions of all obstacles,mines,bottomless pits and robots in the world.\nrobots - Displays all the robots in the world and their respective states.\nhelp   - Displays all the available valid server commands.");
        return true;
    }

    @Override
    public boolean runCommand(String instruction) {
        return false;
    }
}
