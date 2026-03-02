import java.util.ArrayList;
import java.util.List;

/**
 * Registry stores devices as Object so it is not coupled to any fat interface.
 * Lookup by simple class name returns Object; callers cast to the capability
 * interface they actually need (ISP-compliant).
 */
public class DeviceRegistry {
    private final List<Object> devices = new ArrayList<>();

    public void add(Object d) {
        devices.add(d);
    }

    public Object getFirstOfType(String simpleName) {
        for (Object d : devices) {
            if (d.getClass().getSimpleName().equals(simpleName))
                return d;
        }
        throw new IllegalStateException("Missing: " + simpleName);
    }
}
