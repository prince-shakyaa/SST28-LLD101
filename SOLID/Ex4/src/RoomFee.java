import java.util.Map;

public class RoomFee implements FeeComponent {
    private final Map<Integer, Double> pricing;

    public RoomFee(Map<Integer, Double> pricing) {
        this.pricing = pricing;
    }

    @Override
    public double compute(BookingRequest req) {
        return pricing.getOrDefault(req.roomType, 16000.0);
    }
}
