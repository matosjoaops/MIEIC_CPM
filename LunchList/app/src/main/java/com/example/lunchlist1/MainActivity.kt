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
import com.google.android.material.tabs.TabLayout


class MainActivity : AppCompatActivity(), TabLayout.OnTabSelectedListener {
    val rests = arrayListOf<Restaurant>()
    val tabs by lazy { findViewById<TabLayout>(R.id.tabs) }
    val tab1 by lazy {findViewById<ListView>(R.id.rests_list)}
    val tab2 by lazy {findViewById<LinearLayout>(R.id.form_layout)}
    val listTab by lazy {tabs.newTab().setText("List")}
    val detailsTab by lazy {tabs.newTab().setText("Details")}
    val adapter by lazy { RestaurantAdapter() }

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

        //val list = findViewById<ListView>(R.id.rests_list)

        tabs.addTab(listTab)
        tabs.addTab(detailsTab)
        tabs.addOnTabSelectedListener(this)

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
            listTab.select()
        }


        tab1.adapter = adapter
        tab1.setOnItemClickListener { parent, view, position, id ->
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
            detailsTab.select()
        }
        detailsTab.select()
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        when (tab?.position) {
            0 -> tab1.visibility = View.VISIBLE
            1 -> tab2.visibility = View.VISIBLE
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
        when (tab?.position) {
            0 -> tab1.visibility = View.INVISIBLE
            1 -> tab2.visibility = View.INVISIBLE
        }
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
        TODO("Not yet implemented")
    }
}
