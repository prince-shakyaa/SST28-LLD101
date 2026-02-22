import java.util.List;
import java.util.Map;

public class OnboardingService {
    private final InputParser parser;
    private final StudentValidator validator;
    private final StudentRepository repository;
    private final OnboardingPrinter printer;

    public OnboardingService(InputParser parser, StudentValidator validator, StudentRepository repository,
            OnboardingPrinter printer) {
        this.parser = parser;
        this.validator = validator;
        this.repository = repository;
        this.printer = printer;
    }

    public void registerFromRawInput(String raw) {
        printer.printRawInput(raw);

        Map<String, String> kv = parser.parse(raw);
        List<String> errors = validator.validate(kv);

        if (!errors.isEmpty()) {
            printer.printValidationErrors(errors);
            return;
        }

        String id = IdUtil.nextStudentId(repository.count());
        StudentRecord rec = new StudentRecord(
                id,
                kv.getOrDefault("name", ""),
                kv.getOrDefault("email", ""),
                kv.getOrDefault("phone", ""),
                kv.getOrDefault("program", ""));

        repository.save(rec);

        printer.printSuccess(rec, repository.count());
    }
}
