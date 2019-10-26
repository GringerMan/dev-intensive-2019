package ru.skillbranch.devintensive.models.data

import ru.skillbranch.devintensive.extensions.shortFormat
import ru.skillbranch.devintensive.models.BaseMessage
import ru.skillbranch.devintensive.utils.Utils
import androidx.lifecycle.MutableLiveData
import java.util.*

data class Chat (
    val id:String,
    val title: String,
    val members: List<User> = listOf(),
    var messages: MutableList<BaseMessage> = mutableListOf(),
    var isArchived:Boolean = false
){

    fun unreadableMessageCount(): Int{
        return messages.count { !it.isReaded }
    }

    fun lastMessageDate(): Date?{
        return if (messages.isNotEmpty()) messages.last().date else null
    }

    fun lastMessageShort(): Pair<String?, String?>{
        return if (messages.isNotEmpty()) (messages.last().statusMessage() to messages.last().from?.firstName) else (null to null)
    }

    private fun isSingle(): Boolean = (members.size == 1)

    fun toChatItem(): ChatItem {
        return if (isSingle()) {
            val user = members.first()

            ChatItem(
                id,
                user.avatar,
                Utils.toInitials(user.firstName, user.lastName) ?: "??",
                "${user.firstName?:""} ${user.lastName?:""}",
                lastMessageShort().first,
                unreadableMessageCount(),
                lastMessageDate()?.shortFormat(),
                user.isOnline
            )

        } else {

            ChatItem(
                id,
                null,
                "",
                title,
                lastMessageShort().first,
                unreadableMessageCount(),
                lastMessageDate()?.shortFormat(),
                false,
                ChatType.GROUP,
                lastMessageShort().second
            )
        }
    }
}

enum class ChatType{
    SINGLE,
    GROUP,
    ARCHIVE
}


