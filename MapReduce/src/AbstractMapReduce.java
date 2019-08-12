import data.DefaultSplitter;
import data.Splitter;
import data.Writer;
import mapping.Mapper;
import reducing.Reducer;
import shuffling.Shuffler;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public abstract class AbstractMapReduce {

    protected Mapper mapper;
    protected Writer writer;
    protected Reducer reducer;
    protected Splitter splitter;
    protected Shuffler shuffler;
    protected int numOfSplits = 1;
    protected Map<String, List<Integer>> shufflerResult;
    protected Map<String, Integer> reducerResult;
    protected RecursiveTask<Map<String, Integer>>[] nodes;


    protected void createNodes() {

        this.nodes = new RecursiveTask[this.numOfSplits];

        for (int i = 0; i < this.numOfSplits; i++) {

            File file = new File("split_" + i + ".txt");
            this.nodes[i] = new Node(this.mapper, file);
        }

    }

    protected void invokeNodes() throws IOException {

        ForkJoinPool pool = new ForkJoinPool();

        for (int i = 0; i < this.nodes.length; i++) {

            long startTime = System.currentTimeMillis();
            memoryHistogram(i+1);

            Map<String, Integer> map = pool.invoke(this.nodes[i]);
            shuffler.submitNodeResult(map);

            memoryHistogram(-(i+1));
            long endTime = System.currentTimeMillis();
            long time = endTime - startTime;

            File file = new File("node" + (i+1));
            PrintWriter out = new PrintWriter(file);

            out.println("execution time: " + time);
            out.close();
        }
    }

    public void setNumOfSplits(int numOfSplits) {
        if (numOfSplits <= 0) {
            throw new IllegalArgumentException();
        }

        this.numOfSplits = numOfSplits;
    }

    public void initializeSplitter(String inputFilePath) throws FileNotFoundException {

        if (inputFilePath.isEmpty()) {
            throw new IllegalArgumentException();
        }

        this.splitter = new DefaultSplitter(inputFilePath, this.numOfSplits);
    }

    protected void memoryHistogram(int nodeNum) throws IOException {
        String name = ManagementFactory.getRuntimeMXBean().getName();
        String PID = name.substring(0, name.indexOf("@"));
        Process p = Runtime.getRuntime().exec("jcmd " + PID + " GC.class_histogram");

        PrintWriter out = new PrintWriter(new File("memory_node" + nodeNum));

        try (BufferedReader input = new BufferedReader(
                new InputStreamReader(p.getInputStream()))) {
            input.lines().forEach(out::println);
        }
        out.close();
    }

    protected abstract void start() throws Exception;
}
