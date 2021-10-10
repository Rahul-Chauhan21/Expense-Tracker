package com.example.expensetracker

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.expensetracker.databinding.FragmentReminderBinding
import java.util.*

class ReminderFragment : BaseFragment() {
    private lateinit var binding: FragmentReminderBinding
    private val args: ReminderFragmentArgs by navArgs()

    override var bottomNavigationViewVisibility = View.GONE
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReminderBinding.inflate(layoutInflater, container, false)
        initViews()
        return binding.root
    }

    private fun initViews() = with(binding) {
        val transaction = args.transaction
        tvReminderTitle.text = "Set Reminder for ${transaction.title}"
        etTime.setOnClickListener {
            val currentTime: Calendar = Calendar.getInstance()
            val hour: Int = currentTime.get(Calendar.HOUR_OF_DAY)
            val minute: Int = currentTime.get(Calendar.MINUTE)
            val timePicker = TimePickerDialog(
                requireContext(),
                { _, selectedHour, selectedMinute ->
                    etTime.setText(
                        String.format(
                            "%02d:%02d",
                            selectedHour,
                            selectedMinute
                        )
                    )
                },
                hour,
                minute,
                true
            ) //Yes 24 hour time
            timePicker.setTitle("Select Time")
            timePicker.show()
        }
    }
}