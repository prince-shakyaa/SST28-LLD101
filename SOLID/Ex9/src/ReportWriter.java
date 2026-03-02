// DIP: implements the abstraction; concrete write logic stays here, not in pipeline.
public class ReportWriter implements IReportWriter {
    @Override
    public String write(Submission s, int plag, int code) {
        return "report-" + s.roll + ".txt";
    }
}
