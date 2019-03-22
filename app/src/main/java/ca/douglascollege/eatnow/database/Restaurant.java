package ca.douglascollege.eatnow.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity
public class Restaurant {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int imageId;
    private String phone;
    private String name;
    private String type;
    private Double latitude;
    private Double longitude;

    public Restaurant(int imageId, String phone, String name, String type, Double latitude, Double longitude) {
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

    public void setName(String name) {        this.name = name;    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {     this.latitude = latitude;}

    public Double getLongitude() {        return longitude;    }

    public void setLongitude(Double longitude) {        this.longitude = longitude;    }

    public String getType() {       return type;    }

    public void setType(String type) {        this.type = type;    }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone;  }
}
