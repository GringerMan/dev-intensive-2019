package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*

const val SECONDS = 1000L
const val MINUTE = 60 * SECONDS
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.format(pattern:String="HH:mm:ss dd.MM.yy"):String{
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

//Date().add(-2, TimeUnits.HOUR).humanizeDiff() //2 часа назад
//Date().add(-5, TimeUnits.DAY).humanizeDiff() //5 дней назад
//Date().add(2, TimeUnits.MINUTE).humanizeDiff() //через 2 минуты
//Date().add(7, TimeUnits.DAY).humanizeDiff() //через 7 дней
//Date().add(-400, TimeUnits.DAY).humanizeDiff() //более года назад
//Date().add(400, TimeUnits.DAY).humanizeDiff() //более чем через год
fun Date.add(value:Int, units: TimeUnit = TimeUnit.SECOND) : Date{
    var time = this.time

    time += when(units)
    {
        TimeUnit.SECOND -> value * SECONDS
        TimeUnit.MINUTE -> value * MINUTE
        TimeUnit.HOUR -> value * HOUR
        TimeUnit.DAY -> value * DAY
    }

    this.time = time

    return this
}


//Интервалы
//0с - 1с "только что"
//1с - 45с "несколько секунд назад"
//45с - 75с "минуту назад"
//75с - 45мин "N минут назад"
//45мин - 75мин "час назад"
//75мин 22ч "N часов назад"
//22ч - 26ч "день назад"
//26ч - 360д "N дней назад"
//>360д "более года назад"
fun Date.humanizeDiff(date: Date = Date()): String{

    val time:Long = date.time - this.time

    val deltaSec:Long = time  /  1000
    val deltaMin:Long = deltaSec / 60
    val deltaHour:Long = deltaMin / 60
    val deltaDay:Long = deltaHour / 24

    if (deltaDay > 0)
    {
        when(deltaDay)
        {
            in 1..360 -> return "${TimeUnit.DAY.plural(deltaDay.toInt())} назад"
            else -> return "более года назад"
        }
    }
    if (deltaHour > 0)
    {
        when(deltaHour)
        {
            in 1..22 -> return "${TimeUnit.HOUR.plural(deltaHour.toInt())} назад"
            else -> return "день назад"
        }
    }
    if (deltaMin > 0)
    {
        when(deltaMin)
        {
            in 1..24 -> return "${TimeUnit.MINUTE.plural(deltaMin.toInt())} назад"
            else -> return "час назад"
        }
    }
    if (deltaSec >= 0)
    {
        when(deltaSec)
        {
            in 0..1 -> return "только что"
            in 1..45  -> return "несколько секунд назад"
            else -> return "минуту назад"
        }
    }
    return ""
}

enum class TimeUnit
{
    SECOND
    {
        override fun plural(value: Int) :String{
            when (pluralIndex(value))
            {
                1 -> return "$value секунд"
                2 -> return "$value секунды"
                else -> return "$value секунду"
            }

        }
    },
    MINUTE
    {
        override fun plural(value: Int) :String{
            when (pluralIndex(value))
            {
                1 -> return "$value минут"
                2 -> return "$value минуты"
                else -> return "$value минуту"
            }

        }
    },
    HOUR{
        override fun plural(value: Int) :String{
            when (pluralIndex(value))
            {
                1 -> return "$value часов"
                2 -> return "$value часа"
                else -> return "$value час"
            }
        }
    },
    DAY{
        override fun plural(value: Int) :String{
            when (pluralIndex(value))
            {
                1 -> return "$value дней"
                2 -> return "$value дня"
                else -> return "$value день"
            }
        }
    };

    abstract fun plural(value1: Int): String
    fun pluralIndex(value:Int):Int{
        when (value % 10)
        {
            1 -> {if (value % 100 == 11) return 1 else return 3}
            in 2..4 -> {if (value % 100 < 10 || value % 100 > 20) return 2 else return 1}
            else -> return 1
        }
    }
}