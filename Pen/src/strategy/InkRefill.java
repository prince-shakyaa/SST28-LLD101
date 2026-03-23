package strategy;

/**
 * Concrete refill strategy for Ink-based pens.
 */
public class InkRefill implements RefillStrategy {
    private int inkLevel;
    private static final int MAX_INK = 100;

    public InkRefill(int initialInk) {
        this.inkLevel = initialInk;
    }

    @Override
    public void refill() {
        inkLevel = MAX_INK;
        System.out.println("[InkRefill] Pen refilled to full ink level: " + MAX_INK);
    }

    @Override
    public boolean hasInk() {
        return inkLevel > 0;
    }

    public void useInk(int amount) {
        inkLevel = Math.max(0, inkLevel - amount);
    }

    public int getInkLevel() {
        return inkLevel;
    }
}
