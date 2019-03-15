package com.bike.rent.kelly.ui.login

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bike.rent.kelly.R
import com.bike.rent.kelly.data.local.PreferencesHelper
import com.bike.rent.kelly.ui.base.BaseFragment
import com.bike.rent.kelly.utils.SnackBars
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.GoogleApiClient.Builder
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseReference
import com.mapbox.core.utils.TextUtils
import kotlinx.android.synthetic.main.login_fragment.*
import timber.log.Timber

class LoginFragment: BaseFragment(){

    private val TAG = "LOGINFRAGMENT"

    var mDatabase: DatabaseReference? = null
    lateinit var mView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
            baseActivity.signIn()
        }

        mLoginBtn.setOnClickListener {
            var email = mLoginEmail.text.toString().trim()
            var password = mLoginPassword.text.toString().trim()
            if (!TextUtils.isEmpty(email) || !android.text.TextUtils.isEmpty(password)) {
                loginFirebaseUser(email, password)

            }else{
                SnackBars.centerSnackbar(it, "Login failed, Please make sure all fields are filled in and are correct")
            }
        }
    }

    private fun loginFirebaseUser(email: String, password: String) {
        baseActivity.mAuth!!.signInWithEmailAndPassword(email, password)
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