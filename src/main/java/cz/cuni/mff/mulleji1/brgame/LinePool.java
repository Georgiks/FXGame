package cz.cuni.mff.mulleji1.brgame;

import javafx.scene.shape.Line;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Helper pool for Lines to avoid allocations in hot game loop context
 */
public class LinePool {
    public static final LinePool Instance = new LinePool(32);

    private Deque<Line> lineStack = new ArrayDeque<>();
    private int count;

    public LinePool(int initialCount) {
        initialCount = Math.min(initialCount, 2);
        this.count = initialCount;
        fillPool(initialCount);
    }

    /**
     * Fill pool with given amount of new Line objects
     * @param num amount of new Lines to be created
     */
    private void fillPool(int num) {
        if (num <= 0) return;
        for (int i = 0; i < num; i++) {
            lineStack.push(new Line());
        }
        count += num;
    }

    /**
     * @return Line from a pool, new Lines are created if needed
     */
    public Line pull() {
        if (lineStack.isEmpty()) fillPool(count);
        return lineStack.pop();
    }

    /**
     * Return Line to this pool. Make sure to not use returned Line object again!
     * @param l Line to return
     */
    public void push(Line l) {
        lineStack.push(l);
    }
}
