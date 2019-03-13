package ca.douglascollege.eatnow;

import android.graphics.drawable.Drawable;
import android.service.autofill.RegexValidator;
import android.util.Patterns;
import android.widget.EditText;

import java.util.regex.Pattern;

public final class Validation {
    /* isValidEmail
     * Verify the email inside the EditText and set the error if the email is not ok, or blank
     * return: boolean (true) if there is no problem with the email
     */
    public static boolean isValidEmail(EditText etEmail){
        Pattern emailPattern = Patterns.EMAIL_ADDRESS;
        String email = etEmail.getText().toString().trim();
        boolean result = emailPattern.matcher(email).matches();
        if(email.length()==0){
            etEmail.setError("This field is required!");
        }else if(!result){
            etEmail.setError("Invalid email!");
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
     * Verify name MIN 2 and MAX 30 char
     * return: boolean (true) if there is no problem with the Name
     */
    public static boolean isValidName(EditText etName){
        final int MIN_SIZE = 2;
        final int MAX_SIZE = 30;
        String name = etName.getText().toString().trim();
        boolean result = false;
        if(name.length()==0){
            etName.setError("This field is required!");
        }else if(name.length()<MIN_SIZE){
            etName.setError("Name must be at least " + MIN_SIZE + " chars");
        } else if(name.length()>MAX_SIZE){
            etName.setError("Name must be under " + MAX_SIZE + " chars");
            //} else if(!name.matches("^[A-Za-z]+$")) {
        } else if(!name.matches("[A-Z][a-zA-Z]*")) {

            etName.setError("Please enter a valid Name");
        } else{
            result=true;
        }
        return result;
    }
    /* isValidAddress
     * Verify name MIN 2 and MAX 30 char
     * return: boolean (true) if there is no problem with the Name
     */
    public static boolean isValidAddress(EditText etAddress){
        final int MIN_SIZE = 5;
        final int MAX_SIZE = 100;
        String address = etAddress.getText().toString().trim();
        //String regex = "\\d+\\s+([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)";
        String regex ="[a-zA-Z][0-9][a-zA-Z] ?[0-9][a-zA-Z][0-9]";
        boolean result = false;
        if(address.length()==0){
            etAddress.setError("This field is required!");
        }else if(address.length()<MIN_SIZE){
            etAddress.setError("Name must be at least " + MIN_SIZE + " chars");
        } else if(address.length()>MAX_SIZE){
            etAddress.setError("Name must be under " + MAX_SIZE + " chars");
            //} else if(!name.matches("^[A-Za-z]+$")) {
        /*} else if(!address.matches(regex)) {
            etAddress.setError("Please enter a valid Address");*/
        } else{
            result=true;
        }
        return result;
    }

}
