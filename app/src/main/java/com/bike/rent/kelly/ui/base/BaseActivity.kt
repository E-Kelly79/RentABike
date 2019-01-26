package com.bike.rent.kelly.ui.base

import android.content.Context
import android.graphics.Color
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.annotation.StringDef
import android.support.design.widget.NavigationView
import android.support.v4.app.FragmentTransaction
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.bike.rent.kelly.ui.maps.GoogleMapsFragment
import com.bike.rent.kelly.R
import com.bike.rent.kelly.ui.auth.AuthActivity
import com.bike.rent.kelly.ui.bike.BikeList
import com.bike.rent.kelly.ui.city_select.CitySelectFragment
import com.bike.rent.kelly.ui.menu.MenuFragment
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

open class BaseActivity : AppCompatActivity(), MvpView {
    var mActivityId: Long = 0

    /**
     * Set current fragment
     */
    @get:MainFragments
    var currentFragmentKey: String? = CITY_SELECT_FRAGMENT

    /**
     * Get a fragment
     * @return fragment
     */
    var fragment: BaseFragment? = null
        private set


    var bundle: Bundle? = null


    var mFragmentContainer: FrameLayout? = null
    var mDrawerLayout: DrawerLayout? = null
    var mNavView: NavigationView? = null
    var mMainContent: RelativeLayout? = null
    var mToolbar: Toolbar? = null
    var mivToolbarPrimary: ImageView? = null
    var mtvTitle: TextView? = null

    override
    val context: Context
        get() = applicationContext

    override
    val baseActivity: BaseActivity
        get() = this

    @RequiresApi(VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.base_layout)
        mToolbar = findViewById(R.id.Toolbar)
        mFragmentContainer = findViewById(R.id.fragment_container)
        mDrawerLayout = findViewById(R.id.layout_drawer)
        mNavView = findViewById(R.id.left_drawer)
        mMainContent = findViewById(R.id.layout_main_content)
        mivToolbarPrimary = findViewById(R.id.img_home)
        mtvTitle = findViewById(R.id.ToolbarTitle)

