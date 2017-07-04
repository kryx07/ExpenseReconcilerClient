package kryx07.expensereconcilerclient.di


import javax.inject.Singleton

import kryx07.expensereconcilerclient.MainActivity

@Singleton
@dagger.Component(modules = arrayOf(AppModule::class))
interface AppComponent {

    fun inject(mainActivity: MainActivity)


    /*void inject(LoginActivity loginActivity);

    void inject(MainActivity mainActivity);

    void inject(ContactsFragment contactsFragment);*/


}
