package kryx07.expensereconcilerclient

import android.app.Application

import io.realm.Realm
import io.realm.RealmConfiguration
import kryx07.expensereconcilerclient.di.AppComponent
import kryx07.expensereconcilerclient.di.AppModule
import kryx07.expensereconcilerclient.di.DaggerAppComponent

class App : Application() {

    var appComponent: AppComponent? = null
        private set

    override fun onCreate() {
        super.onCreate()
        initRealm()
        initDagger()
    }

    private fun initRealm() {
        Realm.init(this)
        val configuration = RealmConfiguration.Builder().build()
        Realm.setDefaultConfiguration(configuration)
    }

    private fun initDagger() {
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }
}
