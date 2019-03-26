package ca.douglascollege.eatnow.database.order;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface OrderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertOrder(Order order);

    @Query("SELECT * FROM `Order` WHERE user_id = :id")
    LiveData<List<Order>> findOrderByUser(int id);
}
