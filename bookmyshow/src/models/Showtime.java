package models;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Showtime {

    private static int idCounter = 1;

    private final int showtimeId;
    private final int movieId;
    private final int screenId;
    private final int theaterId;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final Map<String, Double> seatPriceMap;

    public Showtime(int movieId, int screenId, int theaterId, LocalDateTime startTime, int movieDurationMinutes) {
        this.showtimeId = idCounter++;
        this.movieId = movieId;
        this.screenId = screenId;
        this.theaterId = theaterId;
        this.startTime = startTime;
        this.endTime = startTime.plusMinutes(movieDurationMinutes);
        this.seatPriceMap = new HashMap<>();
    }

    public void setSeatPrice(String seatType, double price) {
        seatPriceMap.put(seatType, price);
    }

    public double getPriceForSeatType(String seatType) {
        return seatPriceMap.getOrDefault(seatType, 150.0);
    }

    public int getShowtimeId() {
        return showtimeId;
    }

    public int getMovieId() {
        return movieId;
    }

    public int getScreenId() {
        return screenId;
    }

    public int getTheaterId() {
        return theaterId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public Map<String, Double> getSeatPriceMap() {
        return seatPriceMap;
    }

    @Override
    public String toString() {
        return "Showtime{id=" + showtimeId + ", movieId=" + movieId + ", screenId=" + screenId
                + ", theaterId=" + theaterId + ", start=" + startTime + ", end=" + endTime + "}";
    }
}
