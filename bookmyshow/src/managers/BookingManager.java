package managers;

import enums.BookingStatus;
import enums.SeatStatus;
import models.Booking;
import models.Screen;
import models.Seat;
import models.Showtime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingManager {

    private static BookingManager instance;
    private final List<Booking> bookings;
    private final Map<String, Object> seatLocks;

    private BookingManager() {
        this.bookings = new ArrayList<>();
        this.seatLocks = new HashMap<>();
    }

    public static BookingManager getInstance() {
        if (instance == null) {
            synchronized (BookingManager.class) {
                if (instance == null) {
                    instance = new BookingManager();
                }
            }
        }
        return instance;
    }

    public Booking createBooking(int userId, int showtimeId, List<String> seatIds) {
        TheaterManager theaterManager = TheaterManager.getInstance();
        Showtime showtime = theaterManager.getShowtimeById(showtimeId);

        if (showtime == null) {
            System.out.println("[BookingManager] Showtime " + showtimeId + " not found.");
            return null;
        }

        Screen screen = theaterManager.getScreenForShowtime(showtimeId);
        if (screen == null) {
            System.out.println("[BookingManager] Screen for showtime " + showtimeId + " not found.");
            return null;
        }

        Booking booking = new Booking(userId, showtimeId, showtime.getMovieId(),
                showtime.getTheaterId(), showtime.getScreenId());

        List<Seat> seatsToBook = new ArrayList<>();
        for (String seatId : seatIds) {
            Seat seat = screen.getSeatById(seatId);
            if (seat == null) {
                System.out.println("[BookingManager] Seat " + seatId + " not found on screen.");
                return null;
            }
            seatsToBook.add(seat);
        }

        boolean allReserved = reserveSeats(seatsToBook, showtime);
        if (!allReserved) {
            return null;
        }

        for (Seat seat : seatsToBook) {
            double price = showtime.getPriceForSeatType(seat.getSeatType().name());
            booking.addSeat(seat, price);
        }

        boolean confirmed = booking.confirmBooking();
        if (!confirmed) {
            releaseSeats(seatsToBook);
            return null;
        }

        for (Seat seat : seatsToBook) {
            seat.setStatus(SeatStatus.BOOKED);
        }

        bookings.add(booking);
        System.out.println("[BookingManager] Booking created: " + booking.getConfirmationId()
                + " for user " + userId);
        return booking;
    }

    private boolean reserveSeats(List<Seat> seats, Showtime showtime) {
        List<Object> acquiredLocks = new ArrayList<>();
        List<Seat> reservedSeats = new ArrayList<>();

        for (Seat seat : seats) {
            String lockKey = showtime.getShowtimeId() + "_" + seat.getSeatId();
            Object lock = seatLocks.computeIfAbsent(lockKey, k -> new Object());

            synchronized (lock) {
                if (!seat.isAvailable()) {
                    System.out.println("[BookingManager] Seat " + seat.getSeatId()
                            + " is no longer available. Releasing all reservations.");
                    for (Seat reserved : reservedSeats) {
                        reserved.setStatus(SeatStatus.AVAILABLE);
                    }
                    return false;
                }
                seat.setStatus(SeatStatus.RESERVED);
                reservedSeats.add(seat);
                acquiredLocks.add(lock);
            }
        }
        return true;
    }

    private void releaseSeats(List<Seat> seats) {
        for (Seat seat : seats) {
            seat.setStatus(SeatStatus.AVAILABLE);
        }
    }

    public boolean cancelBooking(String confirmationId, int userId) {
        Booking booking = getBookingByConfirmationId(confirmationId);
        if (booking == null) {
            System.out.println("[BookingManager] Booking not found: " + confirmationId);
            return false;
        }
        if (booking.getUserId() != userId) {
            System.out.println("[BookingManager] Unauthorized cancellation attempt by user " + userId);
            return false;
        }
        boolean cancelled = booking.cancelBooking();
        if (cancelled) {
            releaseSeats(booking.getBookedSeats());
        }
        return cancelled;
    }

    public Booking getBookingByConfirmationId(String confirmationId) {
        for (Booking booking : bookings) {
            if (booking.getConfirmationId().equals(confirmationId)) {
                return booking;
            }
        }
        return null;
    }

    public List<Booking> getBookingsForUser(int userId) {
        List<Booking> userBookings = new ArrayList<>();
        for (Booking booking : bookings) {
            if (booking.getUserId() == userId) {
                userBookings.add(booking);
            }
        }
        return userBookings;
    }

    public List<Booking> getAllBookings() {
        return bookings;
    }
}
