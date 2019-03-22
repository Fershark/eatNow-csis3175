package ca.douglascollege.eatnow;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

import ca.douglascollege.eatnow.database.Restaurant;
import ca.douglascollege.eatnow.database.RestaurantRepository;

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

        Location userLocation = new Location("user");
        userLocation.setLatitude(latitude);
        userLocation.setLongitude(longitude);

        Log.d(TAG, "Starting creation..");
        ListView listView = findViewById(R.id.listViewRestaurant);

        Log.d(TAG, "Initializing restaurants..");
        //TODO: The data is not beeing returned
        /*RestaurantRepository restaurantRepository = new RestaurantRepository(this);
        ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>(restaurantRepository.getRestaurants().getValue());
        */
        //TODO: After the table is ok in the database remove this ArrayList.
        ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
        restaurants.add(new Restaurant(R.drawable.restaurant_image,"1541239871","Triple O's", "Fast Food", 49.203512, -122.912552));
        restaurants.add(new Restaurant(R.drawable.restaurant_image,"2541239871","Hyack Sushi", "Japanese Food", 49.202403, -122.912562));
        restaurants.add(new Restaurant(R.drawable.restaurant_image,"3541239871","V Cafe", "Breakfast", 49.202315, -122.912503));
        restaurants.add(new Restaurant(R.drawable.restaurant_image,"4541239871","The Old Spaghetti Factory", "Italian Food", 49.201946, -122.912596));
        restaurants.add(new Restaurant(R.drawable.restaurant_image,"5541239871","Ki Sushi Restaurant", "Japanese Food", 49.202023, -122.911947));
        restaurants.add(new Restaurant(R.drawable.restaurant_image,"6541239871","Piva Modern Italian", "Pizzeria", 49.201540, -122.911385));
        restaurants.add(new Restaurant(R.drawable.restaurant_image,"7541239871","Pizzeria Ludica", "Pizzeria", 49.204141, -122.909211));
        restaurants.add(new Restaurant(R.drawable.restaurant_image,"8541239871","Patsara Thai", "Thai Food", 49.204325, -122.908047));
        restaurants.add(new Restaurant(R.drawable.restaurant_image,"9541239871","Sushi Yen", "Japanese Food", 49.203921, -122.908415));
        restaurants.add(new Restaurant(R.drawable.restaurant_image,"1111111111"," The Old Bavaria Haus Restaurant", "Steakhouse", 49.208526, -122.914147));

        Log.d(TAG,"restaurants[0]:"+restaurants.get(0).getName());

        Log.d(TAG, "Creating Adapter..");
        RestaurantAdapter restaurantAdapter = new RestaurantAdapter(this, R.layout.adapter_view_layout, restaurants, userLocation);
        listView.setAdapter(restaurantAdapter);
    }
}
