package ca.douglascollege.eatnow.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import ca.douglascollege.eatnow.database.user.User;
import ca.douglascollege.eatnow.database.user.UserRepository;

public class Helper {
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
}
