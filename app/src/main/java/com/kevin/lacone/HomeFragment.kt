package com.kevin.lacone


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {

    val adapter = GroupAdapter<GroupieViewHolder>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val x = inflater.inflate(R.layout.fragment_home, container, false)
        x.recyclerview.adapter = adapter

        adapter.setOnItemClickListener{item, view ->

        }

        fetchFirebase()
        return x


    }

    fun fetchFirebase() {
        val ref = FirebaseDatabase.getInstance().getReference("/CUSTOMERS/")
        ref.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val customers = p0.getValue(Customers::class.java) ?: return
                adapter.add(RecyclerAdapter(customers))
            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }
        })
    }


    companion object {
        fun newInstance(): HomeFragment {
            val fragment = HomeFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

}
