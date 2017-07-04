package kryx07.expensereconcilerclient;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import kryx07.expensereconcilerclient.di.AppComponent;
import kryx07.expensereconcilerclient.di.AppModule;
import kryx07.expensereconcilerclient.di.DaggerAppComponent;


/**
 * Created by sda on 24.06.17.
 */

public class App extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initRealm();
        initDagger();
    }

    private void initRealm() {
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(configuration);
    }

    private void initDagger() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
