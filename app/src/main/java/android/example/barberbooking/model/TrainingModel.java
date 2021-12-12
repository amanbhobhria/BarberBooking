package android.example.barberbooking.model;

public class TrainingModel {
    String email,phone,name;

    public TrainingModel() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TrainingModel(String email, String phone, String name) {
        this.email = email;
        this.phone = phone;
        this.name = name;
    }
}
