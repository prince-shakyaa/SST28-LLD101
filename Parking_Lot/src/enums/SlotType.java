package enums;

public enum SlotType {
    SMALL(20.0),    // For 2-wheelers — ₹20/hr
    MEDIUM(40.0),   // For cars        — ₹40/hr
    LARGE(80.0);    // For buses       — ₹80/hr

    private final double hourlyRate;

    SlotType(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }
}
