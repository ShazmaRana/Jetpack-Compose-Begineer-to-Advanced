package com.example.state_hoisting_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.state_hoisting_app.ui.theme.State_Hoisting_AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            State_Hoisting_AppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        ExpenseScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun ExpenseScreen() {
    var amount by remember { mutableStateOf("") }
    var people by remember { mutableStateOf("") }
    var result by remember { mutableStateOf(0.0) } // Changed to Double to match ExpenseResult

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

Text("Expense Splitter" , color = MaterialTheme.colorScheme.primary , fontSize = 30.sp , fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 50.dp))
        
        Box(
            modifier = Modifier
                .offset(y = (20.dp))
                .size(120.dp)
                .shadow(8.dp, shape = CircleShape)
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Payments,
                contentDescription = "Expense Icon",
                modifier = Modifier.size(56.dp),
                tint = MaterialTheme.colorScheme.onPrimary // Fixed color token for contrast
            )
        }


        Spacer(modifier = Modifier.padding(20.dp))
        ExpenseInput(
            amountStr = amount,
            onAmountChange = { amount = it }
        )

        PeopleInput(
            peopleStr = people,
            onPeopleChange = { people = it } // Fixed typo in parameter name
        )

        CalculateButton(
            onCalculate = {
                val amountValue = amount.toDoubleOrNull() ?: 0.0
                val peopleValue = people.toIntOrNull() ?: 1
                result = if (peopleValue > 0) amountValue / peopleValue else 0.0
            }
        )

        ExpenseResult(result = result)
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun ExpenseScreenPrev() {
    State_Hoisting_AppTheme {
        ExpenseScreen()
    }
}

@Composable
fun ExpenseInput(
    amountStr: String,
    onAmountChange: (String) -> Unit
) {
    InputField(
        label = "Enter Amount: $",
        value = amountStr,
        placeholder = "e.g 100",
        icon = Icons.Default.AttachMoney,
        keyboardType = KeyboardType.Number, // Added appropriate keyboard type
        onValueChange = onAmountChange
    )
}

@Composable
fun PeopleInput(
    peopleStr: String,
    onPeopleChange: (String) -> Unit
) {
    InputField(
        label = "Enter no of people",
        value = peopleStr,
        placeholder = "e.g : 2",
        icon = Icons.Default.People,
        keyboardType = KeyboardType.Number, // Added appropriate keyboard type
        onValueChange = onPeopleChange
    )
}

@Composable
private fun InputField(
    label: String,
    value: String,
    placeholder: String,
    icon: ImageVector,
    keyboardType: KeyboardType = KeyboardType.Text,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        leadingIcon = { Icon(icon, contentDescription = null) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun CalculateButton(onCalculate: () -> Unit) {
    Button(
        onClick = onCalculate,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Calculate")
    }
}

@Composable
fun ExpenseResult(result: Double) {
    Text(
        text = "Each person pays: $. ${String.format("%.2f", result)}",
        style = MaterialTheme.typography.titleMedium,
        fontSize = 24.sp,
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Medium,
        modifier = Modifier.padding(top = 30.dp)
    )
}
