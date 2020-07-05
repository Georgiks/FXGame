package cz.cuni.mff.mulleji1.brgame;

/**
 * Utility class
 */
public class Utils {
    private Utils() {

    }
    /**
     * @return distance between two 2D points
     */
    public static double getDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(getDistanceSquared(x1, y1, x2, y2));
    }
    /**
     * @return squared distance between two 2D points (should be used when comparing distances)
     */
    public static double getDistanceSquared(double x1, double y1, double x2, double y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        return getLengthSquared(dx, dy);
    }
    /**
     * @return length of 2D vector
     */
    public static double getLength(double dx, double dy) {
        return Math.sqrt(getLengthSquared(dx, dy));
    }
    /**
     * @return squared length of 2D vector (should be used when comparing lengths)
     */
    public static double getLengthSquared(double dx, double dy) {
        return dx * dx + dy * dy;
    }

    public static final double NANOS_IN_SECONDS = 1_000_000_000;

    /**
     * @param nano nanoseconds
     * @return amount of seconds
     */
    public static double nanoToSeconds(long nano) {
        return nano / NANOS_IN_SECONDS;
    }
}
