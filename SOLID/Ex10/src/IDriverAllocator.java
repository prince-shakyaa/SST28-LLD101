/** DIP abstraction: any driver allocation strategy. */
public interface IDriverAllocator {
    String allocate(String studentId);
}
