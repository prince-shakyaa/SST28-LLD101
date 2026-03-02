// DIP: concrete implementation — details live here, not in the booking service.
public class DriverAllocator implements IDriverAllocator {
    @Override
    public String allocate(String studentId) {
        return "DRV-17";
    }
}
