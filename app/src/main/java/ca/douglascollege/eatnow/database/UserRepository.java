package ca.douglascollege.eatnow.database;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class UserRepository {
    private AppDatabase appDatabase;
    private String TAG = "USER_REPOSITORY";

    public UserRepository(Context context) {
        appDatabase = AppDatabase.getInstance(context);
    }

    public void insertUser(final User user) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.userDao().insertUser(user);
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
