package com.kevin.lacone


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_tambah_pembeli.*
import kotlinx.android.synthetic.main.fragment_tambah_pembeli.view.*


/**
 * A simple [Fragment] subclass.
 */
class TambahPembeliFragment : Fragment() {

    lateinit var ref: DatabaseReference
    private lateinit var frameLayout: FrameLayout
    private lateinit var btn_save: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val x = inflater.inflate(R.layout.fragment_tambah_pembeli, container, false)

        ref = FirebaseDatabase.getInstance().getReference("/CUSTOMERS/")

        x.btn_save.setOnClickListener {
            saveData()
        }
        // Inflate the layout for this fragment
        return x
    }

    private fun saveData() {
        val namaPembeli = edt_text_nama.text.toString()
        val kelasPembeli = edt_text_kelas.text.toString()
        val user = Customers(namaPembeli, kelasPembeli)
        val userId = ref.push().key.toString()
        ref.child(userId).setValue(user).addOnCompleteListener {
            if (!it.isSuccessful) {
                return@addOnCompleteListener
            } else {
                Snackbar.make(
                    frameLayoutContainer!!,
                    "Berhasil Menambahkan Pembeli",
                    Snackbar.LENGTH_LONG
                ).show()
                edt_text_nama.setText("")
                edt_text_kelas.setText("")
            }
        }.addOnFailureListener {
            Snackbar.make(frameLayoutContainer!!, "Gagal: ${it.message}", Snackbar.LENGTH_SHORT)
                .show()
        }

    }

}
