package com.example.expensetracker.onboarding.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.expensetracker.R
import com.example.expensetracker.databinding.FragmentFirstBinding
import com.example.expensetracker.databinding.FragmentSecondBinding


class SecondScreen : Fragment() {
    private lateinit var binding: FragmentSecondBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondBinding.inflate(layoutInflater, container, false)
        initView()
        return binding.root
    }


    private fun initView() = with(binding.obSecondScreen) {
        this.ivObImage.setImageResource(R.drawable.ob_pie)
        this.tvObTitle.text = "Intuitive Charts"
        this.tvObDesc.text = "Know where your money goes"
    }
}