package kryx07.expensereconcilerclient

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import butterknife.ButterKnife
import kryx07.expensereconcilerclient.network.ApiClient
import timber.log.Timber
import java.sql.Time
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var apiClient: ApiClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        Timber.plant(Timber.DebugTree())
        (application as App).appComponent?.inject(this)


        Timber.e(apiClient.javaClass.toString())
    }
}
