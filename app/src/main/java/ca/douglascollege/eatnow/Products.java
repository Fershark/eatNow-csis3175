package ca.douglascollege.eatnow;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.nfc.Tag;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import ca.douglascollege.eatnow.database.product.Product;
import ca.douglascollege.eatnow.database.product.ProductRepository;
import ca.douglascollege.eatnow.database.restaurant.Restaurant;

public class Products extends AppCompatActivity {
    private static final String TAG = "PRODUCTS";
    private static final int VIEW_ORDER_HEIGHT = 60;
    private static final int DETAIL_ACTIVITY_REQUEST = 1;
    Restaurant restaurant;
    ProductAdapter productAdapter;
    ConstraintLayout clViewOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        Intent intent = getIntent();
        if (intent != null)
            restaurant = (Restaurant) intent.getSerializableExtra("restaurant");

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
        clViewOrder.setVisibility(View.INVISIBLE);
        clViewOrder.setMaxHeight(0);
        clViewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "CLICKED");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DETAIL_ACTIVITY_REQUEST) {
            Log.d(TAG, "WORKING");
            if (resultCode == RESULT_OK) {
                data.getSerializableExtra("order_detail");
            }
        }
    }
}
