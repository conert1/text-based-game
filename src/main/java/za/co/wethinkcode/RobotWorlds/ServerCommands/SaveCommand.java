package za.co.wethinkcode.RobotWorlds.ServerCommands;

import za.co.wethinkcode.RobotWorlds.DbConnect;

public class SaveCommand extends ServerCommand{

    static String word;

    public SaveCommand() {
        super("save");
    }


    public static String tableName() {
        return word;
    }

    @Override
    public boolean runCommand() {
        DbConnect.createDataBase();
        return true;
    }


    @Override
    public boolean runCommand(String instruction) {
        return false;
    }

    public static void tableName(String name){
        word = name;
    }
}
