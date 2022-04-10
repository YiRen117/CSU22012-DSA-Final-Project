import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SearchByTime {
    private String filename;
    public ArrayList<String> trip_id, arrival_time, departure_time, stop_id, stop_sequence,
            stop_headsign, pickup_type, drop_off_type, shape_dist_traveled;

    SearchByTime(String stop_times){
        this.trip_id = new ArrayList<>();
        this.arrival_time = new ArrayList<>();
        this.departure_time = new ArrayList<>();
        this.stop_id = new ArrayList<>();
        this.stop_sequence = new ArrayList<>();
        this.stop_headsign = new ArrayList<>();
        this.pickup_type = new ArrayList<>();
        this.drop_off_type = new ArrayList<>();
        this.shape_dist_traveled = new ArrayList<>();
        this.filename = stop_times;
    }

    public void readFile(String inputTime){
        try{
            FileReader fileReader = new FileReader(filename);
            BufferedReader buffer = new BufferedReader(fileReader);
            boolean endOfFile = false;
            buffer.readLine();
            while(!endOfFile){
                String data = buffer.readLine();
                if (data != null){
                    String[] dataArray = data.trim().split(",");
                    if(dataArray[1].trim().equals(inputTime)){
                        trip_id.add(dataArray[0].trim());
                        arrival_time.add(dataArray[1].trim());
                        departure_time.add(dataArray[2].trim());
                        stop_id.add(dataArray[3].trim());
                        stop_sequence.add(dataArray[4].trim());
                        stop_headsign.add(dataArray[5].trim());
                        pickup_type.add(dataArray[6].trim());
                        drop_off_type.add(dataArray[7].trim());
                        shape_dist_traveled.add(dataArray[8].trim());
                    }
                }
                else{
                    endOfFile = true;
                }
            }
            buffer.close();
            fileReader.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }


}
