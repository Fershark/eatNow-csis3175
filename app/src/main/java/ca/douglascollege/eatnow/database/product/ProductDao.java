package ca.douglascollege.eatnow.database.product;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;


@Dao
public interface ProductDao {
    @Insert
    void insertAll(List<Product> products);

    @Query("SELECT * FROM Product WHERE id LIKE :id")
    Product getProduct(int id);

    @Query("SELECT * FROM Product WHERE restaurant_id = :restaurantId")
    LiveData<List<Product>> findProductsByRestaurant(int restaurantId);

    @Query("SELECT COUNT(*) FROM Product")
    int getCount();
}
