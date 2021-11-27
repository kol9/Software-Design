package graph;

import drawingApi.DrawingApi;

import java.util.*;

/**
 * @author Nikolay Yarlychenko
 */
public class ListOfEdgesGraph extends Graph {
    private static final int VERTEX_SIDE = 50;

    private final Set<String> vertices;
    private final List<List<String>> listOfEdges;


    public ListOfEdgesGraph(List<List<String>> listOfEdges, DrawingApi drawingApi) {
        super(drawingApi);

        this.vertices = new LinkedHashSet<>();
        this.listOfEdges = listOfEdges;
        for (List<String> edge : listOfEdges) {
            String from = edge.get(0);
            String to = edge.get(1);

            vertices.add(from);
            vertices.add(to);
        }
    }

    @Override
    public void drawGraph() {
        List<String> orderedVertices = new ArrayList<>(vertices);

        for (List<String> edge : listOfEdges) {
            String from = edge.get(0);
            String to = edge.get(1);

            int fromI = orderedVertices.indexOf(from);
            int toI = orderedVertices.indexOf(to);

            List<Double> fromCoords = getCoordinatesForVertex(fromI);
            List<Double> toCoords = getCoordinatesForVertex(toI);

            double x1 = fromCoords.get(0) + VERTEX_SIDE / 2.0;
            double y1 = fromCoords.get(1) + VERTEX_SIDE / 2.0;
            double x2 = toCoords.get(0) + VERTEX_SIDE / 2.0;
            double y2 = toCoords.get(1) + VERTEX_SIDE / 2.0;
            this.drawingApi.drawLine(x1, y1, x2, y2);
        }

        for (int i = 0; i < orderedVertices.size(); ++i) {
            List<Double> coords = getCoordinatesForVertex(i);
            this.drawingApi.drawCircle(coords.get(0), coords.get(1), VERTEX_SIDE, VERTEX_SIDE, orderedVertices.get(i));
        }

        this.drawingApi.endGraphicsContext();
    }

    private List<Double> getCoordinatesForVertex(int i) {
        List<Double> coordinates = new ArrayList<>();

        long availableWidth = this.drawingApi.getDrawingAreaWidth();
        long availableHeight = this.drawingApi.getDrawingAreaHeight();

        long graphCircleMaxRadius = Math.min(availableWidth, availableHeight) / 2 - VERTEX_SIDE;

        double angle = 2.0 * Math.PI / vertices.size();

        double sX = availableWidth / 2.0;
        double sY = availableHeight / 2.0;

        double x = sX + graphCircleMaxRadius * Math.cos(i * angle);
        double y = sY + graphCircleMaxRadius * Math.sin(i * angle);

        coordinates.add(x);
        coordinates.add(y);
        return coordinates;
    }

}
