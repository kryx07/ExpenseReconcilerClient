package kryx07.expensereconcilerclient.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

import kryx07.expensereconcilerclient.R

class SharedPreferencesManager(val context: Context) {

    private val editor: SharedPreferences.Editor = context.getSharedPreferences(context.getString(R.string.shared_prefs), 0).edit()
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_prefs), Context.MODE_PRIVATE)

    fun write(string: String, key: String) {

        editor.putString(key, string)
        editor.apply()

        val savedValue = sharedPreferences.getString(key,
                context.getString(R.string.no_value))
        Log.e("Saved ", savedValue + " under key: " + key)
    }

    fun read(key: String): String {
        val defVal = context.getString(R.string.no_value)
        return sharedPreferences.getString(key, defVal)

    }
}