import java.util.Set;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Student Onboarding ===");
        FakeDb db = new FakeDb();

        InputParser parser = new InputParser();
        StudentValidator validator = new StudentValidator(Set.of("CSE", "AI", "SWE"));
        OnboardingPrinter printer = new ConsolePrinter();

        OnboardingService svc = new OnboardingService(parser, validator, db, printer);

        String raw = "name=Riya;email=riya@sst.edu;phone=9876543210;program=CSE";
        svc.registerFromRawInput(raw);

        // Stretch goal
        String failRaw = "name=;email=notanemail;phone=abc;program=PHYSICS";
        svc.registerFromRawInput(failRaw);

        System.out.println();
        System.out.println("-- DB DUMP --");
        System.out.print(TextTable.render3(db));
    }
}
