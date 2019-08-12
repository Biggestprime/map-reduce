package data;

import java.io.FileNotFoundException;
import java.util.Map;

public interface Writer {

    void write(Map<String, Integer> map) throws FileNotFoundException;

}

