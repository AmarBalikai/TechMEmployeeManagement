package com.techm.techMEmployeeManagement.utils

import android.content.Context
import android.widget.Toast

/**
 * This class for creating a extension function
 * */
fun Context.toast(message: CharSequence) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()