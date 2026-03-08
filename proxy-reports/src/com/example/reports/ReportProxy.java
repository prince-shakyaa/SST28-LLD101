package com.example.reports;

/**
 * Proxy for Report:
 * - checks access control before allowing display
 * - lazy-loads the RealReport only when access is granted
 * - caches the RealReport so the same proxy never reloads from disk
 */
public class ReportProxy implements Report {

    private final String reportId;
    private final String title;
    private final String classification;
    private final AccessControl accessControl = new AccessControl();

    // null until first authorised access — lazy loading + caching
    private RealReport realReport;

    public ReportProxy(String reportId, String title, String classification) {
        this.reportId = reportId;
        this.title = title;
        this.classification = classification;
    }

    @Override
    public void display(User user) {
        if (!accessControl.canAccess(user, classification)) {
            System.out.println("[PROXY] ACCESS DENIED: " + user.getName()
                    + " (role=" + user.getRole() + ") cannot access report '"
                    + title + "' (classification=" + classification + ")");
            return;
        }

        // Lazy-load and cache
        if (realReport == null) {
            System.out.println("[PROXY] Loading report for the first time: " + reportId);
            realReport = new RealReport(reportId, title, classification);
        } else {
            System.out.println("[PROXY] Reusing cached report: " + reportId);
        }

        realReport.display(user);
    }
}
