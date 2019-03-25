package ca.douglascollege.eatnow.database.user;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import ca.douglascollege.eatnow.database.AppDatabase;

public class UserRepository {
    private AppDatabase appDatabase;
    private String TAG = "USER_REPOSITORY";

    public UserRepository(Context context) {
        appDatabase = AppDatabase.getInstance(context);
    }

    public int insertUser(final User user) {
        int id = -1;

        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return appDatabase.userDao().insertUser(user).intValue();
            }
        };

        Future<Integer> future = Executors.newSingleThreadExecutor().submit(callable);
        try {
            id = future.get();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

        return id;
    }

    public void updateUser(final User user) {
         new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.userDao().updateUser(user);
                return null;
            }
        }.execute();
    }

    public LiveData<List<User>> getUsers() {
        return appDatabase.userDao().getUsers();
    }

    public User getUserByEmail(final String email) {
        User user = null;

        Callable<User> callable = new Callable<User>() {
            @Override
            public User call() throws Exception {
                return appDatabase.userDao().getUserByEmail(email);
            }
        };

        Future<User> future = Executors.newSingleThreadExecutor().submit(callable);
        try {
            user = future.get();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

        return user;
    }
}
