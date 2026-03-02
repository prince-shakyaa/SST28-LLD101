/** DIP abstraction: any report writing strategy. */
public interface IReportWriter {
    String write(Submission s, int plag, int code);
}
