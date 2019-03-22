package ca.douglascollege.eatnow.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executors;

import ca.douglascollege.eatnow.R;

@Database(entities = {User.class,Restaurant.class}, version = 3, exportSchema = false)

@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "EatNow.db";
    private static volatile AppDatabase instance;
    private static final Object LOCK = new Object();

    public abstract UserDao userDao();
    public abstract RestaurantDao restaurantDao();

    /**
     * getRestaurants
     *
     * @return List of 10 restaurants to insert into the Table
     */
    private static ArrayList<Restaurant> getRestaurants(){
        ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
        restaurants.add(new Restaurant(R.drawable.restaurant_image,"1541239871","Triple O's", "Fast Food", 49.203512, -122.912552));
        restaurants.add(new Restaurant(R.drawable.restaurant_image,"2541239871","Hyack Sushi", "Japanese Food", 49.202403, -122.912562));
        restaurants.add(new Restaurant(R.drawable.restaurant_image,"3541239871","V Cafe", "Breakfast", 49.202315, -122.912503));
        restaurants.add(new Restaurant(R.drawable.restaurant_image,"4541239871","The Old Spaghetti Factory", "Italian Food", 49.201946, -122.912596));
        restaurants.add(new Restaurant(R.drawable.restaurant_image,"5541239871","Ki Sushi Restaurant", "Japanese Food", 49.202023, -122.911947));
        restaurants.add(new Restaurant(R.drawable.restaurant_image,"6541239871","Piva Modern Italian", "Pizzeria", 49.201540, -122.911385));
        restaurants.add(new Restaurant(R.drawable.restaurant_image,"7541239871","Pizzeria Ludica", "Pizzeria", 49.204141, -122.909211));
        restaurants.add(new Restaurant(R.drawable.restaurant_image,"8541239871","Patsara Thai", "Thai Food", 49.204325, -122.908047));
        restaurants.add(new Restaurant(R.drawable.restaurant_image,"9541239871","Sushi Yen", "Japanese Food", 49.203921, -122.908415));
        restaurants.add(new Restaurant(R.drawable.restaurant_image,"1111111111"," The Old Bavaria Haus Restaurant", "Steakhouse", 49.208526, -122.914147));

        return restaurants;
    }
    public static AppDatabase getInstance(final Context context){
        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, DATABASE_NAME)
                        .addCallback(new Callback() {
                            @Override
                            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                super.onCreate(db);
                                Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        getInstance(context).userDao().insertUser(new User(1,"client@gmail.com", "", "Client", "eatnow", new Date()));
                                        getInstance(context).restaurantDao().insertAll(getRestaurants());
                                    }
                                });
                            }
                        })
                        .build();
                }
            }
        }

        return instance;
    }

}
