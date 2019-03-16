package ca.douglascollege.eatnow;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.service.autofill.RegexValidator;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import ca.douglascollege.eatnow.database.User;
import ca.douglascollege.eatnow.database.UserDao;
import ca.douglascollege.eatnow.database.UserRepository;

public final class Validation {
    /* isValidEmail
     * Verify the email inside the EditText and set the error if the email is not ok, or blank
     * return: boolean (true) if there is no problem with the email
     */
    public static boolean isValidEmail(EditText etEmail, boolean allowEmpty){
        Pattern emailPattern = Patterns.EMAIL_ADDRESS;
        String email = etEmail.getText().toString().trim();
        boolean result = emailPattern.matcher(email).matches();

        if(!result) {
            if ((allowEmpty)&&(email.length() == 0)) {
                result = true;
            }
            else if(email.length() == 0) {
                etEmail.setError("This field is required!");
            } else {
                etEmail.setError("Invalid email!");
            }
        }
        return result;
    }
    public static boolean isValidEmail(EditText etEmail, Context context){
        boolean result = isValidEmail( etEmail, false);
        if(result){
            UserRepository userRepository = new UserRepository(context);
            try {
                User user = userRepository.getUserByEmail(etEmail.getText().toString().trim());
                if (user != null) {
                    etEmail.setError("This email is already registered!");
                    result = false;
                }
            }
            catch (Exception ex){
                etEmail.setError("Exception occurred validating email");
                result=false;
            }
        }
        return result;
    }

    /* isValidRecommendEmail
     * Verify if its a valid email and check if the email exist in the database
     * return: true if everything is ok
     */
    public static boolean isValidRecommendEmail(EditText etEmail, Context context){
        boolean result = isValidEmail(etEmail, true);
        if(result){
            UserRepository userRepository = new UserRepository(context);
            try {
                //Check empty email
                if(etEmail.getText().toString().length()>0) {
                    User user = userRepository.getUserByEmail(etEmail.getText().toString());
                    if (user == null) {
                        etEmail.setError("This email is not registered, please verify spelling!");
                        result = false;
                    }
                }
            }
            catch (Exception ex){
                etEmail.setError("Exception occurred validating email recommended");
                result=false;
            }
        }
        return result;
    }

    /* isValidPassword
     * Verify password MIN 5 and MAX 20 char
     * return: boolean (true) if there is no problem with the Password
     */
    public static boolean isValidPassword(EditText etPswd){
        final int MIN_SIZE = 5;
        final int MAX_SIZE = 20;
        String pswd = etPswd.getText().toString().trim();
        boolean result = false;
        if(pswd.length()==0){
            etPswd.setError("This field is required!");
        }else if(pswd.length()<MIN_SIZE){
            etPswd.setError("Password must be at least " + MIN_SIZE + " chars");
        } else if(pswd.length()>MAX_SIZE){
            etPswd.setError("Password must be under " + MAX_SIZE + " chars");
        }
        else{
            result=true;
        }
        return result;
    }

    /* isValidConfirmPassword
     * Verify password MIN 5 and MAX 20 char
     * return: boolean (true) if there is no problem with the Password
     */
    public static boolean isValidConfirmPassword(EditText etPswd, EditText etConfirmPswd){
        final int MIN_SIZE = 5;
        final int MAX_SIZE = 20;
        String pswd = etPswd.getText().toString().trim();
        String confirmPswd = etConfirmPswd.getText().toString().trim();
        boolean result = (pswd.compareTo(confirmPswd)==0);
        if(!result){
            etConfirmPswd.setError("Password is different from expected");
        }
        return result;
    }

    /* isValidName
     * Verify full name MIN 2 and MAX 100 char
     * return: boolean (true) if there is no problem with the Name
     */
    public static boolean isValidName(EditText etName){
        final int MIN_SIZE = 2;
        final int MAX_SIZE = 100;
        String name = etName.getText().toString().trim();
        boolean result = false;
        if(name.length()==0){
            etName.setError("This field is required!");
        }else if(name.length()<MIN_SIZE){
            etName.setError("Name must be at least " + MIN_SIZE + " chars");
        } else if(name.length()>MAX_SIZE){
            etName.setError("Name must be under " + MAX_SIZE + " chars");
            //} else if(!name.matches("^[A-Za-z]+$")) {
        } else if(!name.matches("[A-Z][a-zA-Z\\s]*")) {
            etName.setError("Please enter a valid Name");
        } else{
            result=true;
        }
        return result;
    }

    /* isValidPhone
     * Verify the length of the phone to canadian phone
     */
    public static boolean isValidPhone(EditText etPhone) {
        final int SIZE = 10;
        String phone = etPhone.getText().toString().trim();
        boolean result = false;
        if(phone.length()==0){
            etPhone.setError("This field is required!");
        }else if(phone.length()!=SIZE){
            etPhone.setError("Phone number must have " + SIZE + " chars");
        } else if(!phone.matches("\\d{10}")) {
            etPhone.setError("Please enter only Numbers");
        } else{
            result=true;
        }
        return result;
    }

    /* isValidAddress
     * Verify name MIN 2 and MAX 30 char
     * return: boolean (true) if there is no problem with the Name
     */
    public static boolean isValidAddress(EditText etAddress, Context context){
        final int MIN_SIZE = 5;
        final int MAX_SIZE = 100;
        String strAddress = etAddress.getText().toString().trim();
        //String regex = "\\d+\\s+([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)";
        String regex ="[a-zA-Z][0-9][a-zA-Z] ?[0-9][a-zA-Z][0-9]";
        boolean result = false;
        if(strAddress.length()==0){
            etAddress.setError("This field is required!");
        }else if(strAddress.length()<MIN_SIZE){
            etAddress.setError("Name must be at least " + MIN_SIZE + " chars");
        } else if(strAddress.length()>MAX_SIZE){
            etAddress.setError("Name must be under " + MAX_SIZE + " chars");
        }
        else {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            try{
                strAddress += ", BC";
                List<Address> addressList = geocoder.getFromLocationName(strAddress, 1);
                if (addressList == null || addressList.size() == 0) {
                    etAddress.setError("Enter a valid Address");
                }
                else{
                    result = true;
                }
            } catch (Exception ex) {
                etAddress.setError("Error verifiying address");
            }
        }
        return result;
    }

}
