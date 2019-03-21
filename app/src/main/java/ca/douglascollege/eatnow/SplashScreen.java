package ca.douglascollege.eatnow;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Check if a user is logged
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String email = sharedPreferences.getString("email","");

        Intent i;
        if(!email.equals(""))
            i = new Intent(this, Home.class);
        else
            i = new Intent(this, Welcome.class);

        startActivity(i);
        finish();
    }
}
