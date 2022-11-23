package com.taichung.bryant.kotlin_mvp.utils

import android.content.Context
import android.widget.Toast

fun showToast(context: Context, str: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, str, length).show()
}