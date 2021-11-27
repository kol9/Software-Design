package drawingApi;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * @author Nikolay Yarlychenko
 */
public class JavaFxDrawingApiImpl implements DrawingApi {

    private static final int DRAWING_AREA_WIDTH = 600;
    private static final int DRAWING_AREA_HEIGHT = 400;

    private static final Queue<Shape> drawingShapesQueue = new ArrayDeque<>();

    public JavaFxDrawingApiImpl() {
    }

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
        Circle circle = new Circle();
        circle.setCenterX(x + width / 2);
        circle.setCenterY(y + height / 2);
        circle.setRadius(width / 2);
        circle.setFill(Color.ORANGE);
        circle.setStroke(Color.BLACK);

        drawingShapesQueue.add(circle);
    }

    @Override
    public void drawCircle(double x, double y, double width, double height, String label) {
        drawCircle(x, y, width, height);
        Text text = new Text(label);
        text.setX(x + width / 2);
        text.setY(y + height / 2);
        drawingShapesQueue.add(text);
    }

    @Override
    public void drawLine(double fromX, double fromY, double toX, double toY) {
        Line line = new Line(fromX, fromY, toX, toY);
        line.setStroke(Color.BLACK);
        line.setStrokeWidth(3);
        drawingShapesQueue.add(line);
    }

    @Override
    public void endGraphicsContext() {
        Application.launch(JavaFxDrawingApiApplication.class);
    }

    public static class JavaFxDrawingApiApplication extends Application {
        @Override
        public void start(Stage primaryStage) {
            Pane pane = new Pane();
            pane.getChildren().addAll(drawingShapesQueue);
            Scene scene = new Scene(pane, DRAWING_AREA_WIDTH, DRAWING_AREA_HEIGHT);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
    }
}
