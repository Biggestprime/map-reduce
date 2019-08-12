package shuffling;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public final class DefaultShuffler implements Shuffler {

    private Map<String, List<Integer>> result;

    public DefaultShuffler() {
        result = new TreeMap<>();
    }

    @Override
    public void submitNodeResult(Map<String, Integer> map) {

        for (Map.Entry<String, Integer> entry: map.entrySet()){
            List<Integer> temp;
            if(result.containsKey(entry.getKey())){
                temp = result.get(entry.getKey());
                temp.add(entry.getValue());

                result.put(entry.getKey(), temp);
            }
            else{
                temp = new ArrayList<>();
                temp.add(entry.getValue());
                result.put(entry.getKey(), temp);
            }
        }
    }

    @Override
    public Map<String, List<Integer>> getResult() {
        return result;
    }
}
