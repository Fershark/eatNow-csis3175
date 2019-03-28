package ca.douglascollege.eatnow.database.orderDetail;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface OrderDetailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertOrderDetail(OrderDetail orderDetail);

    @Query("SELECT * FROM OrderDetail WHERE order_id = :id")
    LiveData<List<OrderDetail>> findOrderDetailsByOrder(int id);

    @Query("SELECT SUM(quantity) FROM OrderDetail WHERE order_id = :id")
    int countByOrder(int id);
}
