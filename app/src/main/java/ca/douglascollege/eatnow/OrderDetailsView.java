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

import java.util.ArrayList;
import java.util.List;

import ca.douglascollege.eatnow.database.order.Order;
import ca.douglascollege.eatnow.database.orderDetail.OrderDetail;
import ca.douglascollege.eatnow.database.orderDetail.OrderDetailRepository;
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
        isOrderHistory = intent.getBooleanExtra("isOrderHistory", false);

        // Set the image in the toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        //Set title of action bar
        if (isOrderHistory)
            setTitle(getString(R.string.yourPreviousOrder));
        else
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
        orderDetailAdapter = new OrderDetailAdapter(this, isOrderHistory);

        //Adapter
        txtRestaurant.setText(restaurant.getName());
        listView.setAdapter(orderDetailAdapter);
        if (isOrderHistory) {
            OrderDetailRepository orderDetailRepository = new OrderDetailRepository(getApplicationContext());
            orderDetailRepository.findOrderDetailsByOrder(order.getId()).observe(this, new Observer<List<OrderDetail>>() {
                @Override
                public void onChanged(@Nullable List<OrderDetail> orderDetails) {
                    OrderDetailsView.this.orderDetails = (ArrayList<OrderDetail>) orderDetails;
                    setTotalPrice();
                    orderDetailAdapter.setOrderDetails(orderDetails);
                }
            });
            clCheckout.setClickable(false);
            Helper.hideComponentInConstraintLayout(clCheckout);
        } else {
            setTotalPrice();
            orderDetailAdapter.setOrderDetails(orderDetails);
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

                totalPrice = order.getTotalPriceSummed();
                if (order.getDiscount() > 0) {
                    String discount = getString(R.string.discountDialog, Helper.getCurrencyFormatted(order.getTotalPrice() * order.getDiscount()));
                    String price = getString(R.string.priceDialog, Helper.getCurrencyFormatted(totalPrice));
                    titleText = discount + price;
                } else
                    titleText = getString(R.string.priceDialog, Helper.getCurrencyFormatted(totalPrice));


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
                        Intent i = new Intent(OrderDetailsView.this, Checkout.class);
                        i.putExtra("order", order);
                        i.putExtra("orderDetails", orderDetails);
                        i.putExtra("restaurant", restaurant);
                        startActivity(i);
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
