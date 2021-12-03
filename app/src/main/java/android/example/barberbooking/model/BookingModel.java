package android.example.barberbooking.model;

public class BookingModel {
    private String bookingId;
    private String userName;
    private String userAddress;
    private String userPhone;
    private String itemList;
    private String pricePaid;
    private String bycId;
    private String bycName;
    private String bycPhone;
    private String timeSlot;
    private String dateSlot;
    private String status;
    private String trackingList;
    private String currentSlotNo;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    private String transactionId;


    public String getUserCity() {
        return userCity;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    private String userCity;

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getItemList() {
        return itemList;
    }

    public void setItemList(String itemList) {
        this.itemList = itemList;
    }

    public String getPricePaid() {
        return pricePaid;
    }

    public void setPricePaid(String pricePaid) {
        this.pricePaid = pricePaid;
    }

    public String getBycId() {
        return bycId;
    }

    public void setBycId(String bycId) {
        this.bycId = bycId;
    }

    public String getBycPhone() {
        return bycPhone;
    }

    public void setBycPhone(String bycPhone) {
        this.bycPhone = bycPhone;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String getDateSlot() {
        return dateSlot;
    }

    public void setDateSlot(String dateSlot) {
        this.dateSlot = dateSlot;
    }

    public String getBycName() {
        return bycName;
    }

    public void setBycName(String bycName) {
        this.bycName = bycName;
    }

    public BookingModel() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTrackingList() {
        return trackingList;
    }

    public void setTrackingList(String trackingList) {
        this.trackingList = trackingList;
    }

    public BookingModel(String bookingId, String userName,
                        String userAddress, String userCity, String userPhone,
                        String itemList, String pricePaid, String bycId, String bycName,
                        String bycPhone, String timeSlot, String dateSlot, String status, String trackingList,String currentSlotNo,String transactionId) {
        this.bookingId = bookingId;
        this.userName = userName;
        this.userAddress = userAddress;
        this.userPhone = userPhone;
        this.itemList = itemList;
        this.pricePaid = pricePaid;
        this.bycId = bycId;
        this.bycName = bycName;
        this.bycPhone = bycPhone;
        this.timeSlot = timeSlot;
        this.dateSlot = dateSlot;
        this.userCity= userCity;
        this.status= status;
        this.trackingList= trackingList;
        this.currentSlotNo= currentSlotNo;
        this.transactionId= transactionId;
    }
}
