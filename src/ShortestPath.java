import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ShortestPath {

    public Map<String, String> names;
    public Map<String, Integer> indexes;
    public double[][] distTo;
    public int[][] edgeTo;

    /**
     * @param trips: A filename containing the details of the trips
     * @param transfers: A filename containing the details of the transfers between stops
     */
    ShortestPath(String trips, String transfers, String stops) {
        this.names = new HashMap<>();
        this.indexes = new HashMap<>();
        generateStopName(stops);
        generateRoutes(transfers, trips);
    }


    /**
     * Generate the stops according to the stops.txt given
     */
    private void generateStopName(String stops){
        try{
            FileReader stopsReader = new FileReader(stops);
            BufferedReader stopsBuffer = new BufferedReader(stopsReader);
            boolean endOfFile = false;
            int index = 0;
            while(!endOfFile){
                String data = stopsBuffer.readLine();
                if (data != null){
                    String[] dataArray = data.trim().split(",");
                    names.put(dataArray[0].trim(), dataArray[2].trim());
                    indexes.put(dataArray[0].trim(), index);
                    index++;
                }
                else{
                    endOfFile = true;
                }
            }
            stopsBuffer.close();
            stopsReader.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }


    /**
     * Generate the routes for all stops according to the transfer.txt and stop_times.txt given
     */
    private void generateRoutes(String transfers, String trips){
        try {
            FileReader tripsReader = new FileReader(trips);
            FileReader transferReader = new FileReader(transfers);
            BufferedReader tripsBuffer = new BufferedReader(tripsReader);
            BufferedReader transferBuffer = new BufferedReader(transferReader);
            boolean endOfFile = false;
            boolean stopLoop;
            String trip_id;
            Map<Integer, String> sequence = new HashMap<>();
            while (!endOfFile) {
                String tripsData = tripsBuffer.readLine();
                if (tripsData != null) {
                    String[] dataArray = tripsData.split(",");
                    trip_id = dataArray[0].trim();
                    sequence.put(Integer.parseInt(dataArray[4].trim()), dataArray[3].trim());
                    stopLoop = false;
                    while (!stopLoop) {
                        tripsData = tripsBuffer.readLine();
                        if (tripsData != null) {
                            dataArray = tripsData.split(",");
                            if (dataArray[0].trim().equals(trip_id)) {
                                sequence.put(Integer.parseInt(dataArray[4].trim()), dataArray[3].trim());
                            } else {
                                stopLoop = true;
                                tripsBuffer.mark(0);
                            }
                        } else {
                            stopLoop = true;
                            endOfFile = true;
                        }
                    }
                    for (int seq : sequence.keySet()) {
                        String stop_id1 = sequence.get(seq);
                        String stop_id2;
                        if (sequence.containsKey(seq + 1)) {
                            stop_id2 = sequence.get(seq + 1);
                        } else {
                            break;
                        }
                        edgeTo[indexes.get(stop_id1)][indexes.get(stop_id2)] = indexes.get(stop_id1);
                        distTo[indexes.get(stop_id1)][indexes.get(stop_id2)] = 1;
                    }
                    tripsBuffer.reset();
                } else {
                    endOfFile = true;
                }
            }
            tripsBuffer.close();
            tripsReader.close();
            endOfFile = false;
            while (!endOfFile) {
                String tripsData = tripsBuffer.readLine();
                if (tripsData != null) {
                    String[] dataArray = tripsData.split(",");
                    edgeTo[indexes.get(dataArray[0].trim())][indexes.get(dataArray[1].trim())] = indexes.get(dataArray[0].trim());
                    if(dataArray[2].trim().equals("0")){
                        distTo[indexes.get(dataArray[0].trim())][indexes.get(dataArray[1].trim())] = 2;
                    } else{
                        distTo[indexes.get(dataArray[0].trim())][indexes.get(dataArray[1].trim())] = Double.parseDouble(dataArray[3].trim()) / 100;
                    }
                } else {
                    endOfFile = true;
                }
            }
            transferBuffer.close();
            transferBuffer.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }


    private void dijkstraAlgorithm(int start) {
        boolean[] shortest = new boolean[edgeTo.length];
        shortest[start] = true;
        boolean exit = false;
        while(!exit) {
            int vertex = -1;
            for(int i = 0; i < distTo.length; i++) {
                if((!shortest[i]) && (distTo[start][i] != Integer.MAX_VALUE)){
                    vertex = i;
                    shortest[vertex] = true;
                    for (int j = 0; j < distTo.length; j++) {
                        if (distTo[start][vertex] + distTo[vertex][j] < distTo[start][j]) {
                            distTo[start][j] = distTo[start][vertex] + distTo[vertex][j];
                            shortest[j] = false;
                            edgeTo[start][j] = start;
                        }
                    }
                }
            }
            if(vertex == -1) {
                exit = true;
            }
        }
    }

    /**
     * Generate the shortest path between all stops using dijkstra algorithm
     */
    public void generatePath() {
        for (int i = 0; i < distTo.length; i++) {
            dijkstraAlgorithm(i);
        }
    }

}
