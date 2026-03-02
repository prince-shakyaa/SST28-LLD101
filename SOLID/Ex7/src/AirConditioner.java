// ISP: AirConditioner only implements power + temperature. No irrelevant methods.
public class AirConditioner implements IPowerControl, ITemperatureControl {
    @Override
    public void powerOn() {
        /* ok */ }

    @Override
    public void powerOff() {
        System.out.println("AC OFF");
    }

    @Override
    public void setTemperatureC(int c) {
        System.out.println("AC set to " + c + "C");
    }
}
