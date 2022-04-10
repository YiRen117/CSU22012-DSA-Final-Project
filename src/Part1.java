import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Part1 {
    public static void main(String[] args) {
        ShortestPath paths = new ShortestPath("stop_times.txt", "transfers.txt", "stops.txt");
        Scanner input = new Scanner(System.in);
        boolean exit = false;
        while(!exit){
            System.out.println("Please enter the stop_ids (place a ',' between the two ids), or enter 'quit' to exit: ");
            if(input.hasNext()){
                String stops_id = input.next();
                String[] inputArray = stops_id.split(",");
                if(inputArray.length == 2){
                    if(paths.indexes.containsKey(inputArray[0]) && paths.indexes.containsKey(inputArray[1])){
                        String fromID = inputArray[0];
                        String toID = inputArray[1];
                        int from = paths.indexes.get(fromID);
                        int to = paths.indexes.get(toID);
                        ArrayList<String> pathSeq = paths.searchShortest(from, to);
                        Collections.reverse(pathSeq);
                        System.out.print("The sequence of stops is ");
                        for(String s : pathSeq){
                            System.out.printf((s.equals(toID)) ? "%s.\n" : "%s, ", s);
                        }
                    } else{
                        System.out.println("No stops found! Please enter a valid stop id.");
                    }
                } else if(stops_id.equals("quit")){
                    exit = true;
                } else{
                    System.out.println("Incorrect input format!");
                }
            }
        }
    }
}
