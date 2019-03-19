package ca.douglascollege.eatnow;

public class Restaurant {
    private int imageId;
    private String name;
    private String distance;

    public Restaurant(int imageId, String name, String distance) {
        this.imageId = imageId;
        this.name = name;
        this.distance = distance;
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

    public void setName(String name) {
        this.name = name;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
