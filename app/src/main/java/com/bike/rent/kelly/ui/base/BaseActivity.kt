package com.bike.rent.kelly.ui.base

import android.content.Context
import android.graphics.Color
import android.location.LocationManager
import android.os.Build
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.annotation.StringDef
import android.support.v4.app.FragmentTransaction
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager

import com.bike.rent.kelly.R
import com.bike.rent.kelly.SupportMapFragment
import com.bike.rent.kelly.ui.auth.AuthActivity
import com.bike.rent.kelly.ui.bike.BikeList
import com.bike.rent.kelly.ui.city_select.CitySelectFragment
import com.bike.rent.kelly.ui.creditcards.CreditCardFragment
import com.bike.rent.kelly.ui.favorites.FavouritesFragment
import com.bike.rent.kelly.ui.menu.MenuFragment
import com.bike.rent.kelly.ui.payment.PaymentFragment
import com.bike.rent.kelly.ui.wallet.WalletFragment
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import kotlinx.android.synthetic.main.base_layout.*
import kotlinx.android.synthetic.main.toolbar.ToolbarTitle
import kotlinx.android.synthetic.main.toolbar.mToolbarHome

open class BaseActivity : AppCompatActivity() {
    lateinit var bundle: Bundle

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


    val context: Context
        get() = applicationContext


    val baseActivity: BaseActivity
        get() = this

