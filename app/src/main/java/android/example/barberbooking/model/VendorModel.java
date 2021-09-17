package android.example.barberbooking.model;

public class VendorModel {
    private String ownerName, saloonName, phoneNo, aadharNo, city, address, gender, serviceList,time,status;

    public VendorModel() {
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getSaloonName() {
        return saloonName;
    }

    public void setSaloonName(String saloonName) {
        this.saloonName = saloonName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getAadharNo() {
        return aadharNo;
    }

    public void setAadharNo(String aadharNo) {
        this.aadharNo = aadharNo;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getServiceList() {
        return serviceList;
    }

    public void setServiceList(String serviceList) {
        this.serviceList = serviceList;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public VendorModel(String ownerName, String saloonName, String phoneNo, String aadharNo, String city, String address, String gender, String serviceList,String time,String status) {
        this.ownerName = ownerName;
        this.saloonName = saloonName;
        this.phoneNo = phoneNo;
        this.aadharNo = aadharNo;
        this.city = city;
        this.address = address;
        this.gender = gender;
        this.serviceList = serviceList;

        this.time = time;
        this.status = status;
    }



}
