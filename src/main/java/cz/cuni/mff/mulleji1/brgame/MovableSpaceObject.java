package cz.cuni.mff.mulleji1.brgame;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Represents object with ability to move.
 * Extends SpaceObject and adds new drawing features showing current and planned path
 */
public abstract class MovableSpaceObject extends SpaceObject {
    Deque<WaypointInfo> pendingWaypoints = new LinkedList<>();

    private double speed;
    private Group waypointsGraphics;

    public MovableSpaceObject(double x, double y, int maxHP, double speed) {
        super(x, y, maxHP);
        this.speed = speed;

        waypointsGraphics = new Group();
    }

    public Node getWaypointsGraphics() {
        return waypointsGraphics;
    }

    public double getSpeed() {
        return this.speed;
    }
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public void update(GameLoop gameLoop, long deltaNanos) {
        // how much distance we can move this tick
        double dist = speed * Utils.nanoToSeconds(deltaNanos);

        // spend all available distance to move along the way
        while (pendingWaypoints.size() != 0) {
            WaypointInfo currentWp = pendingWaypoints.peek();
            double x = getPosX();
            double y = getPosY();
            double dx = currentWp.getTargetPosX() - x;
            double dy = currentWp.getTargetPosY() - y;
            double toTarget = Utils.getLength(dx, dy);

            if (toTarget <= dist) {
                // we reached destination but still can move a little further
                setPosX(currentWp.getTargetPosX());
                setPosY(currentWp.getTargetPosY());
                dist -= toTarget;
                pendingWaypoints.poll();
                Node l = waypointsGraphics.getChildren().remove(0);
                LinePool.Instance.push((Line)l);
            } else {
                // we didnt reach destination, move along the way
                setPosX(x + dx / toTarget * dist);
                setPosY(y + dy / toTarget * dist);
                Line l = (Line)waypointsGraphics.getChildren().get(0);
                l.setStartX(getPosX());
                l.setStartY(getPosY());
                // we moved as far as we could this tick
                break;
            }
        }

    }

    public void addWaypoint(WaypointInfo wpInfo) {
        if (wpInfo == null) return;
        if (pendingWaypoints.size() > 8) return; // too many waypoints
        WaypointInfo last = pendingWaypoints.peekLast();
        if (last == null || !last.equals(wpInfo)) {
            pendingWaypoints.add(wpInfo);

            Line l = LinePool.Instance.pull();
            if (last != null) {
                l.setStartX(last.getTargetPosX());
                l.setStartY(last.getTargetPosY());
            } else {
                l.setStartX(getPosX());
                l.setStartY(getPosY());
            }
            l.setEndX(wpInfo.getTargetPosX());
            l.setEndY(wpInfo.getTargetPosY());
            l.setStroke(Color.GRAY);
            l.setStrokeWidth(4);
            l.setStrokeType(StrokeType.CENTERED);
            l.setStrokeLineJoin(StrokeLineJoin.ROUND);
            l.setStrokeLineCap(StrokeLineCap.ROUND);

            waypointsGraphics.getChildren().add(l);
        }
    }
    public void removeLastWaypoint() {
        if (pendingWaypoints.size() > 0) {
            pendingWaypoints.removeLast();

            Node l = waypointsGraphics.getChildren().remove(waypointsGraphics.getChildren().size() - 1);
            LinePool.Instance.push((Line)l);
        }
    }
}
