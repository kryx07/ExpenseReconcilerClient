package kryx07.expensereconcilerclient.ui

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import butterknife.ButterKnife
import kotlinx.android.synthetic.main.activity_dashboard.*
import kryx07.expensereconcilerclient.App
import kryx07.expensereconcilerclient.R
import kryx07.expensereconcilerclient.events.HideProgress
import kryx07.expensereconcilerclient.events.ShowProgress
import kryx07.expensereconcilerclient.network.ApiClient
import kryx07.expensereconcilerclient.ui.payables.PayablesFragment
import kryx07.expensereconcilerclient.ui.transactions.TransactionsFragment
import kryx07.expensereconcilerclient.utils.SharedPreferencesManager
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import timber.log.Timber
import javax.inject.Inject
import android.support.v4.widget.SwipeRefreshLayout
import kryx07.expensereconcilerclient.base.Refreshable
import kryx07.expensereconcilerclient.events.ShowRefresher
import kryx07.expensereconcilerclient.events.HideRefresher


class DashboardActivity : AppCompatActivity() {

    @Inject lateinit var apiClient: ApiClient
    @Inject lateinit var sharedPreferencesManager: SharedPreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        ButterKnife.bind(this)
        EventBus.getDefault().register(this)

        setupDrawerAndToolbar()
        setupSwipeRefresher()
        if (savedInstanceState == null) {
            showFragment(TransactionsFragment())
        }
        App.appComponent.inject(this)

        //to be replaced with login!!!
        sharedPreferencesManager.write(getString(R.string.my_user), "testuser1")

        dashboard_progress.indeterminateDrawable.setColorFilter(Color.GRAY, android.graphics.PorterDuff.Mode.SRC_IN)

        //my_button.setOnClickListener { Timber.e(getVisibleFragment().toString()) }

    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    private fun setupDrawerAndToolbar() {
        setSupportActionBar(dashboard_toolbar)
        val toggle = ActionBarDrawerToggle(
                this, dashboard_drawer, dashboard_toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        // Handle clicks on drawer
        dashboard_drawer.addDrawerListener(toggle)
        // Sync state to have a hamburger menu icon
        toggle.syncState()

        dashboard_nav.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_transactions -> {
                    showFragment(TransactionsFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.menu_payables -> {
                    showFragment(PayablesFragment())
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })
    }

    fun setupSwipeRefresher() {

        swipe_refresher.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            val visibleFragment: Refreshable? = getVisibleFragment()
            if (visibleFragment != null) {
                EventBus.getDefault().post(ShowRefresher(visibleFragment))
            } else {
                Timber.e("There is no visible fragment")
            }
            swipe_refresher.setColorSchemeResources(
                    R.color.colorPrimary,
                    R.color.accent_material_light,
                    R.color.dark_yellow,
                    R.color.colorAccent)
            showSwipeRefresher()
            Timber.e("On refresh listener")
        })

    }

    @Subscribe
    fun onHideSwipeRefresher(hideRefresher: HideRefresher) {
        Timber.e("On hide event received")
        hideSwipeRefresher()
    }

    private fun showSwipeRefresher() {
        Timber.e("SwipeRefresher shown")
        swipe_refresher.isRefreshing = true
    }

    private fun hideSwipeRefresher() {
        Timber.e("SwipeRefresher hidden")
        swipe_refresher.isRefreshing = false
    }

    @Subscribe
    fun onShowProgress(showProgress: ShowProgress) {
        fragment_container.visibility = View.INVISIBLE
        dashboard_progress.visibility = View.VISIBLE

    }

    @Subscribe
    fun onHideProgress(showProgress: HideProgress) {
        fragment_container.visibility = View.VISIBLE
        dashboard_progress.visibility = View.GONE

    }

    private fun showFragment(fragment: Fragment) {
        val tag = fragment.javaClass.name
        val manager = supportFragmentManager

        if (manager.findFragmentByTag(tag) == null) {
            // This fragment does not lie on back stack, need to be added
            manager.beginTransaction()
                    // Add a tag to prevent duplicating insertions of the same fragment
                    .replace(R.id.fragment_container, fragment, tag)
                    .addToBackStack(tag)
                    .commit()
        } else {
            // Get the fragment from the back stack
            manager.popBackStackImmediate(tag, 0)
        }
        dashboard_drawer.closeDrawer(Gravity.START)
    }

    fun getVisibleFragmentOld(): Refreshable? {
        val fragmentManager = this.supportFragmentManager
        val fragments = fragmentManager.fragments
        if (fragments != null) {
            fragments
                    .filter { it != null && it.isVisible }
                    .forEach { return it as Refreshable }
        }
        return null
    }

    fun getVisibleFragment(): Refreshable? {
        val fragmentManager = this.supportFragmentManager
        val fragments = fragmentManager.fragments
        if (fragments != null) {
            fragments
                    .filter { it != null && it.isVisible && it is Refreshable }
                    .forEach { return it as Refreshable }
        }
        return null
    }

}
