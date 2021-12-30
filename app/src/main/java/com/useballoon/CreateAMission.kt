package com.useballoon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.useballoon.Ui.CreateAMissionStep1
import com.useballoon.Ui.CreateAMissionStep2
import com.useballoon.Ui.CreateAMissionStep3
import com.useballoon.Utils.NoSwipeViewPager
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList





@Suppress("DEPRECATION")@AndroidEntryPoint
class CreateAMission : AppCompatActivity(), CreateAMissionStep1.Step1SuccessListener, CreateAMissionStep2.Step2SuccessListener  {
    var adapter = viewPagerAdapter(supportFragmentManager)
    var viewPager: NoSwipeViewPager? = null
    var backHome: LinearLayout? = null
    var title: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_amission)
        initView()
    }

    fun initView(){
        viewPager = findViewById(R.id.create_mission_viewpager)
        title = findViewById(R.id.create_a_mission_title)
        setupViewPager(viewPager!!)
        backHome = findViewById(R.id.create_mission_back);
        backHome!!.setOnClickListener {
            if (viewPager!!.currentItem == 2) {
                viewPager!!.setCurrentItem(1, true)
                title!!.text = "Give Instructions - Step 2 of 3"
            } else if (viewPager!!.currentItem == 1) {
                viewPager!!.setCurrentItem(0, true)
                title!!.text = "Create a Mission - Step 1 of 3"
            } else {
                startActivity(Intent(this@CreateAMission, MainActivity::class.java))
            }
        }
    }

    class viewPagerAdapter(fm: FragmentManager?) :
        FragmentPagerAdapter(fm!!, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        private val mFragmentList: MutableList<Fragment> = ArrayList()
        private val mFragmentTitleList: MutableList<String> = ArrayList()
        override fun getItem(position: Int): Fragment {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            when (position) {
                0 -> return CreateAMissionStep1()
                1 -> return CreateAMissionStep2()
                2 -> return CreateAMissionStep3()
            }
            return CreateAMissionStep1()
        }

        override fun getCount(): Int {
            return 3
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
        adapter.addFragment(CreateAMissionStep1(), "Step 1")
        adapter.addFragment(CreateAMissionStep2(), "Step 2")
        adapter.addFragment(CreateAMissionStep3(), "Step 3")
        viewPager.adapter = adapter
    }

    override fun onStep1Success() {
        viewPager!!.setCurrentItem(1,true)
        title!!.text = "Give Instructions - Step 2 of 3"
    }

    override fun onStep2Success() {
        viewPager!!.setCurrentItem(2,true)
        title!!.text = "Final Step - Step 3 of 3"
    }


}