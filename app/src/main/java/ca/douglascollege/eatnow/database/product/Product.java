package ca.douglascollege.eatnow.database.product;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

import ca.douglascollege.eatnow.database.restaurant.Restaurant;

@Entity(foreignKeys = @ForeignKey(entity = Restaurant.class,
        parentColumns = "id",
        childColumns = "restaurant_id"
))
public class Product implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String image;
    private String description;
    @ColumnInfo(name = "unit_price")
    private float unitPrice;
    @ColumnInfo(name = "restaurant_id", index = true)
    private int restaurantId;

    public Product(String name, String image, String description, float unitPrice, int restaurantId) {
        this.name = name;
        this.image = image;
        this.description = description;
        this.unitPrice = unitPrice;
        this.restaurantId = restaurantId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

}
