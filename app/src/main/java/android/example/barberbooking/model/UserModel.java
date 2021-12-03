package android.example.barberbooking.model;

public class UserModel {
    private String userName;
    private String roadName;
    private String city;
    private String phone;
    private String itemList;
    private float price;
    private String slotTime;
    private String slotNo;

    public String getSlotTime() {
        return slotTime;
    }

    public void setSlotTime(String slotTime) {
        this.slotTime = slotTime;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }




    public String getItemList() {
        return itemList;
    }

    public void setItemList(String itemList) {
        this.itemList = itemList;
    }



    public UserModel() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoadName() {
        return roadName;
    }

    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSlotNo() {
        return slotNo;
    }

    public void setSlotNo(String slotNo) {
        this.slotNo = slotNo;
    }

    public UserModel(String userName, String roadName, String city, String phone, String itemList, float price, String slotTime, String slotNo) {
        this.userName = userName;
        this.roadName = roadName;
        this.city = city;
        this.phone = phone;
        this.itemList = itemList;
        this.price = price;
        this.slotTime = slotTime;
        this.slotNo = slotNo;
    }
}
