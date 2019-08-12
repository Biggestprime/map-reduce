import mapping.Mapper;
import reducing.Reducer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        Mapper mapper =   (splitFile) -> {

            Pattern pattern = Pattern.compile("[a-zA-Z]+");
            TreeMap<String,Integer> wordCount = new TreeMap<>();
            try (
                    BufferedReader src =
                            new BufferedReader(new FileReader(splitFile.getPath()))
            ){

                Matcher matcher ;
                String str = src.readLine();
                while(str!=null){
                    if(!str.equals("")){
                        matcher = pattern.matcher(str);
                        while(matcher.find()){
                            String word = matcher.group();
                            if(!wordCount.containsKey(word))
                                wordCount.put(word,1);
                            else
                                wordCount.put(word,wordCount.get(word)+1);
                        }
                    }
                    str = src.readLine();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }

            return wordCount;
        };
        Reducer reducer = (map) -> {
            Map<String, Integer> result = new TreeMap<>();
            for (Map.Entry<String, List<Integer>> entry:
                     map.entrySet()) {
                int sum = 0;
                for (int val: entry.getValue()) {
                    sum += val;
                }
                result.put(entry.getKey(),sum);
            }
            return result;
        };

        MapReduce mapReduce = new MapReduce(mapper, reducer);
        mapReduce.setNumOfSplits(4);
        mapReduce.initializeSplitter (
                "/home/biggestprime/Downloads/input.txt");
        try {
            mapReduce.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

