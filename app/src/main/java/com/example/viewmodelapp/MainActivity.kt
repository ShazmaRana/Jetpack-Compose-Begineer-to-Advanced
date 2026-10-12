package com.example.viewmodelapp

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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.viewmodelapp.ui.theme.ViewModelAppTheme
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ViewModelAppTheme {

                PasswordStrength_AnalyzerScreen()


            }
        }
    }
}
@Composable
fun PasswordStrength_AnalyzerScreen(
    viewModel: PasswordVM = viewModel()
) {
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current




    Column(
        modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp).height(500.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {


            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(10.dp), verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                Box(
                    modifier = Modifier.fillMaxWidth().padding(top = 40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Password Strength Analyzer", fontSize = 18.sp,
                        fontFamily = FontFamily.Monospace,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,


                        )
                }




                Text(
                    "Enter a password to check it's strength",
                    modifier = Modifier.padding(top = 20.dp),
                    textAlign = TextAlign.Left,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )


                OutlinedTextField(
                    value = viewModel.password,
                    onValueChange = viewModel::onPasswordChange,      // event goes UP
                    visualTransformation = if (viewModel.isPasswordVisible)
                        VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = viewModel::togglePasswordVisibility) {
                            Icon(
                                imageVector = if (viewModel.isPasswordVisible)
                                    Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = "Toggle password visibility"
                            )
                        }
                    },
                    label = { Text("Enter your password") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = if (viewModel.password.isBlank()) "Enter a password"
                    else "${viewModel.strengthLabel} Password",
                    fontWeight = FontWeight.Bold
                )

                LinearProgressIndicator(
                    progress = { viewModel.strength },                // state comes DOWN
                    modifier = Modifier.fillMaxWidth()
                )

                // Checklist, just read the booleans
                Text(if (viewModel.hasMinLength) "✅ 8+ Characters" else "❌ 8+ Characters")
                Text(if (viewModel.hasUpper) "✅ Uppercase" else "❌ Uppercase")
                Text(if (viewModel.hasLower) "✅ Lowercase" else "❌ Lowercase")
                Text(if (viewModel.hasDigit) "✅ Numbers" else "❌ Numbers")
                Text(if (viewModel.hasSymbol) "✅ Symbols" else "❌ Symbols")
            }
        }
    }
}

@Preview(showBackground = true , showSystemUi = true)
@Composable
private fun PasswordStrength_AnalyzerScreenPrev() {
    ViewModelAppTheme {

PasswordStrength_AnalyzerScreen()

    }
}