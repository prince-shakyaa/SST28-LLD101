public class PhoneValidator {
    public static String validate(String phone) {
        if (phone == null || !phone.startsWith("+")) {
            throw new IllegalArgumentException("phone must start with + and country code");
        }
        return phone;
    }
}
