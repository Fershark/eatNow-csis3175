package ca.douglascollege.eatnow.database.order;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

import ca.douglascollege.eatnow.database.restaurant.Restaurant;
import ca.douglascollege.eatnow.database.user.User;

@Entity(foreignKeys = {
        @ForeignKey(entity = User.class,
                parentColumns = "id",
                childColumns = "user_id"
        ),
        @ForeignKey(entity = Restaurant.class,
                parentColumns = "id",
                childColumns = "restaurant_id"
        )
})
public class Order implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private Date date;
    private float totalPrice;
    @ColumnInfo(name="is_delivery")
    private boolean isDelivery;
    private float discount;
    @ColumnInfo(name = "delivery_address")
    private String deliveryAddress;
    @ColumnInfo(name = "user_id", index = true)
    private int userId;
    @ColumnInfo(name = "restaurant_id", index = true)
    private int restaurantId;
    @Ignore
    private int orderDetailsCount;

    public Order(String deliveryAddress, int userId) {
        this.deliveryAddress = deliveryAddress;
        totalPrice = 0;
        discount = 0;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean isDelivery() {
        return isDelivery;
    }

    public void setDelivery(boolean delivery) {
        isDelivery = delivery;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public float getTotalPriceSummed() {
        float totalPrice = 0;
        if (getDiscount() > 0)
            totalPrice = getTotalPrice() * (1 - getDiscount());
        else
            totalPrice = getTotalPrice();
        return totalPrice;
    }

    public int getOrderDetailsCount() {
        return orderDetailsCount;
    }

    public void setOrderDetailsCount(int orderDetailsCount) {
        this.orderDetailsCount = orderDetailsCount;
    }

}
