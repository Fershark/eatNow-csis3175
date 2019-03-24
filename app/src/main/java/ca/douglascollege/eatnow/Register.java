package ca.douglascollege.eatnow;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ca.douglascollege.eatnow.database.User;
import ca.douglascollege.eatnow.database.UserRepository;
import ca.douglascollege.eatnow.utilities.Validation;

public class Register extends AppCompatActivity {

    TextInputLayout tiEmail, tiPassword, tiConfirmPassword, tiName, tiPhone, tiAddress, tiEmailRecommend;
    EditText etEmail, etPassword, etConfirmPassword, etName, etPhone, etAddress, etEmailRecommend;
    Button btnSubmit;
    Validation validation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        tiEmail = findViewById(R.id.tiEmail);
        tiPassword = findViewById(R.id.tiPassword);
        tiConfirmPassword = findViewById(R.id.tiConfirmPassword);
        tiName = findViewById(R.id.tiName);
        tiPhone = findViewById(R.id.tiPhone);
        tiAddress = findViewById(R.id.tiAddress);
        tiEmailRecommend = findViewById(R.id.tiEmailRecommend);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etAddress = findViewById(R.id.etAddress);
        etEmailRecommend = findViewById(R.id.etEmailRecommend);

        btnSubmit = findViewById(R.id.btnSubmit);
        validation = new Validation(this);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isEmailOk = validation.isValidNewEmail(tiEmail, etEmail);
                boolean isPasswordOk = validation.isValidPassword(tiPassword, etPassword);
                boolean isConfirmPasswordOk = validation.isValidConfirmPassword(tiConfirmPassword, etConfirmPassword, etPassword);
                boolean isNameOk = validation.isValidName(tiName, etName);
                boolean isPhoneOk = validation.isValidPhone(tiPhone, etPhone);
                boolean isAddressOk = validation.isValidAddress(tiAddress, etAddress);
                boolean recommendationEmailEntered = (etEmailRecommend.getText().toString().length() > 0);
                boolean isEmailRecOk = !recommendationEmailEntered || validation.isValidExistingEmail(tiEmailRecommend, etEmailRecommend);

                if(isEmailOk && isPasswordOk && isConfirmPasswordOk && isNameOk && isPhoneOk && isAddressOk && isEmailRecOk){

                    User user = new User(etEmail.getText().toString(), etPhone.getText().toString(),
                            etName.getText().toString(), etPassword.getText().toString());

                    UserRepository userRepository = new UserRepository(Register.this.getApplicationContext());
                    userRepository.insertUser(user);

                    if(recommendationEmailEntered) {
                        //TODO: register the promotion for both users
                    }

                    //Login
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Register.this);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("email", etEmail.toString());
                    editor.commit();
                    Intent i = new Intent(Register.this, Home.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }
            }
        });


    }
}
