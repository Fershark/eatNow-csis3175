package ca.douglascollege.eatnow.database.recommendation;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;
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

    public LiveData<List<Recommendation>> findUnsedRecommendationForUser(int userId) {
        return appDatabase.recommendationDao().findUnsedRecommendationForUser(userId);
    }
}
