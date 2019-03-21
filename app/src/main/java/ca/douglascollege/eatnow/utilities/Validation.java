package ca.douglascollege.eatnow.utilities;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.util.Patterns;
import android.widget.EditText;

import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import ca.douglascollege.eatnow.R;
import ca.douglascollege.eatnow.database.User;
import ca.douglascollege.eatnow.database.UserRepository;

public class Validation {
    private Context c;
    public final int PASSWORD_MIN_SIZE = 5;
    public final int PASSWORD_MAX_SIZE = 20;
    public final int NAME_MIN_SIZE = 2;
    public final int NAME_MAX_SIZE = 30;
    public final int PHONE_SIZE = 10;
    public final int ADDRESS_MIN_SIZE = 5;
    public final int ADDRESS_MAX_SIZE = 100;
    public final String LOG_STRING = "VALIDATION";

    public Validation(Context c) {
        this.c = c;
    }

    /**
     * Verify the email inside the EditText and set the error if the email is not ok, or blank
     *
     * @param tiEmail The text input layout surronding the edit text.
     * @param etEmail The edit text to validate.
     * @return true if there is no problem with the email
     */
    private boolean isValidEmail(TextInputLayout tiEmail, EditText etEmail) {
        String email = etEmail.getText().toString().trim();
        boolean result = false;

        if (email.length() == 0)
            tiEmail.setError(c.getString(R.string.required));
        else {
            Pattern emailPattern = Patterns.EMAIL_ADDRESS;
            if (!emailPattern.matcher(email).matches())
                tiEmail.setError(c.getString(R.string.invalid));
            else {
                tiEmail.setError(null);
                result = true;
            }
        }

        return result;
    }

    /**
     * Verify the email inside the EditText and set the error if the email is not ok, or blank and validate
     * the mail doesn't exist in the database
     *
     * @param tiEmail The text input layout surronding the edit text.
     * @param etEmail The edit text to validate.
     * @return true if there is no problem with the email
     */
    public boolean isValidNewEmail(TextInputLayout tiEmail, EditText etEmail) {
        boolean result = isValidEmail(tiEmail, etEmail);

        if (result) {
            String email = etEmail.getText().toString().trim();
            UserRepository userRepository = new UserRepository(c);
            try {
                if (userRepository.getUserByEmail(email) != null) {
                    tiEmail.setError(c.getString(R.string.emailExist));
                    result = false;
                } else {
                    tiEmail.setError(null);
                    result = true;
                }
            } catch (Exception e) {
                tiEmail.setError("Exception");
                result = false;
                Log.d(LOG_STRING, e.getMessage());
            }
        }

        return result;
    }

    /**
     * Verify if its a valid email and check if the email exist in the database
     *
     * @param tiEmail The text input layout surronding the edit text.
     * @param etEmail The edit text to validate.
     * @return true if there is no problem with the email
     */
    public boolean isValidExistingEmail(TextInputLayout tiEmail, EditText etEmail) {
        return (validExistingEmail(tiEmail, etEmail) != null);
    }

    /**
     * Verify if its a valid email and check if the email exist in the database
     *
     * @param tiEmail The text input layout surronding the edit text.
     * @param etEmail The edit text to validate.
     * @return The user if the mail exists or null if it does not exist
     */
    public User validExistingEmail(TextInputLayout tiEmail, EditText etEmail) {
        boolean result = isValidEmail(tiEmail, etEmail);
        User user = null;

        if (result) {
            String email = etEmail.getText().toString().trim();
            UserRepository userRepository = new UserRepository(c);
            try {
                user = userRepository.getUserByEmail(email);
                if (user == null)
                    tiEmail.setError(c.getString(R.string.emailNotExist));
                else
                    tiEmail.setError(null);
            } catch (Exception e) {
                tiEmail.setError("Exception");
                Log.d(LOG_STRING, e.getMessage());
            }
        }

        return user;
    }

