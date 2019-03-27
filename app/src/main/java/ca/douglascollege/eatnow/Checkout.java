package ca.douglascollege.eatnow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

import ca.douglascollege.eatnow.database.order.Order;
import ca.douglascollege.eatnow.database.orderDetail.OrderDetail;
import ca.douglascollege.eatnow.database.restaurant.Restaurant;
import ca.douglascollege.eatnow.utilities.Helper;

public class Checkout extends AppCompatActivity {

    private Order order;
    private TextView address, subtotal, gst, discount, total, delivery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        final float deliveryFee = (float)6.00;
        final float gstRate = (float)0.05;
        //Helper help = new Helper(this);

        address = findViewById(R.id.txtCheckoutAddress);
        delivery = findViewById(R.id.txtCourierPrice);
        subtotal = findViewById(R.id.txtSubtotalPrice);
        gst = findViewById(R.id.txtGSTPrice);
        discount = findViewById(R.id.txtDiscountPrice);
        total = findViewById(R.id.txtTotalPrice);

        Intent intent = getIntent();

        order = (Order) intent.getSerializableExtra("order");
        address.setText(order.getDeliveryAddress());
        delivery.setText(Helper.getCurrencyFormatted(deliveryFee));
        subtotal.setText( Helper.getCurrencyFormatted(order.getTotalPrice()));
        gst.setText(Helper.getCurrencyFormatted((deliveryFee + order.getTotalPrice())*gstRate));
        discount.setText(Helper.getCurrencyFormatted(order.getDiscount()));
        total.setText(Helper.getCurrencyFormatted((deliveryFee + order.getTotalPrice())*(1+gstRate) - order.getDiscount()));
    }
}
