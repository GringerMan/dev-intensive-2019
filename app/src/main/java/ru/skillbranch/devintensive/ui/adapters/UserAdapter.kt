package ru.skillbranch.devintensive.ui.adapters

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
import ru.skillbranch.devintensive.models.data.UserItem

class UserAdapter(val listener : (UserItem)->Unit) : RecyclerView.Adapter<UserAdapter.UserViewHolder>(){

    var items : List<UserItem> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val convertView = inflater.inflate(R.layout.item_user_list, parent, false)
        return UserViewHolder(convertView)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) = holder.bind(items[position], listener)

    fun updateData(data: List<UserItem>){
        val diffCallback = object : DiffUtil.Callback(){
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

    inner class UserViewHolder(convertView: View) : RecyclerView.ViewHolder(convertView), LayoutContainer{
        override val containerView: View?
            get() = itemView

        val iv_avatar_user = convertView.findViewById<ImageView>(R.id.iv_avatar_user)
        val sv_indicator = convertView.findViewById<View>(R.id.sv_indicator)
        val tv_user_name = convertView.findViewById<TextView>(R.id.tv_user_name)
        val tv_last_activity = convertView.findViewById<TextView>(R.id.tv_last_activity)
        val iv_selected = convertView.findViewById<ImageView>(R.id.iv_selected)

        fun bind(user: UserItem, listener: (UserItem)->Unit){
            if (user.avatar != null)
            {
                Glide.with(itemView)
                    .load(user.avatar)
                    .into(iv_avatar_user)
            }
            else
            {
                Glide.with(itemView).clear(iv_avatar_user)
                //iv_avatar_user.setInitials(user.initials ?: "??")
            }

            sv_indicator.visibility = if (user.isOnLine) View.VISIBLE else View.GONE
            tv_user_name.text = user.fullName
            tv_last_activity.text = user.lastActivity
            iv_selected.visibility = if (user.isSelected) View.VISIBLE else View.GONE

            itemView.setOnClickListener{
                listener.invoke(user)
            }

        }
    }
}
