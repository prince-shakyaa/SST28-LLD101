/** DIP abstraction: any code grading strategy. */
public interface ICodeGrader {
    int grade(Submission s, Rubric r);
}
