package com.vimal.caculator

import android.util.Log
import androidx.compose.ui.graphics.Path
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.mozilla.javascript.Context
import org.mozilla.javascript.Scriptable

class CalculatorViewModel: ViewModel() {
    // Variables to hold the operands and type of calculation
    private val _equation = MutableLiveData<String>("")
    val equation: LiveData<String> = _equation

    // Variables to hold results
    private val _result = MutableLiveData<String>("0")
    val result: LiveData<String> = _result

    // Function to handle button clicks
    fun onButtonClick(btn: String) {
        Log.i("Clicked Button", btn)

        _equation.value?.let {
            if(btn == "AC") {
                _equation.value = ""
                _result.value = ""
                return
            }
            if(btn == "C") {
                if(it.isNotEmpty()) {
                    _equation.value = it.substring(0, it.length - 1)
                }
                return
            }
            if(btn == "=") {
                _equation.value = _result.value
                return
            }

            _equation.value += btn

            // Calculate Result
            try {
                _result.value = calculateResult(_equation.value.toString())
            } catch (e: Exception) {}
        }
    }
    fun calculateResult(equation: String): String {
        val context: Context = Context.enter()
        context.optimizationLevel = -1
        val scriptable: Scriptable = context.initStandardObjects()
        var finalResult = context.evaluateString(scriptable, equation, "Javascript", 1, null).toString()
        if(finalResult.endsWith(".0")) {
            finalResult = finalResult.replace(".0", "")
        }
        return finalResult
    }
}