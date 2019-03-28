package ca.douglascollege.eatnow.database.orderDetail;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import ca.douglascollege.eatnow.database.AppDatabase;

public class OrderDetailRepository {
    private AppDatabase appDatabase;
    private String TAG = "ORDER_DETAIL_REPOSITORY";

    public OrderDetailRepository(Context context) {
        appDatabase = AppDatabase.getInstance(context);
    }

    public void insertOrderDetail(final OrderDetail orderDetail) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.orderDetailDao().insertOrderDetail(orderDetail);
                return null;
            }
        }.execute();
    }

    public LiveData<List<OrderDetail>> findOrderDetailsByOrder(int id) {
        return appDatabase.orderDetailDao().findOrderDetailsByOrder(id);
    }

    public int countByOrder(final int id) {
        int count = 0;

        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return appDatabase.orderDetailDao().countByOrder(id);
            }
        };

        Future<Integer> future = Executors.newSingleThreadExecutor().submit(callable);
        try {
            count = future.get();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

        return count;
    }
}
