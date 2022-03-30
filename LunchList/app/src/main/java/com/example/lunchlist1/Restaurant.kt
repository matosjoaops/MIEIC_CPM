package com.example.lunchlist1

import java.io.Serializable

enum class RestaurantType {
    TAKEOUT,
    SITDOWN,
    DELIVERY
}

class Restaurant(val name: String, val address: String, val type: RestaurantType?, val notes: String) : Serializable {

    override fun toString(): String {
        return "$name | $address | $type | $notes"
    }
}