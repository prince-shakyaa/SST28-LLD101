package models;

public class Address {

    private String streetNo;
    private String landmark;
    private String city;
    private String state;
    private String pinCode;
    private String country;

    public Address(String streetNo, String landmark, String city, String state, String pinCode, String country) {
        this.streetNo = streetNo;
        this.landmark = landmark;
        this.city = city;
        this.state = state;
        this.pinCode = pinCode;
        this.country = country;
    }

    public String getStreetNo() {
        return streetNo;
    }

    public String getLandmark() {
        return landmark;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPinCode() {
        return pinCode;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public String toString() {
        return streetNo + ", " + landmark + ", " + city + ", " + state + " - " + pinCode + ", " + country;
    }
}
