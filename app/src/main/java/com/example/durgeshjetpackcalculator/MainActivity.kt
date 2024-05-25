package com.example.durgeshjetpackcalculator


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.durgeshjetpackcalculator.ui.theme.CalculatorComponent
import com.example.durgeshjetpackcalculator.ui.theme.CalculatorTheme
import com.example.durgeshjetpackcalculator.viewmodel.CalculatorViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: CalculatorViewModel = CalculatorViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorTheme {
                CalculatorComponent(viewModel)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CalculatorPreview() {
    val viewModel = CalculatorViewModel()
    CalculatorTheme {
        CalculatorComponent(viewModel)
    }
}



