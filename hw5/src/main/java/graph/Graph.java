package graph;

import drawingApi.DrawingApi;

/**
 * @author Nikolay Yarlychenko
 */
public abstract class Graph {
    /**
     * Bridge to drawing API
     */
    protected final DrawingApi drawingApi;

    public Graph(DrawingApi drawingApi) {
        this.drawingApi = drawingApi;
    }

    public abstract void drawGraph();
}
