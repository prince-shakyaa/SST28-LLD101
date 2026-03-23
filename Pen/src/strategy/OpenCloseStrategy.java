package strategy;

/**
 * Strategy interface for opening and closing a pen.
 * Concrete implementations include click mechanism and cap mechanism.
 */
public interface OpenCloseStrategy {
    void open();
    void close();
}
