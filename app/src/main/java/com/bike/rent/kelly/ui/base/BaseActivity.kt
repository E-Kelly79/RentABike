package com.bike.rent.kelly.ui.base

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.annotation.StringDef
import android.support.v4.app.FragmentTransaction
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

import com.bike.rent.kelly.R
import com.bike.rent.kelly.data.local.PreferencesHelper
import com.bike.rent.kelly.model.tickets.Ticket
import com.bike.rent.kelly.ui.wallet.WalletFragment
import com.bike.rent.kelly.ui.auth.AuthActivity
import com.bike.rent.kelly.ui.bike.BikeList
import com.bike.rent.kelly.ui.card_payment.CardPaymentFragment
import com.bike.rent.kelly.ui.city_select.CitySelectFragment
import com.bike.rent.kelly.ui.favorites.FavouritesFragment
import com.bike.rent.kelly.ui.menu.MenuFragment
import com.bike.rent.kelly.ui.tickets.TicketFragment
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import kotlinx.android.synthetic.main.base_layout.*
import kotlinx.android.synthetic.main.toolbar.ToolbarTitle
import kotlinx.android.synthetic.main.toolbar.mToolbarHome
import net.danlew.android.joda.JodaTimeAndroid
import timber.log.Timber

open class BaseActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener{


    private val RC_SIGN_IN: Int = 1234
    private var mGoogleApiClient: GoogleApiClient? = null
    private var mGoogleSignInClient: GoogleSignInClient? = null
    var mAuth: FirebaseAuth? = null
    lateinit var bundle: Bundle
    var mActivityId: Long = 0
    var currentUser: FirebaseUser? = null

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


    val context: Context get() = applicationContext


    val baseActivity: BaseActivity get() = this

    @RequiresApi(VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.base_layout)
        JodaTimeAndroid.init(this)
        mAuth = FirebaseAuth.getInstance()
        val gso: GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_token_google_signin))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        mGoogleApiClient = GoogleApiClient.Builder(context!!)
            .enableAutoManage(baseActivity, this)
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build()

        bundle = Bundle()
        initNavDrawer()

    }

    override fun onStart() {
        super.onStart()
        //see if user is logged in
        currentUser = mAuth!!.currentUser
    }

    override fun onPause() {
        super.onPause()
        if (currentUser != null) {
            var prefs = PreferencesHelper(context!!)

            prefs.setPrefString("Google_Email", currentUser!!.email!!)
            prefs.setPrefString("Google_Photo", currentUser!!.photoUrl!!.toString())
            prefs.setPrefString("Google_Name", currentUser!!.displayName!!)
        }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN){
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                var account = task.getResult(ApiException::class.java)
                firebaseGoogleSignIn(account!!)
            }catch (e: ApiException){
                Log.w("BaseActivirty", "Google sign in failed $e")
            }
        }
    }

    private fun firebaseGoogleSignIn(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mAuth!!.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("BaseActivity", "signInWithCredential:success")
                    currentUser = mAuth!!.currentUser
                    loadCitySelectFragment(getArguments(), false)

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("BaseActivity", "signInWithCredential:failure", task.exception)
                    currentUser = null
                }
            }
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        Timber.d("onConnectionFailed: " + connectionResult)
    }

    fun signIn(){
        var signInIntent = mGoogleSignInClient!!.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }


    fun signOut() {
        // sign out Firebase
        mAuth!!.signOut()

        // sign out Google
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback { currentUser = null }
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

    fun closeNavDrawer() {
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
            TICKET_FRAGMENT -> {
                currentFragmentKey = TICKET_FRAGMENT
                loadTicketFragment(args, addToBackStack)
            }
            WALLET_FRAGMENT -> {
                currentFragmentKey = WALLET_FRAGMENT
                loadWalletFragment(args, addToBackStack)
            }
            CARD_PAYMENT_FRAGMENT -> {
                currentFragmentKey = CARD_PAYMENT_FRAGMENT
                loadCardPaymentFragment(args, addToBackStack)
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
        getFragment(args, addToBackStack, MenuFragment(), MENU_FRAGMENT
        ).commit()
    }

    /**
     * Load Ticket Fragment
     *
     * @param args           Bundle
     * @param addToBackStack Boolean
     */
    fun loadTicketFragment(args: Bundle, addToBackStack: Boolean) {
        getFragment(args, addToBackStack, TicketFragment(), MENU_FRAGMENT
        ).commit()
    }

    /**
     * Load Wallet Fragment
     *
     * @param args           Bundle
     * @param addToBackStack Boolean
     */
    fun loadWalletFragment(args: Bundle, addToBackStack: Boolean) {
        getFragment(args, addToBackStack, WalletFragment(), MENU_FRAGMENT
        ).commit()
    }

    /**
     * Load Card Payment Fragment Fragment
     *
     * @param args           Bundle
     * @param addToBackStack Boolean
     */
    fun loadCardPaymentFragment(args: Bundle, addToBackStack: Boolean) {
        getFragment(args, addToBackStack, CardPaymentFragment(), MENU_FRAGMENT
        ).commit()
    }

    /**
     * Load Favourite Fragment
     *
     * @param args           Bundle
     * @param addToBackStack Boolean
     */
    open fun loadFavouriteFragment(args: Bundle, addToBackStack: Boolean) {
        getFragment(args, addToBackStack, FavouritesFragment(), FAVOURITES_FRAGMENT
        ).commit()
    }

    /**
     * Definition of fragments supported
     */
    @StringDef(CITY_SELECT_FRAGMENT, MENU_FRAGMENT, BIKE_LIST_FRAGMENT, LOGIN_FRAGMENT, GOOGLE_MAPS,
        FAVOURITES_FRAGMENT, AUTH_FRAGMENT, TICKET_FRAGMENT, CARD_PAYMENT_FRAGMENT, WALLET_FRAGMENT)
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

    fun loadWallet(view: View){
        loadWalletFragment(getArguments(), NOT_ADD_TO_BACKSTACK)
        closeNavDrawer()
    }

    fun loadBuyTickets(view: View){
        loadTicketFragment(getArguments(), NOT_ADD_TO_BACKSTACK)
        closeNavDrawer()
    }


    fun loadSearchStation(view: View){
        loadCitySelectFragment(getArguments(), NOT_ADD_TO_BACKSTACK)
        closeNavDrawer()
    }

    fun logout(view: View){
        signOut()
        Toast.makeText(this, "Loggedout", Toast.LENGTH_LONG).show()
        loadAuthFragment(getArguments(), NOT_ADD_TO_BACKSTACK)
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
        const val TICKET_FRAGMENT = "TICKET_FRAGMENT"
        const val CARD_PAYMENT_FRAGMENT = "CARD_PAYMENT_FRAGMENT"
        const val WALLET_FRAGMENT = "WALLET_FRAGMENT"

        const val KEY_ACTIVITY_ID = "KEY_ACTIVITY_ID"
        const val KEY_FRAGMENT_ARGS = "KEY_FRAGMENT_ARGS"
        const val ADD_FRAGMENT_TO_BACKSTACK = true
        const val NOT_ADD_TO_BACKSTACK = false
        const val LAT = "LAT"
        const val LNG = "LNG"
        const val TITLE ="TITLE"
        const val CITY ="CITY"
        const val ADDRESS ="ADDRESS"
        var CREDIT_AMOUNT = 500.00f
        val WALLET_LIST: ArrayList<Ticket> = ArrayList()
    }

    fun getArguments(): Bundle{
        bundle = Bundle()
        bundle.putString("KEY", "")
        return bundle
    }
}

