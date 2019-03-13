package ca.douglascollege.eatnow;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isEmailOk = Validation.isValidEmail(etEmail);
                boolean isPasswordOk = Validation.isValidPassword(etPassword);
                boolean isConfirmPswdOk = Validation.isValidConfirmPassword(etPassword,etConfirmPassword);
                boolean isNameOk = Validation.isValidName(etName);
                boolean isLastNameOk = Validation.isValidName(etLastName);
                boolean isAddressOk = Validation.isValidAddress(etAddress);
                boolean isEmailRecOk = Validation.isValidEmail(etEmail);

                if(isEmailOk && isPasswordOk && isConfirmPswdOk && isNameOk && isLastNameOk && isAddressOk && isEmailRecOk){
                    Toast.makeText(Register.this, "OK!!!",Toast.LENGTH_LONG).show();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("email",etEmail.getText().toString());
                    editor.putString("pswd",etPassword.getText().toString());
                    editor.putString("name",etName.getText().toString());
                    editor.putString("lastName",etLastName.getText().toString());
                    editor.putString("address",etAddress.getText().toString());
                    editor.putString("emailRec",etEmailRecommend.getText().toString());
                }
                else{
                    Toast.makeText(Register.this, "Please correct errors",Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}