    @RequiresApi(VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.base_layout)
        bundle = Bundle()
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
        mLayoutDrawer.setScrimColor(Color.TRANSPARENT)
        mDrawer.outlineProvider = null
        mLayoutDrawer.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                val moveFactor = mDrawer.width * slideOffset
                mMainContent.translationX = moveFactor
                mMainContent.translationY = 80f * slideOffset
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
        mLayoutDrawer.openDrawer(Gravity.LEFT)
    }

    protected fun closeNavDrawer() {
        mLayoutDrawer.closeDrawer(Gravity.LEFT)
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
        mLayoutDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    /**
     * Unlock Nav Drawer for Fragments that allow Nav Drawer access.
     */
    fun unlockNavDrawer() {
        mLayoutDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }

    /**
     * Hide Toolbar for Fragments that do not require it.
     */
    fun hideToolbar() {
        Toolbar.visibility = View.GONE
    }

    /**
     * Show Toolbar when required.
     */
    fun showToolbar() {
        Toolbar.visibility = View.VISIBLE
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
        ft.replace(R.id.mFragmentContainer, fragment, fragmentKey)
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
            AUTH_FRAGMENT -> {
                currentFragmentKey = AUTH_FRAGMENT
                loadAuthFragment(args, addToBackStack)
            }
            GOOGLE_MAPS ->{
                currentFragmentKey = GOOGLE_MAPS
                loadGoogleMapsFragment(args, addToBackStack)
            }
            FAVOURITES_FRAGMENT -> {
                currentFragmentKey = FAVOURITES_FRAGMENT
                loadFavouriteFragment(args, addToBackStack)
            }
            PAYMENT_FRAGMENT -> {
                currentFragmentKey = PAYMENT_FRAGMENT
                loadFavouriteFragment(args, addToBackStack)
            }
            CREDIT_CARD_FRAGMENT -> {
                currentFragmentKey = CREDIT_CARD_FRAGMENT
                loadFavouriteFragment(args, addToBackStack)
            }
            WALLET_FRAGMENT -> {
                currentFragmentKey = WALLET_FRAGMENT
                loadFavouriteFragment(args, addToBackStack)
            }
        }
    }

    /**
     * Load Auth Fragment
     *
     * @param args           Bundle
     * @param addToBackStack Boolean
     */
    fun loadAuthFragment(args: Bundle, addToBackStack: Boolean) {
        getFragment(args, addToBackStack, AuthActivity(), AUTH_FRAGMENT).commit()
    }

    /**
     * Load City Select Fragment
     *
     * @param args           Bundle
     * @param addToBackStack Boolean
     */
    fun loadCitySelectFragment(args: Bundle?, addToBackStack: Boolean) {
        getFragment(args!!, addToBackStack, CitySelectFragment(), CITY_SELECT_FRAGMENT).commit()
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
        //getFragment(args, addToBackStack, Mapbox(), GOOGLE_MAPS).commit()
    }

    /**
     * Load Menu Fragment
     *
     * @param args           Bundle
     * @param addToBackStack Boolean
     */
    fun loadMenuFragment(args: Bundle, addToBackStack: Boolean) {
        getFragment(args, addToBackStack, MenuFragment(), MENU_FRAGMENT).commit()
    }

    /**
     * Load Favourite Fragment
     *
     * @param args           Bundle
     * @param addToBackStack Boolean
     */
    open fun loadFavouriteFragment(args: Bundle, addToBackStack: Boolean) {
        getFragment(args, addToBackStack, FavouritesFragment(), FAVOURITES_FRAGMENT).commit()
    }

    /**
     * Load Payment Fragment
     *
     * @param args           Bundle
     * @param addToBackStack Boolean
     */
    fun loadPaymentFragment(args: Bundle?, addToBackStack: Boolean) {
        getFragment(args!!, addToBackStack, PaymentFragment(), PAYMENT_FRAGMENT).commit()
    }

    /**
     * Load Wallet Fragment
     *
     * @param args           Bundle
     * @param addToBackStack Boolean
     */
    fun loadWalletFragment(args: Bundle?, addToBackStack: Boolean) {
        getFragment(args!!, addToBackStack, WalletFragment(), WALLET_FRAGMENT).commit()
    }

    /**
     * Load Payment Fragment
     *
     * @param args           Bundle
     * @param addToBackStack Boolean
     */
    fun loadCreditCardFragment(args: Bundle?, addToBackStack: Boolean) {
        getFragment(args!!, addToBackStack, CreditCardFragment(), CREDIT_CARD_FRAGMENT).commit()
    }

    /**
     * Definition of fragments supported
     */
    @StringDef(CITY_SELECT_FRAGMENT, MENU_FRAGMENT, BIKE_LIST_FRAGMENT, LOGIN_FRAGMENT, GOOGLE_MAPS,
        FAVOURITES_FRAGMENT, AUTH_FRAGMENT, PAYMENT_FRAGMENT)
    @Retention(RetentionPolicy.SOURCE)
    annotation class MainFragments

    override fun onUserInteraction() {
        super.onUserInteraction()
        if (fragment != null) {
            fragment!!.onUserInteraction()
        }
    }

    val baseArguments: Bundle?
        get() = baseArguments


    @RequiresApi(api = VERSION_CODES.LOLLIPOP)
    fun setToolbarButton(drawable: Int) {
        mToolbarHome.setImageDrawable(this.resources.getDrawable(drawable, applicationContext.theme))
    }

    fun setTitle(title: String) {
        ToolbarTitle.text = title
    }

    fun loadWallet(){
        //loadWalletFragment(getArguments(), NOT_ADD_TO_BACKSTACK)
        closeNavDrawer()
    }

    fun loadTickets(){
        //loadTicketFragment( getArguments(), NOT_ADD_TO_BACKSTACK)
        closeNavDrawer()
    }


    fun loadJourney(){
        //loadJourneyFragment( getArguments(), NOT_ADD_TO_BACKSTACK)
        closeNavDrawer()
    }


    fun loadSearchStation(){
       //loadSearchStationsFragment(getArguments(), NOT_ADD_TO_BACKSTACK)
        closeNavDrawer()
    }


    fun loadFavourites(view: View){
        loadFavouriteFragment(getArguments(), NOT_ADD_TO_BACKSTACK)
        closeNavDrawer()
    }

    fun setStatusBarColor(statusColor: Int, navColor: Int, uiFlag: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            val decor = baseActivity.window.decorView
            val window = baseActivity.window
            decor.systemUiVisibility = uiFlag
            window.statusBarColor = resources.getColor(statusColor)
            window.navigationBarColor = resources.getColor(navColor)
        }
    }

    companion object {
        const val CITY_SELECT_FRAGMENT = "MAPS_FRAGMENT"
        const val MENU_FRAGMENT = "MENU_FRAGMENT"
        const val BIKE_LIST_FRAGMENT = "BIKE_LIST"
        const val LOGIN_FRAGMENT = "LOGIN"
        const val GOOGLE_MAPS = "GOOGLE_MAPS"
        const val FAVOURITES_FRAGMENT = "FAVOURITES_FRAGMENT"
        const val AUTH_FRAGMENT = "AUTH_FRAGMENT"
        const val PAYMENT_FRAGMENT = "AUTH_FRAGMENT"
        const val WALLET_FRAGMENT = "WALLET_FRAGMENT"
        const val CREDIT_CARD_FRAGMENT = "CREDIT_CARD_FRAGMENT"


        const val KEY_ACTIVITY_ID = "KEY_ACTIVITY_ID"
        const val KEY_FRAGMENT_ARGS = "KEY_FRAGMENT_ARGS"
        const val ADD_FRAGMENT_TO_BACKSTACK = true
        const val NOT_ADD_TO_BACKSTACK = false
        const val LAT = "LAT"
        const val LNG = "LNG"
        const val TITLE ="TITLE"
        const val CITY ="CITY"
        const val ADDRESS ="ADDRESS"
    }
    fun getArguments(): Bundle{
        bundle = Bundle()
        bundle.putString("KEY", "")
        return bundle
    }
}
