package com.example.expensetracker.onboarding.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.expensetracker.R
import com.example.expensetracker.databinding.FragmentThirdBinding

class ThirdScreen : Fragment() {
    private lateinit var binding : FragmentThirdBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentThirdBinding.inflate(layoutInflater, container, false)
        initView()
        return binding.root
    }

    private fun initView() = with(binding.obThirdScreen) {
        this.ivObImage.setImageResource(R.drawable.ob_cloud)
        this.tvObTitle.text = "Cloud Support"
        this.tvObDesc.text = "Sign up to support cloud backup"
    }

}