package com.example.myfirstapplication

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowCompat
import org.mariuszgromada.math.mxparser.Expression

class MainActivity : AppCompatActivity() {
    private lateinit var display: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        display = findViewById(R.id.display)
        setButtonListeners()
    }

    private fun setButtonListeners() {
        val buttons = listOf(
            R.id.btn_zero, R.id.btn_one, R.id.btn_two, R.id.btn_three, R.id.btn_four, R.id.btn_five,
            R.id.btn_six, R.id.btn_seven, R.id.btn_eight, R.id.btn_nine, R.id.btn_dot, R.id.btn_add,
            R.id.btn_subtract, R.id.btn_multiply, R.id.btn_divide, R.id.btn_bracket_open,
            R.id.btn_bracket_close
        )

        for (id in buttons) {
            findViewById<Button>(id).setOnClickListener { appendToDisplay((it as Button).text.toString()) }
        }

        findViewById<Button>(R.id.btn_clear).setOnClickListener { display.text = "" }
        findViewById<Button>(R.id.btn_backspace).setOnClickListener { backspace() }
        findViewById<Button>(R.id.btn_equals).setOnClickListener { calculateResult() }
    }

    private fun appendToDisplay(value: String) {
        display.append(value)
    }

    private fun backspace() {
        val text = display.text.toString()
        if (text.isNotEmpty()) {
            display.text = text.dropLast(1)
        }
    }

    private fun calculateResult() {
        try {
            val expression = Expression(display.text.toString())
            val result = expression.calculate()
            if (result.isNaN()) {
                display.text = "Error"
            } else {
                display.text = result.toString()
            }
        } catch (e: Exception) {
            display.text = "Error"
        }
    }
}
