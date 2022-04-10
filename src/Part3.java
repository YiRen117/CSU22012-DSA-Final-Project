import java.util.Scanner;

public class Part3 {
    public static void start() {
        SearchByTime timeSearch = new SearchByTime("stop_times.txt");
        Scanner input = new Scanner(System.in);
        boolean exit = false;
        System.out.println("\nWelcome to the search-by-time service.");
        while(!exit) {
            System.out.println("Please enter the time (in 'hh:mm:ss' format), " +
                    "or enter 'back' to go back to the previous menu: ");
            if (input.hasNext()) {
                String time = input.next();
                String[] inputArray = time.split(":");
                if (inputArray.length == 3 && inputArray[0].length() >= 1 && inputArray[0].length() <= 2 &&
                        inputArray[1].length() == 2 && inputArray[2].length() == 2) {
                    if(Integer.parseInt(inputArray[0]) <= 23 && Integer.parseInt(inputArray[1]) <= 59 &&
                            Integer.parseInt(inputArray[2]) <= 59){
                        timeSearch.readFile(time);
                        if (!timeSearch.trip_id.isEmpty()) {
                            System.out.println("------------------------------------------------------------------------" +
                                    "------------------------------------------------------------------------");
                            System.out.printf("%10s %15s %15s %10s %15s %15s %15s %15s %20s\n", "TRIP ID", "ARRIVAL TIME",
                                    "DEPARTURE TIME", "STOP ID", "STOP SEQUENCE", "STOP HEADSIGN", "PICKUP TYPE",
                                    "DROP OFF TYPE", "SHAPE DIST TRAVELED");
                            System.out.println("------------------------------------------------------------------------" +
                                    "------------------------------------------------------------------------");
                            for(int i = 0; i < timeSearch.trip_id.size(); i++){
                                System.out.format("%10s %15s %15s %10s %15s %15s %15s %15s %20s\n", timeSearch.trip_id.get(i),
                                        timeSearch.arrival_time.get(i), timeSearch.departure_time.get(i), timeSearch.stop_id.get(i),
                                        timeSearch.stop_sequence.get(i), timeSearch.stop_headsign.get(i), timeSearch.pickup_type.get(i),
                                        timeSearch.drop_off_type.get(i), timeSearch.shape_dist_traveled.get(i));
                            }
                            System.out.println("------------------------------------------------------------------------" +
                                    "------------------------------------------------------------------------");
                        } else {
                            System.out.println("No trips found.");
                        }
                    } else {
                        System.out.println("WARNING: Maximum time allowed is 23:59:59.");
                    }
                } else if (time.equalsIgnoreCase("back")) {
                    exit = true;
                } else {
                    System.out.println("Incorrect input format!");
                }
            }
        }
    }
}
