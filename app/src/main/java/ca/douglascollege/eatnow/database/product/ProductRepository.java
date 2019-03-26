package ca.douglascollege.eatnow.database.product;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.util.Log;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import ca.douglascollege.eatnow.database.AppDatabase;

public class ProductRepository {
    private AppDatabase appDatabase;
    private String TAG = "PRODUCT_REPOSITORY";

    public ProductRepository(Context context) {
        appDatabase = AppDatabase.getInstance(context);
    }

    public LiveData<List<Product>> findProductsForRestaurant(int restaurantId) {
        return appDatabase.productDao().findProductsByRestaurant(restaurantId);
    }

    public Product getProduct(final int id) {
        Product product = null;

        Callable<Product> callable = new Callable<Product>() {
            @Override
            public Product call() throws Exception {
                return appDatabase.productDao().getProduct(id);
            }
        };

        Future<Product> future = Executors.newSingleThreadExecutor().submit(callable);
        try {
            product = future.get();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

        return product;
    }
}
