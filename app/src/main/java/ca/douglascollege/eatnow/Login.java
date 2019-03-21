package ca.douglascollege.eatnow;

import android.arch.lifecycle.LiveData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ca.douglascollege.eatnow.database.User;
import ca.douglascollege.eatnow.database.UserRepository;

public class Login extends AppCompatActivity {

    String email, password;
    TextInputLayout tiEmail, tiPassword;
    EditText etEmail, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        tiEmail = findViewById(R.id.tiEmail);
        tiPassword = findViewById(R.id.tiPassword);
        Button btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = etEmail.getText().toString();
                password = etPassword.getText().toString();

                //TODO: Change it to validation
                if (email.length() <= 0)
                    tiEmail.setError("Enter an email");
                else
                    tiEmail.setError(null);
                if (password.length() <= 0)
                    tiPassword.setError("Enter an password");
                else
                    tiPassword.setError(null);

                if(email.length() > 0 && password.length() > 0) {
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
                                finish();
                            } else {
                                tiPassword.setError("Incorrect password");
                            }
                        } else {
                            tiEmail.setError("That user does not exist");
                        }
                    } catch (Exception e) {
                        Log.d("Login", e.getMessage());
                    }
                }
            }
        });


    }
}
