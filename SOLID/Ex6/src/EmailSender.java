public class EmailSender extends NotificationSender<EmailMessage> {
    public EmailSender(AuditLog audit) {
        super(audit);
    }

    @Override
    public void send(EmailMessage n) {
        System.out.println("EMAIL -> to=" + n.email + " subject=" + n.subject + " body=" + n.body);
        audit.add("email sent");
    }
}
