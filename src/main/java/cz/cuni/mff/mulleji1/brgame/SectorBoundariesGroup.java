package cz.cuni.mff.mulleji1.brgame;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import org.hexworks.mixite.core.api.Hexagon;
import org.hexworks.mixite.core.api.HexagonalGrid;
import org.hexworks.mixite.core.api.Point;

/**
 * Group for drawing sectors' borders from hexagonal grid
 */
public class SectorBoundariesGroup extends Group {
    private double width = 0;
    private double height = 0;

    private HexagonalGrid<GameSectorData> grid;

    public SectorBoundariesGroup(HexagonalGrid<GameSectorData> grid) {
        this.grid = grid;

        for (Object oh : grid.getHexagons()) {
            Hexagon h = (Hexagon)oh;

            Point lastPoint = (Point)h.getPoints().get(h.getPoints().size() - 1);
            for (Object op : h.getPoints()) {
                Point p = (Point)op;
                double f = 0.98;
                double fromX = h.getCenterX() + (lastPoint.getCoordinateX() - h.getCenterX()) * f;
                double fromY = h.getCenterY() + (lastPoint.getCoordinateY() - h.getCenterY()) * f;
                double toX = h.getCenterX() + (p.getCoordinateX() - h.getCenterX()) * f;
                double toY = h.getCenterY() + (p.getCoordinateY() - h.getCenterY()) * f;
                Line l = new Line(fromX, fromY, toX, toY);
                l.setStroke(Color.DIMGRAY);
                l.setStrokeWidth(1);

                getChildren().add(l);
                lastPoint = p;

                width = Math.max(width, toX);
                height = Math.max(height, toY);
            }
        }
    }

    public double getWidth() {return width;}
    public double getHeight() {return height;}

    public HexagonalGrid<GameSectorData> getHexGrid() {
        return grid;
    }
}
