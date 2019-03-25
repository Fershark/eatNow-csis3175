package ca.douglascollege.eatnow.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Date;

import ca.douglascollege.eatnow.database.product.Product;
import ca.douglascollege.eatnow.database.product.ProductDao;
import ca.douglascollege.eatnow.database.recommendation.Recommendation;
import ca.douglascollege.eatnow.database.recommendation.RecommendationDao;
import ca.douglascollege.eatnow.database.restaurant.Restaurant;
import ca.douglascollege.eatnow.database.restaurant.RestaurantDao;
import ca.douglascollege.eatnow.database.user.User;
import ca.douglascollege.eatnow.database.user.UserDao;

@Database(entities = {User.class, Restaurant.class, Product.class, Recommendation.class}, version = 9, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "EatNow.db";
    private static volatile AppDatabase INSTANCE;
    private static String FIRST_EMAIL = "client@gmail.com";

    public abstract UserDao userDao();
    public abstract RestaurantDao restaurantDao();
    public abstract ProductDao productDao();
    public abstract RecommendationDao recommendationDao();

    public static AppDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DATABASE_NAME)
                            .addCallback(new Callback() {
                                @Override
                                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                                    super.onOpen(db);
                                    new PopulateDBAsync().execute(context);
                                }
                            })
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }

        return INSTANCE;
    }

    /**
     * Private class for populating the database
     */
    private static class PopulateDBAsync extends AsyncTask<Context, Void, Void> {
        @Override
        protected Void doInBackground(Context... contexts) {
            Context c = contexts[0];
            UserDao userDao = getInstance(c).userDao();
            if (userDao.getUserByEmail(FIRST_EMAIL) == null) {
                getInstance(c).userDao().insertAll(defaultUsers());
                getInstance(c).restaurantDao().insertAll(getRestaurants());
                getInstance(c).productDao().insertAll(getProducts());
            }
            return null;
        }
    }

    /**
     * Initialize the default users in the app
     *
     * @return List of default users
     */
    private static ArrayList<User> defaultUsers() {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User(FIRST_EMAIL, "7787777777", "Client", "eatnow", "700 Royal Ave", new Date()));
        return users;
    }

    /**
     * Initialize the restaurants in the app
     *
     * @return List of 10 restaurants to insert into the Table
     */
    private static ArrayList<Restaurant> getRestaurants() {
        ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
        restaurants.add(new Restaurant("restaurant_triple_os", "1541239871", "Triple O's", "Fast Food", 49.203512, -122.912552));
        restaurants.add(new Restaurant("restaurant_hyack_sushi", "2541239871", "Hyack Sushi", "Japanese Food", 49.202403, -122.912562));
        restaurants.add(new Restaurant("restaurant_v_cafe", "3541239871", "V Cafe", "Breakfast", 49.202315, -122.912503));
        restaurants.add(new Restaurant("restaurant_spaghetti_factory", "4541239871", "The Old Spaghetti Factory", "Italian Food", 49.201946, -122.912596));
        restaurants.add(new Restaurant("restaurant_ki_sushi", "5541239871", "Ki Sushi Restaurant", "Japanese Food", 49.202023, -122.911947));
        restaurants.add(new Restaurant("restaurant_piva_modern_italian", "6541239871", "Piva Modern Italian", "Pizzeria", 49.201540, -122.911385));
        restaurants.add(new Restaurant("restaurant_pizzeria_ludica", "7541239871", "Pizzeria Ludica", "Pizzeria", 49.204141, -122.909211));
        restaurants.add(new Restaurant("restaurant_patsara_thai", "8541239871", "Patsara Thai", "Thai Food", 49.204325, -122.908047));
        restaurants.add(new Restaurant("restaurant_sushi_yen", "9541239871", "Sushi Yen", "Japanese Food", 49.203921, -122.908415));
        restaurants.add(new Restaurant("restaurant_bavaria_haus", "1111111111", "The Old Bavaria Haus Restaurant", "Steakhouse", 49.208526, -122.914147));

        return restaurants;
    }

    /**
     * Initialize the products in the app
     *
     * @return the list of Products
     */
    private static ArrayList<Product> getProducts() {
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product("Test product", "restaurant_image", "test description", (float) 11.1, 1));
        products.add(new Product("Test product 2", "restaurant_image", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.", (float) 11.1, 10));
        products.add(new Product("Test product 3", null, "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.", (float) 18.1, 10));
        return products;
    }
}
