import drawingApi.AwtDrawingApiImpl;
import drawingApi.DrawingApi;
import drawingApi.JavaFxDrawingApiImpl;
import graph.AdjacencyMatrixGraph;
import graph.Graph;
import graph.ListOfEdgesGraph;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Nikolay Yarlychenko
 */

//{--list --matrix } {--awt | --jfx}
public class Main {
    public static void main(String[] args) {
        if (args.length == 0 || args.length > 2 || args[0] == null) {
            System.err.println("USAGE: java Main args:[{--list --matrix } {--awt | --jfx}?]");
            throw new RuntimeException();
        }

        DrawingApi drawingApi = null;
        if (args.length == 1) {
            drawingApi = new AwtDrawingApiImpl();
        } else {
            if (args[1].equals("--awt")) {
                drawingApi = new AwtDrawingApiImpl();
            } else {
                drawingApi = new JavaFxDrawingApiImpl();
            }
        }

        Graph graph = null;
        switch (args[0]) {
            case "--list":
                List<List<String>> edges = readListOfEdgesGraph("graph1.txt");
                if (edges != null) {
                    graph = new ListOfEdgesGraph(edges, drawingApi);
                }
                break;
            case "--matrix":
                List<List<Integer>> matrix = readAdjacencyMatrixGraph("graph2.txt");
                if (matrix != null) {
                    graph = new AdjacencyMatrixGraph(matrix, drawingApi);
                }
                break;
            default:
                System.err.println("USAGE: java Main args:[{--list --matrix } {--awt | --jfx}?]");
                throw new RuntimeException();
        }
        if (graph == null) {
            throw new RuntimeException();
        }
        graph.drawGraph();
    }

    private static List<List<String>> readListOfEdgesGraph(String fileName) {
        List<List<String>> result = new ArrayList<>();
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(fileName);
            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] arr = line.split(" ");
                    assert (arr.length == 2);
                    result.add(List.of(arr[0], arr[1]));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List<List<Integer>> readAdjacencyMatrixGraph(String fileName) {
        List<List<Integer>> result = new ArrayList<>();
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(fileName);
            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] arr = line.split(" ");
                    result.add(Arrays.stream(arr).map(Integer::valueOf).collect(Collectors.toList()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}