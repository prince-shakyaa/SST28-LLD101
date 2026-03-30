package models;

import enums.SeatStatus;
import enums.SeatType;

public class Seat {

    private final String seatId;
    private final int screenId;
    private final char row;
    private final int seatNumber;
    private final SeatType seatType;
    private volatile SeatStatus status;

    public Seat(int screenId, char row, int seatNumber, SeatType seatType) {
        this.screenId = screenId;
        this.row = row;
        this.seatNumber = seatNumber;
        this.seatType = seatType;
        this.status = SeatStatus.AVAILABLE;
        this.seatId = "S" + screenId + "_" + row + seatNumber;
    }

    public String getSeatId() {
        return seatId;
    }

    public int getScreenId() {
        return screenId;
    }

    public char getRow() {
        return row;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public SeatType getSeatType() {
        return seatType;
    }

    public SeatStatus getStatus() {
        return status;
    }

    public void setStatus(SeatStatus status) {
        this.status = status;
    }

    public boolean isAvailable() {
        return this.status == SeatStatus.AVAILABLE;
    }

    @Override
    public String toString() {
        return "Seat{id='" + seatId + "', row=" + row + ", number=" + seatNumber
                + ", type=" + seatType + ", status=" + status + "}";
    }
}
