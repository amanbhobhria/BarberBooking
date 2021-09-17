package android.example.barberbooking.model;

public class SliderData {

    public SliderData(int imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(int imgUrl) {
        this.imgUrl = imgUrl;
    }

    // image url is used to
    // store the url of image
    private int imgUrl;


}