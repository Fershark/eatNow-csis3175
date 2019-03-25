package ca.douglascollege.eatnow.database.recommendation;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface RecommendationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertRecommendation(Recommendation recommendation);

    @Update
    int updateRecommendation(Recommendation recommendation);

    @Query("SELECT * FROM Recommendation WHERE date_used IS NULL AND user_id = :userId LIMIT 1")
    LiveData<List<Recommendation>> findUnsedRecommendationForUser(int userId);
}
