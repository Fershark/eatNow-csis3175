package ca.douglascollege.eatnow.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.Date;
import java.util.concurrent.Executors;

@Database(entities = {User.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "EatNow.db";
    private static volatile AppDatabase instance;
    private static final Object LOCK = new Object();

    public abstract UserDao userDao();

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
