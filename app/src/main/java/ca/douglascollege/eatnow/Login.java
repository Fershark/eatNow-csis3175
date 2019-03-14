package ca.douglascollege.eatnow;

import android.arch.lifecycle.LiveData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

import ca.douglascollege.eatnow.database.User;
import ca.douglascollege.eatnow.database.UserRepository;

public class Login extends AppCompatActivity {

    String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText etEmail = findViewById(R.id.etEmail);
        final EditText etPassword = findViewById(R.id.etPassword);
        Button btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = etEmail.getText().toString();
                password = etPassword.getText().toString();

                if (email.length() <= 0) {
                    Toast.makeText(Login.this,"Enter an email",Toast.LENGTH_LONG).show();
                }
                else if (password.length() <= 0) {
                    Toast.makeText(Login.this,"Enter a password",Toast.LENGTH_LONG).show();
                }
                else {
                    UserRepository userRepository = new UserRepository(Login.this);
                    try {
                        User user = userRepository.getUserByEmail(email);
                        if (user != null) {
                            if (user.getPassword().equals(password) && user.getEmail().equals(email)) {
                                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Login.this);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("email", email);
                                editor.commit();
                                Intent i = new Intent(Login.this, Home.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                            } else {
                                Toast.makeText(Login.this, "Incorrect password", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(Login.this, "That user does not exist", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        Log.d("Login", e.getMessage());
                    }
                }
            }
        });


    }
}
