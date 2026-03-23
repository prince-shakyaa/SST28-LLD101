package pen;

import strategy.InkRefill;
import strategy.OpenCloseStrategy;

/**
 * BallPen — ballpoint pen, typically with click mechanism.
 */
public class BallPen extends Pen {
    private InkRefill inkRefill;

    public BallPen(String color, InkRefill inkRefill, OpenCloseStrategy openCloseStrategy) {
        super(color, inkRefill, openCloseStrategy);
        this.inkRefill = inkRefill;
    }

    @Override
    public void write(String text) {
        if (!isOpen) {
            System.out.println("[BallPen] Cannot write. Please start (open) the pen first.");
            return;
        }
        if (!inkRefill.hasInk()) {
            System.out.println("[BallPen] Out of ink! Please refill.");
            return;
        }
        inkRefill.useInk(text.length());
        System.out.println("[BallPen] Writing in " + color + " ink: \"" + text + "\"  (Ink left: " + inkRefill.getInkLevel() + ")");
    }
}
