package mapping;

import java.io.File;
import java.util.Map;

public interface Mapper {

    Map<String, Integer> map(File file);

}
