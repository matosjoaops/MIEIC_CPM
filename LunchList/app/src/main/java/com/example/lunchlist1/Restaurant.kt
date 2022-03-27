package com.example.lunchlist1

enum class RestaurantType {
    TAKEOUT,
    SITDOWN,
    DELIVERY
}

class Restaurant(val name: String, val address: String, val type: RestaurantType?) {

    override fun toString(): String {
        return "$name | $address | $type"
    }
}