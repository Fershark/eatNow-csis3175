package ca.douglascollege.eatnow;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Date;

import ca.douglascollege.eatnow.database.User;
import ca.douglascollege.eatnow.database.UserRepository;

public class Welcome extends AppCompatActivity implements View.OnClickListener {

    Button btnRegister;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        //Check if a user is logged
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String email = sharedPreferences.getString("email","");
        if(!email.equals("")) {
            Intent i = new Intent(this, Home.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            overridePendingTransition(0, 0);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnRegister:
                Intent iRegister = new Intent(this, Register.class);
                startActivity(iRegister);
                break;
            case R.id.btnLogin:
                Intent iLogin = new Intent(this, Login.class);
                startActivity(iLogin);
                break;
            default:
                break;
        }
    }
}
