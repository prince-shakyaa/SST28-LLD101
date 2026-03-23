package pen;

import strategy.InkRefill;
import strategy.OpenCloseStrategy;

/**
 * InkPen — fountain/ink pen with cap-based mechanism.
 */
public class InkPen extends Pen {
    private InkRefill inkRefill;

    public InkPen(String color, InkRefill inkRefill, OpenCloseStrategy openCloseStrategy) {
        super(color, inkRefill, openCloseStrategy);
        this.inkRefill = inkRefill;
    }

    @Override
    public void write(String text) {
        if (!isOpen) {
            System.out.println("[InkPen] Cannot write. Please start (open) the pen first.");
            return;
        }
        if (!inkRefill.hasInk()) {
            System.out.println("[InkPen] Out of ink! Please refill.");
            return;
        }
        inkRefill.useInk(text.length());
        System.out.println("[InkPen] Writing in " + color + " ink: \"" + text + "\"  (Ink left: " + inkRefill.getInkLevel() + ")");
    }
}
