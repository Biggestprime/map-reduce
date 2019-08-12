package shuffling;

import java.util.List;
import java.util.Map;

public interface Shuffler {

    void submitNodeResult(Map<String, Integer> map);

    Map<String, List<Integer>> getResult();

}
