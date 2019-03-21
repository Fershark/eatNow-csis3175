package ca.douglascollege.eatnow;

public class Restaurant {
    private int imageId;
    private String name;
    private String type;
    private Double latitude;
    private Double longitude;

    public Restaurant(int imageId, String name, String type, Double latitude, Double longitude) {
        this.imageId = imageId;
        this.name = name;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {        this.name = name;    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {     this.latitude = latitude;}

    public Double getLongitude() {        return longitude;    }

    public void setLongitude(Double longitude) {        this.longitude = longitude;    }

    public String getType() {       return type;    }

    public void setType(String type) {        this.type = type;    }
}
