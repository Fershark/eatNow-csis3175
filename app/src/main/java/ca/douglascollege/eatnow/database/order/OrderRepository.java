package ca.douglascollege.eatnow.database.order;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import ca.douglascollege.eatnow.database.AppDatabase;

public class OrderRepository {
    private AppDatabase appDatabase;
    private String TAG = "ORDER_REPOSITORY";

    public OrderRepository(Context context) {
        appDatabase = AppDatabase.getInstance(context);
    }

    public void insertOrder(final Order order) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.orderDao().insertOrder(order);
                return null;
            }
        }.execute();
    }

    LiveData<List<Order>> findOrderByUser(int id) {
        return appDatabase.orderDao().findOrderByUser(id);
    }
}
