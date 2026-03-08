package com.example.reports;

/**
 * Viewer depends on the Report interface only.
 * It knows nothing about ReportFile or ReportProxy.
 */
public class ReportViewer {

    public void open(Report report, User user) {
        report.display(user);
    }
}
