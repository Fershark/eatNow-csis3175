package ca.douglascollege.eatnow;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import ca.douglascollege.eatnow.database.restaurant.Restaurant;
import ca.douglascollege.eatnow.database.user.User;
import ca.douglascollege.eatnow.database.user.UserRepository;
import ca.douglascollege.eatnow.utilities.Helper;
import ca.douglascollege.eatnow.utilities.Validation;

public class Register extends AppCompatActivity {
    TextInputLayout tiEmail, tiPassword, tiConfirmPassword, tiName, tiPhone, tiAddress, tiEmailRecommend;
    EditText etEmail, etPassword, etConfirmPassword, etName, etPhone, etAddress, etEmailRecommend;
    Button btnSubmit;
    Validation validation;
    Boolean isEdit;
    User loggedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent i = getIntent();
        if (i != null)
            isEdit = i.getBooleanExtra("isEdit", false);
        //If is edit change the theme before the view is created
        if (isEdit)
            setTheme(R.style.AppTheme);
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

        //Configurations if is edit profile
        if (isEdit) {
            // Set the action Bar title
            setTitle(getString(R.string.editProfile));
            // Set the image in the toolbar
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setLogo(R.mipmap.ic_launcher);
            getSupportActionBar().setDisplayUseLogoEnabled(true);

            //Initialize the variable to disappear margins in the layout
            ConstraintLayout.LayoutParams params;

            //Hide unused components and make their margin 0
            //Title
            TextView txtRegister = findViewById(R.id.txtRegister);
            txtRegister.setVisibility(View.INVISIBLE);
            txtRegister.setHeight(0);
            params = (ConstraintLayout.LayoutParams) txtRegister.getLayoutParams();
            params.topMargin = 0;
            txtRegister.setLayoutParams(params);

            //Promotion Hide
            ConstraintLayout clRecommend = findViewById(R.id.clRecommend);
            clRecommend.setVisibility(View.INVISIBLE);
            clRecommend.setMaxHeight(0);
            params = (ConstraintLayout.LayoutParams) clRecommend.getLayoutParams();
            params.topMargin = 0;
            clRecommend.setLayoutParams(params);

            //Change btn title
            btnSubmit.setText(getString(R.string.edit));

            //Get logged user and set the edit texts
            Helper helper = new Helper(this);
            loggedUser = helper.getLoggedUser();

            etEmail.setText(loggedUser.getEmail());
            etPassword.setText(loggedUser.getPassword());
            etConfirmPassword.setText(loggedUser.getPassword());
            etName.setText(loggedUser.getName());
            etPhone.setText(loggedUser.getPhone());
            etAddress.setText(loggedUser.getAddress());
        }

        validation = new Validation(this);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isEmailOk;
                if (isEdit) {
                    if (etEmail.getText().toString().equals(loggedUser.getEmail()))
                        isEmailOk = true;
                    else
                        isEmailOk = validation.isValidNewEmail(tiEmail, etEmail);
                } else
                    isEmailOk = validation.isValidNewEmail(tiEmail, etEmail);
                boolean isPasswordOk = validation.isValidPassword(tiPassword, etPassword);
                boolean isConfirmPasswordOk = validation.isValidConfirmPassword(tiConfirmPassword, etConfirmPassword, etPassword);
                boolean isNameOk = validation.isValidName(tiName, etName);
                boolean isPhoneOk = validation.isValidPhone(tiPhone, etPhone);
                boolean isAddressOk = validation.isValidAddress(tiAddress, etAddress);
                boolean recommendationEmailEntered = (etEmailRecommend.getText().toString().length() > 0);
                boolean isEmailRecOk = !recommendationEmailEntered || validation.isValidExistingEmail(tiEmailRecommend, etEmailRecommend);

                if (isEmailOk && isPasswordOk && isConfirmPasswordOk && isNameOk && isPhoneOk && isAddressOk && isEmailRecOk) {
                    UserRepository userRepository = new UserRepository(Register.this.getApplicationContext());
                    if (isEdit) {
                        loggedUser.setEmail(etEmail.getText().toString());
                        loggedUser.setPhone(etPhone.getText().toString());
                        loggedUser.setName(etName.getText().toString());
                        loggedUser.setPassword(etPassword.getText().toString());
                        loggedUser.setAddress(etAddress.getText().toString());
                        userRepository.updateUser(loggedUser);
                        Toast.makeText(Register.this, getString(R.string.userUpdated), Toast.LENGTH_SHORT).show();
                    } else {
                        User user = new User(etEmail.getText().toString(), etPhone.getText().toString(),
                                etName.getText().toString(), etPassword.getText().toString(), etAddress.getText().toString(), new Date());
                        userRepository.insertUser(user);

                        if (recommendationEmailEntered) {
                            //TODO: register the promotion for both users
                        }

                        //Login
                        Helper helper = new Helper(Register.this);
                        helper.setEmailLoggedUser(user.getEmail());
                        Intent i = new Intent(Register.this, Home.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                    }
                } else {
                    Toast.makeText(Register.this, getString(R.string.someErrors), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
