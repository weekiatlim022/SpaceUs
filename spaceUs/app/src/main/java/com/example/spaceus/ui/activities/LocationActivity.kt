package com.example.spaceus.ui.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.spaceus.R
import com.example.spaceus.firestore.FirestoreClass
import com.example.spaceus.models.Location
import kotlinx.android.synthetic.main.activity_location.*
import kotlinx.android.synthetic.main.activity_register.*

class LocationActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        ivPrevious.setOnClickListener{
            val intent = Intent(this@LocationActivity, DashboardActivity::class.java)
            startActivity(intent)
        }

        CreateBtn.setOnClickListener{
            registerLocation()
        }
    }

    private fun validateLocationDetails():Boolean{
        return when {

            TextUtils.isEmpty(et_locName.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_locName),true)
                false
            }

            TextUtils.isEmpty(et_locAddress.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_locAddress),true)
                false
            }

            TextUtils.isEmpty(et_phoneNumber.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_phoneNum),true)
                false
            }

            TextUtils.isEmpty(et_hours.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_hours),true)
                false
            }
            TextUtils.isEmpty(et_amenities.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_amenities),true)
                false
            }
            TextUtils.isEmpty(et_transport.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_transport),true)
                false
            }
            TextUtils.isEmpty(et_desc.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_desc),true)
                false
            }

            else -> {
                //showErrorSnackBar(resources.getString(R.string.register_Successful),false)
                true
            }
        }
    }

    private fun registerLocation() {
        if(validateLocationDetails()){
            showProgressDialog("Please Wait ...")
            val location = Location(
                et_locName.text.toString().trim{ it <= ' ' },
                et_locAddress.text.toString().trim{ it <= ' ' },
                et_phoneNumber.text.toString().trim{ it <= ' ' },
                et_hours.text.toString().trim{ it <= ' ' },
                et_amenities.text.toString().trim{ it <= ' ' },
                et_transport.text.toString().trim{ it <= ' ' },
                et_desc.text.toString().trim{ it <= ' ' },
            )
            FirestoreClass().addLocation(this@LocationActivity,location)
            val intent = Intent(this@LocationActivity, DashboardActivity::class.java)
            startActivity(intent)
            showErrorSnackBar(resources.getString(R.string.register_Successful),false)

        }
    }


    fun locRegistrationSuccess(){

        //Hide the progress dialog
        hideProgressDialog()

        Toast.makeText(this@LocationActivity,resources.getString(R.string.add_success), Toast.LENGTH_SHORT).show()
    }
}