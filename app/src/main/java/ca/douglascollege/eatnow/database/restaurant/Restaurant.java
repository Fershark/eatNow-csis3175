package ca.douglascollege.eatnow.database.restaurant;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.location.Location;

import java.io.Serializable;

@Entity
public class Restaurant implements Comparable<Restaurant>, Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String image;
    private String phone;
    private String name;
    private String type;
    private double latitude;
    private double longitude;
    @Ignore
    private float distanceFromUser;

    public Restaurant(String image, String phone, String name, String type, double latitude, double longitude) {
        this.image = image;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public float getDistanceFromUser() {
        return distanceFromUser;
    }

    public void setDistanceFromUser(Location userLocation) {
        Location restaurantLocation = new Location("restaurant_" + id);
        restaurantLocation.setLatitude(latitude);
        restaurantLocation.setLongitude(longitude);

        //Distance is get in meters, it will be transformed to kms
        float distanceFromUserKms = userLocation.distanceTo(restaurantLocation) / 1000;
        //Round it to two decimal
        distanceFromUser = ((float) Math.round(distanceFromUserKms * 100)) / 100;
    }

    public int compareTo(Restaurant r) {
        if (distanceFromUser > r.getDistanceFromUser())
            return 1;
        else if (distanceFromUser < r.getDistanceFromUser())
            return -1;
        else
            return 0;
    }
}
