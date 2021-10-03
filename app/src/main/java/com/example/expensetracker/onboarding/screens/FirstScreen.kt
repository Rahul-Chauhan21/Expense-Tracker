package com.example.expensetracker.onboarding.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.expensetracker.R
import com.example.expensetracker.databinding.FragmentFirstBinding


class FirstScreen : Fragment() {
    private lateinit var binding : FragmentFirstBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFirstBinding.inflate(layoutInflater, container, false)
        initView()
        return binding.root
    }

    private fun initView () = with(binding.obFirstScreen) {
        this.ivObImage.setImageResource(R.drawable.ob_expense)
        this.tvObTitle.text = "One-click Tracking"
        this.tvObDesc.text = "Track your financial activity in just a few seconds"
    }

}