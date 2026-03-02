/** DIP abstraction: any payment gateway implementation. */
public interface IPaymentGateway {
    String charge(String studentId, double amount);
}
