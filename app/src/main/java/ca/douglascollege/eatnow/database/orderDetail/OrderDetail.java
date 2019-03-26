package ca.douglascollege.eatnow.database.orderDetail;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

import ca.douglascollege.eatnow.database.order.Order;
import ca.douglascollege.eatnow.database.product.Product;

@Entity(foreignKeys = {
        @ForeignKey(entity = Order.class,
                parentColumns = "id",
                childColumns = "order_id"
        ),
        @ForeignKey(entity = Product.class,
                parentColumns = "id",
                childColumns = "product_id"
        )
})
public class OrderDetail implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int quantity;
    @ColumnInfo(name = "total_price")
    private float totalPrice;
    @ColumnInfo(name = "special_instructions")
    private String specialInstructions;
    @ColumnInfo(name = "order_id", index = true)
    private int orderId;
    @ColumnInfo(name = "product_id", index = true)
    private int productId;

    public OrderDetail(float totalPrice, int productId) {
        quantity = 1;
        this.totalPrice = totalPrice;
        specialInstructions = null;
        this.productId = productId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getSpecialInstructions() {
        return specialInstructions;
    }

    public void setSpecialInstructions(String specialInstructions) {
        this.specialInstructions = specialInstructions;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

}
