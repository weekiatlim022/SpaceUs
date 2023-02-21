package com.example.spaceus.ui.activities

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.example.spaceus.R
import com.example.spaceus.firestore.FirestoreClass
import com.example.spaceus.models.User
import com.example.spaceus.utils.Constants
import com.example.spaceus.utils.GlideLoader
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_user_profile.*
import java.io.IOException

class UserProfileActivity : BaseActivity(), View.OnClickListener {

    private lateinit var mUserDetails: User
    private var mSelectedImageFileUri: Uri? =null
    private var mUserProfileImageURL: String =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        if(intent.hasExtra(Constants.EXTRA_USER_DETAILS)){
            //Get the user details from instant as a ParcelableExtra.
            mUserDetails = intent.getParcelableExtra(Constants.EXTRA_USER_DETAILS)!!
        }


        et_usernameProfile.isEnabled=false
        et_usernameProfile.setText(mUserDetails.username)

        et_emailProfile.isEnabled=false
        et_emailProfile.setText(mUserDetails.email)

        GlideLoader(this@UserProfileActivity).loadUserPicture(mUserDetails.image.toUri(),iv_user_photo)

        iv_user_photo.setOnClickListener(this@UserProfileActivity)
        btn_save.setOnClickListener(this@UserProfileActivity)
        btn_cancel.setOnClickListener(this@UserProfileActivity)
    }


    override fun onClick(v: View?) {
        if(v != null){
            when (v.id){
                R.id.iv_user_photo ->{
                    if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                        //showErrorSnackBar("You already have the storage permission.",false)
                        Constants.showImageChooser(this)
                    }else{
                        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),Constants.READ_STORAGE_PERMISSION_CODE)
                    }
                }

                R.id.btn_save ->{

                    if(validateUserProfileDetails()){

                        showProgressDialog("Please Wait ...")

                        if(mSelectedImageFileUri!=null)
                            FirestoreClass().uploadImageToCloudStorage(this,mSelectedImageFileUri)
                        else{
                            updateUserProfileDetails()
                        }
                    }
                }

                R.id.btn_cancel ->{
                    val intent = Intent(this@UserProfileActivity, DashboardActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    private fun updateUserProfileDetails(){
        val userHashMap =HashMap<String,Any>()

        val mobileNumber = et_phoneProfile.text.toString().trim{it <= ' '}

        val gender = if(rb_male.isChecked){
            Constants.MALE
        }else{
            Constants.FEMALE
        }

        if(mUserProfileImageURL.isNotEmpty()){
            userHashMap[Constants.IMAGE] = mUserProfileImageURL
        }

        if(mobileNumber.isNotEmpty()){
            userHashMap[Constants.MOBILE]=mobileNumber
        }

        userHashMap[Constants.GENDER]= gender

        userHashMap[Constants.COMPLETE_PROFILE] = 1

        FirestoreClass().updateUserProfileData(this,userHashMap)
    }

    fun userProfileUpdateSuccess(){
        hideProgressDialog()
        Toast.makeText(this@UserProfileActivity,resources.getString(R.string.msg_profile_update_success),Toast.LENGTH_SHORT).show()
        startActivity(Intent(this@UserProfileActivity, DashboardActivity::class.java))
        finish()
    }

    override fun onRequestPermissionsResult(requestCode:Int,permissions:Array<out String>,grantResults:IntArray){
        super.onRequestPermissionsResult(requestCode,permissions,grantResults)

        if(requestCode == Constants.READ_STORAGE_PERMISSION_CODE){

            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //showErrorSnackBar("The storage permission is granted.",false)
                Constants.showImageChooser(this)
            }else{
                Toast.makeText(this,resources.getString(R.string.read_storage_permission_denied),Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK){
            if(requestCode == Constants.PICK_IMAGE_REQUEST_CODE){
                if(data !=null){
                    try{
                        //the uri of selected image from photo storage
                        mSelectedImageFileUri= data.data!!
                        GlideLoader(this).loadUserPicture(mSelectedImageFileUri!!,iv_user_photo)
                    }catch (e:IOException){
                        e.printStackTrace()
                        Toast.makeText(this@UserProfileActivity,resources.getString(R.string.image_selection_failed),Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }else if (resultCode == Activity.RESULT_CANCELED){
            Log.e("Request Cancelled","Image selection cancelled")
        }
    }

    private fun validateUserProfileDetails(): Boolean{
        return when{
            TextUtils.isEmpty(et_phoneProfile.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_phoneNum),true)
                false
            }
            else ->{
                true
            }
        }
    }

    fun imageUploadSuccess(imageURL:String){
        //hideProgressDialog()

        mUserProfileImageURL = imageURL
        updateUserProfileDetails()
    }
}