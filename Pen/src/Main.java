import pen.BallPen;
import pen.GelPen;
import pen.InkPen;
import strategy.ClickStrategy;
import strategy.InkRefill;
import strategy.UncapStrategy;

/**
 * Main driver class to demonstrate Pen functionalities:
 * start(), write(), close(), refill()
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("           PEN DESIGN DEMO             ");
        System.out.println("========================================\n");

        // --- Ink Pen (blue, uses UncapStrategy) ---
        System.out.println("--- Ink Pen (Blue, UncapStrategy) ---");
        InkPen inkPen = new InkPen("Blue", new InkRefill(10), new UncapStrategy());
        inkPen.start();
        inkPen.write("Hello World");
        inkPen.write("LLD101 Assignment");
        inkPen.close();
        inkPen.refill();
        System.out.println();

        // --- Ball Pen (black, uses ClickStrategy) ---
        System.out.println("--- Ball Pen (Black, ClickStrategy) ---");
        BallPen ballPen = new BallPen("Black", new InkRefill(15), new ClickStrategy());
        ballPen.start();
        ballPen.write("Design a Pen");
        ballPen.close();
        ballPen.refill();
        System.out.println();

        // --- Gel Pen (red, uses UncapStrategy) ---
        System.out.println("--- Gel Pen (Red, UncapStrategy) ---");
        GelPen gelPen = new GelPen("Red", new InkRefill(5), new UncapStrategy());
        gelPen.start();
        gelPen.write("Gel pens write smoothly!");
        gelPen.close();
        gelPen.refill();
        System.out.println();

        // --- Edge case: write without starting ---
        System.out.println("--- Edge Case: Write without opening ---");
        InkPen closedPen = new InkPen("Green", new InkRefill(50), new UncapStrategy());
        closedPen.write("This should fail gracefully.");
        System.out.println();

        // --- Edge case: refill while open ---
        System.out.println("--- Edge Case: Refill while pen is open ---");
        BallPen openPen = new BallPen("Blue", new InkRefill(50), new ClickStrategy());
        openPen.start();
        openPen.refill();   // should warn
        openPen.close();

        System.out.println("\n========================================");
        System.out.println("              DEMO COMPLETE            ");
        System.out.println("========================================");
    }
}
