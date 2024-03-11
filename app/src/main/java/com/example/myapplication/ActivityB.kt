package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme

class ActivityB : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {

                ActivityBScreen {
                    PreferencesManager.setCounterIncrement(this, 5)
                    finish()
                }
            }
        }
    }
}

@Composable
fun ActivityBScreen(onFinishClicked: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Activity B", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(32.dp))
            // Use ButtonDefaults.buttonColors to set the button color to brown
            Button(
                onClick = onFinishClicked,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF795548)) // Brown color
            ) {
                Text("Finish B")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ActivityBPreview() {
    MyApplicationTheme {

        ActivityBScreen {}
    }
}
