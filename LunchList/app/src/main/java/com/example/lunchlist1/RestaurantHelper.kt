package com.example.lunchlist1

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

const val DATABASE_NAME = "lunchlist.db"
const val SCHEMA_VERSION = 1

class RestaurantHelper(val ctx: Context) : SQLiteOpenHelper(ctx, DATABASE_NAME, null, SCHEMA_VERSION) {
    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL("CREATE TABLE Restaurants(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "address TEXT," +
                "type TEXT," +
                "notes TEXT)")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun insert(name: String, address: String, type: RestaurantType, notes: String): Long {
        val cv = ContentValues()
        cv.put("name", name)
        cv.put("address", address)
        cv.put("type", when (type){
            RestaurantType.DELIVERY -> "delivery"
            RestaurantType.SITDOWN -> "sitdown"
            else -> "takeout"
        })
        cv.put("notes", notes)
        return writableDatabase.insert("Restaurants", "name", cv)
    }

    fun update(id: String, name: String, address: String, type: RestaurantType, notes: String){
        val cv = ContentValues()
        val args = arrayOf(id)
        cv.put("name", name)
        cv.put("address", address)
        cv.put("type", when (type){
           RestaurantType.DELIVERY -> "delivery"
           RestaurantType.SITDOWN -> "sitdown"
           else -> "takeout"
        })
        cv.put("notes", notes)
        writableDatabase.update("Restaurants", cv, "_id=?", args)
    }

}