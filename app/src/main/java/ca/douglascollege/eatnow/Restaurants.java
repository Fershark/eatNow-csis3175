package ca.douglascollege.eatnow;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.location.Location;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Collections;
import java.util.List;

import ca.douglascollege.eatnow.database.order.Order;
import ca.douglascollege.eatnow.database.restaurant.Restaurant;
import ca.douglascollege.eatnow.database.restaurant.RestaurantRepository;

public class Restaurants extends AppCompatActivity {
    private final String TAG = "RESTAURANTS";
    private Location userLocation;
    private Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);

        // Set the image in the toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        Intent intent = getIntent();
        double latitude = 0, longitude = 0;
        if (intent != null) {
            latitude = intent.getDoubleExtra("latitude", 0);
            longitude = intent.getDoubleExtra("longitude", 0);
            order = (Order) intent.getSerializableExtra("order");
        }
        Log.d(TAG, "Lat/Long : " + latitude + " " + longitude);

        userLocation = new Location("user");
        userLocation.setLatitude(latitude);
        userLocation.setLongitude(longitude);

        Log.d(TAG, "Starting creation...");
        ListView listView = findViewById(R.id.listViewRestaurant);
        Log.d(TAG, "Creating Adapter...");
        final RestaurantAdapter restaurantAdapter = new RestaurantAdapter(this);
        listView.setAdapter(restaurantAdapter);

        Log.d(TAG, "Initializing restaurants...");
        RestaurantRepository restaurantRepository = new RestaurantRepository(getApplicationContext());
        restaurantRepository.getRestaurants().observe(this, new Observer<List<Restaurant>>() {
            @Override
            public void onChanged(@Nullable List<Restaurant> restaurants) {
                for (Restaurant restaurant : restaurants)
                    restaurant.setDistanceFromUser(userLocation);
                Collections.sort(restaurants);
                restaurantAdapter.setRestaurants(restaurants);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Restaurant restaurant = (Restaurant) parent.getAdapter().getItem(position);
                order.setRestaurantId(restaurant.getId());

                Intent i = new Intent(Restaurants.this, Products.class);
                i.putExtra("restaurant", restaurant);
                i.putExtra("order", order);
                startActivity(i);
            }
        });
    }
}
