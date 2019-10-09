package com.weiwei.chocotv.util

import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

fun String?.getTimeZoneFormatDate() : String {
    var simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    simpleDateFormat.also {
        it.timeZone = TimeZone.getTimeZone("GMT+8")
    }
    var date: Date? = simpleDateFormat.parse(this) ?: return ""
    date = Date(date!!.time)
    simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return simpleDateFormat.format(date)
}

fun Float.getRoundTo2DecimalPlaces() : Float {
    return BigDecimal(this.toDouble()).setScale(2, BigDecimal.ROUND_HALF_UP).toFloat()
}

fun Int.getTotalViewsFormat(): String {
    val df = DecimalFormat("#,###")
    return df.format(this)
}