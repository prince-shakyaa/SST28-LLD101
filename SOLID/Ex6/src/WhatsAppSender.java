public class WhatsAppSender extends NotificationSender<WhatsAppMessage> {
    public WhatsAppSender(AuditLog audit) {
        super(audit);
    }

    @Override
    public void send(WhatsAppMessage n) {
        System.out.println("WA -> to=" + n.phone + " body=" + n.body);
        audit.add("wa sent");
    }
}
