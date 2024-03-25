package za.co.wethinkcode.RobotWorlds;

//import org.turtle.*;

import java.util.ArrayList;
import java.util.Random;

public class Obstacles {
    public static Random random = new Random();

    public static SquareObstacle getObstacle(int horizontalLength , int verticalLength){
            int x = (random.nextInt(horizontalLength) - horizontalLength/2);
            int y = (random.nextInt(verticalLength) - verticalLength/2);
            return new SquareObstacle(x , y);
    }


    public static boolean blocksPosition(Position a){
        for (int i = 0; i < World.obstacles.size(); i++) {
            if(World.obstacles.get(i).blocksPosition(a)){
                return true;
            }
        }
        return false;
    }

    public static boolean blocksPath(Position a, Position b){
        for (int i = 0; i < World.obstacles.size(); i++) {
            if(World.obstacles.get(i).blocksPath(a, b)){
                return true;
            }
        }
        return false;
    }
}
