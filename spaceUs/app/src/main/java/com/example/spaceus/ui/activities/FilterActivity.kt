package com.example.spaceus.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.spaceus.R
import kotlinx.android.synthetic.main.activity_filter.*

class FilterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)

        exit_filter.setOnClickListener {
            val intent = Intent(this@FilterActivity, DashboardActivity::class.java)
            startActivity(intent)
        }
    }
}