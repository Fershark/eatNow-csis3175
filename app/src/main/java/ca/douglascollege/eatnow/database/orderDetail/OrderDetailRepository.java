package ca.douglascollege.eatnow.database.orderDetail;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;
import java.util.List;

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

    LiveData<List<OrderDetail>> findOrderDetailsByOrder(int id) {
        return appDatabase.orderDetailDao().findOrderDetailsByOrder(id);
    }
}
