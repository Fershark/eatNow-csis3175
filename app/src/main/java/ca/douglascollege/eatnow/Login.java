package ca.douglascollege.eatnow;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends AppCompatActivity {

    String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText etEmail = findViewById(R.id.etEmail);
        final EditText etPassword = findViewById(R.id.etPassword);
        Button btnSubmit = findViewById(R.id.btnSubmit);
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = etEmail.getText().toString();
                password = etPassword.getText().toString();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("keyEmail", email);
                editor.putString("keyPassword", password);
            }
        });


    }
}
