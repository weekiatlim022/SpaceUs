package com.example.spaceus.utils

import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.spaceus.R
import com.example.spaceus.ui.activities.UserProfileActivity
import com.example.spaceus.ui.fragments.SettingsFragment
import java.io.IOException

class GlideLoader(val context: UserProfileActivity) {
    fun loadUserPicture(imageURI: Uri, imageView: ImageView){
        try{
            Glide
                .with(context)
                .load(Uri.parse(imageURI.toString()))//URI of the image
                .centerCrop()//Scale type of the image
                .placeholder(R.drawable.ic_user_placeholder) //A default place holder if image is failed to load.
                .into(imageView)//the view in which the image will be loaded.
        }catch (e: IOException){
            e.printStackTrace()
        }
    }
}