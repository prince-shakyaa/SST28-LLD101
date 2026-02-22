import java.util.List;
import java.util.Map;

public interface InvoiceFormatter {
    String format(String invId, List<OrderLine> lines, Map<String, MenuItem> menu, double subtotal, double taxPct,
            double tax, double discount, double total);
}
