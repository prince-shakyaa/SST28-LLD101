import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Hostel Fee Calculator ===");
        BookingRequest req = new BookingRequest(LegacyRoomTypes.DOUBLE, List.of(AddOn.LAUNDRY, AddOn.MESS));

        List<FeeComponent> rules = List.of(
                new RoomFee(Map.of(
                        LegacyRoomTypes.SINGLE, 14000.0,
                        LegacyRoomTypes.DOUBLE, 15000.0,
                        LegacyRoomTypes.TRIPLE, 12000.0)),
                new AddonFee(Map.of(
                        AddOn.MESS, 1000.0,
                        AddOn.LAUNDRY, 500.0,
                        AddOn.GYM, 300.0)));
        HostelFeeCalculator calc = new HostelFeeCalculator(rules);
        BookingProcessor processor = new BookingProcessor(calc, new FakeBookingRepo());
        processor.process(req);
    }
}
