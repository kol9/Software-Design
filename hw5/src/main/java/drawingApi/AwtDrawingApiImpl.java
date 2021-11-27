package drawingApi;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

/**
 * @author Nikolay Yarlychenko
 */
public class AwtDrawingApiImpl extends Frame implements DrawingApi {
    private static final int DRAWING_AREA_WIDTH = 600;
    private static final int DRAWING_AREA_HEIGHT = 400;

    private final Queue<Shape> drawingShapesQueue;
    private final Map<Shape, String> labelForShape;

    public AwtDrawingApiImpl() {
        this.drawingShapesQueue = new ArrayDeque<>();
        this.labelForShape = new HashMap<>();
    }

    // DrawingApi

    @Override
    public long getDrawingAreaWidth() {
        return DRAWING_AREA_WIDTH;
    }

    @Override
    public long getDrawingAreaHeight() {
        return DRAWING_AREA_HEIGHT;
    }

    @Override
    public void drawCircle(double x, double y, double width, double height) {
        drawingShapesQueue.add(new Ellipse2D.Double(x, y, width, height));
    }

    @Override
    public void drawCircle(double x, double y, double width, double height, String label) {
        Shape circle = new Ellipse2D.Double(x, y, width, height);
        drawingShapesQueue.add(circle);
        labelForShape.put(circle, label);
    }

    @Override
    public void drawLine(double fromX, double fromY, double toX, double toY) {
        drawingShapesQueue.add(new Line2D.Double(fromX, fromY, toX, toY));
    }

    @Override
    public void endGraphicsContext() {
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
        this.setSize(DRAWING_AREA_WIDTH, DRAWING_AREA_HEIGHT);
        this.setVisible(true);
    }

    // Frame

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D ga = (Graphics2D) g;

        ga.setStroke(new BasicStroke(3.0f));
        for (Shape shape : drawingShapesQueue) {
            ga.setColor(Color.ORANGE);
            ga.fill(shape);
            ga.setColor(Color.black);
            ga.draw(shape);

            String textForShape = labelForShape.getOrDefault(shape, "");
            ga.drawString(textForShape, (int) shape.getBounds2D().getCenterX(), (int) shape.getBounds2D().getCenterY());
        }
    }
}
