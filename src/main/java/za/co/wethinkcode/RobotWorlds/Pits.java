package za.co.wethinkcode.RobotWorlds;

import java.util.*;
import java.util.Random;
public class Pits {
    public static Random random = new Random();
    public static SquareObstacle getPit(int horizontalLength , int verticalLength){
        int x = (random.nextInt(horizontalLength) - horizontalLength/2);
        int y = (random.nextInt(verticalLength) - verticalLength/2);
        return new SquareObstacle(x , y);
    }

    public static boolean inPit(Position robotPosition){
        for (int i = 0; i < World.pits.size(); i++){
            if (inXRangeOfPit(robotPosition , World.pits.get(i)) && inYRangeOfPit(robotPosition , World.pits.get(i))){
                return true;
            }
        }
        return false;
    }

    public static boolean overPit(Position currentPosition , Position newPosition){
        for (int i = 0; i < World.pits.size(); i++){
            if (inXRangeOfPit(currentPosition , World.pits.get(i)) && passY(currentPosition , newPosition , World.pits.get(i))){
                return true;
            } else if (inYRangeOfPit(currentPosition , World.pits.get(i)) && passX(currentPosition , newPosition , World.pits.get(i))){
                return true;
            }
        }
        return false;
    }

    private static boolean inXRangeOfPit(Position robotPosition , SquareObstacle pit){
        return ((robotPosition.getX() >= pit.getBottomLeftX()) && (robotPosition.getX() <= (pit.getBottomLeftX() + 4)));
    }

    private static boolean inYRangeOfPit(Position robotPosition , SquareObstacle pit){
        return ((robotPosition.getY() >= pit.getBottomLeftY()) && (robotPosition.getY() <= (pit.getBottomLeftY() + 4)));
    }

    private static boolean passX(Position currentPosition , Position newPosition , SquareObstacle pit){
        if (currentPosition.getX() < pit.getBottomLeftX() && newPosition.getX() > (pit.getBottomLeftX() + 4)){
            return true;
        } else return currentPosition.getX() > (pit.getBottomLeftX() + 4) && newPosition.getX() < pit.getBottomLeftX();
    }

    private static boolean passY(Position currentPosition , Position newPosition , SquareObstacle pit){
        if (currentPosition.getY() < pit.getBottomLeftY() && newPosition.getY() > (pit.getBottomLeftY() + 4)){
            return true;
        } else return currentPosition.getY() > (pit.getBottomLeftY() + 4) && newPosition.getY() < pit.getBottomLeftY();
    }
}
