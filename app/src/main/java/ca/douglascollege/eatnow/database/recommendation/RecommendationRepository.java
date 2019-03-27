package ca.douglascollege.eatnow.database.recommendation;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import ca.douglascollege.eatnow.database.AppDatabase;

public class RecommendationRepository {
    private AppDatabase appDatabase;
    private String TAG = "PRODUCT_REPOSITORY";

    public RecommendationRepository(Context context) {
        appDatabase = AppDatabase.getInstance(context);
    }

    public void insertRecommendation(final Recommendation recommendation) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.recommendationDao().insertRecommendation(recommendation);
                return null;
            }
        }.execute();
    }

    public void updateRecommendation(final Recommendation recommendation) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.recommendationDao().updateRecommendation(recommendation);
                return null;
            }
        }.execute();
    }

    public Recommendation findUnsedRecommendationByUser(final int userId) {
        Recommendation recommendation = null;

        Callable<Recommendation> callable = new Callable<Recommendation>() {
            @Override
            public Recommendation call() throws Exception {
                return appDatabase.recommendationDao().findUnsedRecommendationByUser(userId);
            }
        };

        Future<Recommendation> future = Executors.newSingleThreadExecutor().submit(callable);
        try {
            recommendation = future.get();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

        return recommendation;
    }
}
