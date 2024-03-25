package za.co.wethinkcode.RobotWorlds;

public class Mine {

    private int x;
    private int y;

    public Mine(Position position){
        this.x = position.getX();
        this.y = position.getY();
    }


    public static Mine getMine(Position robotPosition){
        return new Mine(robotPosition);
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public static boolean inMine(Position robotPosition){
        for (int i = 0; i < World.mines.size() ; i ++){
            if ((robotPosition.getX() == World.mines.get(i).getX()) && (robotPosition.getY() == World.mines.get(i).getY())){
                World.mines.remove(World.mines.get(i));
                return true;
            } else
                return false;
        }
        return false;
    }

    public static boolean overMine(Position currentRobotPosition , Position newRobotPosition){
        for (int i = 0; i < World.mines.size() ; i ++) {
            if (World.mines.get(i).sameX(currentRobotPosition, newRobotPosition, World.mines.get(i)) && World.mines.get(i).inYRange(currentRobotPosition, newRobotPosition, World.mines.get(i))){
                World.mines.remove(World.mines.get(i));
            return true;
            }else if (World.mines.get(i).sameY(currentRobotPosition , newRobotPosition , World.mines.get(i)) && World.mines.get(i).inXRange(currentRobotPosition , newRobotPosition , World.mines.get(i))) {
                World.mines.remove(World.mines.get(i));
                return true;
            }
        }
        return false;
    }

    private boolean sameX(Position robotPosition ,Position newRobotPosition , Mine mine) {
        return robotPosition.getX() == mine.getX() && mine.getX() == newRobotPosition.getX();
    }

    private boolean sameY(Position robotPosition ,Position newRobotPosition , Mine mine) {
        return robotPosition.getY() == mine.getY() && mine.getY() == newRobotPosition.getY();
    }

    private boolean inYRange(Position robotPosition ,Position newRobotPosition , Mine mine) {
        return (robotPosition.getY() < mine.getY() && newRobotPosition.getY() > mine.getY()) || (robotPosition.getY() > mine.getY() && newRobotPosition.getY() < mine.getY());
    }

    private boolean inXRange(Position robotPosition ,Position newRobotPosition , Mine mine){
        return (robotPosition.getX() < mine.getX() && newRobotPosition.getX() > mine.getX()) || (robotPosition.getX() > mine.getX() && newRobotPosition.getX() < mine.getX());
    }


}
