package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Map;

public final class DefaultWriter implements Writer {

    private String path;
    private StringBuilder sb;

    public DefaultWriter() {
        this.path = "output/output_test.txt";
        sb = new StringBuilder();
    }

    private void prepare(Map<String, Integer> map) {
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            sb.append(entry.getKey() + " " + entry.getValue() + "\n");
        }
    }

    private void writeOutput() throws FileNotFoundException {

        PrintWriter out = new PrintWriter(new File(this.path));
        out.print(sb);
        out.close();
    }

    @Override
    public void write(Map<String, Integer> map) throws FileNotFoundException {
          prepare(map);
          writeOutput();
    }
}

