package com.kevin.lacone

import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.blog_row.view.*

class RecyclerAdapter(val customers: Customers):Item<GroupieViewHolder>(){
    override fun getLayout(): Int {
        return R.layout.blog_row
    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.nama.text = customers.nama
        viewHolder.itemView.kelas.text = customers.kelas
    }

}