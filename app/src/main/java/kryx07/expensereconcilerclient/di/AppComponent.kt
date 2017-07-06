package kryx07.expensereconcilerclient.di


import javax.inject.Singleton

import kryx07.expensereconcilerclient.ui.MainActivity
import kryx07.expensereconcilerclient.ui.TransactionsFragment

@Singleton
@dagger.Component(modules = arrayOf(AppModule::class))
interface AppComponent {

    fun inject(mainActivity: MainActivity)

    fun inject(transactionsFragment: TransactionsFragment)
    /*void inject(LoginActivity loginActivity);

    void inject(MainActivity mainActivity);

    void inject(ContactsFragment contactsFragment);*/


}
