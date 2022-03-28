package com.example.lunchlist1

enum class RestaurantType {
    TAKEOUT,
    SITDOWN,
    DELIVERY
}

class Restaurant(val name: String, val address: String, val type: RestaurantType?, val notes: String) {

    override fun toString(): String {
        return "$name | $address | $type | $notes"
    }
}