import java.util.Scanner;

public class UserInterface{
    public static void main(String[] args) {
        System.out.println("Welcome to the searching service for Vancouver public transport system.\n");
        System.out.println("1. We can help finding shortest paths between 2 bus stops (as input by the user), " +
                "returning the list of stops and route as well as the associated “cost”. ");
        System.out.println("2. We can search for a bus stop by full name or by the first few characters in the name, " +
                "returning the full stop information for each stop matching the search criteria.");
        System.out.println("3. We can search for all trips with a given arrival time, returning full details " +
                "of all trips matching the criteria, sorted by trip id.\n");
        System.out.println("By entering '1', jump to the shortest-paths service.");
        System.out.println("By entering '2', jump to the search-by-name service.");
        System.out.println("By entering '3', jump tp the search-by-time service.\n");
        Scanner input = new Scanner(System.in);
        boolean exit = false;
        while(!exit) {
            System.out.println("Please select a service (enter 'quit' for exit):");
            if (input.hasNext()) {
                switch (input.next()) {
                    case "1" -> Part1.start();
                    case "2" -> Part2.start();
                    case "3" -> Part3.start();
                    case "quit" -> exit = true;
                    default -> System.out.println("Please select among '1', '2' and '3'.");
                }
            }
        }
    }
}