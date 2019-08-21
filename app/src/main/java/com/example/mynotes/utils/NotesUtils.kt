package com.example.mynotes.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*


fun getCurrenDate(): String{
    val date = Date()
    val formatter = SimpleDateFormat("yyyy-MMM-dd 'at' HH:mm", Locale.getDefault())

    return formatter.format(date)

}

fun Context.toast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()


fun hideSoftKeyboard(activity: Activity, view: View){
    val inputMethodManager: InputMethodManager  = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)

}
