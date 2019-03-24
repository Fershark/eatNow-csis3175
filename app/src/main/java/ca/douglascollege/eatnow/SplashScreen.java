package ca.douglascollege.eatnow;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ca.douglascollege.eatnow.database.user.UserRepository;
import ca.douglascollege.eatnow.utilities.Helper;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Create instance of the database
        //SQLiteOpenHelper does not create the database when you create the SQLiteOpenHelper instance. It will do so once you call getReadableDatabase() or getWriteableDatabase().
        //https://stackoverflow.com/questions/47619718/room-database-not-created
        new UserRepository(getApplicationContext()).getUserByEmail(" ");

        //Check if a user is logged
        Helper helper = new Helper(this);

        Intent i;
        if(helper.getEmailLoggedUser() != null)
            i = new Intent(this, Home.class);
        else
            i = new Intent(this, Welcome.class);

        startActivity(i);
        finish();
    }
}
