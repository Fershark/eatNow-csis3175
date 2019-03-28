package ca.douglascollege.eatnow;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ca.douglascollege.eatnow.database.order.Order;
import ca.douglascollege.eatnow.database.orderDetail.OrderDetail;
import ca.douglascollege.eatnow.database.product.Product;
import ca.douglascollege.eatnow.database.product.ProductRepository;
import ca.douglascollege.eatnow.database.restaurant.Restaurant;
import ca.douglascollege.eatnow.utilities.Helper;

public class Products extends AppCompatActivity {
    private static final String TAG = "PRODUCTS";
    private static final int DETAIL_ACTIVITY_REQUEST = 1;
    private static final int CHECKOUT_ACTIVITY_REQUEST = 2;
    private Order order;
    private Restaurant restaurant;
    private ArrayList<OrderDetail> orderDetails;
    private ProductAdapter productAdapter;
    private ConstraintLayout clViewOrder;
    private TextView txtProductsNum;
    private TextView txtTotalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        Intent intent = getIntent();
        restaurant = (Restaurant) intent.getSerializableExtra("restaurant");
        order = (Order) intent.getSerializableExtra("order");

        orderDetails = new ArrayList<>();

        // Set the image in the toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        //Set title of action bar
        setTitle(restaurant.getName());

        ListView listView = findViewById(R.id.listViewProducts);
        productAdapter = new ProductAdapter(this);
        listView.setAdapter(productAdapter);

        ProductRepository productRepository = new ProductRepository(getApplicationContext());
        productRepository.findProductsForRestaurant(restaurant.getId()).observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(@Nullable List<Product> products) {
                productAdapter.setProducts(products);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product product = (Product) parent.getAdapter().getItem(position);
                Intent i = new Intent(Products.this, ProductDetail.class);
                i.putExtra("product", product);
                startActivityForResult(i, DETAIL_ACTIVITY_REQUEST);
            }
        });

        clViewOrder = findViewById(R.id.clViewOrder);
        txtProductsNum = findViewById(R.id.txtProductsNum);
        txtTotalPrice = findViewById(R.id.txtTotalPrice);

        updateOrder();
        clViewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Products.this, OrderDetailsView.class);
                i.putExtra("order", order);
                i.putExtra("orderDetails", orderDetails);
                i.putExtra("restaurant", restaurant);
                startActivityForResult(i, CHECKOUT_ACTIVITY_REQUEST);
            }
        });
    }

    private void updateOrder() {
        int productsNum = 0;
        float totalPrice = 0;
        for (OrderDetail orderDetail : orderDetails) {
            productsNum += orderDetail.getQuantity();
            totalPrice += orderDetail.getTotalPrice();
        }

        if (productsNum == 0)
            Helper.hideComponentInConstraintLayout(clViewOrder);
        else {
            order.setTotalPrice(totalPrice);
            Helper.showComponentInConstraintLayout(clViewOrder, (int) getResources().getDimension(R.dimen.bottom_banner_height));
            txtProductsNum.setText(Integer.toString(productsNum));
            txtTotalPrice.setText(Helper.getCurrencyFormatted(order.getTotalPriceSummed()));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DETAIL_ACTIVITY_REQUEST) {
            if (resultCode == RESULT_OK) {
                OrderDetail orderDetail = (OrderDetail) data.getSerializableExtra("orderDetail");
                orderDetails.add(orderDetail);
                updateOrder();
            }
        } else if (requestCode == CHECKOUT_ACTIVITY_REQUEST) {
            orderDetails = (ArrayList<OrderDetail>) data.getSerializableExtra("orderDetails");
            updateOrder();
        }
    }
}
