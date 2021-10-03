package com.example.expensetracker.onboarding

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.expensetracker.MainActivity
import com.example.expensetracker.databinding.FragmentViewPagerBinding
import com.example.expensetracker.onboarding.screens.FirstScreen
import com.example.expensetracker.onboarding.screens.SecondScreen
import com.example.expensetracker.onboarding.screens.ThirdScreen
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class ViewPagerFragment : Fragment() {

    private lateinit var binding: FragmentViewPagerBinding
    var sharedPreferences: SharedPreferences? = null
    private var position = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        if (restorePrefData()) {
            startMainActivity()
        }

        binding = FragmentViewPagerBinding.inflate(layoutInflater, container, false)

        val fragmentList = arrayListOf(
            FirstScreen(),
            SecondScreen(),
            ThirdScreen()
        )

        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabIndicator, binding.viewPager) { _, _ -> }.attach()

        position = binding.viewPager.currentItem
        binding.tvNext.setOnClickListener { updatePosition(fragmentList) }

        binding.tabIndicator.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                position = tab!!.position
                if (position == fragmentList.size - 1) binding.tvNext.text = "Get Started"
                else binding.tvNext.text = "Next"

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })


        return binding.root
    }

    private fun updatePosition(fragmentList: ArrayList<Fragment>) = with(binding) {
        if (position < fragmentList.size) {
            position++
            binding.viewPager.currentItem = position
        }
        if (position == fragmentList.size) {
            savePrefData()
            startMainActivity()
        }
    }

    private fun startMainActivity() {
        val i = Intent(activity, MainActivity::class.java)
        startActivity(i)
    }

    private fun savePrefData() {
        sharedPreferences =
            requireActivity().applicationContext.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val editor = sharedPreferences!!.edit()
        editor.putBoolean("onBoardingActivityFinished", true)
        editor.apply()
    }

    private fun restorePrefData(): Boolean {
        sharedPreferences = requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE)
        return sharedPreferences!!.getBoolean("onBoardingActivityFinished", false)
    }

}