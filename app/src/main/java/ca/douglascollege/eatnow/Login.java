package ca.douglascollege.eatnow;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ca.douglascollege.eatnow.database.user.User;
import ca.douglascollege.eatnow.utilities.Helper;
import ca.douglascollege.eatnow.utilities.Validation;

public class Login extends AppCompatActivity {

    TextInputLayout tiEmail, tiPassword;
    EditText etEmail, etPassword;
    Validation validation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        validation = new Validation(this);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        tiEmail = findViewById(R.id.tiEmail);
        tiPassword = findViewById(R.id.tiPassword);
        Button btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = validation.validExistingEmail(tiEmail, etEmail);
                boolean isPasswordOk = validation.isValidPassword(tiPassword, etPassword);

                if (user != null && isPasswordOk) {
                    String password = etPassword.getText().toString();
                    String email = etEmail.getText().toString();

                    if (user.getPassword().equals(password) && user.getEmail().equals(email)) {
                        Helper helper = new Helper(Login.this);
                        helper.setEmailLoggedUser(email);
                        Intent i = new Intent(Login.this.getApplicationContext(), Home.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                    } else {
                        tiPassword.setError(Login.this.getString(R.string.incorrectPassword));
                    }
                }
            }
        });
    }
}
