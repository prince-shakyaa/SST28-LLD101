import java.util.List;

public interface OnboardingPrinter {
    void printRawInput(String raw);

    void printValidationErrors(List<String> errors);

    void printSuccess(StudentRecord rec, int totalStudents);
}
