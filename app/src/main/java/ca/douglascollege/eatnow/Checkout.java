package ca.douglascollege.eatnow;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import ca.douglascollege.eatnow.database.order.Order;
import ca.douglascollege.eatnow.database.order.OrderRepository;
import ca.douglascollege.eatnow.database.orderDetail.OrderDetail;
import ca.douglascollege.eatnow.database.orderDetail.OrderDetailRepository;
import ca.douglascollege.eatnow.database.recommendation.Recommendation;
import ca.douglascollege.eatnow.database.recommendation.RecommendationRepository;
import ca.douglascollege.eatnow.database.restaurant.Restaurant;
import ca.douglascollege.eatnow.utilities.Helper;

public class Checkout extends AppCompatActivity {
    private Order order;
    private ArrayList<OrderDetail> orderDetails;
    private Restaurant restaurant;
    private boolean isOrderHistory;
    private TextView txtDate, txtType, txtCheckoutAddress, txtCheckoutTime, txtDelivery, txtDeliveryFee, txtDiscount, txtDiscountPrice, txtSubtotalPrice, txtTotalPrice;
    private ConstraintLayout clBottom;
    private TextView txtBottomButton, txtProductsCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        Intent intent = getIntent();
        order = (Order) intent.getSerializableExtra("order");
        orderDetails = (ArrayList<OrderDetail>) intent.getSerializableExtra("orderDetails");
        restaurant = (Restaurant) intent.getSerializableExtra("restaurant");
        isOrderHistory = intent.getBooleanExtra("isOrderHistory", false);

        // Set the image in the toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        //Set title of action bar
        setTitle(restaurant.getName());

        txtDate = findViewById(R.id.txtDate);
        txtType = findViewById(R.id.txtType);
        txtCheckoutAddress = findViewById(R.id.txtCheckoutAddress);
        txtCheckoutTime = findViewById(R.id.txtCheckoutTime);
        txtDelivery = findViewById(R.id.txtDelivery);
        txtDeliveryFee = findViewById(R.id.txtDeliveryFee);
        txtDiscount = findViewById(R.id.txtDiscount);
        txtDiscountPrice = findViewById(R.id.txtDiscountPrice);
        txtSubtotalPrice = findViewById(R.id.txtSubtotalPrice);
        txtTotalPrice = findViewById(R.id.txtTotalPrice);
        clBottom = findViewById(R.id.clBottom);
        txtBottomButton = findViewById(R.id.txtBottomButton);
        txtProductsCount = findViewById(R.id.txtProductsCount);

        if (isOrderHistory) {
            txtDate.setText(Helper.getDateFormatted(order.getDate()));
            txtBottomButton.setText(getString(R.string.viewProducts));
            txtProductsCount.setText(Integer.toString(order.getOrderDetailsCount()));
        } else {
            Helper.hideComponentInConstraintLayout(txtDate);
            txtProductsCount.setText(Integer.toString(orderDetails.size()));
        }
        if (order.isDelivery()) {
            txtType.setText(getString(R.string.delivery));
            txtCheckoutAddress.setText(order.getDeliveryAddress());
            txtDeliveryFee.setText(Helper.getCurrencyFormatted(Helper.DELIVERY_FEE));
            txtTotalPrice.setText(Helper.getCurrencyFormatted(order.getTotalPriceSummed() + Helper.DELIVERY_FEE));
        } else {
            txtType.setText(getString(R.string.pickUp));
            txtType.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_car, 0, 0, 0);
            Helper.hideComponentInConstraintLayout(txtCheckoutAddress);
            Helper.hideComponentInConstraintLayout(txtCheckoutTime);
            Helper.hideComponentInConstraintLayout(txtDelivery);
            Helper.hideComponentInConstraintLayout(txtDeliveryFee);
            txtTotalPrice.setText(Helper.getCurrencyFormatted(order.getTotalPriceSummed()));
        }
        if (order.getDiscount() > 0)
            txtDiscountPrice.setText(Helper.getCurrencyFormatted(order.getTotalPrice() * order.getDiscount()));
        else {
            Helper.hideComponentInConstraintLayout(txtDiscount);
            Helper.hideComponentInConstraintLayout(txtDiscountPrice);
        }
        txtSubtotalPrice.setText(Helper.getCurrencyFormatted(order.getTotalPrice()));

        clBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOrderHistory) {
                    Intent i = new Intent(Checkout.this, OrderDetailsView.class);
                    i.putExtra("order", order);
                    i.putExtra("restaurant", restaurant);
                    i.putExtra("isOrderHistory", true);
                    startActivity(i);
                } else {
                    // Save order, order details and recommendation if there is a discount
                    order.setDate(new Date());

                    OrderRepository orderRepository = new OrderRepository(Checkout.this.getApplicationContext());
                    OrderDetailRepository orderDetailRepository = new OrderDetailRepository(Checkout.this.getApplicationContext());

                    int orderId = orderRepository.insertOrder(order);
                    for (OrderDetail orderDetail : orderDetails) {
                        orderDetail.setOrderId(orderId);
                        orderDetailRepository.insertOrderDetail(orderDetail);
                    }
                    if (order.getDiscount() > 0) {
                        RecommendationRepository recommendationRepository = new RecommendationRepository(Checkout.this.getApplicationContext());
                        Recommendation recommendation = recommendationRepository.findUnsedRecommendationByUser(order.getUserId());
                        recommendation.setDateUsed(new Date());
                        recommendationRepository.updateRecommendation(recommendation);
                    }

                    Toast.makeText(Checkout.this.getApplicationContext(), R.string.orderComplete, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Checkout.this, Home.class));
                }
            }
        });
    }
}
