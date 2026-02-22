public class SmsSender extends NotificationSender<SmsMessage> {
    public SmsSender(AuditLog audit) {
        super(audit);
    }

    @Override
    public void send(SmsMessage n) {
        System.out.println("SMS -> to=" + n.phone + " body=" + n.body);
        audit.add("sms sent");
    }
}
