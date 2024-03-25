package za.co.wethinkcode.RobotWorlds.ServerCommands;


public abstract class ServerCommand {
    private final String name;
    private String argument;

    public ServerCommand(String name){
        this.name = name.trim().toLowerCase();
        this.argument = "";
    }

    public ServerCommand(String name, String argument) {
        this(name);
        this.argument = argument.trim();
    }

    public String getName() {
        return name;
    }

    public String getArgument() {
        return this.argument;
    }

    public static ServerCommand create(String instruction) {
        String[] args = instruction.toLowerCase().trim().split(" ",2);

        switch (args[0].toLowerCase()){

            case "dump":
                return new DumpCommand();

            case "quit":
                return new QuitCommand();

            case "robots":
                return  new RobotsCommand();

            case "help":
                return new HelpCommand();

            case "save":
                SaveCommand.tableName(args[1]);
                return new SaveCommand();

            case "restore":
                try {
                    RestoreCommand.databaseName(args[1]);
                    return new RestoreCommand();
                } catch (ArrayIndexOutOfBoundsException e){
                    System.out.println("Please enter name of database!");
                    return new InvalidCommand(instruction);
                }

            case "purge":
                try {
                    PurgeCommand.setName(args[1]);
                    return new PurgeCommand();
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Please enter name of robot!");
                    return new InvalidCommand(instruction);
                }

            default:
                return new InvalidCommand(instruction);
        }
    }

    public abstract boolean runCommand();

    public abstract boolean runCommand(String instruction);
}


