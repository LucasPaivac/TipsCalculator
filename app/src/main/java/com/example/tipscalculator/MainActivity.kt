package com.example.tipscalculator

import android.R
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.example.tipscalculator.databinding.ActivityMainBinding
import com.google.android.material.internal.ViewUtils
import com.google.android.material.internal.ViewUtils.hideKeyboard
import com.google.android.material.snackbar.Snackbar

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        fun hideKeyboard(view: View) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        var percentage: Int = 0

        /*val colorStateList = ColorStateList(
            arrayOf(
                intArrayOf(R.attr.state_checked), // Checked state
                intArrayOf() // Default state
            ),
            intArrayOf(
                Color.parseColor("#26736B"),  // Color when checked
                Color.parseColor("#808080")  // Color when unchecked
            )
        )
        binding.rbOptionOne.buttonTintList = colorStateList
        binding.rbOptionTwo.buttonTintList = colorStateList
        binding.rbOptionThree.buttonTintList = colorStateList*/

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

                val totalTableStr = "R$" + String.format("%.2f", totalTable)
                val totalWithTipsStr = "R$" + String.format("%.2f", totalWithTips)

                val intent = Intent(this, SummaryActivity::class.java)
                intent.apply {
                    putExtra(KEY_TOTAL_TABLE, totalTableStr)
                    putExtra(KEY_N_PEOPLE, nPeople)
                    putExtra(KEY_PERCENTAGE, percentage)
                    putExtra(KEY_TOTAL, totalWithTipsStr)
                }
                clean()
                hideKeyboard(it)
                startActivity(intent)
            }
        }

        binding.btnClear.setOnClickListener {
            hideKeyboard(it)
            clean()
        }

    }

    private fun clean(){
        binding.tieTotal.setText("")
        binding.tiePeople.setText("")
        binding.rgPercentOptions.clearCheck()
        binding.main.clearFocus()

    }
}