package com.example.expensetracker

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController
import com.example.expensetracker.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding
    var sharedPreferences: SharedPreferences? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSplashBinding.inflate(layoutInflater, container, false)

        binding.ivSplashMain.setImageResource(R.drawable.splash_expense)
        binding.tvSplashTitle.text = getString(R.string.app_name)

        val manager: FragmentManager = requireActivity().supportFragmentManager
        val trans: FragmentTransaction = manager.beginTransaction()

        Handler().postDelayed({
            if (restorePrefData()) {
                startMainActivity()
                trans.remove(this)
                trans.commit()
                manager.popBackStack()
            } else
                findNavController().navigate(R.id.action_splashFragment_to_viewPagerFragment)
        }, 3000)

        return binding.root
    }

    private fun startMainActivity() {
        val i = Intent(activity, MainActivity::class.java)
        startActivity(i)
    }

    private fun restorePrefData(): Boolean {
        sharedPreferences = requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE)
        return sharedPreferences!!.getBoolean("onBoardingActivityFinished", false)
    }

}