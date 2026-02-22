import java.util.List;

public class HostelFeeCalculator {
    private final List<FeeComponent> components;

    public HostelFeeCalculator(List<FeeComponent> components) {
        this.components = components;
    }

    public Money calculateMonthly(BookingRequest req) {
        double sum = 0.0;
        for (FeeComponent c : components) {
            sum += c.compute(req);
        }
        return new Money(sum);
    }
}
