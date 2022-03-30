package com.example.lunchlist1

import android.app.Application

class LunchApp: Application() {
    val rests: ArrayList<Restaurant> = ArrayList()
    var current: Restaurant? = null
    var adapter: MainActivity.RestaurantAdapter? = null
}