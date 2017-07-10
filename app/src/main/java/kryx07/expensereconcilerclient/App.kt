package kryx07.expensereconcilerclient

import android.app.Application
import android.arch.persistence.room.Room
import kryx07.expensereconcilerclient.db.MyDatabase

import kryx07.expensereconcilerclient.di.AppComponent
import kryx07.expensereconcilerclient.di.AppModule
import kryx07.expensereconcilerclient.di.DaggerAppComponent
import timber.log.Timber

class App : Application() {


    companion object {
        lateinit var appComponent: AppComponent
        //lateinit var database: MyDatabase
    }

    override fun onCreate() {
        super.onCreate()
        //initRoom()
        initDagger()
        initTimber()
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }

    private fun initRoom() {
        /*      Realm.init(this)
              val configuration = RealmConfiguration.Builder().build()
              Realm.setDefaultConfiguration(configuration)
      */
        /*database = Room
                .databaseBuilder(this, MyDatabase::class.java, "we-need-db")
                .allowMainThreadQueries()
                .build()*/
    }

    private fun initDagger() {
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }
}
