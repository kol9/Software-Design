package drawingApi;

/**
 * @author Nikolay Yarlychenko
 */
public interface DrawingApi {
    long getDrawingAreaWidth();

    long getDrawingAreaHeight();

    void drawCircle(double x, double y, double width, double height);

    void drawCircle(double x, double y, double width, double height, String label);

    void drawLine(double fromX, double fromY, double toX, double toY);

    void endGraphicsContext();
}
