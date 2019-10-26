package ru.skillbranch.devintensive.ui.adapters

import android.graphics.Color
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.models.data.ChatItem
import ru.skillbranch.devintensive.models.data.ChatType

class ChatAdapter(val listener: (ChatItem)->Unit) : RecyclerView.Adapter<ChatAdapter.ChatItemViewHolder>() {

    companion object{
        private const val ARCHIVE_TYPE = 0
        private const val SINGLE_TYPE = 1
        private const val GROUP_TYPE = 2
    }

    var items: List<ChatItem> = listOf()

    override fun getItemViewType(position: Int): Int = when(items[position].chatType){
        ChatType.ARCHIVE -> ARCHIVE_TYPE
        ChatType.SINGLE -> SINGLE_TYPE
        ChatType.GROUP -> GROUP_TYPE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatItemViewHolder {
        var inflater = LayoutInflater.from(parent.context)

        return when(viewType){
            SINGLE_TYPE -> SingleViewHolder(inflater.inflate(R.layout.item_chat_single, parent, false))
            GROUP_TYPE -> GroupViewHolder(inflater.inflate(R.layout.item_chat_group, parent, false))
            else -> SingleViewHolder(inflater.inflate(R.layout.item_chat_single, parent, false))
        }

        /*val convertView = inflater.inflate(R.layout.item_chat_single, parent, false)
        Log.d("M_ChatAdapter", "onCreateViewHolder")
        return SingleViewHolder(convertView)*/
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ChatItemViewHolder, position: Int) {
        Log.d("M_ChatAdapter", "onBindViewHolder $position")

        holder.bind(items[position], listener)
    }

    fun updateData(data: List<ChatItem>){

        Log.d("M_ChatAdapter", "update data adapter - new data ${data.size} hash: ${data.hashCode()}"
            + "old data ${items.size} hash: ${items.hashCode()}")

        val diffCallback = object :DiffUtil.Callback(){
            override fun areItemsTheSame(oldPos: Int, newPos: Int): Boolean = (items[oldPos].id == data[newPos].id)

            override fun areContentsTheSame(oldPos: Int, newPos: Int): Boolean = (items[oldPos].hashCode() == data[newPos].hashCode())

            override fun getOldListSize() = items.size

            override fun getNewListSize() = data.size
            }

        val diffResult = DiffUtil.calculateDiff(diffCallback)

        items = data
        //notifyDataSetChanged()
        diffResult.dispatchUpdatesTo(this)
    }

    abstract inner class ChatItemViewHolder(convertView: View) : RecyclerView.ViewHolder(convertView), LayoutContainer{
        override val containerView: View?
            get() = itemView
        abstract fun bind(item: ChatItem, listener: (ChatItem) -> Unit)
    }

    inner class SingleViewHolder(convertView: View) : ChatItemViewHolder(convertView), ItemTouchViewHolder{
        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY)
        }

        override fun onItemCleared() {
            itemView.setBackgroundColor(Color.WHITE)
        }

        val iv_avatar = convertView.findViewById<ImageView>(R.id.iv_avatar_single)
        val tv_title = convertView.findViewById<TextView>(R.id.tv_title_single)
        val tv_indicator = convertView.findViewById<View>(R.id.sv_indicator)
        val tv_date = convertView.findViewById<TextView>(R.id.tv_date_single)
        val tv_counter = convertView.findViewById<TextView>(R.id.tv_counter_single)
        val tv_message = convertView.findViewById<TextView>(R.id.tv_message_single)

        override fun bind(item: ChatItem, listener: (ChatItem)->Unit){
            if (item.avatar == null) {
                Glide.with(itemView)
                    .clear(iv_avatar)
                //iv_avatar.setInitials(item.initials)
            }
            else {
                Glide.with(itemView)
                    .load(item.avatar)
                    .into(iv_avatar)
            }
            tv_indicator.visibility = if (item.isOnline) View.VISIBLE else View.GONE

            with(tv_date){
                visibility = if (item.lastMessageDate != null) View.VISIBLE else View.GONE
                text = item.lastMessageDate
            }

            with(tv_counter){
                visibility = if (item.messageCount > 0) View.VISIBLE else View.GONE
                text = item.messageCount.toString()
            }


            tv_title.text = item.title
            tv_message.text = item.shortDescription

            itemView.setOnClickListener{
                listener.invoke(item)
            }
        }
    }

    inner class GroupViewHolder(convertView: View) : ChatItemViewHolder(convertView), ItemTouchViewHolder{
        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY)
        }

        override fun onItemCleared() {
            itemView.setBackgroundColor(Color.WHITE)
        }

        val iv_avatar_group = convertView.findViewById<ImageView>(R.id.iv_avatar_group)
        val tv_title_group = convertView.findViewById<TextView>(R.id.tv_title_group)
        val tv_date_group = convertView.findViewById<TextView>(R.id.tv_date_group)
        val tv_counter_group = convertView.findViewById<TextView>(R.id.tv_counter_group)
        val tv_message_group = convertView.findViewById<TextView>(R.id.tv_message_group)
        val tv_message_author = convertView.findViewById<TextView>(R.id.tv_message_author)

        override fun bind(item: ChatItem, listener: (ChatItem)->Unit){
            //iv_avatar_group.setInitials(item.title[0].toString())

            with(tv_date_group){
                visibility = if (item.lastMessageDate != null) View.VISIBLE else View.GONE
                text = item.lastMessageDate
            }

            with(tv_counter_group){
                visibility = if (item.messageCount > 0) View.VISIBLE else View.GONE
                text = item.messageCount.toString()
            }


            tv_title_group.text = item.title
            tv_message_group.text = item.shortDescription

            with(tv_message_author){
                visibility = if (item.messageCount > 0) View.VISIBLE else View.GONE
                text = item.author
            }


            itemView.setOnClickListener{
                listener.invoke(item)
            }
        }
    }

}//02:10:03