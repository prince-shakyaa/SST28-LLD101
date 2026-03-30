package managers;

import models.Theater;
import models.Screen;
import models.Showtime;

import java.util.ArrayList;
import java.util.List;

public class TheaterManager {

    private static TheaterManager instance;
    private final List<Theater> theaters;
    private final List<Showtime> showtimes;

    private TheaterManager() {
        this.theaters = new ArrayList<>();
        this.showtimes = new ArrayList<>();
    }

    public static TheaterManager getInstance() {
        if (instance == null) {
            synchronized (TheaterManager.class) {
                if (instance == null) {
                    instance = new TheaterManager();
                }
            }
        }
        return instance;
    }

    public void addTheater(Theater theater) {
        theaters.add(theater);
        System.out.println("[TheaterManager] Added: " + theater.getTheaterName());
    }

    public void addShowtime(Showtime showtime) {
        showtimes.add(showtime);
        System.out.println("[TheaterManager] Showtime added: " + showtime);
    }

    public Theater getTheaterById(int theaterId) {
        for (Theater theater : theaters) {
            if (theater.getTheaterId() == theaterId) {
                return theater;
            }
        }
        return null;
    }

    public List<Theater> getTheatersByCity(String city) {
        List<Theater> results = new ArrayList<>();
        for (Theater theater : theaters) {
            if (theater.getAddress().getCity().equalsIgnoreCase(city)) {
                results.add(theater);
            }
        }
        return results;
    }

    public List<Showtime> getShowtimesForMovie(int movieId) {
        List<Showtime> results = new ArrayList<>();
        for (Showtime showtime : showtimes) {
            if (showtime.getMovieId() == movieId) {
                results.add(showtime);
            }
        }
        return results;
    }

    public List<Showtime> getShowtimesForMovieInCity(int movieId, String city) {
        List<Theater> cityTheaters = getTheatersByCity(city);
        List<Integer> theaterIds = new ArrayList<>();
        for (Theater t : cityTheaters) {
            theaterIds.add(t.getTheaterId());
        }
        List<Showtime> results = new ArrayList<>();
        for (Showtime showtime : showtimes) {
            if (showtime.getMovieId() == movieId && theaterIds.contains(showtime.getTheaterId())) {
                results.add(showtime);
            }
        }
        return results;
    }

    public Showtime getShowtimeById(int showtimeId) {
        for (Showtime showtime : showtimes) {
            if (showtime.getShowtimeId() == showtimeId) {
                return showtime;
            }
        }
        return null;
    }

    public Screen getScreenForShowtime(int showtimeId) {
        Showtime showtime = getShowtimeById(showtimeId);
        if (showtime == null) {
            return null;
        }
        Theater theater = getTheaterById(showtime.getTheaterId());
        if (theater == null) {
            return null;
        }
        return theater.getScreenById(showtime.getScreenId());
    }

    public List<Theater> getAllTheaters() {
        return theaters;
    }

    public List<Showtime> getAllShowtimes() {
        return showtimes;
    }
}