        initNavDrawer()
    }

    public override fun onResume() {
        super.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong(KEY_ACTIVITY_ID, mActivityId)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    @RequiresApi(VERSION_CODES.LOLLIPOP)
    private fun initNavDrawer() {
        mDrawerLayout?.setScrimColor(Color.TRANSPARENT)
        mNavView?.outlineProvider = null
        mDrawerLayout?.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                val moveFactor = mNavView!!.width * slideOffset
                mMainContent!!.translationX = moveFactor
                mMainContent!!.translationY = 80f * slideOffset
            }

            override fun onDrawerOpened(drawerView: View) {
            }

            override fun onDrawerClosed(drawerView: View) {
            }

            override fun onDrawerStateChanged(newState: Int) {
            }
        })
    }

    fun showNavDrawer() {
        closeSoftKeyboard()
        mDrawerLayout!!.openDrawer(Gravity.LEFT)
    }

    protected fun closeNavDrawer() {
        mDrawerLayout!!.closeDrawer(Gravity.LEFT)
    }

    fun closeSoftKeyboard() {
        // Check if no view has focus:
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    /**
     * Lock Nav Drawer for Fragments that do not allow Nav Drawer access.
     */
    fun lockNavDrawer() {
        mDrawerLayout!!.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    /**
     * Unlock Nav Drawer for Fragments that allow Nav Drawer access.
     */
    fun unlockNavDrawer() {
        mDrawerLayout!!.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }

    /**
     * Hide Toolbar for Fragments that do not require it.
     */
    fun hideToolbar() {
        mToolbar!!.visibility = View.GONE
    }

    /**
     * Show Toolbar when required.
     */
    fun showToolbar() {
        mToolbar!!.visibility = View.VISIBLE
    }

    fun getFragment(args: Bundle, addToBackStack: Boolean, fragment: BaseFragment,
            fragmentKey: String): FragmentTransaction {
        return getFragmentAnim(args, addToBackStack, fragment,
                fragmentKey, android.R.anim.fade_in, android.R.anim.fade_out,
                android.R.anim.fade_in, android.R.anim.fade_out
        )
    }

    fun getFragmentAnim(
        args: Bundle?, addToBackStack: Boolean, fragment: BaseFragment,
        fragmentKey: String, animEnter: Int, animExit: Int, animBackEnter: Int, animBackExit: Int): FragmentTransaction {
        this.fragment = fragment
        val ft = supportFragmentManager.beginTransaction()
        ft.addToBackStack(null)
        ft.setCustomAnimations(animEnter, animExit, animBackEnter, animBackExit)
        if (args != null) fragment.arguments = args
        ft.replace(R.id.fragment_container, fragment, fragmentKey)
        if (addToBackStack && currentFragmentKey != fragmentKey) ft.addToBackStack(fragment.tag())
        currentFragmentKey = fragmentKey
        return ft
    }

    /**
     * Load fragment based on Tag
     */
    fun loadFragment(args: Bundle, fragment: String, addToBackStack: Boolean) {
        when (fragment) {
           CITY_SELECT_FRAGMENT -> {
                currentFragmentKey = CITY_SELECT_FRAGMENT
                loadCitySelectFragment(args, addToBackStack)
            }
           MENU_FRAGMENT -> {
                currentFragmentKey = MENU_FRAGMENT
                loadMenuFragment(args, addToBackStack)
            }
            BIKE_LIST_FRAGMENT -> {
                currentFragmentKey = BIKE_LIST_FRAGMENT
                loadBikeListFragment(args, addToBackStack)
            }
            LOGIN_FRAGMENT -> {
                currentFragmentKey = LOGIN_FRAGMENT
                loadLoginFragment(args, addToBackStack)
            }
            GOOGLE_MAPS ->{
                currentFragmentKey = GOOGLE_MAPS
                loadGoogleMapsFragment(args, addToBackStack)
            }
        }
    }

    /**
     * Load City Select Fragment
     *
     * @param args           Bundle
     * @param addToBackStack Boolean
     */
    fun loadCitySelectFragment(args: Bundle, addToBackStack: Boolean) {
        getFragment(args, addToBackStack, CitySelectFragment(), CITY_SELECT_FRAGMENT).commit()
    }

    /**
     * Load BikeList Fragment
     *
     * @param args           Bundle
     * @param addToBackStack Boolean
     */
    fun loadBikeListFragment(args: Bundle, addToBackStack: Boolean) {
        getFragment(args, addToBackStack, BikeList(), BIKE_LIST_FRAGMENT).commit()
    }

    /**
     * Load Login Fragment
     *
     * @param args           Bundle
     * @param addToBackStack Boolean
     */
    fun loadLoginFragment(args: Bundle, addToBackStack: Boolean) {
        getFragment(args, addToBackStack, AuthActivity(), LOGIN_FRAGMENT).commit()
    }

    /**
     * Load Google Maps Fragment
     *
     * @param args           Bundle
     * @param addToBackStack Boolean
     */
    fun loadGoogleMapsFragment(args: Bundle, addToBackStack: Boolean) {
        getFragment(args, addToBackStack, GoogleMapsFragment(), GOOGLE_MAPS).commit()
    }

    /**
     * Load Menu Fragment
     *
     * @param args           Bundle
     * @param addToBackStack Boolean
     */
    fun loadMenuFragment(args: Bundle, addToBackStack: Boolean) {
        getFragment(args, addToBackStack, MenuFragment(), MENU_FRAGMENT
        ).commit()
    }

    /**
     * Definition of fragments supported
     */
    @StringDef(CITY_SELECT_FRAGMENT, MENU_FRAGMENT, BIKE_LIST_FRAGMENT, LOGIN_FRAGMENT, GOOGLE_MAPS )
    @Retention(RetentionPolicy.SOURCE)
    annotation class MainFragments

    override fun onUserInteraction() {
        super.onUserInteraction()
        if (fragment != null) {
            fragment!!.onUserInteraction()
        }
    }


    @RequiresApi(api = VERSION_CODES.LOLLIPOP)
    fun setToolbarButton(drawable: Int) {
        mivToolbarPrimary!!.setImageDrawable(this.resources.getDrawable(drawable, applicationContext.theme))
    }

    fun setTitle(title: String) {
        mtvTitle?.setText(title)
        mtvTitle?.setTextColor(resources.getColor(R.color.color_black))
    }

    companion object {
        const val CITY_SELECT_FRAGMENT = "MAPS_FRAGMENT"
        const val MENU_FRAGMENT = "MENU_FRAGMENT"
        const val BIKE_LIST_FRAGMENT = "BIKE_LIST"
        const val LOGIN_FRAGMENT = "LOGIN"
        const val GOOGLE_MAPS = "GOOGLE_MAPS"

        const val KEY_ACTIVITY_ID = "KEY_ACTIVITY_ID"
        const val KEY_FRAGMENT_ARGS = "KEY_FRAGMENT_ARGS"
        const val ADD_FRAGMENT_TO_BACKSTACK = true
        const val NOT_ADD_TO_BACKSTACK = false
        const val LAT = "LAT"
        const val LNG = "LNG"
        const val TITLE ="TITLE"
    }
}