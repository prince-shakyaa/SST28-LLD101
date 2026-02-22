import java.util.Random;

public class BookingProcessor {
    private final HostelFeeCalculator calculator;
    private final FakeBookingRepo repo;

    public BookingProcessor(HostelFeeCalculator calculator, FakeBookingRepo repo) {
        this.calculator = calculator;
        this.repo = repo;
    }

    public void process(BookingRequest req) {
        Money monthly = calculator.calculateMonthly(req);
        Money deposit = new Money(5000.00);

        ReceiptPrinter.print(req, monthly, deposit);

        String bookingId = "H-" + (7000 + new Random(1).nextInt(1000));
        repo.save(bookingId, req, monthly, deposit);
    }
}
