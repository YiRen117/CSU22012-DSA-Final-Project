import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShortestPath {

    public Map<String, Integer> indexes;
    private Map<Integer, String> stopID;
    public double[][] distTo;
    private int[][] edgeTo;

    /**
     * @param trips: A filename containing the details of the trips
     * @param transfers: A filename containing the details of the transfers between stops
     */
    ShortestPath(String trips, String transfers, String stops) {
        this.indexes = new HashMap<>();
        this.stopID = new HashMap<>();
        generateStopName(stops);
        this.distTo = new double[indexes.size()][indexes.size()];
        this.edgeTo = new int[indexes.size()][indexes.size()];
        for(int i = 0; i < indexes.size(); i++){
            for(int j = 0; j < indexes.size(); j++) {
                distTo[i][j] = (i == j) ? 0 : Double.MAX_VALUE;
                edgeTo[i][j] = -1;
            }
        }
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
            stopsBuffer.readLine();
            while(!endOfFile){
                String data = stopsBuffer.readLine();
                if (data != null){
                    String[] dataArray = data.trim().split(",");
                    indexes.put(dataArray[0].trim(), index);
                    stopID.put(index, dataArray[0].trim());
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
            boolean stopLoop, pause;
            String trip_id;
            Map<Integer, String> sequence;
            tripsBuffer.readLine();
            while (!endOfFile) {
                String tripsData = tripsBuffer.readLine();
                sequence = new HashMap<>();
                if (tripsData != null) {
                    String[] dataArray = tripsData.split(",");
                    trip_id = dataArray[0].trim();
                    sequence.put(Integer.parseInt(dataArray[4].trim()), dataArray[3].trim());
                    stopLoop = false;
                    pause = false;
                    while (!stopLoop && !pause) {
                        tripsData = tripsBuffer.readLine();
                        if (tripsData != null) {
                            dataArray = tripsData.split(",");
                            if (dataArray[0].trim().equals(trip_id)) {
                                sequence.put(Integer.parseInt(dataArray[4].trim()), dataArray[3].trim());
                            } else {
                                pause = true;
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
                    if (pause) {
                        tripsBuffer.reset();
                    }
                } else {
                    endOfFile = true;
                }
            }
            tripsBuffer.close();
            tripsReader.close();
            endOfFile = false;
            transferBuffer.readLine();
            while (!endOfFile) {
                String transferData = transferBuffer.readLine();
                if (transferData != null) {
                    String[] dataArray = transferData.split(",");
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
                            edgeTo[start][j] = vertex;
                        }
                    }
                }
            }
            if(vertex == -1) {
                exit = true;
            }
        }
    }


    public ArrayList<String> searchShortest(int start, int end){
        ArrayList<String> pathSeq = new ArrayList<>();
        ArrayList<Integer> indexSeq = new ArrayList<>();
        dijkstraAlgorithm(start);
        int vertex = end;
        while (vertex != start) {
            indexSeq.add(vertex);
            vertex = edgeTo[start][vertex];
        }
        indexSeq.add(start);
        for(int i : indexSeq){
            pathSeq.add(stopID.get(i));
        }
        return pathSeq;
    }

}
