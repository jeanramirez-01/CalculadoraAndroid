package com.example.calculadorapro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.calculadorapro.databinding.ActivityMainBinding
import java.lang.NumberFormatException
import kotlin.math.pow
import kotlin.math.sqrt


class MainActivity : AppCompatActivity(), View.OnClickListener {


    private lateinit var binding: ActivityMainBinding
    private lateinit var recordList: ArrayList<String>
    private lateinit var operationAuxList: ArrayList<String>
    private var firstNumber = 0.0
    private var secondNumber = 0.0
    private var operation: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        operation = null
        recordList = ArrayList()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bt0.setOnClickListener(this)
        binding.bt1.setOnClickListener(this)
        binding.bt2.setOnClickListener(this)
        binding.bt3.setOnClickListener(this)
        binding.bt4.setOnClickListener(this)
        binding.bt5.setOnClickListener(this)
        binding.bt6.setOnClickListener(this)
        binding.bt7.setOnClickListener(this)
        binding.bt8.setOnClickListener(this)
        binding.bt9.setOnClickListener(this)
        binding.btComa.setOnClickListener(this)
        binding.btAditition.setOnClickListener(this)
        binding.btSubstraction.setOnClickListener(this)
        binding.btPositiveNegative.setOnClickListener(this)
        binding.btDivision.setOnClickListener(this)
        binding.btMultiplication.setOnClickListener(this)
        binding.btRaiz.setOnClickListener(this)
        binding.btPotencia.setOnClickListener(this)
        binding.btClear.setOnClickListener(this)
        binding.btDelete.setOnClickListener(this)
        binding.btEqual.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.bt0 -> onNumberPressed("0")
            binding.bt1 -> onNumberPressed("1")
            binding.bt2 -> onNumberPressed("2")
            binding.bt3 -> onNumberPressed("3")
            binding.bt4 -> onNumberPressed("4")
            binding.bt5 -> onNumberPressed("5")
            binding.bt6 -> onNumberPressed("6")
            binding.bt7 -> onNumberPressed("7")
            binding.bt8 -> onNumberPressed("8")
            binding.bt9 -> onNumberPressed("9")
            binding.btComa -> onCommaPressed()
            binding.btAditition -> onOperationPressed("+")
            binding.btSubstraction -> onOperationPressed("-")
            binding.btDivision -> onOperationPressed("/")
            binding.btMultiplication -> onOperationPressed("*")
            binding.btPotencia -> onOperationPressed("^")
            binding.btPositiveNegative -> onPositivoNegaticoPressed()
            binding.btRaiz -> onSqrtPressed()
            binding.btClear -> onClearPressed()
            binding.btDelete -> onDeletePressed()
            binding.btEqual -> onEqualPressed()
        }
    }

    private fun onNumberPressed(number: String) {
        renderScreen(number)
        checkOperation()
    }

    private fun renderScreen(number: String) {
        val result = if (binding.tvTotal.text.toString() == "0") {
            number
        } else {
            "${binding.tvTotal.text}$number"
        }
        binding.tvTotal.text = result

    }

    private fun checkOperation() {
        if (operation == null) {
            firstNumber = binding.tvTotal.text.toString().toDouble()
        } else {
            secondNumber = binding.tvTotal.text.toString().toDouble()
        }
    }

    private fun onOperationPressed(operation: String) {
        this.operation = operation
        firstNumber = binding.tvTotal.text.toString().toDouble()

        recordList.add(formatResult(binding.tvTotal.text.toString()))
        recordList.add(operation)
        binding.tvRecord.text = recordList.joinToString("")

        binding.tvTotal.text = ""
    }

    private fun onEqualPressed() {
        val result = when (operation) {
            "+" -> firstNumber + secondNumber
            "-" -> firstNumber - secondNumber
            "*" -> firstNumber * secondNumber
            "/" -> firstNumber / secondNumber
            "^" -> firstNumber.pow(secondNumber)
            else -> firstNumber
        }

        recordList.add(formatResult(binding.tvTotal.text.toString()))
        recordList.add("=")
        binding.tvRecord.text = recordList.joinToString("")
        recordList.clear()

        operation = null
        firstNumber = result
        secondNumber = 0.0

        binding.tvTotal.text = formatResult(result.toString())
    }


    private fun onCommaPressed() {
        val currentText = binding.tvTotal.text.toString()
        val result = if (currentText.isEmpty()) {
            "0."
        } else if (!currentText.contains(".")) {
            "$currentText."
        } else {
            currentText
        }
        binding.tvTotal.text = result
    }


    private fun onPositivoNegaticoPressed() {
        binding.tvTotal.text = formatResult((-firstNumber).toString())
        firstNumber = binding.tvTotal.text.toString().toDouble()
    }


    private fun onSqrtPressed() {

        binding.tvTotal.text = formatResult(sqrt(firstNumber).toString())
        firstNumber = binding.tvTotal.text.toString().toDouble()
    }

    private fun onClearPressed() {
        binding.tvTotal.text = "0"
        binding.tvRecord.text = "0"
        firstNumber = 0.0
        secondNumber = 0.0
        recordList.clear()
    }

    private fun onDeletePressed() {
        binding.tvTotal.text = "0"
    }

    private fun formatResult(number: String): String {
        return try {
            val doubleNumber = number.toDouble()

            if (doubleNumber % 1 == 0.0) {
                doubleNumber.toInt().toString()
            } else {
                "%.3f".format(doubleNumber)
            }
        } catch (e: NumberFormatException) {
            e.printStackTrace()
            number
        }
    }

}