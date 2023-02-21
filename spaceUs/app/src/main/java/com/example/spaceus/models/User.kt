package com.example.spaceus.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class User(
    val id: String = "",
    val username : String ="",
    val email: String = "",
    val mobile: String ="",
    val image: String = "",
    val gender:String ="",
    val profileCompleted: Int =0) : Parcelable


