// DIP: concrete implementation — details live here, not in the booking service.
public class DistanceCalculator implements IDistanceCalculator {
    @Override
    public double km(GeoPoint a, GeoPoint b) {
        double d = Math.abs(a.lat - b.lat) + Math.abs(a.lon - b.lon);
        return Math.round((d * 200.0) * 10.0) / 10.0;
    }
}
