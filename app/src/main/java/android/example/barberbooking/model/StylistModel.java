package android.example.barberbooking.model;

public class StylistModel {
    public StylistModel() {
    }

    private String bycId,ownername,phoneNo,city,address,serviceList,workImg,basePrice;

    public String getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(String basePrice) {
        this.basePrice = basePrice;
    }

    public String getOwnername() {
        return ownername;
    }

    public void setOwnername(String ownername) {
        this.ownername = ownername;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getServiceList() {
        return serviceList;
    }

    public void setServiceList(String serviceList) {
        this.serviceList = serviceList;
    }

    public String getWorkImg() {
        return workImg;
    }

    public void setWorkImg(String workImg) {
        this.workImg = workImg;
    }



    public String getBycId() {
        return bycId;
    }

    public void setBycId(String bycId) {
        this.bycId = bycId;
    }



    public StylistModel(String bycId, String ownername, String phoneNo, String city, String address, String serviceList, String workImg,String basePrice){
        this.bycId =bycId;
        this.ownername = ownername;
        this.phoneNo = phoneNo;
        this.city = city;
        this.address = address;
        this.serviceList = serviceList;
        this.workImg = workImg;
        this.basePrice = basePrice;
    }
}
