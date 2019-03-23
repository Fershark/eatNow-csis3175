//public class RestaurantRepository {
package ca.douglascollege.eatnow.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class RestaurantRepository {
    private AppDatabase appDatabase;
    private String TAG = "RESTAURANT_REPOSITORY";

    public RestaurantRepository(Application application) {
        appDatabase = AppDatabase.getInstance(application);
    }

    public void insertRestaurant(final Restaurant restaurant) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.restaurantDao().insertRestaurant(restaurant);
                return null;
            }
        }.execute();
    }

    public LiveData<List<Restaurant>> getRestaurants() {
        return appDatabase.restaurantDao().getRestaurants();
    }

    public Restaurant getRestaurant(final int id) {
        Restaurant restaurant = null;

        Callable<Restaurant> callable = new Callable<Restaurant>() {
            @Override
            public Restaurant call() throws Exception {
                return appDatabase.restaurantDao().getRestaurant(id);
            }
        };

        Future<Restaurant> future = Executors.newSingleThreadExecutor().submit(callable);
        try {
            restaurant = future.get();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

        return restaurant;
    }
}

