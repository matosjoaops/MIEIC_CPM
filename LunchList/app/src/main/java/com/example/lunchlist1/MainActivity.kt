package com.example.lunchlist1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.view.menu.ListMenuItemView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setIcon(R.drawable.rest_icon)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        setContentView(R.layout.activity_main)

        val rests = arrayListOf<Restaurant>();

        val adapter by lazy { ArrayAdapter(this, android.R.layout.simple_list_item_1, rests) }
        val list = findViewById<ListView>(R.id.rests_list)
        list.adapter = adapter

        findViewById<Button>(R.id.save_btn).setOnClickListener{
            val name = findViewById<EditText>(R.id.edit_name).text.toString()
            val address = findViewById<EditText>(R.id.edit_address).text.toString()
            val selectedTypeId = findViewById<RadioGroup>(R.id.edit_type).checkedRadioButtonId
            val selectedButton = findViewById<RadioButton>(selectedTypeId)

            val type = when (selectedButton.text) {
                "Take-Out" -> RestaurantType.TAKEOUT
                "Sit-Down" -> RestaurantType.SITDOWN
                "Delivery" -> RestaurantType.DELIVERY
                else -> null
            }

            val restaurant = Restaurant(name, address, type)

            adapter.add(restaurant)

            //println(restaurant.name)
            //println(restaurant.address)
            //println(restaurant.type)
        }

        list.setOnItemClickListener { parent, view, position, id ->
            val clickedRestaurant = adapter.getItem(position)

            findViewById<EditText>(R.id.edit_name).setText(clickedRestaurant?.name)
            findViewById<EditText>(R.id.edit_address).setText(clickedRestaurant?.address)

            val selectedRadioButtonId = when (clickedRestaurant?.type) {
                RestaurantType.TAKEOUT -> R.id.edit_type_takeout
                RestaurantType.SITDOWN -> R.id.edit_type_sitdown
                RestaurantType.DELIVERY -> R.id.edit_type_delivery
                else -> R.id.edit_type_takeout
            }

            findViewById<RadioButton>(selectedRadioButtonId).isChecked = true
        }

    }
}
