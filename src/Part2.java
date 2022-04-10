import java.util.Scanner;

public class Part2 {
    public static void main(String[] args) {
        SearchByName nameSearch = new SearchByName("stops.txt");
        Scanner input = new Scanner(System.in);
        boolean exit = false;
        while(!exit) {
            System.out.println("Please enter the name(in UPPERCASE), or enter 'quit' to exit: ");
            if (input.hasNext()) {
                String name = input.next();
                nameSearch.search(name);
                if (!nameSearch.id.isEmpty()) {
                    System.out.printf("The stops that includes '%s' is as below:\n", name);
                    System.out.println("-----------------------------------------------------------------------------" +
                            "----------------------------------------------------------------------------------------");
                    System.out.printf("%10s %10s %45s %45s %15s %15s %12s %10s %15s %18s\n", "STOP ID", "STOP CODE",
                            "STOP NAME", "STOP DESC", "STOP LAT", "STOP LON", "ZONE ID", "STOP URL",
                            "LOCATION TYPE", "PARENT STATION");
                    System.out.println("-----------------------------------------------------------------------------" +
                            "----------------------------------------------------------------------------------------");
                    for(int i = 0; i < nameSearch.id.size(); i++){
                        System.out.format("%10s %10s %45s %45s %15s %15s %12s %10s %15s %18s\n", nameSearch.id.get(i),
                                nameSearch.s_code.get(i), nameSearch.s_name.get(i), nameSearch.s_desc.get(i),
                                nameSearch.s_lat.get(i), nameSearch.s_lon.get(i), nameSearch.zone.get(i),
                                nameSearch.s_url.get(i), nameSearch.loc_type.get(i), nameSearch.par_station.get(i));
                    }
                    System.out.println("-----------------------------------------------------------------------------" +
                            "----------------------------------------------------------------------------------------");
                } else if (name.equals("quit")) {
                    exit = true;
                } else {
                    System.out.println("No stops found");
                }
            }
        }
    }
}
