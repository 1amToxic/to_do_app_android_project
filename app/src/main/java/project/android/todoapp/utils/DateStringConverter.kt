package project.android.todoapp.utils

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateStringConverter {
    @SuppressLint("SimpleDateFormat")
    fun stringToDate(s : String) : Date?{
        var date : Date? = null
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        try {
             date = format.parse(s)
        } catch (e: ParseException) {
            return null
        }
        return date
    }
    @SuppressLint("SimpleDateFormat")
    fun dateToString(date: Date) : String{
        var dateTime : String? = null
        val dateFormat =
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        try {
             dateTime = dateFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return dateTime!!
    }
}