package pen;

import strategy.RefillStrategy;
import strategy.OpenCloseStrategy;

/**
 * Abstract Pen class — base for all pen types.
 * Applies the Strategy Pattern for open/close and refill behaviors.
 *
 * Functionalities:
 *  - start()  → opens the pen (via OpenCloseStrategy)
 *  - write()  → writes text (implemented by concrete subclasses)
 *  - close()  → closes the pen (via OpenCloseStrategy)
 *  - refill() → refills ink (via RefillStrategy)
 */
public abstract class Pen {
    protected String color;
    protected boolean isOpen;
    protected RefillStrategy refillStrategy;
    protected OpenCloseStrategy openCloseStrategy;

    public Pen(String color, RefillStrategy refillStrategy, OpenCloseStrategy openCloseStrategy) {
        this.color = color;
        this.isOpen = false;
        this.refillStrategy = refillStrategy;
        this.openCloseStrategy = openCloseStrategy;
    }

    /** Opens the pen so it can write. */
    public void start() {
        if (isOpen) {
            System.out.println("[Pen] Already open.");
            return;
        }
        openCloseStrategy.open();
        isOpen = true;
    }

    /** Writes text — must be implemented by each pen type. */
    public abstract void write(String text);

    /** Closes the pen. */
    public void close() {
        if (!isOpen) {
            System.out.println("[Pen] Already closed.");
            return;
        }
        openCloseStrategy.close();
        isOpen = false;
    }

    /** Refills the pen's ink. */
    public void refill() {
        if (isOpen) {
            System.out.println("[Pen] Please close the pen before refilling.");
            return;
        }
        refillStrategy.refill();
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isOpen() {
        return isOpen;
    }
}
