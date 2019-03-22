//public class RestaurantRepository {
package ca.douglascollege.eatnow.database;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class RestaurantRepository {
    private AppDatabase appDatabase;

    public RestaurantRepository(Context context){
        appDatabase = AppDatabase.getInstance(context);
    }

    public void insertRestaurant(final Restaurant restaurant){
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

    public Restaurant getRestaurant(final int id) throws ExecutionException, InterruptedException {
        Callable<Restaurant> callable = new Callable<Restaurant>() {
            @Override
            public Restaurant call() throws Exception {
                return appDatabase.restaurantDao().getRestaurant(id);
            }
        };

        Future<Restaurant> future = Executors.newSingleThreadExecutor().submit(callable);

        return future.get();
    }
}

