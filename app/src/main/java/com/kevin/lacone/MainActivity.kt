package com.kevin.lacone

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var content: FrameLayout? = null

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    val fragment = HomeFragment.newInstance()
                    addFragment(fragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_tambah_pembeli -> {
                    val fragment = TambahPembeliFragment()
                    addFragment(fragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_menu -> {
                    val fragment = MenuFragment()
                    addFragment(fragment)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.content, fragment, fragment.javaClass.getSimpleName())
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        supportActionBar!!.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        val fragment = HomeFragment.newInstance()
        addFragment(fragment)

    }

}
