package com.example.tipscalculator

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.example.tipscalculator.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        fun hideKeyboard(view: View) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        var percentage: Int = 0

        binding.rbOptionOne.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                percentage = 10
            }
        }

        binding.rbOptionTwo.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                percentage = 15
            }
        }

        binding.rbOptionThree.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                percentage = 20
            }
        }

        binding.btnCalculate.setOnClickListener {
            val totalTableTemp = binding.tieTotal.text
            val nPeopleTemp = binding.tiePeople.text

            if (
                totalTableTemp?.isEmpty() == true ||
                nPeopleTemp?.isEmpty() == true ||
                binding.rgPercentOptions.checkedRadioButtonId == -1
            ) {

                hideKeyboard(it)
                binding.main.clearFocus()
                Snackbar.make(view, "Preencha todos os campos", Snackbar.LENGTH_LONG).show()
            } else {
                val totalTable: Float = totalTableTemp.toString().toFloat()
                val nPeople: Int = nPeopleTemp.toString().toInt()

                val totalTemp = totalTable / nPeople
                val tips = totalTemp * percentage / 100
                val totalWithTips = totalTemp + tips

                val totalWithTipsStr = String.format("%.2f", totalWithTips)

                binding.tvResultLabel.text = "O valor para cada pessoa é:"
                binding.tvResult.text = "R$$totalWithTipsStr"

                hideKeyboard(it)
            }
        }

        binding.btnClear.setOnClickListener {

            binding.tieTotal.setText("")
            binding.tiePeople.setText("")
            binding.rgPercentOptions.clearCheck()
            binding.tvResultLabel.text = ""
            binding.tvResult.text = ""
            binding.main.clearFocus()
            hideKeyboard(it)

        }


    }
}