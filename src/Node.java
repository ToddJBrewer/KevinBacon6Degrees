import java.util.LinkedList;

public class Node {
    LinkedList<Edge> edges;
    int n;
    String name;
    private boolean visited;

    Node(int n, String name) {
        this.n = n;
        this.name = name;
        visited = false;
        edges = new LinkedList<>();
    }

    void resetNodes() {
        visited = false;
    }

    public boolean isVisited() {
        return visited;
    }

    public void visit() {
        visited = true;
    }


    @Override
    public String toString() {
        return "Node{" +
                "n=" + n +
                ", name='" + name + '\'' +
                ", edges=" + edges +
                '}';
    }
}
