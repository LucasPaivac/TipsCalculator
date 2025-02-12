package com.example.tipscalculator

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

import com.example.tipscalculator.databinding.ActivitySummaryBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

const val KEY_TOTAL_TABLE = "SummaryActivity.totalTable"
const val KEY_N_PEOPLE = "SummaryActivity.nPeople"
const val KEY_PERCENTAGE = "SummaryActivity.percentage"
const val KEY_TOTAL = "SummaryActivity.totalWithTipsStr"

private lateinit var binding: ActivitySummaryBinding

class SummaryActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivitySummaryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.tvTotalPerPerson.text = intent.getStringExtra(KEY_TOTAL)
        binding.tvNPeople.text = intent.getIntExtra(KEY_N_PEOPLE, 0).toString()
        binding.tvTotalTable.text = intent.getStringExtra(KEY_TOTAL_TABLE)

        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
        val dateTimeFormatted = currentDateTime.format(formatter)

        binding.tvDateHour.text = dateTimeFormatted

        binding.btnBack.setOnClickListener {
            finish()
        }

    }
}
