/**
 * DIP: High-level booking service depends only on abstractions.
 * Concrete implementations are injected via constructor — no 'new' inside
 * book().
 */
public class TransportBookingService {
    private final IDistanceCalculator distCalc;
    private final IDriverAllocator allocator;
    private final IPaymentGateway payment;

    public TransportBookingService(IDistanceCalculator distCalc,
            IDriverAllocator allocator,
            IPaymentGateway payment) {
        this.distCalc = distCalc;
        this.allocator = allocator;
        this.payment = payment;
    }

    public void book(TripRequest req) {
        double km = distCalc.km(req.from, req.to);
        System.out.println("DistanceKm=" + km);

        String driver = allocator.allocate(req.studentId);
        System.out.println("Driver=" + driver);

        double fare = 50.0 + km * 6.6666666667;
        fare = Math.round(fare * 100.0) / 100.0;

        String txn = payment.charge(req.studentId, fare);
        System.out.println("Payment=PAID txn=" + txn);

        BookingReceipt r = new BookingReceipt("R-501", fare);
        System.out.println("RECEIPT: " + r.id + " | fare=" + String.format("%.2f", r.fare));
    }
}
