package com.useballoon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.useballoon.Ui.CreateAMissionStep1
import com.useballoon.Ui.CreateAMissionStep2
import com.useballoon.Ui.CreateAMissionStep3
import com.useballoon.Utils.NoSwipeViewPager
import java.util.ArrayList

@Suppress("DEPRECATION")
class CreateAMission : AppCompatActivity(), CreateAMissionStep1.Step1SuccessListener, CreateAMissionStep2.Step2SuccessListener  {
    var adapter = viewPagerAdapter(supportFragmentManager)
    var viewPager: NoSwipeViewPager? = null
    var backHome: LinearLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_amission)
        initView()
    }

    fun initView(){
        viewPager = findViewById(R.id.create_mission_viewpager);
        setupViewPager(viewPager!!)
        backHome = findViewById(R.id.create_mission_back);
        backHome!!.setOnClickListener {
            if (viewPager!!.currentItem == 2) {
                viewPager!!.setCurrentItem(1, true)
            } else if (viewPager!!.currentItem == 1) {
                viewPager!!.setCurrentItem(0, true)
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
    }

    override fun onStep2Success() {
        viewPager!!.setCurrentItem(2,true)
    }


}