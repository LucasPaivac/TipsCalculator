package com.example.tipscalculator

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
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

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.num_people,
            android.R.layout.simple_spinner_item
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinnerNumberOfPeople.adapter = adapter

        var nPeopleSelected: String = "1"

        binding.spinnerNumberOfPeople.setSelection(0)

        binding.spinnerNumberOfPeople.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (parent != null) {
                    nPeopleSelected = parent.getItemAtPosition(position).toString()
                }else{
                    nPeopleSelected = parent?.getItemAtPosition(0).toString()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        binding.btnCalculate.setOnClickListener {
            val totalTableTemp = binding.tieTotal.text
            val nPeopleTemp = binding.tiePeople.text

            if (
                totalTableTemp?.isEmpty() == true ||
                /*nPeopleTemp?.isEmpty() == true ||*/
                binding.rgPercentOptions.checkedRadioButtonId == -1
            ) {

                hideKeyboard(it)
                binding.main.clearFocus()
                Snackbar.make(view, "Preencha todos os campos", Snackbar.LENGTH_LONG).show()
            } else {
                val totalTable: Float = totalTableTemp.toString().toFloat()
                //val nPeople: Int = nPeopleTemp.toString().toInt()
                val nPeople: Int = nPeopleSelected.toInt()

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