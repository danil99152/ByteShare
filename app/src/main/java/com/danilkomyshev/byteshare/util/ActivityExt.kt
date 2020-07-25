package com.danilkomyshev.byteshare.util

import android.app.Activity
import android.widget.Toast


fun Activity.toast(messageRes: Int, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, getString(messageRes), length).show()
}

fun Activity.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, length).show()
}