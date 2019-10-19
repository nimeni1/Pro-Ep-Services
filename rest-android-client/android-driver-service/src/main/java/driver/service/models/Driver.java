package driver.service.models;

import com.google.gson.annotations.SerializedName;
import org.bson.types.ObjectId;

public class Driver {
    @SerializedName("_id")
    private ObjectId id;
    private Name name;
    @SerializedName("license_number")
    private String licenseNumber;
    private String email;
    private String password;
    private String phone;
    private Boolean active;
    @SerializedName("average_rating")
    private double averageRating;
    private double payslip;

    public Driver(Name name, String licenseNumber, String email, String password, String phone, Boolean active,
                  double averageRating, double payslip) {
        this.name = name;
        this.licenseNumber = licenseNumber;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.active = active;
        this.averageRating = averageRating;
        this.payslip = payslip;
    }

    public Driver(Name name, String licenseNumber, String email, String password, String phone, Boolean active) {
        this.name = name;
        this.licenseNumber = licenseNumber;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.active = active;
    }

    public Driver() { }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public String getId() {
        return id.toString();
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public double getPayslip() {
        return payslip;
    }

    public void setPayslip(double payslip) {
        this.payslip = payslip;
    }

}