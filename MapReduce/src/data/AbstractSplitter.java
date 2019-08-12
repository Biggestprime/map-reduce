package data;

import java.io.BufferedReader;
import java.io.File;

public abstract class AbstractSplitter implements Splitter {

    protected int numOfSplits;
    protected File inputFile;
    protected long chunkLines;
    protected File[] splitFiles;
    protected BufferedReader br;

    protected void createSplitFiles() {

        this.splitFiles = new File[this.numOfSplits];

        for (int i = 0; i < this.splitFiles.length; i++) {
            splitFiles[i] = new File("split_" + i + ".txt");
        }
    }

    protected abstract void calculateChunkSize() throws Exception;

    protected abstract void split() throws Exception;
}
