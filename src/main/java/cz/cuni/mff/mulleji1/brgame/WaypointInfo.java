package cz.cuni.mff.mulleji1.brgame;

public class WaypointInfo {
    private double targetPosX;
    private double targetPosY;

    public WaypointInfo(double targetPosX, double targetPosY) {
        this.targetPosX = targetPosX;
        this.targetPosY = targetPosY;
    }

    public double getTargetPosX() {
        return targetPosX;
    }
    public double getTargetPosY() {
        return targetPosY;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof WaypointInfo) {
            WaypointInfo wp = (WaypointInfo) obj;
            return wp.targetPosX == this.targetPosX && wp.targetPosY == this.targetPosY;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(targetPosX) ^ Double.hashCode(targetPosY) * 17;
    }
}
