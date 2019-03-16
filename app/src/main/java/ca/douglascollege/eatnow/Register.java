package ca.douglascollege.eatnow;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ca.douglascollege.eatnow.database.User;
import ca.douglascollege.eatnow.database.UserRepository;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        final EditText etEmail = findViewById(R.id.etEmail);
        final EditText etPassword = findViewById(R.id.etPassword);
        final EditText etConfirmPassword = findViewById(R.id.etConfirmPasword);
        final EditText etName = findViewById(R.id.etName);
        final EditText etPhone = findViewById(R.id.etPhone);
        final EditText etAddress = findViewById(R.id.etAddress);
        final EditText etEmailRecommend = findViewById(R.id.etEmailRecommend);

        final Button btnSubmit = findViewById(R.id.btnSubmit);

        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isEmailOk = Validation.isValidEmail(etEmail, Register.this);
                boolean isPasswordOk = Validation.isValidPassword(etPassword);
                boolean isConfirmPswdOk = Validation.isValidConfirmPassword(etPassword,etConfirmPassword);
                boolean isNameOk = Validation.isValidName(etName);
                boolean isPhoneOk = Validation.isValidPhone(etPhone);
                boolean isAddressOk = Validation.isValidAddress(etAddress, Register.this);
                boolean isEmailRecOk = Validation.isValidRecommendEmail(etEmailRecommend, Register.this);

                if(isEmailOk && isPasswordOk && isConfirmPswdOk && isNameOk && isPhoneOk && isAddressOk && isEmailRecOk){

                    User user = new User(etEmail.getText().toString(), etPhone.getText().toString(),
                            etName.getText().toString(), etPassword.getText().toString());

                    UserRepository userRepository = new UserRepository(Register.this);
                    userRepository.insertUser(user);

                    //Login
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Register.this);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("email", etEmail.toString());
                    editor.commit();

                    Intent i = new Intent(Register.this, Home.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }
                else{
                    Toast.makeText(Register.this, "Please correct errors",Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}
