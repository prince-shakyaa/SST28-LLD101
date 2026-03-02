// DIP: implements the abstraction; concrete grading logic stays here, not in pipeline.
public class CodeGrader implements ICodeGrader {
    @Override
    public int grade(Submission s, Rubric r) {
        int base = Math.min(80, 50 + s.code.length() % 40);
        return base + r.bonus;
    }
}
