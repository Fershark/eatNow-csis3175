package ca.douglascollege.eatnow.database.recommendation;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

import ca.douglascollege.eatnow.database.user.User;
import ca.douglascollege.eatnow.utilities.Helper;

@Entity(foreignKeys = {
        @ForeignKey(entity = User.class,
                parentColumns = "id",
                childColumns = "user_id"
        ),
        @ForeignKey(entity = User.class,
                parentColumns = "id",
                childColumns = "recommended_by_user"
        ),
})
public class Recommendation implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private float discount;
    @ColumnInfo(name = "date_used")
    private Date dateUsed;
    @ColumnInfo(name = "user_id", index = true)
    private int userId;
    @ColumnInfo(name = "recommended_by_user", index = true)
    private int recommendedByUser;

    public Recommendation(float discount, int userId, int recommendedByUser) {
        this.discount = discount;
        this.userId = userId;
        this.recommendedByUser = recommendedByUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getDiscount() {
        float discount = Helper.roundToDigits(this.discount, 2);
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public Date getDateUsed() {
        return dateUsed;
    }

    public void setDateUsed(Date dateUsed) {
        this.dateUsed = dateUsed;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRecommendedByUser() {
        return recommendedByUser;
    }

    public void setRecommendedByUser(int recommendedByUser) {
        this.recommendedByUser = recommendedByUser;
    }
}
