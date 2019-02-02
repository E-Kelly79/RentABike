package com.bike.rent.kelly.data.local

import android.content.Context
import android.content.SharedPreferences

class PreferencesHelper(context: Context) {
    val PREF_FILE_NAME = "GBS_BIKES"

    private var mPref: SharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)

    fun setPrefString(KEY: String, value: String){
        val editor: SharedPreferences.Editor = mPref.edit()
        editor.putString(KEY, value)
        editor.commit()
    }

    fun setPrefInt(KEY: String, value: Int){
        val editor: SharedPreferences.Editor = mPref.edit()
        editor.putInt(KEY, value)
        editor.commit()
    }

    fun setPrefFloat(KEY: String, value: Float){
        val editor: SharedPreferences.Editor = mPref.edit()
        editor.putFloat(KEY, value)
        editor.commit()
    }

    fun setPrefLong(KEY: String, value: Long){
        val editor: SharedPreferences.Editor = mPref.edit()
        editor.putLong(KEY, value)
        editor.commit()
    }

    fun setPrefBoolean(KEY: String, status: Boolean){
        val editor: SharedPreferences.Editor = mPref.edit()
        editor.putBoolean(KEY, status)
        editor.commit()
    }

    fun getPrefString(KEY_NAME: String): String? {
        return mPref.getString(KEY_NAME, "")
    }

    fun getPrefInt(KEY_NAME: String): Int? {
        return mPref.getInt(KEY_NAME, 0)
    }

    fun getPrefFloat(KEY_NAME: String): Float? {
        return mPref.getFloat(KEY_NAME, 0.0f)
    }

    fun getPrefLong(KEY_NAME: String): Long? {
        return mPref.getLong(KEY_NAME, 0L)
    }

    fun getPrefBoolean(KEY_NAME: String): Boolean? {
        return mPref.getBoolean(KEY_NAME, false)
    }

    fun clearPrefValue(KEY: String){
        val editor: SharedPreferences.Editor = mPref.edit()
        editor.remove(KEY)
        editor.commit()
    }

    fun clearPrefs(){
        val editor: SharedPreferences.Editor = mPref.edit()
        editor.clear()
        editor.commit()
    }
}