import mapping.Mapper;
import java.io.File;
import java.util.Map;
import java.util.concurrent.RecursiveTask;

public final class Node extends RecursiveTask<Map<String, Integer>> {

    private Mapper mapper;
    private File file;

    public Node(Mapper mapper, File file) {
        this.mapper = mapper;
        this.file = file;
    }

    @Override
    protected Map<String, Integer> compute() {
        return mapper.map(this.file);
    }
}
