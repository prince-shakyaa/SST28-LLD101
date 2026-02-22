public class SizeConstrainedExporter extends Exporter {
    private final Exporter delegate;
    private final int maxSize;
    private final String formatName;

    public SizeConstrainedExporter(Exporter delegate, int maxSize, String formatName) {
        this.delegate = delegate;
        this.maxSize = maxSize;
        this.formatName = formatName;
    }

    @Override
    protected ExportResult doExport(ExportRequest req) {
        if (req.body != null && req.body.length() > maxSize) {
            throw new IllegalArgumentException(formatName + " cannot handle content > " + maxSize + " chars");
        }
        return delegate.export(req);
    }
}
