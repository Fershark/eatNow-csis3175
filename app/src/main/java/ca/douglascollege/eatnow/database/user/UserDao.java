package ca.douglascollege.eatnow.database.user;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertUser(User user);

    @Insert
    void insertAll(List<User> users);

    @Query("SELECT * FROM User")
    LiveData<List<User>> getUsers();

    @Query("SELECT * FROM User WHERE email LIKE :email")
    User getUserByEmail(String email);

    @Query("SELECT COUNT(*) FROM User")
    int getCount();
}
