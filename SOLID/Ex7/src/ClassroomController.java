/**
 * ClassroomController depends only on the specific capability interfaces it
 * needs.
 * It retrieves a device by name and casts to the required interface — no device
 * is forced to implement capabilities it does not possess (ISP).
 */
public class ClassroomController {
    private final DeviceRegistry reg;

    public ClassroomController(DeviceRegistry reg) {
        this.reg = reg;
    }

    public void startClass() {
        IPowerControl pj = (IPowerControl) reg.getFirstOfType("Projector");
        IInputConnection pjIn = (IInputConnection) reg.getFirstOfType("Projector");
        pj.powerOn();
        pjIn.connectInput("HDMI-1");

        IBrightnessControl lights = (IBrightnessControl) reg.getFirstOfType("LightsPanel");
        lights.setBrightness(60);

        ITemperatureControl ac = (ITemperatureControl) reg.getFirstOfType("AirConditioner");
        ac.setTemperatureC(24);

        IScanner scan = (IScanner) reg.getFirstOfType("AttendanceScanner");
        System.out.println("Attendance scanned: present=" + scan.scanAttendance());
    }

    public void endClass() {
        System.out.println("Shutdown sequence:");
        ((IPowerControl) reg.getFirstOfType("Projector")).powerOff();
        ((IPowerControl) reg.getFirstOfType("LightsPanel")).powerOff();
        ((IPowerControl) reg.getFirstOfType("AirConditioner")).powerOff();
    }
}
