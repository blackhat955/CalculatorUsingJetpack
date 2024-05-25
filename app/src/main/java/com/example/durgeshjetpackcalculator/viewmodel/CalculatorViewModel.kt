package com.example.durgeshjetpackcalculator.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.durgeshjetpackcalculator.model.Calculator
import com.example.durgeshjetpackcalculator.model.Operation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CalculatorViewModel : ViewModel() {
    private val _mutableEraserState = MutableStateFlow(false)
    private val _mutableMemoryState = MutableStateFlow<Float?>(null)
    private val _mutableButtonState = MutableStateFlow(Operation.NONE)
    private val _mutableEntryState = MutableStateFlow("0")

    val buttonState = _mutableButtonState.asStateFlow()
    val entryState = _mutableEntryState.asStateFlow()

    fun onEvent(calculatorEvent: CalculatorEvent) {
        viewModelScope.launch {
            when (calculatorEvent) {
                CalculatorEvent.AllClear -> allClear()
                CalculatorEvent.Equals -> equals()
                is CalculatorEvent.Calculation -> {
                    when (calculatorEvent.operation) {
                        Operation.NONE -> TODO()
                        Operation.AC -> allClear()
                        Operation.PERCENTAGE1 -> percentage1()
                        Operation.COMMA -> addDecimalPoint()
                        Operation.PERCENTAGE -> percentage()
                        Operation.EQUALS -> equals()
                        else -> selectOperator(calculatorEvent.operation)
                    }

                }

                is CalculatorEvent.Number -> {
                    enterNumber(calculatorEvent.value)
                }
            }
        }
    }

    private fun addDecimalPoint() {
        if (_mutableEraserState.value.not()) {
            val currentValue = _mutableEntryState.value ?: ""
            if (!currentValue.contains(".")) {
                if (currentValue.isEmpty()) {
                    _mutableEntryState.value = "0."
                } else {
                    _mutableEntryState.value = "$currentValue."
                }
            }
        }
    }


    private fun enterNumber(entry: Int) {
        viewModelScope.launch {
            if (_mutableEntryState.value.length < 12) {
                if (_mutableEraserState.value) {
                    _mutableEntryState.value = "0"
                    _mutableEraserState.value = false
                }
                val valueBuilder = StringBuilder()
                if (_mutableEntryState.value != "0") {
                    valueBuilder
                        .append(_mutableEntryState.value)
                }
                valueBuilder.append(entry)
                _mutableEntryState.value = valueBuilder.toString()
            }
        }
    }

    private fun allClear() {
        print("working")
        _mutableEntryState.value = "0"
        _mutableMemoryState.value = null

    }

    private fun percentage() {
        if (_mutableEraserState.value.not()) {
            _mutableEntryState.value = (entryState.value.toFloat() / 100).toString()
    }
}



    private fun percentage1() {
//        if (_mutableEraserState.value.not()) {
//            _mutableEntryState.value = (entryState.value.toFloat() / 100).toString()
//        }

        println("this is working")
        if (_mutableEraserState.value.not()) {
            val currentValue = _mutableEntryState.value
            if (!currentValue.isNullOrEmpty()) {
                try {
                    val number = currentValue.toFloat()
                    val toggledNumber = -number
                    _mutableEntryState.value = toggledNumber.toString()
                } catch (e: NumberFormatException) {
                    // Handle the case where the current value is not a valid number
                    _mutableEntryState.value = "Error"
                }
            }
        }
    }

    private fun toggleSign() {
        println("this is working")
        if (_mutableEraserState.value.not()) {
            val currentValue = _mutableEntryState.value
            if (!currentValue.isNullOrEmpty()) {
                try {
                    val number = currentValue.toFloat()
                    val toggledNumber = -number
                    _mutableEntryState.value = toggledNumber.toString()
                } catch (e: NumberFormatException) {
                    // Handle the case where the current value is not a valid number
                    _mutableEntryState.value = "Error"
                }
            }
        }
    }

    private fun equals() {
        val total = calculation(
            _mutableMemoryState.value ?: 0f,
            _mutableEntryState.value.toFloat(),
            _mutableButtonState.value
        )
        _mutableMemoryState.value = total
        _mutableEntryState.value = total.toString()
        _mutableEraserState.value = true
        _mutableButtonState.value = Operation.NONE
    }

    private fun selectOperator(
        button: Operation
    ) {
        if (_mutableEraserState.value.not())  {
            if (_mutableMemoryState.value == null) {
                _mutableMemoryState.value = _mutableEntryState.value.toFloat()
            } else {
                val total = calculation(
                    actual = _mutableMemoryState.value ?: 0f,
                    entry = _mutableEntryState.value.toFloat(),
                    _mutableButtonState.value
                )
                _mutableMemoryState.value = total
                _mutableEntryState.value = total.toString()
            }
            _mutableButtonState.value = button
            _mutableEraserState.value = true
        } else  {
            _mutableButtonState.value = button
        }
    }

    private fun calculation(
        actual: Float,
        entry: Float,
        currentOperation: Operation
    ): Float {
        val calculation = when (currentOperation) {
            Operation.DIVISION -> Calculator.Division(actual, entry)()
            Operation.MULTIPLICATION -> Calculator.Multiplication(actual, entry)()
            Operation.SUBTRACTION -> Calculator.Subtraction(actual, entry)()
            Operation.ADDITION -> Calculator.Addition(actual, entry)()
            Operation.PERCENTAGE1->Calculator.ToggleSign(actual)()
            Operation.PERCENTAGE -> {
                percentage1()
                0f
            }
            else -> 0f
        }
        return calculation
    }
}

sealed class CalculatorEvent{
    data class Number(val value: Int) : CalculatorEvent()
    data class Calculation(val operation: Operation): CalculatorEvent()
    object Equals : CalculatorEvent()
    object AllClear : CalculatorEvent()
}