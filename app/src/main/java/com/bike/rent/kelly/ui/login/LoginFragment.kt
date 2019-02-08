package com.bike.rent.kelly.ui.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bike.rent.kelly.R
import com.bike.rent.kelly.ui.base.BaseFragment
import com.bike.rent.kelly.utils.SnackBars
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.mapbox.core.utils.TextUtils
import kotlinx.android.synthetic.main.login_fragment.*

class LoginFragment: BaseFragment(){

    lateinit var mView: View
    var mAuth: FirebaseAuth? = null
    var mDatabase: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.login_fragment, container, false)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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