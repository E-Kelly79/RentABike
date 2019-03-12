package com.bike.rent.kelly.ui.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bike.rent.kelly.R
import com.bike.rent.kelly.ui.base.BaseFragment
import com.bike.rent.kelly.utils.SnackBars
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.GoogleApiClient.Builder
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.mapbox.core.utils.TextUtils
import kotlinx.android.synthetic.main.login_fragment.*
import timber.log.Timber

class LoginFragment: BaseFragment(), GoogleApiClient.OnConnectionFailedListener{

    lateinit var mView: View
    var mAuth: FirebaseAuth? = null
    var mDatabase: DatabaseReference? = null

    val RC_SIGN_IN: Int = 9001
    private var mGoogleApiClient: GoogleApiClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        //updateUi(false)
        val gso: GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleApiClient = GoogleApiClient.Builder(context!!)
            .enableAutoManage(baseActivity, this)
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build()

        //mGoogleSigninClient = GoogleSignIn.getClient(context!!, mGoogleSignin)
    }

    override fun onStart() {
        super.onStart()


    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        Timber.d("onConnectionFailed: " + connectionResult)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.login_fragment, container, false)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        baseActivity.setStatusBarColor(R.color.color_gradent_start, R.color.color_black,
            View.SYSTEM_UI_LAYOUT_FLAGS
        )

        googleSigninBtn.setOnClickListener {
            var signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }

        mLoginBtn.setOnClickListener {
            var email = mLoginEmail.text.toString().trim()
            var password = mLoginPassword.text.toString().trim()
            if (!TextUtils.isEmpty(email) || !android.text.TextUtils.isEmpty(password)) {
                loginUser(email, password)

            }else{
                SnackBars.centerSnackbar(it, "Login failed, Please make sure all fields are filled in and are correct")
            }
        }
    }


    private fun signOut(){

    }

    private fun handleResult(result: GoogleSignInResult){

    }


//    private fun updateUi(isLoggedIn: Boolean){
//        if(isLoggedIn){
//            googleSigninLayout.visibility = View.VISIBLE
//        }else{
//            googleSigninLayout.visibility = View.GONE
//        }
//
//    }

    private fun loginUser(email: String, password: String) {
        mAuth!!.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task: Task<AuthResult> ->
                if (task.isSuccessful){
                    Log.i("LOGIN", "Logged in")
                    val args = Bundle()
                    baseActivity.loadCitySelectFragment(args, false)
                }else{
                    SnackBars.centerSnackbar(mView, "Email or Password are incorrect")
                }
            }
    }
}