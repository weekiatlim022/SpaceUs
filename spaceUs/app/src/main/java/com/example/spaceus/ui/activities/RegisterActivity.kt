package com.example.spaceus.ui.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import com.example.spaceus.R
import com.example.spaceus.firestore.FirestoreClass
import com.example.spaceus.models.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        textViewLogin.setOnClickListener{
            //val intent = Intent(this@RegisterActivity,LoginActivity::class.java)
            //startActivity(intent)
            onBackPressed()
        }

        btnRegister.setOnClickListener{
            registerUser()
        }

        textView_terms.setOnClickListener {
            val intent = Intent(this, TermsActivity::class.java)
            startActivity(intent)
        }

        textView_terms2.setOnClickListener {
            val intent = Intent(this, TermsActivity::class.java)
            startActivity(intent)
        }

        privacy_policy.setOnClickListener {
            val intent = Intent(this, PrivacyActivity::class.java)
            startActivity(intent)
        }

    }

    private fun validateRegisterDetails():Boolean{
        return when {

            TextUtils.isEmpty(et_username.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_username),true)
                false
            }

            TextUtils.isEmpty(et_register_email.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email),true)
                false
            }

            TextUtils.isEmpty(et_register_password.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password),true)
                false
            }

            TextUtils.isEmpty(et_confirm_password.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_confirmPassword),true)
                false
            }
            et_register_password.text.toString().trim{ it <= ' '} != et_confirm_password.text.toString()
                .trim{ it <= ' '} ->{
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password_and_confirm_password_mismatch),true)
                false
            }

            !term.isChecked -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_agree_terms_and_condition),true)
                false
            }

            else -> {
                //showErrorSnackBar(resources.getString(R.string.register_Successful),false)
                true
            }
        }
    }

    private fun registerUser(){

        //Check with validate function if the entries are valid or not.
        if(validateRegisterDetails()){

            showProgressDialog("Please Wait ...")
            val email: String = et_register_email.text.toString().trim{it <= ' '}
            val password: String = et_register_password.text.toString().trim{it <= ' '}

            //Create an instance and create a register a user with email and password.
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(
                    OnCompleteListener<AuthResult> { task ->

                        //If the registration is successfully done
                        if(task.isSuccessful){
                            //Firebase registered user
                            val firebaseUser:FirebaseUser = task.result!!.user!!
                            val user = User(
                                firebaseUser.uid,
                                et_username.text.toString().trim{ it <= ' ' },
                                et_register_email.text.toString().trim{ it <= ' ' }
                            )

                            FirestoreClass().registerUser(this@RegisterActivity,user)
                            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                            startActivity(intent)
                            //FirebaseAuth.getInstance().signOut()
                            //finish()
                        }else{
                            hideProgressDialog()
                            showErrorSnackBar(task.exception!!.message.toString(),true)
                        }
                    })
        }
    }

    fun userRegistrationSuccess(){

        //Hide the progress dialog
        hideProgressDialog()

        Toast.makeText(this@RegisterActivity,resources.getString(R.string.register_success),Toast.LENGTH_SHORT).show()


    }

}