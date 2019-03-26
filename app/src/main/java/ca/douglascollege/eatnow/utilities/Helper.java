package ca.douglascollege.eatnow.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;

import ca.douglascollege.eatnow.database.user.User;
import ca.douglascollege.eatnow.database.user.UserRepository;

public class Helper {
    private static final DecimalFormat CURRENCY_FORMATTER = new DecimalFormat("$ #,###.##");
    private Context context;

    public Helper(Context context) {
        this.context = context;
    }

    public String getEmailLoggedUser() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("email", null);
    }

    public void setEmailLoggedUser(String email) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email);
        editor.commit();
    }

    public void removeLoggedUser() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().clear().commit();
    }

    public User getLoggedUser() {
        User user = null;
        String email = getEmailLoggedUser();

        if (email != null) {
            UserRepository userRepository = new UserRepository(context.getApplicationContext());
            user = userRepository.getUserByEmail(email);
        }

        return user;
    }

    /**
     * Round a float to a given number of digits
     *
     * @param numToRound The number to be rounded
     * @param digits     The desired digits
     * @return The float rounded
     */
    public static float roundToDigits(float numToRound, int digits) {
        int tenPow = (int) Math.pow(10, digits);
        return ((float) Math.round(numToRound * tenPow)) / tenPow;
    }

    /**
     * Hide a view which is inside a constraint layout
     *
     * @param view The view to be hidden
     */
    public static void hideComponentInConstraintLayout(View view) {
        view.setVisibility(View.INVISIBLE);
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) view.getLayoutParams();
        params.topMargin = 0;
        if (view instanceof TextView)
            ((TextView) view).setHeight(0);
        else if (view instanceof ConstraintLayout)
            ((ConstraintLayout) view).setMaxHeight(0);
        else if (view instanceof ImageView)
            params.height = 0;
        view.setLayoutParams(params);
    }

    /**
     * Show component in constraint layout by specifying the height in pixels
     * @param view The view to be shown
     * @param height The height in pixels of the view
     */
    public static void showComponentInConstraintLayout(View view, int height) {
        view.setVisibility(View.VISIBLE);
        if (view instanceof TextView)
            ((TextView) view).setHeight(height);
        else if (view instanceof ConstraintLayout)
            ((ConstraintLayout) view).setMaxHeight(height);
        else if (view instanceof ImageView) {
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) view.getLayoutParams();
            params.height = height;
            view.setLayoutParams(params);
        }
    }

    /**
     * Format a float with an specific currency format
     *
     * @param currency The float to be formatted
     * @return The currency formatted
     */
    public static String getCurrencyFormatted(float currency) {
        return CURRENCY_FORMATTER.format(currency);
    }
}
