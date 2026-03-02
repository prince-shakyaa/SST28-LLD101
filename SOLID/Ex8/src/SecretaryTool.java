// ISP: SecretaryTool implements only IMinutesClient — no irrelevant methods.
public class SecretaryTool implements IMinutesClient {
    private final MinutesBook book;

    public SecretaryTool(MinutesBook book) {
        this.book = book;
    }

    @Override
    public void addMinutes(String text) {
        book.add(text);
    }
}
