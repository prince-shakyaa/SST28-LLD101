package pen;

import strategy.InkRefill;
import strategy.OpenCloseStrategy;

/**
 * GelPen — gel ink pen, smooth writing with cap-based mechanism.
 */
public class GelPen extends Pen {
    private InkRefill inkRefill;

    public GelPen(String color, InkRefill inkRefill, OpenCloseStrategy openCloseStrategy) {
        super(color, inkRefill, openCloseStrategy);
        this.inkRefill = inkRefill;
    }

    @Override
    public void write(String text) {
        if (!isOpen) {
            System.out.println("[GelPen] Cannot write. Please start (open) the pen first.");
            return;
        }
        if (!inkRefill.hasInk()) {
            System.out.println("[GelPen] Out of ink! Please refill.");
            return;
        }
        inkRefill.useInk(text.length());
        System.out.println("[GelPen] Writing smoothly in " + color + " gel ink: \"" + text + "\"  (Ink left: " + inkRefill.getInkLevel() + ")");
    }
}
