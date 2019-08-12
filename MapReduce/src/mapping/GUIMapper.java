package mapping;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class GUIMapper implements Mapper {
    @Override
    public Map<String, Integer> map(File splitFile) {
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

    }
}
