import data.DefaultWriter;
import mapping.Mapper;
import reducing.Reducer;
import shuffling.DefaultShuffler;

import java.io.FileNotFoundException;
import java.io.IOException;

public final class MapReduce extends AbstractMapReduce {

    public MapReduce(Mapper mapper, Reducer reducer) {
        if (mapper == null || reducer == null) {
            throw new IllegalArgumentException();
        }

        this.mapper = mapper;
        this.reducer = reducer;
        this.shuffler = new DefaultShuffler();
        this.writer = new DefaultWriter();
    }

    private void spilt() throws Exception {
        splitter.start();
    }

    private void map() throws IOException {
        createNodes();
        invokeNodes();
    }

    private void shuffle() {
        shufflerResult = shuffler.getResult();
    }

    private void reduce() {
        reducerResult = reducer.reduce(shufflerResult);
    }

    private void write() throws FileNotFoundException {
        writer.write(reducerResult);
    }

    @Override
    public void start() throws Exception {

        spilt();
        map();
        shuffle();
        reduce();
        write();
    }
}
