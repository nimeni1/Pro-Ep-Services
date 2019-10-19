package client.service.models;

import com.google.gson.annotations.SerializedName;
import org.bson.types.ObjectId;

public class Client {
    @SerializedName("_id")
    private ObjectId id;
    private Name name;
    private String email;
    private String password;
    @SerializedName("phone_number")
    private String phone;


    private Client(Name name, String email, String password, String phone) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    public Client() {}

    public String getId() {
        return id.toString();
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
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
}
