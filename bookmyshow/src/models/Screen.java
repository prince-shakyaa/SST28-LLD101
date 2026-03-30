package models;

import enums.SeatStatus;
import enums.SeatType;

import java.util.ArrayList;
import java.util.List;

public class Screen {

    private static int idCounter = 1;

    private final int screenId;
    private final String screenName;
    private final int theaterId;
    private final int totalRows;
    private final int seatsPerRow;
    private final List<Seat> seats;

    public Screen(String screenName, int theaterId, int totalRows, int seatsPerRow) {
        this.screenId = idCounter++;
        this.screenName = screenName;
        this.theaterId = theaterId;
        this.totalRows = totalRows;
        this.seatsPerRow = seatsPerRow;
        this.seats = new ArrayList<>();
        initializeSeats();
    }

    private void initializeSeats() {
        for (int r = 0; r < totalRows; r++) {
            char row = (char) ('A' + r);
            SeatType type = assignSeatType(r, totalRows);
            for (int s = 0; s < seatsPerRow; s++) {
                seats.add(new Seat(screenId, row, s, type));
            }
        }
    }

    private SeatType assignSeatType(int rowIndex, int totalRows) {
        double ratio = (double) rowIndex / totalRows;
        if (ratio < 0.30) {
            return SeatType.VIP;
        } else if (ratio < 0.55) {
            return SeatType.PREMIUM;
        } else if (ratio < 0.75) {
            return SeatType.EXECUTIVE;
        } else {
            return SeatType.NORMAL;
        }
    }

    public int getScreenId() {
        return screenId;
    }

    public String getScreenName() {
        return screenName;
    }

    public int getTheaterId() {
        return theaterId;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public int getSeatsPerRow() {
        return seatsPerRow;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public int getTotalCapacity() {
        return totalRows * seatsPerRow;
    }

    public List<Seat> getAvailableSeats() {
        List<Seat> available = new ArrayList<>();
        for (Seat seat : seats) {
            if (seat.isAvailable()) {
                available.add(seat);
            }
        }
        return available;
    }

    public Seat getSeatById(String seatId) {
        for (Seat seat : seats) {
            if (seat.getSeatId().equals(seatId)) {
                return seat;
            }
        }
        return null;
    }

    public void displaySeatMap() {
        System.out.println("\n--- Seat Map: " + screenName + " (Screen " + screenId + ") ---");
        char currentRow = 0;
        for (Seat seat : seats) {
            if (seat.getRow() != currentRow) {
                if (currentRow != 0) {
                    System.out.println();
                }
                currentRow = seat.getRow();
                System.out.print(currentRow + " | ");
            }
            String symbol = seat.isAvailable() ? "[ ]" : "[X]";
            System.out.print(symbol + " ");
        }
        System.out.println("\n--- End of Seat Map ---\n");
    }

    @Override
    public String toString() {
        return "Screen{id=" + screenId + ", name='" + screenName + "', capacity=" + getTotalCapacity() + "}";
    }
}
