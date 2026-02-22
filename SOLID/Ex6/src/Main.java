public class Main {
    public static void main(String[] args) {
        System.out.println("=== Notification Demo ===");
        AuditLog audit = new AuditLog();

        Notification n = new Notification("Welcome", "Hello and welcome to SST!", "riya@sst.edu", "9876543210");

        NotificationSender<EmailMessage> email = new EmailSender(audit);
        NotificationSender<SmsMessage> sms = new SmsSender(audit);
        NotificationSender<WhatsAppMessage> wa = new WhatsAppSender(audit);

        email.send(new EmailMessage(n.email, n.subject, StringNormalizer.truncate(n.body, 40)));
        sms.send(new SmsMessage(n.phone, n.body));
        try {
            wa.send(new WhatsAppMessage(PhoneValidator.validate(n.phone), n.body));
        } catch (RuntimeException ex) {
            System.out.println("WA ERROR: " + ex.getMessage());
            audit.add("WA failed");
        }

        System.out.println("AUDIT entries=" + audit.size());
    }
}
