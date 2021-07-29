package com.example.cleanapp.user_data

import android.content.Context
import android.content.SharedPreferences
import com.example.cleanapp.R
import com.example.cleanapp.models.User
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UserData @Inject constructor(@ApplicationContext private val context: Context) {
    companion object {
        private const val IS_MASTER = "IS_MASTER"
        private const val USER_EMAIL = "USER_EMAIL"
        private const val USER_NAME = "USER_NAME"
        private const val USER_SURNAME = "USER_SURNAME"
        private const val USER_ID = "USER_ID"
        private const val USER_IMAGE = "USER_IMAGE"
        private const val USER_REGISTRATION_DATE = "USER_REGISTRATION_DATE"
        private const val USER_PHONE = "USER_PHONE"
    }

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(context.getString(R.string.user_data), Context.MODE_PRIVATE)
    }

    fun saveUser(user: User) {
        val edit = sharedPreferences.edit()
        edit.putBoolean(IS_MASTER, user.isMaster!!)
        edit.putString(USER_EMAIL, user.email!!)
        edit.putString(USER_NAME, user.name!!)
        edit.putString(USER_SURNAME, user.surname!!)
        edit.putString(USER_ID, user.uid!!)
        edit.putString(USER_IMAGE, user.imgUrl!!)
        edit.putLong(USER_REGISTRATION_DATE, user.registrationDate!!)
        edit.putString(USER_PHONE, user.phone!!)
        edit.apply()
    }

    fun getUser() = User(
        sharedPreferences.getString(USER_ID, ""),
        sharedPreferences.getString(USER_EMAIL, ""),
        sharedPreferences.getString(USER_NAME, ""),
        sharedPreferences.getString(USER_SURNAME, ""),
        sharedPreferences.getString(USER_PHONE, ""),
        sharedPreferences.getString(USER_IMAGE, ""),
        sharedPreferences.getBoolean(IS_MASTER, false),
        sharedPreferences.getLong(USER_REGISTRATION_DATE, 0)
    )

    fun deleteUser() {
        val edit = sharedPreferences.edit()
        edit.remove(IS_MASTER)
        edit.remove(USER_EMAIL)
        edit.remove(USER_NAME)
        edit.remove(USER_SURNAME)
        edit.remove(USER_ID)
        edit.remove(USER_IMAGE)
        edit.remove(USER_REGISTRATION_DATE)
        edit.remove(USER_PHONE)
        edit.apply()
    }

    fun selectAsMaster() {
        sharedPreferences.edit().putBoolean(IS_MASTER, true).apply()
    }

}