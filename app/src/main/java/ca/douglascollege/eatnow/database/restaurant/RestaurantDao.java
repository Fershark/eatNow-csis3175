package ca.douglascollege.eatnow.database.restaurant;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface RestaurantDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertRestaurant(Restaurant restaurant);

    @Insert
    void insertAll(List<Restaurant> restaurants);

    @Query("SELECT * FROM Restaurant")
    LiveData<List<Restaurant>> getRestaurants();

    @Query("SELECT * FROM Restaurant WHERE id LIKE :id")
    Restaurant getRestaurant(int id);

    @Query("SELECT COUNT(*) FROM Restaurant")
    int getCount();

}

