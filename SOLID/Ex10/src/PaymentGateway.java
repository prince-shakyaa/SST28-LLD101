// DIP: concrete implementation — details live here, not in the booking service.
public class PaymentGateway implements IPaymentGateway {
    @Override
    public String charge(String studentId, double amount) {
        return "TXN-9001";
    }
}
