// DIP: Main is the composition root — it constructs concretes and injects them.
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Evaluation Pipeline ===");
        Submission sub = new Submission("23BCS1007", "public class A{}", "A.java");

        // Dependency injection — swap any impl without touching EvaluationPipeline
        EvaluationPipeline pipeline = new EvaluationPipeline(
                new PlagiarismChecker(),
                new CodeGrader(),
                new ReportWriter());
        pipeline.evaluate(sub);
    }
}
