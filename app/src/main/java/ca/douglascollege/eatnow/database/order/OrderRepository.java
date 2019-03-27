package ca.douglascollege.eatnow.database.order;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import ca.douglascollege.eatnow.database.AppDatabase;

public class OrderRepository {
    private AppDatabase appDatabase;
    private String TAG = "ORDER_REPOSITORY";

    public OrderRepository(Context context) {
        appDatabase = AppDatabase.getInstance(context);
    }

    public int insertOrder(final Order order) {
        int id = -1;

        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return appDatabase.orderDao().insertOrder(order).intValue();
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

    LiveData<List<Order>> findOrderByUser(int id) {
        return appDatabase.orderDao().findOrderByUser(id);
    }
}
