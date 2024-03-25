package za.co.wethinkcode.RobotWorlds.WebApi;


import java.io.IOException;
import java.util.*;


import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.json.JSONArray;
import za.co.wethinkcode.RobotWorlds.*;

public class RunOnJavalinServer {

    public static ArrayList<String> StringConverter(String word){
        word = word.replace("[", "");
        word = word.replace("]", "");
        String[] strSplit = word.split("\",\"");


        ArrayList<String> strList = new ArrayList<String>(Arrays.asList(strSplit));

        String fir = strList.get(0).replace("\"", "");
        strList.set(0,fir);
        String las = strList.get(strList.size()-1).replace("\"", "");
        strList.set(strList.size()-1, las);

        return strList;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Scanner key = new Scanner(System.in);
        ArrayList<String> obstacles = new ArrayList<>();
        ArrayList<String> pits = new ArrayList<>();
        ArrayList<String> mines = new ArrayList<>();


        HttpResponse<String> response2 = Unirest.get("http://localhost:4000/obstacles").asString();
        HttpResponse<String> response3 = Unirest.get("http://localhost:4000/pits").asString();
        HttpResponse<String> response4 = Unirest.get("http://localhost:4000/pits").asString();
        String word = response2.getBody();

        String WordPits = response3.getBody();
        String WordMine = response4.getBody();

        obstacles = StringConverter(word);
        for(int i=0; i<obstacles.size(); i++){
            /**adding the obstacles from the database*/

            String w = obstacles.get(i).replace("(", "");
            w = w.replace(")", "");
            int space = w.indexOf(",");

            int Obs1 = Integer.parseInt(w.substring(0, space));
            int Obs2 = Integer.parseInt(w.substring(w.lastIndexOf(",")+1));
            World.obstacles.add(new SquareObstacle(Obs1, Obs2));
        }

        pits = StringConverter(WordPits);

        pits.removeAll(Collections.singleton("nothing"));
        for(int i=0; i<pits.size(); i++){

            /**adding the obstacles from the database*/

            String w = pits.get(i).replace("(", "");
            w = w.replace(")", "");
            int space = w.indexOf(",");

            int Obs1 = Integer.parseInt(w.substring(0, space));
            int Obs2 = Integer.parseInt(w.substring(w.lastIndexOf(",")+1));
            World.pits.add(new SquareObstacle(Obs1, Obs2));
        }

        mines = StringConverter(WordMine);
        mines.removeAll(Collections.singleton("nothing"));
        System.out.println("mine "+mines);
        for(int i=0; i<pits.size(); i++){

            /**adding the mines from the database*/

            String w = pits.get(i).replace("(", "");
            w = w.replace(")", "");
            int space = w.indexOf(",");

            int Obs1 = Integer.parseInt(w.substring(0, space));
            int Obs2 = Integer.parseInt(w.substring(w.lastIndexOf(",")+1));
            World.mines.add(new Mine(new Position(Obs1, Obs2)));
        }


        /**As soon as we call the activate server the obstacles get generated automatically
         * we cannot remove those obstacles in this class because once activate server is
         * running it we cannot write code that tempers with that here
         * we can add obstacles and mines from the database we choose*/

        ActivateServer.main(new String[] {});

    }
}