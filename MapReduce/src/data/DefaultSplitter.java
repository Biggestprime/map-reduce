package data;

import java.io.*;
import java.nio.file.Files;

public final class DefaultSplitter extends AbstractSplitter {

    public DefaultSplitter(String inputFilePath, int numOfSplits) throws FileNotFoundException {

        this.inputFile = new File(inputFilePath);
        this.numOfSplits = numOfSplits;
        FileReader fileReader = new FileReader(this.inputFile.getPath());
        br = new BufferedReader(fileReader);
    }

    @Override
    protected void calculateChunkSize() throws Exception {
        long total = Files.lines(inputFile.toPath()).count();

        if (total < this.numOfSplits) {
            throw new Exception("Large Number Of Splits");
        }

        this.chunkLines = (long) Math.ceil(1.0*total / this.numOfSplits);
    }

    @Override
    protected void split() throws Exception {

        for (int i = 0; i < this.numOfSplits; i++) {
            File currentChunk = this.splitFiles[i];
            PrintWriter out = new PrintWriter(currentChunk.getPath());
            StringBuilder sb = new StringBuilder();

            for (int j = 1; j < this.chunkLines; j++) {
                String line = br.readLine();
                if (line != null) {
                    sb.append(line + "\n");
                } else {
                    break;
                }
            }

            out.print(sb);
            out.close();
        }
    }

    @Override
    public void start() throws Exception {

        createSplitFiles();
        calculateChunkSize();
        split();

    }

}
