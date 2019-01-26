package com.bike.rent.kelly.ui.auth

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.bike.rent.kelly.ui.login.LoginFragment
import com.bike.rent.kelly.ui.signup.SignupFragment

class AuthPagerAdapter(fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment? {
        when(position){
            0 -> return LoginFragment()
            1-> return SignupFragment()
        }
        return null
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position){
            0 -> return "Login"
            1-> return "Signup"
        }
        return null
    }
}