// ISP: LightsPanel only implements power + brightness. No irrelevant methods.
public class LightsPanel implements IPowerControl, IBrightnessControl {
    @Override
    public void powerOn() {
        /* always on when added */ }

    @Override
    public void powerOff() {
        System.out.println("Lights OFF");
    }

    @Override
    public void setBrightness(int pct) {
        System.out.println("Lights set to " + pct + "%");
    }
}
