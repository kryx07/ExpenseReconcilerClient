package kryx07.expensereconcilerclient.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Provides;

/**
 * Created by sda on 26.06.17.
 */

@dagger.Module
public class AppModule {

    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    Context providesContext() {
        return context;
    }

    /*@Provides
    @Singleton
    PrefsManager providesPrefsManager(Context context) {
        return new PrefsManager(context);
    }

    @Provides
    @Singleton
    ApiClient providesApiClient(PrefsManager prefsManager) {
        return new ApiClient(prefsManager);
    }*/
}
