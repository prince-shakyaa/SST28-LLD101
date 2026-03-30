package system;

import managers.BookingManager;
import managers.MovieManager;
import managers.TheaterManager;
import managers.UserManager;
import models.Booking;
import models.Movie;
import models.Screen;
import models.Showtime;
import models.Theater;
import models.User;
import enums.Genre;
import enums.MovieType;

import java.util.List;

public class BookMyShowSystem {

    private static BookMyShowSystem instance;

    private final UserManager userManager;
    private final MovieManager movieManager;
    private final TheaterManager theaterManager;
    private final BookingManager bookingManager;

    private BookMyShowSystem() {
        this.userManager = UserManager.getInstance();
        this.movieManager = MovieManager.getInstance();
        this.theaterManager = TheaterManager.getInstance();
        this.bookingManager = BookingManager.getInstance();
    }

    public static BookMyShowSystem getInstance() {
        if (instance == null) {
            synchronized (BookMyShowSystem.class) {
                if (instance == null) {
                    instance = new BookMyShowSystem();
                }
            }
        }
        return instance;
    }

    public void registerUser(User user) {
        userManager.registerUser(user);
    }

    public void addMovie(Movie movie) {
        movieManager.addMovie(movie);
    }

    public void addTheater(Theater theater) {
        theaterManager.addTheater(theater);
    }

    public void addShowtime(Showtime showtime) {
        theaterManager.addShowtime(showtime);
    }

    public List<Movie> searchMovieByTitle(String title) {
        List<Movie> results = movieManager.searchByTitle(title);
        System.out.println("\n[Search] Results for '" + title + "':");
        for (Movie movie : results) {
            System.out.println("  " + movie);
        }
        return results;
    }

    public List<Movie> searchMovieByGenre(Genre genre) {
        List<Movie> results = movieManager.searchByGenre(genre);
        System.out.println("\n[Search] Results for genre " + genre + ":");
        for (Movie movie : results) {
            System.out.println("  " + movie);
        }
        return results;
    }

    public List<Showtime> getShowtimesInCity(int movieId, String city) {
        List<Showtime> results = theaterManager.getShowtimesForMovieInCity(movieId, city);
        System.out.println("\n[Showtimes] For movieId=" + movieId + " in " + city + ":");
        for (Showtime s : results) {
            System.out.println("  " + s);
        }
        return results;
    }

    public void displaySeatMap(int showtimeId) {
        Screen screen = theaterManager.getScreenForShowtime(showtimeId);
        if (screen != null) {
            screen.displaySeatMap();
        } else {
            System.out.println("[System] Screen not found for showtime " + showtimeId);
        }
    }

    public Booking bookTickets(int userId, int showtimeId, List<String> seatIds) {
        return bookingManager.createBooking(userId, showtimeId, seatIds);
    }

    public boolean cancelBooking(String confirmationId, int userId) {
        return bookingManager.cancelBooking(confirmationId, userId);
    }

    public List<Booking> getBookingHistory(int userId) {
        List<Booking> bookings = bookingManager.getBookingsForUser(userId);
        System.out.println("\n[Booking History] User " + userId + ":");
        for (Booking b : bookings) {
            System.out.println("  " + b);
        }
        return bookings;
    }

    public void printSystemSummary() {
        System.out.println("\n========== BOOKMYSHOW SYSTEM SUMMARY ==========");
        System.out.println("Total Movies   : " + movieManager.getAllMovies().size());
        System.out.println("Total Theaters : " + theaterManager.getAllTheaters().size());
        System.out.println("Total Showtimes: " + theaterManager.getAllShowtimes().size());
        System.out.println("Total Bookings : " + bookingManager.getAllBookings().size());
        System.out.println("Total Users    : " + userManager.getAllUsers().size());
        System.out.println("================================================\n");
    }
}