    /**
     * Verify password MIN 5 and MAX 20 char
     *
     * @param tiPassword The text input layout surronding the edit text.
     * @param etPassword The edit text of password.
     * @return True if the field is valid
     */
    public boolean isValidPassword(TextInputLayout tiPassword, EditText etPassword) {
        String password = etPassword.getText().toString().trim();
        boolean result = false;

        if (password.length() == 0)
            tiPassword.setError(c.getString(R.string.required));
        else if (password.length() < PASSWORD_MIN_SIZE)
            tiPassword.setError(c.getString(R.string.minLength, Integer.toString(PASSWORD_MIN_SIZE)));
        else if (password.length() > PASSWORD_MAX_SIZE)
            tiPassword.setError(c.getString(R.string.maxLength, Integer.toString(PASSWORD_MAX_SIZE)));
        else {
            tiPassword.setError(null);
            result = true;
        }

        return result;
    }

    /**
     * Compare the password and the confirm password fields
     *
     * @param tiConfirmPassword The text input layout surronding the edit text
     * @param etConfirmPassword The edit text of confirm password
     * @param etPassword        The edit text of password for comparing the fields
     * @return True if the edit text is valid
     */
    public boolean isValidConfirmPassword(TextInputLayout tiConfirmPassword, EditText etConfirmPassword, EditText etPassword) {
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();
        boolean result = false;

        if (confirmPassword.length() == 0)
            tiConfirmPassword.setError(c.getString(R.string.required));
        else if (password.compareTo(confirmPassword) != 0)
            tiConfirmPassword.setError(c.getString(R.string.confirmPasswordNotMatch));
        else {
            result = true;
            tiConfirmPassword.setError(null);
        }

        return result;
    }

    /**
     * Verify full name MIN 2 and MAX 100 char
     *
     * @param tiName The text input layout surronding the edit text
     * @param etName The edit text of the name
     * @return true if there is no problem with the name edit text
     */
    public boolean isValidName(TextInputLayout tiName, EditText etName) {
        String name = etName.getText().toString().trim();
        boolean result = false;

        if (name.length() == 0)
            tiName.setError(c.getString(R.string.required));
        else if (name.length() < NAME_MIN_SIZE)
            tiName.setError(c.getString(R.string.minLength, Integer.toString(NAME_MIN_SIZE)));
        else if (name.length() > NAME_MAX_SIZE)
            tiName.setError(c.getString(R.string.maxLength, Integer.toString(NAME_MAX_SIZE)));
        else {
            result = true;
            tiName.setError(null);
        }
        return result;
    }

    /**
     * Verify the length of the phone to canadian phone
     * @param tiPhone The text input layour surronding the edit text
     * @param etPhone The edit text of the phone
     * @return true if there is no problem with the phone edit text
     */
    public boolean isValidPhone(TextInputLayout tiPhone, EditText etPhone) {
        String phone = etPhone.getText().toString().trim();
        boolean result = false;

        if (phone.length() == 0)
            tiPhone.setError(c.getString(R.string.required));
        else if (phone.length() != PHONE_SIZE)
            tiPhone.setError(c.getString(R.string.specificLength, Integer.toString(PHONE_SIZE)));
        else if (!phone.matches("\\d{10}"))
            tiPhone.setError(c.getString(R.string.onlyNumbers));
        else {
            result = true;
            tiPhone.setError(null);
        }
        return result;
    }

    /**
     * Verify the address is valid address with google geocoder
     * @param tiAddress The text input layout surronding the text edit
     * @param etAddress The text edit of the address
     * @return True if there is no error in the address field
     */
    public boolean isValidAddress(TextInputLayout tiAddress, EditText etAddress) {
        String address = etAddress.getText().toString().trim();
        boolean result = false;

        if (address.length() == 0)
            tiAddress.setError(c.getString(R.string.required));
        else if (address.length() < ADDRESS_MIN_SIZE)
            tiAddress.setError(c.getString(R.string.minLength, Integer.toString(ADDRESS_MIN_SIZE)));
        else if (address.length() > ADDRESS_MAX_SIZE)
            tiAddress.setError(c.getString(R.string.maxLength, Integer.toString(ADDRESS_MAX_SIZE)));
        else {
            Geocoder geocoder = new Geocoder(c, Locale.getDefault());
            address += ", BC";
            try {
                List<Address> addressList = geocoder.getFromLocationName(address, 1);
                if (addressList == null || addressList.size() == 0)
                    tiAddress.setError(c.getString(R.string.invalid));
                else {
                    result = true;
                    tiAddress.setError(null);
                }
            } catch (Exception ex) {
                Log.d(LOG_STRING, ex.getMessage());
                tiAddress.setError(c.getString(R.string.checkInternet));
            }
        }

        return result;
    }

}
