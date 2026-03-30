package models;

import java.util.ArrayList;
import java.util.List;

public class Theater {

    private static int idCounter = 1;

    private final int theaterId;
    private String theaterName;
    private Address address;
    private float rating;
    private final List<Screen> screens;

    public Theater(String theaterName, Address address) {
        this.theaterId = idCounter++;
        this.theaterName = theaterName;
        this.address = address;
        this.rating = 0.0f;
        this.screens = new ArrayList<>();
    }

    public void addScreen(Screen screen) {
        screens.add(screen);
        System.out.println("[Theater: " + theaterName + "] Screen '" + screen.getScreenName() + "' added.");
    }

    public Screen getScreenById(int screenId) {
        for (Screen screen : screens) {
            if (screen.getScreenId() == screenId) {
                return screen;
            }
        }
        return null;
    }

    public int getTheaterId() {
        return theaterId;
    }

    public String getTheaterName() {
        return theaterName;
    }

    public void setTheaterName(String theaterName) {
        this.theaterName = theaterName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public List<Screen> getScreens() {
        return screens;
    }

    @Override
    public String toString() {
        return "Theater{id=" + theaterId + ", name='" + theaterName + "', city='" + address.getCity()
                + "', screens=" + screens.size() + ", rating=" + rating + "}";
    }
}
