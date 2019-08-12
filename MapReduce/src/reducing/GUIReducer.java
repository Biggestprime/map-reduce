package reducing;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public final class GUIReducer implements Reducer {
    @Override
    public Map<String, Integer> reduce(Map<String, List<Integer>> map) {
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
    }
}
