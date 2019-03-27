package ca.douglascollege.eatnow;

import android.app.Activity;
import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ca.douglascollege.eatnow.database.order.Order;
import ca.douglascollege.eatnow.database.order.OrderRepository;
import ca.douglascollege.eatnow.database.orderDetail.OrderDetail;
import ca.douglascollege.eatnow.database.orderDetail.OrderDetailRepository;
import ca.douglascollege.eatnow.database.recommendation.Recommendation;
import ca.douglascollege.eatnow.database.recommendation.RecommendationRepository;
import ca.douglascollege.eatnow.database.restaurant.Restaurant;
import ca.douglascollege.eatnow.utilities.Helper;

public class OrderDetailsView extends AppCompatActivity {
    private Order order;
    private ArrayList<OrderDetail> orderDetails;
    private Restaurant restaurant;
    private OrderDetailAdapter orderDetailAdapter;
    private boolean isOrderHistory;
    private TextView txtSubtotal;
    private TextView txtDiscount;
    private TextView txtDiscountText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details_view);

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

        //Footer
        View footerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.order_view_list_footer_layout, null, false);
        listView.addFooterView(footerView, null, false);
        txtSubtotal = footerView.findViewById(R.id.txtSubtotal);
        txtDiscount = footerView.findViewById(R.id.txtDiscount);
        txtDiscountText = footerView.findViewById(R.id.txtDiscountText);

        setTotalPrice();

        //Adapter
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
        } else {
            orderDetailAdapter.setOrderDetails(orderDetails);
            txtRestaurant.setText(restaurant.getName());
        }

        clCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder deliveryPickup = new AlertDialog.Builder(OrderDetailsView.this, R.style.AppTheme_DialogTheme);
                final String titleText;
                final float totalPrice;
                final TextView txtTitleDialog = new TextView(OrderDetailsView.this);
                txtTitleDialog.setPadding((int) getResources().getDimension(R.dimen.dialog_title_margin),
                        (int) getResources().getDimension(R.dimen.dialog_title_margin),
                        0,
                        (int) getResources().getDimension(R.dimen.dialog_title_margin_bottom)
                );
                txtTitleDialog.setTextSize(getResources().getDimensionPixelSize(R.dimen.dialog_title_text_size));
                txtTitleDialog.setTextColor(Color.BLACK);

                if (order.getDiscount() > 0) {
                    totalPrice = order.getTotalPrice() * (1 - order.getDiscount());
                    String discount = getString(R.string.discountDialog, Helper.getCurrencyFormatted(order.getTotalPrice() * order.getDiscount()));
                    String price = getString(R.string.priceDialog, Helper.getCurrencyFormatted(totalPrice));
                    titleText = discount + price;
                } else {
                    totalPrice = order.getTotalPrice();
                    titleText = getString(R.string.priceDialog, Helper.getCurrencyFormatted(order.getTotalPrice()));
                }

                txtTitleDialog.setText(titleText);
                deliveryPickup.setCustomTitle(txtTitleDialog);
                deliveryPickup.setSingleChoiceItems(getResources().getStringArray(R.array.deliveryPickup),
                        -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int selectedIndex) {
                                String totalPriceString;

                                if (selectedIndex == 1) {
                                    order.setDelivery(true);
                                    String deliveryString = getString(R.string.deliveryFee, Helper.getCurrencyFormatted(Helper.DELIVERY_FEE));
                                    totalPriceString = deliveryString + getString(R.string.totalPriceDialog, Helper.getCurrencyFormatted(totalPrice + Helper.DELIVERY_FEE));
                                } else {
                                    order.setDelivery(false);
                                    totalPriceString = getString(R.string.totalPriceDialog, Helper.getCurrencyFormatted(totalPrice));
                                }
                                txtTitleDialog.setText(titleText + "\n" + totalPriceString);
                                deliveryPickup.setCustomTitle(txtTitleDialog);
                            }
                        });
                deliveryPickup.setPositiveButton(getString(R.string.checkout), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Intent i = new Intent(OrderView.this, Checkout.class);
//                        i.putExtra("order", order);
//                        i.putExtra("orderDetails", orderDetails);
//                        i.putExtra("restaurant", restaurant);
//                        startActivity(i);
                        // Save order, order details and recommendation if there is a discount
                        order.setDate(new Date());

                        OrderRepository orderRepository = new OrderRepository(OrderDetailsView.this.getApplicationContext());
                        OrderDetailRepository orderDetailRepository = new OrderDetailRepository(OrderDetailsView.this.getApplicationContext());

                        int orderId = orderRepository.insertOrder(order);
                        for (OrderDetail orderDetail : orderDetails) {
                            orderDetail.setOrderId(orderId);
                            orderDetailRepository.insertOrderDetail(orderDetail);
                        }
                        if (order.getDiscount() > 0) {
                            RecommendationRepository recommendationRepository = new RecommendationRepository(OrderDetailsView.this.getApplicationContext());
                            Recommendation recommendation = recommendationRepository.findUnsedRecommendationByUser(order.getUserId());
                            recommendation.setDateUsed(new Date());
                            recommendationRepository.updateRecommendation(recommendation);
                        }

                        Toast.makeText(OrderDetailsView.this.getApplicationContext(), R.string.orderComplete, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(OrderDetailsView.this, Home.class));
                    }
                });
                deliveryPickup.setNegativeButton(getString(R.string.cancel), null);
                deliveryPickup.show();
            }
        });
    }

    public void deleteOrderDetail(int index) {
        orderDetails.remove(index);
        if (orderDetails.isEmpty())
            onBackPressed();
        else {
            setTotalPrice();
            orderDetailAdapter.setOrderDetails(orderDetails);
        }
    }

    public void setTotalPrice() {
        float totalPrice = 0;
        for (OrderDetail orderDetail : orderDetails)
            totalPrice += orderDetail.getTotalPrice();
        order.setTotalPrice(totalPrice);
        if (order.getDiscount() > 0) {
            txtDiscount.setText(Helper.getCurrencyFormatted(totalPrice * order.getDiscount()));
            txtSubtotal.setText(Helper.getCurrencyFormatted(totalPrice * (1 - order.getDiscount())));
        } else {
            Helper.hideComponentInConstraintLayout(txtDiscount);
            Helper.hideComponentInConstraintLayout(txtDiscountText);
            txtSubtotal.setText(Helper.getCurrencyFormatted(totalPrice));
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent();
        i.putExtra("orderDetails", orderDetails);
        setResult(Activity.RESULT_OK, i);
        finish();
    }
}
