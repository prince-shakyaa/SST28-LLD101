/** ISP: client interface for finance operations only. */
public interface IFinanceClient {
    void addIncome(double amt, String note);

    void addExpense(double amt, String note);
}
