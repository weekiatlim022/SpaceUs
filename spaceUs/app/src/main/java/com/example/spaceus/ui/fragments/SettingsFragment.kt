package com.example.spaceus.ui.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.spaceus.databinding.FragmentSettingsBinding
import com.example.spaceus.models.User
import com.example.spaceus.ui.activities.*
import com.example.spaceus.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_settings.view.*

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private lateinit var mUserDetails: User
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //notificationsViewModel =ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val sharedPreferences = this.requireActivity().getSharedPreferences(Constants.MYSHOPPAL_PREFERENCES, Context.MODE_PRIVATE)

        val username = sharedPreferences.getString(Constants.LOGGED_IN_USERNAME,"")!!
        val email = sharedPreferences.getString(Constants.EMAIL,"")!!
        root.tv_username.text = "$username"
        root.tv_email.text = "$email"


        root.btn_logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            Toast.makeText(activity, "Logout Successfully.", Toast.LENGTH_SHORT).show()
        }



        root.iv_edit.setOnClickListener{
            //val intent = Intent(requireContext(), UserProfileActivity::class.java)
            //mUserDetails = intent.getParcelableExtra(Constants.EXTRA_USER_DETAILS)!!
            //intent.putExtra(Constants.EXTRA_USER_DETAILS,mUserDetails)
            //startActivity(intent)
        }

        root.btn_addLoc.setOnClickListener{
            val intent = Intent(requireContext(), LocationActivity::class.java)
            startActivity(intent)
        }

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}