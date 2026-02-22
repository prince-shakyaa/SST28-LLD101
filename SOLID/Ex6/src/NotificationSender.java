public abstract class NotificationSender<T> {
    protected final AuditLog audit;

    protected NotificationSender(AuditLog audit) {
        this.audit = audit;
    }

    public abstract void send(T message);
}
