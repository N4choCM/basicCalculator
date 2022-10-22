package com.example.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private var tvPnlInput: TextView? = null
    private var lastNumeric: Boolean = false
    var lastDot: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvPnlInput = findViewById(R.id.tvPnlInput)
    }

    fun onDigit(view: View) {
        tvPnlInput?.append((view as Button).text)
        lastNumeric = true
        lastDot = false
    }

    fun onClear(view: View) {
        tvPnlInput?.text = ""
    }

    fun onDecimalPoint(view: View) {
        if (lastNumeric && !lastDot) {
            tvPnlInput?.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onOperator(view: View) {
        tvPnlInput?.text?.let {

            if (lastNumeric && !isOperatorAdded(it.toString())) {
                tvPnlInput?.append((view as Button).text)
                lastNumeric = false
                lastDot = false
            }
        }
    }

    private fun removeZeroAfterDot(result : String) : String{
        var value = result
        if(result.contains(".0")){
            value = result.substring(0, result.length-2)
        }
        return value
    }

    private fun isOperatorAdded(value: String): Boolean {

        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("/")
                    || value.contains("*")
                    || value.contains("+")
                    || value.contains("-")
        }
    }

    fun onEqual(view: View) {
        if (lastNumeric) {
            var tvValue = tvPnlInput?.text.toString()
            var prefix = ""
            try {
                if (tvValue.startsWith("-")) {
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }
                if (tvValue.contains("-")) {
                    val splitValue = tvValue.split("-")

                    var a = splitValue[0]
                    var b = splitValue[1]

                    if (prefix.isNotEmpty()) {
                        a = prefix + a
                    }

                    tvPnlInput?.text = removeZeroAfterDot((a.toDouble() - b.toDouble()).toString())
                } else if (tvValue.contains("+")) {
                    val splitValue = tvValue.split("+")

                    var a = splitValue[0]
                    var b = splitValue[1]

                    if (prefix.isNotEmpty()) {
                        a = prefix + a
                    }

                    tvPnlInput?.text = removeZeroAfterDot((a.toDouble() + b.toDouble()).toString())

                } else if (tvValue.contains("/")) {
                    val splitValue = tvValue.split("/")

                    var a = splitValue[0]
                    var b = splitValue[1]

                    if (prefix.isNotEmpty()) {
                        a = prefix + a
                    }

                    tvPnlInput?.text = removeZeroAfterDot((a.toDouble() / b.toDouble()).toString())

                } else if (tvValue.contains("x")) {
                    val splitValue = tvValue.split("x")

                    var a = splitValue[0]
                    var b = splitValue[1]

                    if (prefix.isNotEmpty()) {
                        a = prefix + a
                    }

                    tvPnlInput?.text = removeZeroAfterDot((a.toDouble() * b.toDouble()).toString())

                }

            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }
}