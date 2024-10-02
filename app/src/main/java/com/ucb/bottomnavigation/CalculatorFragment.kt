package com.ucb.bottomnavigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment

class CalculatorFragment : Fragment() {

    private lateinit var editTextResult: EditText
    private var operator: String? = null
    private var firstValue: Double = 0.0
    private lateinit var bounceAnimation: Animation

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_calculator, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        editTextResult = view.findViewById(R.id.editTextResult)

        // Load the bounce animation
        bounceAnimation = AnimationUtils.loadAnimation(context, R.anim.bounce)

        val button0: Button = view.findViewById(R.id.button0)
        val button1: Button = view.findViewById(R.id.button1)
        val button2: Button = view.findViewById(R.id.button2)
        val button3: Button = view.findViewById(R.id.button3)
        val button4: Button = view.findViewById(R.id.button4)
        val button5: Button = view.findViewById(R.id.button5)
        val button6: Button = view.findViewById(R.id.button6)
        val button7: Button = view.findViewById(R.id.button7)
        val button8: Button = view.findViewById(R.id.button8)
        val button9: Button = view.findViewById(R.id.button9)
        val buttonDot: Button = view.findViewById(R.id.buttonDot)
        val buttonAdd: Button = view.findViewById(R.id.buttonAdd)
        val buttonSubtract: Button = view.findViewById(R.id.buttonSubtract)
        val buttonMultiply: Button = view.findViewById(R.id.buttonMultiply)
        val buttonDivide: Button = view.findViewById(R.id.buttonDivide)
        val buttonEquals: Button = view.findViewById(R.id.buttonEquals)
        val buttonClear: Button = view.findViewById(R.id.buttonClear)

        val buttons = listOf(button0, button1, button2, button3, button4, button5, button6, button7, button8, button9, buttonDot)

        buttons.forEach { button ->
            button.setOnClickListener {
                it.startAnimation(bounceAnimation)  // Apply the bounce animation
                editTextResult.append((it as Button).text)
            }
        }

        buttonAdd.setOnClickListener {
            it.startAnimation(bounceAnimation)
            performOperation("+")
        }
        buttonSubtract.setOnClickListener {
            it.startAnimation(bounceAnimation)
            performOperation("-")
        }
        buttonMultiply.setOnClickListener {
            it.startAnimation(bounceAnimation)
            performOperation("*")
        }
        buttonDivide.setOnClickListener {
            it.startAnimation(bounceAnimation)
            performOperation("/")
        }
        buttonEquals.setOnClickListener {
            it.startAnimation(bounceAnimation)
            calculateResult()
        }
        buttonClear.setOnClickListener {
            it.startAnimation(bounceAnimation)
            editTextResult.text.clear()
        }
    }

    private fun performOperation(op: String) {
        firstValue = editTextResult.text.toString().toDoubleOrNull() ?: 0.0
        operator = op
        editTextResult.text.clear()
    }

    private fun calculateResult() {
        val secondValue = editTextResult.text.toString().toDoubleOrNull() ?: return
        val result = when (operator) {
            "+" -> firstValue + secondValue
            "-" -> firstValue - secondValue
            "*" -> firstValue * secondValue
            "/" -> if (secondValue != 0.0) firstValue / secondValue else "Error"
            else -> return
        }
        editTextResult.setText(result.toString())
    }
}
