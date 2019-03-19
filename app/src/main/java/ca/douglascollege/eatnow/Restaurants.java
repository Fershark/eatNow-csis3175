package ca.douglascollege.eatnow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

public class Restaurants extends AppCompatActivity {

    private final String TAG = "RestaurantsActivity";
    private final int SIZE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);
        Intent intent = getIntent();
        double latitude = 0, longitude = 0;
        if(intent != null){
            latitude = intent.getDoubleExtra("latitude",0);
            longitude = intent.getDoubleExtra("longitude",0);
        }
        Log.d(TAG, "Lat/Long : " + latitude+" "+longitude);

        Log.d(TAG, "Starting creation..");
        ListView listView = findViewById(R.id.listViewRestaurant);

        Log.d(TAG, "Initializing restaurants..");
        ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
        for(int i =0; i<SIZE;i++){
            restaurants.add(new Restaurant(R.drawable.restaurant_image,"Restaurant"+i, "Meat and More", (1000+i) + "m"));
        }

        Log.d(TAG, "Creating Adapter..");
        RestaurantAdapter restaurantAdapter = new RestaurantAdapter(this, R.layout.adapter_view_layout, restaurants);
        listView.setAdapter(restaurantAdapter);
    }
}
