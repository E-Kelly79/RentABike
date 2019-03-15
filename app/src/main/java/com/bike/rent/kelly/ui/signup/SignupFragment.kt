package com.bike.rent.kelly.ui.signup

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bike.rent.kelly.R
import com.bike.rent.kelly.ui.base.BaseFragment
import com.bike.rent.kelly.utils.ProgressDialog
import com.bike.rent.kelly.utils.SnackBars
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.signup_fragment.*

class SignupFragment: BaseFragment() {
    lateinit var mView: View
    var mAuth: FirebaseAuth? = null
    var mDatabase:DatabaseReference? = null
    lateinit var mDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.signup_fragment, container, false)
        mDialog = ProgressDialog.progressDialog(context!!)
        return mView

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mSignupBtn.setOnClickListener {
            mDialog.show()
            var email = mEmailSignup.text.toString().trim()
            var password = mPasswordSignup.text.toString().trim()
            var name = mFullName.text.toString().trim()

            if(!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password) || !TextUtils.isEmpty(name)){
                createAccount(email ,password, name)
            }else{
                SnackBars.centerSnackbar(it, "Please make sure all fields are filled in")
            }

        }
    }

    fun createAccount(email:String, password:String, username:String){
        mAuth!!.createUserWithEmailAndPassword(email, password).addOnCompleteListener {task: Task<AuthResult> ->
                //Set user object for firebase
            if (task.isSuccessful){
                var currentUserId = mAuth!!.currentUser
                var userId = currentUserId!!.uid

                mDatabase = FirebaseDatabase.getInstance().reference.child("Users").child(userId)
                var userObject = HashMap<String, String>()
                userObject["username"] = username
                userObject["name"] = "Hello there"
                userObject["image"] = "default"
                mDatabase!!.setValue(userObject).addOnCompleteListener {task: Task<Void> ->
                    if (task.isSuccessful){
                        mDialog.dismiss()
                        SnackBars.centerSnackbar(view!!, "User created")
                        mFullName.text.clear()
                        mEmailSignup.text.clear()
                        mPasswordSignup.text.clear()
                    }else{
                        SnackBars.centerSnackbar(view!!, "Error Something went wrong when trying to create user")
                    }
                }
            }

        }
    }
}