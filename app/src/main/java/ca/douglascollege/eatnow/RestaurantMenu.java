package ca.douglascollege.eatnow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ca.douglascollege.eatnow.database.Restaurant;

public class RestaurantMenu extends AppCompatActivity {
    private final String TAG = "RESTAURANT_MENU";
    Restaurant restaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_menu);

        // Set the image in the toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        Intent intent = getIntent();
        if (intent != null)
            restaurant = (Restaurant) intent.getSerializableExtra("restaurant");

        setTitle(restaurant.getName());
    }
}
