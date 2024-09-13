package com.example.simplecal

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    private lateinit var editTextResult: EditText
    private var operand1: Double = 0.0
    private var operand2: Double = 0.0
    private var currentOperation: String = ""
    private val decimalFormat = DecimalFormat("#.##")

    private fun applyPulseAnimation(button: Button) {
        val pulseAnimation = AnimationUtils.loadAnimation(this, R.anim.glow_effect)
        button.startAnimation(pulseAnimation)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextResult = findViewById(R.id.editTextResult)
        Log.d("Calculator", "editTextResult initialized: $editTextResult")


        val button0 = findViewById<Button>(R.id.button0)
        val button1 = findViewById<Button>(R.id.button1)
        val button2 = findViewById<Button>(R.id.button2)
        val button3 = findViewById<Button>(R.id.button3)
        val button4 = findViewById<Button>(R.id.button4)
        val button5 = findViewById<Button>(R.id.button5)
        val button6 = findViewById<Button>(R.id.button6)
        val button7 = findViewById<Button>(R.id.button7)
        val button8 = findViewById<Button>(R.id.button8)
        val button9 = findViewById<Button>(R.id.button9)
        val buttonDot = findViewById<Button>(R.id.buttonDot)
        val buttonAdd = findViewById<Button>(R.id.buttonAdd)
        val buttonSubtract = findViewById<Button>(R.id.buttonSubtract)
        val buttonMultiply = findViewById<Button>(R.id.buttonMultiply)
        val buttonDivide = findViewById<Button>(R.id.buttonDivide)
        val buttonEquals = findViewById<Button>(R.id.buttonEquals)
        val buttonClear = findViewById<Button>(R.id.buttonClear)

        val numberButtons = listOf(
            button0,
            button1,
            button2,
            button3,
            button4,
            button5,
            button6,
            button7,
            button8,
            button9
        )
        numberButtons.forEach { button ->
            button.setOnClickListener {
                playButtonSound()
                applyPulseAnimation(button)
                editTextResult.append((it as Button).text)
            }
        }

        buttonDot.setOnClickListener {
            playButtonSound()
            applyPulseAnimation(buttonDot)
            if (!editTextResult.text.contains(".")) {
                editTextResult.append(".")
            }
        }

        buttonAdd.setOnClickListener {
            playButtonSound()
            applyPulseAnimation(buttonAdd)
            performOperation("+")
        }
        buttonSubtract.setOnClickListener {
            playButtonSound()
            applyPulseAnimation(buttonSubtract)

            performOperation("-")
        }
        buttonMultiply.setOnClickListener {
            playButtonSound()
            applyPulseAnimation(buttonMultiply)

            performOperation("*")
        }
        buttonDivide.setOnClickListener {
            playButtonSound()
            applyPulseAnimation(buttonDivide)

            performOperation("/")
        }
        buttonEquals.setOnClickListener {
            playButtonSound()
            applyPulseAnimation(buttonEquals)
            calculateResult()
        }

        buttonClear.setOnClickListener {
            playButtonSound()
            applyPulseAnimation(buttonClear)
            editTextResult.text.clear()
            operand1 = 0.0
            operand2 = 0.0
            currentOperation = ""
        }
    }

    private fun playButtonSound() {
        val mediaPlayer = MediaPlayer.create(this, R.raw.button_click)
        mediaPlayer.start()
        mediaPlayer.setOnCompletionListener {
            it.release()
        }
    }

    private fun performOperation(operation: String) {
        currentOperation = operation
        try {
            operand1 = editTextResult.text.toString().toDoubleOrNull() ?: 0.0
        } catch (e: NumberFormatException) {
            Log.e("Calculator", "Invalid number input")
            operand1 = 0.0
        }
        editTextResult.text.clear()
    }

    private fun calculateResult() {
        try {
            operand2 = editTextResult.text.toString().toDoubleOrNull() ?: 0.0
            val result = when (currentOperation) {
                "+" -> operand1 + operand2
                "-" -> operand1 - operand2
                "*" -> operand1 * operand2
                "/" -> if (operand2 != 0.0) operand1 / operand2 else Double.NaN
                else -> Double.NaN
            }
            if (result.isNaN() || result == Double.POSITIVE_INFINITY || result == Double.NEGATIVE_INFINITY) {
                editTextResult.setText("Error")
            } else {
                editTextResult.setText(decimalFormat.format(result))
            }
        } catch (e: NumberFormatException) {
            Log.e("Calculator", "Error calculating result: ${e.message}")
            editTextResult.setText("Error")
        }
    }
}
