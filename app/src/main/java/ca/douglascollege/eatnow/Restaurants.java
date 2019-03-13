package ca.douglascollege.eatnow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Restaurants extends AppCompatActivity {

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
        System.out.println(latitude+" "+longitude);
    }
}
