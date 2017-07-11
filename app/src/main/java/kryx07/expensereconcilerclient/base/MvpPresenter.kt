package kryx07.expensereconcilerclient.base

/**
 * Created by sda on 06.07.17.
 */

interface MvpPresenter<T : MvpView> {

    fun attachView(view: T)

    fun detach()

    val view: T

    val isViewAttached: Boolean
}