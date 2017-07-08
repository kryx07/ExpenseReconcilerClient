package kryx07.expensereconcilerclient.di

import android.content.Context

import javax.inject.Singleton

import dagger.Provides
import kryx07.expensereconcilerclient.network.ApiClient
import kryx07.expensereconcilerclient.ui.transactions.TransactionsPresenter
import kryx07.expensereconcilerclient.utils.SharedPreferencesManager

@dagger.Module
class AppModule(private val context: Context) {

    @Provides @Singleton
    fun providesContext(): Context {
        return context
    }

    @Provides @Singleton
    fun sharedPreferencesManager(context: Context): SharedPreferencesManager {
        return SharedPreferencesManager(context)
    }


    @Provides @Singleton
    fun providesApiClient(sharedPreferencesManager: SharedPreferencesManager): ApiClient {
        return ApiClient(sharedPreferencesManager)
    }

/*
    @Provides @Singleton
    fun providesTransactionsPresenter(apiClient: ApiClient, context: Context) = TransactionsPresenter(apiClient, context)
*/

}
