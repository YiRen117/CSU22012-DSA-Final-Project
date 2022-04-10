import TST.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SearchByName {
    private String fileName;
    private TST<String> tree;
    public ArrayList<String> id, s_code, s_name, s_desc, s_lat, s_lon, zone, s_url, loc_type, par_station;

    SearchByName(String stops){
        this.fileName = stops;
        this.tree = new TST<String>();
        readFile();
    }

    private void readFile(){
        try{
            FileReader fileReader = new FileReader(fileName);
            BufferedReader buffer = new BufferedReader(fileReader);
            boolean endOfFile = false;
            buffer.readLine();
            while(!endOfFile){
                String data = buffer.readLine();
                if (data != null){
                    String[] dataArray = data.trim().split(",");
                    tree.put(formatWords(dataArray[2]), dataArray[0]);
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

    private String formatWords(String words){
        String[] wordsArray = words.trim().split("\\s+", 2);
        if(wordsArray[0].equals("WB") || wordsArray[0].equals("NB") || wordsArray[0].equals("SB")|| wordsArray[0].equals("EB")){
            words = wordsArray[1] + " " + wordsArray[0];
        }
        return words;
    }

    public void search(String inputName){
        this.id = new ArrayList<>();
        this.s_code = new ArrayList<>();
        this.s_name = new ArrayList<>();
        this.s_desc = new ArrayList<>();
        this.s_lat = new ArrayList<>();
        this.s_lon = new ArrayList<>();
        this.zone = new ArrayList<>();
        this.s_url = new ArrayList<>();
        this.loc_type = new ArrayList<>();
        this.par_station = new ArrayList<>();

        Iterable<String> queue = tree.keysWithPrefix(inputName);
        String stop_id;
        if(queue.iterator().hasNext()) {
            for (String s : queue) {
                stop_id = tree.get(s);
                findInfo(stop_id);
            }
        }
    }

    private void findInfo(String stop_id){
        try{
            FileReader fileReader = new FileReader(fileName);
            BufferedReader buffer = new BufferedReader(fileReader);
            boolean endOfFile = false;
            buffer.readLine();
            while(!endOfFile){
                String data = buffer.readLine();
                if (data != null){
                    String[] dataArray = data.trim().split(",");
                    if(dataArray[0].equals(stop_id)){
                        id.add(dataArray[0].trim());
                        s_code.add(dataArray[1].trim());
                        s_name.add(dataArray[2].trim());
                        s_desc.add(dataArray[3].trim());
                        s_lat.add(dataArray[4].trim());
                        s_lon.add(dataArray[5].trim());
                        zone.add(dataArray[6].trim());
                        s_url.add(dataArray[7].trim());
                        loc_type.add(dataArray[8].trim());
                        par_station.add((dataArray.length == 9) ? " " : dataArray[9].trim());
                        endOfFile = true;
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
