package ca.douglascollege.eatnow.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.location.Location;

@Entity
public class Restaurant implements Comparable<Restaurant> {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int imageId;
    private String phone;
    private String name;
    private String type;
    private double latitude;
    private double longitude;
    @Ignore
    private int distanceFromUser;

    public Restaurant(int imageId, String phone, String name, String type, double latitude, double longitude) {
        this.imageId = imageId;
        this.name = name;
        this.phone = phone;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getDistanceFromUser() {
        return distanceFromUser;
    }

    public void setDistanceFromUser(Location userLocation) {
        Location restaurantLocation = new Location("restaurant_" + id);
        restaurantLocation.setLatitude(latitude);
        restaurantLocation.setLongitude(longitude);

        distanceFromUser = (int) userLocation.distanceTo(restaurantLocation);
    }

    public int compareTo(Restaurant r) {
        return distanceFromUser - r.getDistanceFromUser();
    }
}
