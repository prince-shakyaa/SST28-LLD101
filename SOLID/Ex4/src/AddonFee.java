import java.util.Map;

public class AddonFee implements FeeComponent {
    private final Map<AddOn, Double> pricing;

    public AddonFee(Map<AddOn, Double> pricing) {
        this.pricing = pricing;
    }

    @Override
    public double compute(BookingRequest req) {
        double total = 0.0;
        for (AddOn a : req.addOns) {
            total += pricing.getOrDefault(a, 0.0);
        }
        return total;
    }
}
