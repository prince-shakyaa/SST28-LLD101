import enums.Genre;
import enums.MovieType;
import enums.SeatType;
import managers.BookingManager;
import models.Address;
import models.Booking;
import models.Movie;
import models.Screen;
import models.Showtime;
import models.Theater;
import models.User;
import system.BookMyShowSystem;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        BookMyShowSystem bms = BookMyShowSystem.getInstance();

        System.out.println("=== Setting up Users ===");
        User alice = new User("Alice", "alice@mail.com", "9876543210", "F", LocalDate.of(1998, 5, 12),
                new Address("12", "Near Park", "Mumbai", "Maharashtra", "400001", "India"));
        User bob = new User("Bob", "bob@mail.com", "9876543211", "M", LocalDate.of(1995, 8, 20),
                new Address("22", "IT Hub", "Mumbai", "Maharashtra", "400002", "India"));
        User carol = new User("Carol", "carol@mail.com", "9876543212", "F", LocalDate.of(2000, 3, 15),
                new Address("33", "Old City", "Delhi", "Delhi", "110001", "India"));
        bms.registerUser(alice);
        bms.registerUser(bob);
        bms.registerUser(carol);

        System.out.println("\n=== Setting up Movies ===");
        Movie inception = new Movie("Inception", "A thief enters dreams to plant ideas", 148,
                Genre.THRILLER, MovieType.ENGLISH, "Christopher Nolan", LocalDate.of(2010, 7, 16));
        Movie dangal = new Movie("Dangal", "A father trains his daughters for wrestling gold", 161,
                Genre.DRAMA, MovieType.HINDI, "Nitesh Tiwari", LocalDate.of(2016, 12, 21));
        Movie avengers = new Movie("Avengers: Endgame", "Heroes assemble for final battle", 181,
                Genre.ACTION, MovieType.ENGLISH, "Russo Brothers", LocalDate.of(2019, 4, 26));
        bms.addMovie(inception);
        bms.addMovie(dangal);
        bms.addMovie(avengers);

        System.out.println("\n=== Setting up Theaters ===");
        Address pvr1Address = new Address("Level 3", "Phoenix Mall", "Mumbai", "Maharashtra", "400068", "India");
        Theater pvrMumbai = new Theater("PVR Phoenix Mumbai", pvr1Address);

        Screen screen1 = new Screen("Audi 1", pvrMumbai.getTheaterId(), 6, 10);
        Screen screen2 = new Screen("Audi 2", pvrMumbai.getTheaterId(), 8, 12);
        pvrMumbai.addScreen(screen1);
        pvrMumbai.addScreen(screen2);

        Address imax1Address = new Address("4th Floor", "Inorbit Mall", "Mumbai", "Maharashtra", "400064", "India");
        Theater imaxMumbai = new Theater("IMAX Inorbit Mumbai", imax1Address);
        Screen screen3 = new Screen("IMAX Hall", imaxMumbai.getTheaterId(), 10, 15);
        imaxMumbai.addScreen(screen3);

        bms.addTheater(pvrMumbai);
        bms.addTheater(imaxMumbai);

        System.out.println("\n=== Setting up Showtimes ===");
        Showtime show1 = new Showtime(inception.getMovieId(), screen1.getScreenId(),
                pvrMumbai.getTheaterId(), LocalDateTime.of(2026, 4, 1, 10, 0), inception.getDurationMinutes());
        show1.setSeatPrice(SeatType.NORMAL.name(), 150);
        show1.setSeatPrice(SeatType.EXECUTIVE.name(), 250);
        show1.setSeatPrice(SeatType.PREMIUM.name(), 350);
        show1.setSeatPrice(SeatType.VIP.name(), 500);

        Showtime show2 = new Showtime(inception.getMovieId(), screen3.getScreenId(),
                imaxMumbai.getTheaterId(), LocalDateTime.of(2026, 4, 1, 14, 30), inception.getDurationMinutes());
        show2.setSeatPrice(SeatType.NORMAL.name(), 200);
        show2.setSeatPrice(SeatType.EXECUTIVE.name(), 300);
        show2.setSeatPrice(SeatType.PREMIUM.name(), 450);
        show2.setSeatPrice(SeatType.VIP.name(), 700);

        Showtime show3 = new Showtime(avengers.getMovieId(), screen2.getScreenId(),
                pvrMumbai.getTheaterId(), LocalDateTime.of(2026, 4, 1, 18, 0), avengers.getDurationMinutes());
        show3.setSeatPrice(SeatType.NORMAL.name(), 180);
        show3.setSeatPrice(SeatType.EXECUTIVE.name(), 280);
        show3.setSeatPrice(SeatType.PREMIUM.name(), 400);
        show3.setSeatPrice(SeatType.VIP.name(), 600);

        bms.addShowtime(show1);
        bms.addShowtime(show2);
        bms.addShowtime(show3);

        System.out.println("\n=== Scenario 1: Alice searches for Inception ===");
        bms.searchMovieByTitle("Inception");

        System.out.println("\n=== Scenario 2: Alice views showtimes in Mumbai ===");
        bms.getShowtimesInCity(inception.getMovieId(), "Mumbai");

        System.out.println("\n=== Scenario 3: Alice views seat map for Show 1 ===");
        bms.displaySeatMap(show1.getShowtimeId());

        System.out.println("\n=== Scenario 4: Alice books 2 seats for Inception at PVR ===");
        List<String> aliceSeats = Arrays.asList(
                "S" + screen1.getScreenId() + "_A0",
                "S" + screen1.getScreenId() + "_A1"
        );
        Booking aliceBooking = bms.bookTickets(alice.getUserId(), show1.getShowtimeId(), aliceSeats);

        System.out.println("\n=== Scenario 5: Bob tries to book same seats (concurrency conflict) ===");
        List<String> bobSeats = Arrays.asList(
                "S" + screen1.getScreenId() + "_A0",
                "S" + screen1.getScreenId() + "_A2"
        );
        Booking bobBooking = bms.bookTickets(bob.getUserId(), show1.getShowtimeId(), bobSeats);

        System.out.println("\n=== Scenario 6: Bob books different available seats ===");
        List<String> bobNewSeats = Arrays.asList(
                "S" + screen1.getScreenId() + "_B0",
                "S" + screen1.getScreenId() + "_B1"
        );
        Booking bobNewBooking = bms.bookTickets(bob.getUserId(), show1.getShowtimeId(), bobNewSeats);

        System.out.println("\n=== Scenario 7: Alice views her booking history ===");
        bms.getBookingHistory(alice.getUserId());

        System.out.println("\n=== Scenario 8: Alice cancels her booking ===");
        if (aliceBooking != null) {
            bms.cancelBooking(aliceBooking.getConfirmationId(), alice.getUserId());
        }

        System.out.println("\n=== Scenario 9: Concurrency test - two threads book the same seat ===");
        String sharedSeatId = "S" + screen3.getScreenId() + "_A0";

        Thread t1 = new Thread(() -> {
            Booking b = bms.bookTickets(alice.getUserId(), show2.getShowtimeId(),
                    Arrays.asList(sharedSeatId));
            System.out.println("[Thread-1 Alice] Result: " + (b != null ? b.getConfirmationId() : "FAILED"));
        });

        Thread t2 = new Thread(() -> {
            Booking b = bms.bookTickets(carol.getUserId(), show2.getShowtimeId(),
                    Arrays.asList(sharedSeatId));
            System.out.println("[Thread-2 Carol] Result: " + (b != null ? b.getConfirmationId() : "FAILED"));
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("\n=== Scenario 10: Search by genre ===");
        bms.searchMovieByGenre(Genre.ACTION);

        bms.printSystemSummary();
    }
}
