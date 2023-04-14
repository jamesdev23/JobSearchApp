package com.example.kodegojobsearchapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kodegojobsearchapp.R
import com.example.kodegojobsearchapp.api_model.UserListData
import com.example.kodegojobsearchapp.databinding.ItemUserListBinding
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso

// TODO: (for nico) adapter for displaying applicant list

class UserListAdapter (private var context: Context, private var userLists : ArrayList<UserListData>, private val fragmentManager: FragmentManager)
    : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    fun setList(newUsers: ArrayList<UserListData>) {
        this.userLists.clear()
        this.userLists.addAll(newUsers)
        notifyDataSetChanged()
    }

    override fun getItemCount() = userLists.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemBinding = ItemUserListBinding
            .inflate(
                LayoutInflater.from(parent.context),
                parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder,
                                  position: Int) {
        holder.bindItems(userLists[position])

//        holder.itemView.setOnClickListener{
//            val userID = userLists[position].iD
//            val intent = Intent(context, UserInfoActivity::class.java)
//
//            val bundle = Bundle().apply {
//                putString("user_id", userID)
//            }
//            intent.putExtras(bundle)
//
//            Log.d("user id from adapter", userID)
//            context.startActivity(intent)
//        }
    }

    inner class ViewHolder(private val itemBinding: ItemUserListBinding) :
        RecyclerView.ViewHolder(itemBinding.root), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        fun bindItems(user: UserListData) {
            val imageURL = user.avatarUrl

            itemBinding.userName.text = "${user.firstName} ${user.lastName}"

            imageURL?.let{
                Picasso
                    .with(itemView.context)
                    .load(it)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .placeholder(R.drawable.baseline_person_24)
                    .error(R.drawable.baseline_person_24)
                    .into(itemBinding!!.userAvatar)
            }
        }

        override fun onClick(view: View?) {
            // do nothing for now
        }
    }
}
