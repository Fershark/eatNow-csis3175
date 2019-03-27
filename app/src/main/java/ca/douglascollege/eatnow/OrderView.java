package ca.douglascollege.eatnow;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.CheckedOutputStream;

import ca.douglascollege.eatnow.database.order.Order;
import ca.douglascollege.eatnow.database.orderDetail.OrderDetail;
import ca.douglascollege.eatnow.database.orderDetail.OrderDetailRepository;
import ca.douglascollege.eatnow.database.restaurant.Restaurant;

public class OrderView extends AppCompatActivity {
    private Order order;
    private ArrayList<OrderDetail> orderDetails;
    private Restaurant restaurant;
    private OrderDetailAdapter orderDetailAdapter;
    private static final int CHECKOUT_ACTIVITY_REQUEST = 1;
    private boolean isOrderHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_view);

        Intent intent = getIntent();
        order = (Order) intent.getSerializableExtra("order");
        orderDetails = (ArrayList<OrderDetail>) intent.getSerializableExtra("orderDetails");
        restaurant = (Restaurant) intent.getSerializableExtra("restaurant");

        // Set the image in the toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        //Set title of action bar
        setTitle(getString(R.string.yourOrder));

        TextView txtRestaurant = findViewById(R.id.txtRestaurant);
        ConstraintLayout clCheckout = findViewById(R.id.clCheckout);
        ListView listView = findViewById(R.id.lvOrder);

        orderDetailAdapter = new OrderDetailAdapter(this);
        listView.setAdapter(orderDetailAdapter);

        //TODO: Get restaurant if there is not for show order history
        if (restaurant == null) {
            isOrderHistory = true;
            OrderDetailRepository orderDetailRepository = new OrderDetailRepository(getApplicationContext());
            orderDetailRepository.findOrderDetailsByOrder(order.getId()).observe(this, new Observer<List<OrderDetail>>() {
                @Override
                public void onChanged(@Nullable List<OrderDetail> orderDetails) {
                    orderDetailAdapter.setOrderDetails(orderDetails);
                }
            });
        }
        else {
            orderDetailAdapter.setOrderDetails(orderDetails);
            txtRestaurant.setText(restaurant.getName());
        }

        clCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ORDER_VIEW", "GO TO CHECKOUT");
                Intent i = new Intent(OrderView.this, Checkout.class);
                i.putExtra("order", order);
                i.putExtra("orderDetails", orderDetails);
                i.putExtra("restaurant", restaurant);
                startActivityForResult(i, CHECKOUT_ACTIVITY_REQUEST);
            }
        });
    }

    public void deleteOrderDetail(int index) {
        orderDetails.remove(index);
        if (orderDetails.isEmpty())
            onBackPressed();
        else
            orderDetailAdapter.setOrderDetails(orderDetails);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent();
        i.putExtra("orderDetails", orderDetails);
        setResult(Activity.RESULT_OK, i);
        finish();
    }
}
