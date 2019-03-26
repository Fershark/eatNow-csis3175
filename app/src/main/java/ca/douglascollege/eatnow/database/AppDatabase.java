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

import ca.douglascollege.eatnow.database.order.Order;
import ca.douglascollege.eatnow.database.order.OrderDao;
import ca.douglascollege.eatnow.database.orderDetail.OrderDetail;
import ca.douglascollege.eatnow.database.orderDetail.OrderDetailDao;
import ca.douglascollege.eatnow.database.product.Product;
import ca.douglascollege.eatnow.database.product.ProductDao;
import ca.douglascollege.eatnow.database.recommendation.Recommendation;
import ca.douglascollege.eatnow.database.recommendation.RecommendationDao;
import ca.douglascollege.eatnow.database.restaurant.Restaurant;
import ca.douglascollege.eatnow.database.restaurant.RestaurantDao;
import ca.douglascollege.eatnow.database.user.User;
import ca.douglascollege.eatnow.database.user.UserDao;

@Database(entities = {
        User.class,
        Restaurant.class,
        Product.class,
        Recommendation.class,
        Order.class,
        OrderDetail.class
}, version = 12, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "EatNow.db";
    private static volatile AppDatabase INSTANCE;
    private static String FIRST_EMAIL = "client@gmail.com";

    public abstract UserDao userDao();
    public abstract RestaurantDao restaurantDao();
    public abstract ProductDao productDao();
    public abstract RecommendationDao recommendationDao();
    public abstract OrderDao orderDao();
    public abstract OrderDetailDao orderDetailDao();

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
        products.addAll(getTripleOProducts());
        products.addAll(getHyackProducts());
        products.addAll(getVCafeProducts());
        products.addAll(getSpaghettiFactoryProducts());
        products.addAll(getKiSushiProducts());
        products.addAll(getPivaProducts());
        products.addAll(getLudicaProducts());
        products.addAll(getPatsaraProducts());
        products.addAll(getSushiYenProducts());
        products.addAll(getBavariaProducts());
        return products;
    }

    private static ArrayList<Product> getTripleOProducts() {
        int restaurantId = 1;//Triple O
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product("2-Piece Crispy Fish Burger", "restaurant_image", "Two Ocean Wise crispy beer battered cod fillets, crisp iceberg lettuce, fresh vine-ripened tomatoes, red onion and tartar sauce with our signature pickle on top!", (float) 5.65, restaurantId));
        products.add(new Product("Bacon Cheddar Burger", "restaurant_image", "100% fresh Canadian beef, naturally smoked bacon, aged Canadian cheddar, crisp iceberg lettuce, fresh vine-ripened tomatoes, secret Triple O sauce and our signature pickle on top.", (float) 3.95, restaurantId));
        products.add(new Product("Double Double", "restaurant_image", "Not one, but two 100% fresh Canadian beef patties, two slices of cheese, crisp iceberg lettuce, fresh vine-ripened tomatoes, secret Triple O sauce and two signature pickles", (float) 4.05, restaurantId));
        products.add(new Product("Dippin’ Chicken Tenders", "restaurant_image", "Our 5-piece or 3-piece panko-breaded Dippin' Chicken tenders are made from 100% BC chicken, served your choice of dip. Try them with our signature honey mustard or chipotle mayo!", (float) 6.11, restaurantId));
        products.add(new Product("Chocolate Milkshake", "restaurant_image", "Rich chocolate blended with local Island Farms, premium vanilla bean ice cream, topped with real whipped cream!", (float) 3.55, restaurantId));
        products.add(new Product("Strawberry Milkshake", "restaurant_image", "BC strawberries blended with our local Island Farms, premium vanilla bean ice cream and topped with real whipped cream!", (float) 3.91, restaurantId));
        products.add(new Product("Vanilla Milkshake", "restaurant_image", "Hand-scooped, local Island Farms vanilla bean ice cream, blended then topped with real whipped cream!", (float) 3.51, restaurantId));

        return products;
    }

    private static ArrayList<Product> getHyackProducts() {
        int restaurantId = 2;//Hyack
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product("SNAPPER (TAI) NIGIRI", "restaurant_image", "Wasabi inside	", (float) 1.35, restaurantId));
        products.add(new Product("COOKED PRAWN (EBI) NIGIRI", "restaurant_image", "Wasabi inside", (float) 1.65, restaurantId));
        products.add(new Product(" SMOKED SALMON NIGIRI", "restaurant_image", "Wasabi inside", (float) 1.75, restaurantId));
        products.add(new Product("Pop can", "restaurant_image", "Variety", (float) 1.50, restaurantId));
        products.add(new Product("Iced Tea", "restaurant_image", "Lemon Iced tea", (float) 1.25, restaurantId));
        return products;
    }

    private static ArrayList<Product> getVCafeProducts() {
        int restaurantId = 3;//VCafe
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product("Blueberry Muffin", "restaurant_image", "", (float) 1.15, restaurantId));
        products.add(new Product("Strawberry Muffin", "restaurant_image", "", (float) 1.35, restaurantId));
        products.add(new Product("Bacon and Eggs", "restaurant_image", "", (float) 2.65, restaurantId));
        products.add(new Product("Dark Roast Coffee", "restaurant_image", "Strong flavor", (float) 1.50, restaurantId));
        products.add(new Product("Orange Juice", "restaurant_image", "Freshly squeezed", (float) 2.50, restaurantId));
        products.add(new Product("Hot Chocolate", "restaurant_image", "", (float) 2.50, restaurantId));
        return products;
    }

    private static ArrayList<Product> getSpaghettiFactoryProducts() {
        int restaurantId = 4;//SpaghettiFactory
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product("Spagheti and meat ball", "restaurant_image", "", (float) 9.99, restaurantId));
        products.add(new Product("Fettuccine", "restaurant_image", "", (float) 8.98, restaurantId));
        products.add(new Product("Lasagna", "restaurant_image", "", (float) 9.88, restaurantId));
        products.add(new Product("Strawberry Juice", "restaurant_image", "", (float) 2.60, restaurantId));
        products.add(new Product("Orange Juice", "restaurant_image", "Freshly squeezed", (float) 2.50, restaurantId));
        products.add(new Product("Iced Tea", "restaurant_image", "Lemon Iced tea", (float) 1.25, restaurantId));
        return products;
    }

    private static ArrayList<Product> getKiSushiProducts() {
        int restaurantId = 5;//KiSushi
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product("Salmon Sushi", "restaurant_image", "Wild Salmon", (float) 1.35, restaurantId));
        products.add(new Product("Tuna Sushi", "restaurant_image", "Wasabi inside", (float) 1.65, restaurantId));
        products.add(new Product("SMOKED SALMON NIGIRI", "restaurant_image", "Wasabi inside", (float) 1.75, restaurantId));
        products.add(new Product("Pop can", "restaurant_image", "Variety", (float) 1.50, restaurantId));
        products.add(new Product("Iced Tea", "restaurant_image", "Lemon Iced tea", (float) 1.25, restaurantId));
        return products;
    }

    private static ArrayList<Product> getPivaProducts() {
        int restaurantId = 6;//Piva
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product("Pizza", "restaurant_image", "", (float) 9.99, restaurantId));
        products.add(new Product("Fettuccine", "restaurant_image", "", (float) 8.98, restaurantId));
        products.add(new Product("Lasagna", "restaurant_image", "", (float) 9.88, restaurantId));
        products.add(new Product("Strawberry Juice", "restaurant_image", "", (float) 2.60, restaurantId));
        products.add(new Product("Orange Juice", "restaurant_image", "Freshly squeezed", (float) 2.50, restaurantId));
        products.add(new Product("Iced Tea", "restaurant_image", "Lemon Iced tea", (float) 1.25, restaurantId));
        return products;
    }

    private static ArrayList<Product> getLudicaProducts() {
        int restaurantId = 7;//Ludica
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product("Tuna Pizza", "restaurant_image", "", (float) 9.99, restaurantId));
        products.add(new Product("Cheese Pizza", "restaurant_image", "", (float) 8.98, restaurantId));
        products.add(new Product("Crusty Pizza", "restaurant_image", "", (float) 9.88, restaurantId));
        products.add(new Product("Pop", "restaurant_image", "", (float) 2.60, restaurantId));
        products.add(new Product("Orange Juice", "restaurant_image", "Freshly squeezed", (float) 2.50, restaurantId));
        products.add(new Product("Iced Tea", "restaurant_image", "Lemon Iced tea", (float) 1.25, restaurantId));
        return products;
    }
    private static ArrayList<Product> getPatsaraProducts() {
        int restaurantId = 8;//Patsara
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product("Spring Rolls", "restaurant_image", "Vegetarian", (float) 6.88, restaurantId));
        products.add(new Product("Chicken Satay", "restaurant_image", "4 skewers", (float) 7.90, restaurantId));
        products.add(new Product("Lemon Chicken", "restaurant_image", "", (float) 8.59, restaurantId));
        products.add(new Product("Grilled Chicken", "restaurant_image", "", (float) 9.88, restaurantId));
        products.add(new Product("Iced Tea", "restaurant_image", "Lemon Iced tea", (float) 1.25, restaurantId));
        return products;
    }
    private static ArrayList<Product> getSushiYenProducts() {
        int restaurantId = 9;//SushiYen
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product("Salmon Sushi", "restaurant_image", "Wild Salmon", (float) 8.50, restaurantId));
        products.add(new Product("Tuna Sushi", "restaurant_image", "Pacific Tuna", (float) 9.50, restaurantId));
        products.add(new Product("Smoked Salmon", "restaurant_image", "Wasabi inside", (float) 8.65, restaurantId));
        products.add(new Product("Pop can", "restaurant_image", "Variety", (float) 2.15, restaurantId));
        products.add(new Product("Iced Tea", "restaurant_image", "Lemon Iced tea", (float) 1.35, restaurantId));
        return products;
    }
    private static ArrayList<Product> getBavariaProducts() {
        int restaurantId = 10;//Bavaria
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product("Tenderloin", "restaurant_image", "", (float) 11.99, restaurantId));
        products.add(new Product("Sirloin", "restaurant_image", "", (float) 11.66, restaurantId));
        products.add(new Product("Lasagna", "restaurant_image", "", (float) 9.88, restaurantId));
        products.add(new Product("Strawberry Juice", "restaurant_image", "", (float) 2.60, restaurantId));
        products.add(new Product("Orange Juice", "restaurant_image", "Freshly squeezed", (float) 2.50, restaurantId));
        products.add(new Product("Iced Tea", "restaurant_image", "Lemon Iced tea", (float) 1.25, restaurantId));
        return products;
    }

}
