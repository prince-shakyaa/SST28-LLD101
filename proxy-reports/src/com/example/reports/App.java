package com.example.reports;

/**
 * Demo: uses ReportProxy instead of ReportFile.
 * - Unauthorized access is blocked at proxy level.
 * - Real report loads lazily (only on first authorised view).
 * - Repeated views through the same proxy reuse the cached RealReport.
 */
public class App {

    public static void main(String[] args) {
        User student = new User("Jasleen", "STUDENT");
        User faculty = new User("Prof. Noor", "FACULTY");
        User admin = new User("Kshitij", "ADMIN");

        Report publicReport = new ReportProxy("R-101", "Orientation Plan", "PUBLIC");
        Report facultyReport = new ReportProxy("R-202", "Midterm Review", "FACULTY");
        Report adminReport = new ReportProxy("R-303", "Budget Audit", "ADMIN");

        ReportViewer viewer = new ReportViewer();

        System.out.println("=== CampusVault Demo ===");

        viewer.open(publicReport, student); // allowed
        System.out.println();

        viewer.open(facultyReport, student); // DENIED
        System.out.println();

        viewer.open(facultyReport, faculty); // allowed, loads from disk
        System.out.println();

        viewer.open(adminReport, admin); // allowed, loads from disk
        System.out.println();

        viewer.open(adminReport, admin); // allowed, reuses cache (no reload)
    }
}
