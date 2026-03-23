package strategy;

/**
 * Strategy interface for refill behavior.
 * Allows different types of pens to have different refill mechanisms.
 */
public interface RefillStrategy {
    void refill();
    boolean hasInk();
}
