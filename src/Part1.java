import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Part1 {
    public static void start() {
        ShortestPath paths = new ShortestPath("stop_times.txt", "transfers.txt", "stops.txt");
        Scanner input = new Scanner(System.in);
        boolean exit = false;
        System.out.println("\nWelcome to the shortest-paths service.");
        while(!exit){
            System.out.println("Please enter the stop_ids (place a ',' between the two ids), " +
                    "or enter 'back' to go back to the previous menu: ");
            if(input.hasNext()){
                String stops_id = input.next();
                String[] inputArray = stops_id.split(",");
                if(inputArray.length == 2){
                    if(paths.indexes.containsKey(inputArray[0].trim()) && paths.indexes.containsKey(inputArray[1].trim())){
                        String fromID = inputArray[0].trim();
                        String toID = inputArray[1].trim();
                        int from = paths.indexes.get(fromID);
                        int to = paths.indexes.get(toID);
                        ArrayList<String> pathSeq = paths.searchShortest(from, to);
                        if(pathSeq.isEmpty()){
                            System.out.printf("No paths from stop %s to stop %s.\n", fromID, toID);
                        } else {
                            Collections.reverse(pathSeq);
                            System.out.print("The sequence of stops is ");
                            for (String s : pathSeq) {
                                System.out.printf((s.equals(toID)) ? "%s, " : "%s -> ", s);
                            }
                            System.out.printf("and the cost is %.2f.\n", paths.distTo[from][to]);
                        }
                    } else{
                        System.out.println("No stops found! Please enter a valid stop id.");
                    }
                } else if(stops_id.equalsIgnoreCase("back")){
                    exit = true;
                } else{
                    System.out.println("Incorrect input format!");
                }
            }
        }
    }
}
