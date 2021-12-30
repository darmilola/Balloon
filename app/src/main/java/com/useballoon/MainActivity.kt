package com.useballoon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.useballoon.Ui.*
import com.useballoon.Utils.NoSwipeViewPager
import java.util.ArrayList
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.graphics.Color
import android.preference.PreferenceManager
import android.view.View

import com.amulyakhare.textdrawable.TextDrawable


import androidx.core.content.ContextCompat

import android.os.Build














@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    var bottomNavigationView: BottomNavigationView? = null
    var adapter = ViewPagerAdapter(supportFragmentManager)
    var viewPager: NoSwipeViewPager? = null
    var userProfilePicture: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        initView()
    }

    private fun initView(){
        userProfilePicture = findViewById(R.id.user_profile_picture)
        bottomNavigationView = findViewById(R.id.main_activity_bottom_nav)
        viewPager = findViewById(R.id.content_frame)
        viewPager!!.offscreenPageLimit = 3
        viewPager!!.setCurrentItem(0, false);
        bottomNavigationView!!.selectedItemId = R.id.dashboard
        bottomNavigationView!!.isSelected = true
        setupViewPager(viewPager!!)

        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val userFirstname = preferences.getString(getString(R.string.saved_user_firstname), "")

        val drawable: TextDrawable = TextDrawable.builder()
            .buildRound(userFirstname?.get(0).toString(), Color.parseColor("#ff6633"))

        userProfilePicture!!.setImageDrawable(drawable)

        bottomNavigationView!!.setOnNavigationItemSelectedListener { item ->
            val i = item.itemId
            if (i == R.id.dashboard) {
                viewPager!!.setCurrentItem(0, false)
            } else if (i == R.id.wallet) {
                viewPager!!.setCurrentItem(1, false)
            } else if (i == R.id.notification) {
                viewPager!!.setCurrentItem(2, false)
            } else if (i == R.id.profile) {
                viewPager!!.setCurrentItem(3, false)
            }
            true
        }
    }


    class ViewPagerAdapter(fm: FragmentManager?) :
        FragmentPagerAdapter(fm!!, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        private val mFragmentList: MutableList<Fragment> = ArrayList()
        private val mFragmentTitleList: MutableList<String> = ArrayList()
        override fun getItem(position: Int): Fragment {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            when (position) {
                0 -> return Dashboard()
                1 -> return Wallet()
                2 -> return Notifications()
                3 -> return Profile()
            }
            return Dashboard()
        }

        override fun getCount(): Int {
            return 4
        }

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mFragmentTitleList[position]
        }
    }

    private fun setupViewPager(viewPager: NoSwipeViewPager) {
        adapter.addFragment(Dashboard(), "Dashboard")
        adapter.addFragment(Wallet(), "Wallet")
        adapter.addFragment(Notifications(), "Notification")
        adapter.addFragment(Profile(), "Profile")
        viewPager.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.white)
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

}