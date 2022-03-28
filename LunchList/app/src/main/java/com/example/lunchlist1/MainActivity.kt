package com.example.lunchlist1

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import android.view.ViewGroup
import android.widget.*

import android.widget.ArrayAdapter


class MainActivity : AppCompatActivity() {
    val rests = arrayListOf<Restaurant>()

    inner class RestaurantAdapter: ArrayAdapter<Restaurant>(this@MainActivity, R.layout.row, rests) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val row = convertView ?: layoutInflater.inflate(R.layout.row, parent, false)
            val r = rests[position]
            row.findViewById<TextView>(R.id.rest_name).text = r.name
            row.findViewById<TextView>(R.id.rest_address).text = r.address

            val symbol = row.findViewById<ImageView>(R.id.type_image)
            when (r.type) {
                RestaurantType.SITDOWN -> symbol.setImageResource(R.drawable.ball_red)
                RestaurantType.TAKEOUT -> symbol.setImageResource(R.drawable.ball_yellow)
                RestaurantType.DELIVERY -> symbol.setImageResource(R.drawable.ball_green)
                else -> symbol.setImageResource(R.drawable.ball_green)
            }

            return row
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setIcon(R.drawable.rest_icon)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        setContentView(R.layout.activity_main)

        //val adapter by lazy { ArrayAdapter(this, android.R.layout.simple_list_item_1, rests) }
        val list = findViewById<ListView>(R.id.rests_list)
        val adapter = RestaurantAdapter()
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
