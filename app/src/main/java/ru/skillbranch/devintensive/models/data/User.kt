package ru.skillbranch.devintensive.models.data

import ru.skillbranch.devintensive.extensions.humanizeDiff
import java.util.*
import ru.skillbranch.devintensive.utils.Utils

data class User (
    val id:String,
    var firstName:String?,
    var lastName:String?,
    var avatar:String?,
    var rating:Int = 0,
    var respect:Int = 0,
    val lastVisit: Date? = Date(),
    val isOnline:Boolean = false
){

    fun toUserItem() :  UserItem{
        val lastActivity = when{
            lastVisit == null -> "Еще ни разу не заходил"
            isOnline -> "online"
            else -> "Последний раз был ${lastVisit.humanizeDiff()}"
        }

        return UserItem(
            id,
            "${firstName.orEmpty()} ${lastName.orEmpty()}",
            Utils.toInitials(firstName, lastName),
            avatar,
            lastActivity,
            false,
            isOnline
        )
    }


    constructor(id: String, firstName: String?, lastName: String?) : this(
        id = id,
        firstName = firstName,
        lastName = lastName,
        avatar = null
    )

    companion object Factory {

        private var lastId : Int = -1

        fun makeUser(fullname:String?) : User {

            lastId++

            val (firstName, lastName) = Utils.parseFullName(fullname)

            return User(
                id = "$lastId",
                firstName = firstName,
                lastName = lastName
            )
        }
    }

    data class Builder(
        var id:String,
        var firstName:String?,
        var lastName:String?,
        var avatar:String?,
        var rating:Int = 0,
        var respect:Int = 0,
        var lastVisit: Date? = Date(),
        var isOnline:Boolean = false){

        constructor() : this("0", null, null, null, 0, 0, null)

        fun id(value: String) = apply { this.id = value }
        fun firstName(value: String?) = apply { this.firstName = value }
        fun lastName(value: String?) = apply { this.lastName = value }
        fun avatar(value: String?) = apply { this.avatar = value }
        fun rating(value: Int) = apply { this.rating = value }
        fun respect(value: Int) = apply { this.respect = value }
        fun lastVisit(value: Date?) = apply { this.lastVisit = value }
        fun isOnline(value: Boolean) = apply { this.isOnline = value }

        fun build() = User(
            id,
            firstName,
            lastName,
            avatar,
            rating,
            respect,
            lastVisit,
            isOnline
        )
    }
}