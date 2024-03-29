package com.example.lunchlist1

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.*
import android.widget.*

import android.widget.ArrayAdapter
import com.google.android.material.tabs.TabLayout


class MainActivity : AppCompatActivity(), TabLayout.OnTabSelectedListener {
    var rests = arrayListOf<Restaurant>()
    val tabs by lazy { findViewById<TabLayout>(R.id.tabs) }
    val tab1 by lazy {findViewById<ListView>(R.id.rests_list)}
    val tab2 by lazy {findViewById<LinearLayout>(R.id.form_layout)}
    val listTab by lazy {tabs.newTab().setText("List")}
    val detailsTab by lazy {tabs.newTab().setText("Details")}
    val adapter by lazy { RestaurantAdapter() }
    var current: Restaurant? = null
    val dbHelper by lazy { RestaurantHelper(this) }
    var currentId: Long = 0

    override fun onDestroy() {
        super.onDestroy()
        dbHelper.close()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable("current", current)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        current = savedInstanceState.getSerializable("current") as Restaurant
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.toast){
            val message = current?.notes ?: "No restaurant selected"
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        MenuInflater(this).inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

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
                else -> RestaurantType.DELIVERY
            }

            val notes = findViewById<EditText>(R.id.edit_notes).text.toString()

            val restaurant = Restaurant(name, address, type, notes)

            adapter.add(restaurant)


            if (current == null) currentId = dbHelper.insert(name, address, type, notes)
            else {
                dbHelper.update(currentId.toString(), name, address, type, notes)
                //finish()
            }

            current = restaurant



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
            current = clickedRestaurant
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
