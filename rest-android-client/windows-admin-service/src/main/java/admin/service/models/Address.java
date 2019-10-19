package admin.service.models;

import com.google.gson.annotations.SerializedName;

public class Address {
    private String street;
    private String number;
    private String city;
    @SerializedName("postal_code")
    private String postCode;
    private double latitude;
    private double longitude;

    public Address(String street, String number, String city, String postCode, double latitude, double longitude) {
        this.street = street;
        this.number = number;
        this.city = city;
        this.postCode = postCode;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Address() {
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
