package ru.skillbranch.devintensive.extentions

fun String.truncate(limit:Int=16):String{
    val current:String? = this
    if (current == null){
        return ""
    }
    else{
        if (current.length > limit && limit > 0)
        {
            return "${current.dropLast(current.length - limit).trimEnd()}..."
        }
    }
    return "${current}"
}