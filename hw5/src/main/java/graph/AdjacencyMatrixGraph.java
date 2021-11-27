package graph;

import drawingApi.DrawingApi;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nikolay Yarlychenko
 */
public class AdjacencyMatrixGraph extends Graph {

    List<List<Integer>> matrix;

    public AdjacencyMatrixGraph(List<List<Integer>> matrix, DrawingApi drawingApi) {
        super(drawingApi);
        this.matrix = matrix;
    }

    @Override
    public void drawGraph() {
        Graph listOfEdgesGraph = new ListOfEdgesGraph(listOfEdgesFromMatrix(), drawingApi);
        listOfEdgesGraph.drawGraph();
    }

    private List<List<String>> listOfEdgesFromMatrix() {
        List<List<String>> result = new ArrayList<>();

        int rows = matrix.size();
        int cols = matrix.get(0).size();

        assert (rows == cols);

        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j <= i; ++j) {
                if (matrix.get(i).get(j) == 1) {
                    String from = String.valueOf(i + 1);
                    String to = String.valueOf(j + 1);
                    result.add(List.of(from, to));
                }
            }
        }
        return result;
    }
}
