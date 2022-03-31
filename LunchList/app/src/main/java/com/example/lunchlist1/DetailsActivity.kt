package com.example.lunchlist1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class DetailsActivity : AppCompatActivity() {
    val dbHelper by lazy { RestaurantHelper(this) }

    override fun onDestroy() {
        super.onDestroy()
        dbHelper.close()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_details)
    }
}