package kryx07.expensereconcilerclient.di


import javax.inject.Singleton

import kryx07.expensereconcilerclient.ui.DashboardActivity
import kryx07.expensereconcilerclient.ui.payables.PayablesFragment
import kryx07.expensereconcilerclient.ui.transactions.TransactionsFragment

@Singleton
@dagger.Component(modules = arrayOf(AppModule::class))
interface AppComponent {

    fun inject(dashboardActivity: DashboardActivity)

    fun inject(transactionsFragment: TransactionsFragment)
    fun inject(payablesFragment: PayablesFragment)
    /*void inject(LoginActivity loginActivity);

    void inject(DashboardActivity mainActivity);

    void inject(ContactsFragment contactsFragment);*/


}
