package za.co.wethinkcode.RobotWorlds.ServerCommands;

import za.co.wethinkcode.RobotWorlds.Restore;
import za.co.wethinkcode.RobotWorlds.World;

public class RestoreCommand extends ServerCommand{

    static String tableName;


    public RestoreCommand() {
        super("restore");
    }

    public static void databaseName(String arg) {
        tableName = arg;
    }

    @Override
    public boolean runCommand() {
        Restore restore = new Restore();
        restore.ClearAll();
        restore.RestoreWorld();
        return true;
    }

    public static String databaseName(){
        return tableName;
    }

    @Override
    public boolean runCommand(String instruction) {
        return false;
    }
}
