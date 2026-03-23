package strategy;

/**
 * Click-based open/close mechanism (retractable pens like ball pens).
 */
public class ClickStrategy implements OpenCloseStrategy {

    @Override
    public void open() {
        System.out.println("[ClickStrategy] Pen nib extended by clicking.");
    }

    @Override
    public void close() {
        System.out.println("[ClickStrategy] Pen nib retracted by clicking.");
    }
}
