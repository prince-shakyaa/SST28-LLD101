package strategy;

/**
 * Cap-based open/close mechanism (ink pens / fountain pens with removable caps).
 */
public class UncapStrategy implements OpenCloseStrategy {

    @Override
    public void open() {
        System.out.println("[UncapStrategy] Cap removed. Pen is ready to write.");
    }

    @Override
    public void close() {
        System.out.println("[UncapStrategy] Cap placed back. Pen is closed.");
    }
}
