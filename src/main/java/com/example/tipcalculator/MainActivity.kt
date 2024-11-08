package com.example.tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tipcalculator.ui.theme.TipCalculatorTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import java.text.NumberFormat
import  androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Switch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipCalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TextText()
                }
            }
        }
    }
}

@Composable
fun TextText() {
    var roundUpTwo by remember { mutableStateOf(false) }
    var totalAmount by remember { mutableStateOf("0") }
    var tip by remember { mutableStateOf("0") }

    var tipTwo = tip.toDoubleOrNull() ?: 0.0
    var amount = totalAmount.toDoubleOrNull() ?: 0.0
    var tipAmount = calculateTip(tipPercent = tipTwo, amount = amount, roundUp = roundUpTwo)
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = stringResource(id = R.string.Calculate_Tip),
            modifier = Modifier
                .padding(16.dp)

        )
        EditNumberField(
            value = totalAmount,
            valueOnChange = { totalAmount = it},
            name = stringResource(id = R.string.Text_Label)
        )
        Spacer(
            modifier = Modifier
                .padding(8.dp)
        )
        EditNumberField(
            value = tip,
            valueOnChange = { tip = it},
            name = stringResource(id = R.string.Tip_Label)
        )
        SwitchRound(
            roundUp = roundUpTwo,
            onRoundUpChanged = { roundUpTwo = it }
        )
        Text(
            text = stringResource(id = R.string.Tip_Amount, tipAmount),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxSize()
        )
    }
}

@Composable
fun EditNumberField(value: String, valueOnChange: (String) -> Unit, name: String) {
    TextField(
        value = value,
        onValueChange = valueOnChange,
        label = { Text(name) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
    )
}

@Composable
fun SwitchRound(roundUp: Boolean, onRoundUpChanged:(Boolean) -> Unit){
    Row(verticalAlignment = Alignment.CenterVertically
        ) {
        Text(
            text = "Round up",
        )
        Spacer(modifier = Modifier
            .padding(32.dp)
        )
        Switch(
            checked = roundUp,
            onCheckedChange = onRoundUpChanged
        )
    }
}


fun calculateTip(tipPercent: Double, amount:Double, roundUp: Boolean): String {
//    100 x tipPercent/100
    var tipAmount = (tipPercent/100) * amount
    if (roundUp) {
        tipAmount = kotlin.math.ceil(tipAmount)
    }

    return NumberFormat.getCurrencyInstance().format(tipAmount)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TipCalculatorTheme {
        TextText()
    }
}