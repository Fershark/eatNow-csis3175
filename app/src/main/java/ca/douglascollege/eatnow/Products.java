package ca.douglascollege.eatnow;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.List;

import ca.douglascollege.eatnow.database.product.Product;
import ca.douglascollege.eatnow.database.product.ProductRepository;
import ca.douglascollege.eatnow.database.restaurant.Restaurant;

public class Products extends AppCompatActivity {
    private final String TAG = "PRODUCTS";
    Restaurant restaurant;
    ProductAdapter productAdapter;

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
    }
}
