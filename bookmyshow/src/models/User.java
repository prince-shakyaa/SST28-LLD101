package models;

import java.time.LocalDate;

public class User {

    private static int idCounter = 1;

    private final int userId;
    private String name;
    private String emailId;
    private String mobNo;
    private String sex;
    private LocalDate dateOfBirth;
    private Address address;

    public User(String name, String emailId, String mobNo, String sex, LocalDate dateOfBirth, Address address) {
        this.userId = idCounter++;
        this.name = name;
        this.emailId = emailId;
        this.mobNo = mobNo;
        this.sex = sex;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getMobNo() {
        return mobNo;
    }

    public void setMobNo(String mobNo) {
        this.mobNo = mobNo;
    }

    public String getSex() {
        return sex;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "User{id=" + userId + ", name='" + name + "', email='" + emailId + "', mobile='" + mobNo + "'}";
    }
}
