package kryx07.expensereconcilerclient.base

interface MvpPresenter<T : MvpView> {

    fun attachView(view: T)

    fun detach()

    val view: T

    val isViewAttached: Boolean
}