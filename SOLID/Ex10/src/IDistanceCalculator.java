/** DIP abstraction: any distance calculation strategy. */
public interface IDistanceCalculator {
    double km(GeoPoint a, GeoPoint b);
}
