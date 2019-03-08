package ca.douglascollege.eatnow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText etEmail = findViewById(R.id.etEmail);
        final EditText etPassword = findViewById(R.id.etPassword);
        final EditText etConfirmPassword = findViewById(R.id.etConfirmPasword);
        final EditText etName = findViewById(R.id.etFirstName);
        final EditText etLastName = findViewById(R.id.etLastName);
        final EditText etAddress = findViewById(R.id.etAddress);
        final EditText etEmailRecommend = findViewById(R.id.etEmailRecommend);
        final Button btnSubmit = findViewById(R.id.btnSubmit);
    }
}
