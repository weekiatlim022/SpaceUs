package com.example.spaceus.ui.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import com.example.spaceus.R
import com.example.spaceus.firestore.FirestoreClass
import com.example.spaceus.models.User
import com.example.spaceus.ui.fragments.HomeFragment
import com.example.spaceus.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class LoginActivity : BaseActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        ForgotPassword.setOnClickListener(this)
        btnLogin.setOnClickListener(this)
        textViewCreate.setOnClickListener(this)

    }

    fun userLoggedInSuccess(user: User){
        hideProgressDialog()

        val intent = Intent(this@LoginActivity, UserProfileActivity::class.java)
        intent.putExtra(Constants.EXTRA_USER_DETAILS,user)
        startActivity(intent)
        finish()
    }


    override fun onClick(view:View?){
        if(view != null){
            when (view.id){

                R.id.ForgotPassword -> {
                    val intent = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
                    startActivity(intent)
                }

                R.id.btnLogin -> {
                    logInRegisteredUser()
                }

                R.id.textViewCreate -> {
                    val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    private fun validateLoginDetails():Boolean {

        return when {

            TextUtils.isEmpty(et_login_email.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email),true)
                false
            }

            TextUtils.isEmpty(et_login_password.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password),true)
                false
            }

            else -> {
                true
            }
        }
    }

    private fun logInRegisteredUser(){

        if(validateLoginDetails()){

            //show the progress dialog.
            showProgressDialog("Please Wait ...")

            //Get the text from editText and trim the space
            val email = et_login_email.text.toString().trim{it <= ' '}
            val password = et_login_password.text.toString().trim{it <= ' '}

            //Log-In using FirebaseAuth
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener{ task ->

                    if(task.isSuccessful){
                        FirestoreClass().getUserDetails(this@LoginActivity)
                    }else{
                        hideProgressDialog()
                        showErrorSnackBar(task.exception!!.message.toString(),true)
                    }
                }
        }
    }

}