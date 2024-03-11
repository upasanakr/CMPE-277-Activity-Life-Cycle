package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    private var onRestartCounter by mutableStateOf(0)

    private lateinit var startActivityBResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var startActivityCResultLauncher: ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize the launcher for ActivityB
        startActivityBResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                onRestartCounter += 5
            }
        }

        // Initialize the launcher for ActivityC
        startActivityCResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                onRestartCounter += 10
            }
        }

        setContent {
            MyApplicationTheme {
                MainContent(threadCounter = onRestartCounter,
                    onNavigateToActivityB = {
                        startActivityBResultLauncher.launch(Intent(this@MainActivity, ActivityB::class.java))
                    },
                    onNavigateToActivityC = {
                        startActivityCResultLauncher.launch(Intent(this@MainActivity, ActivityC::class.java))
                    },
                    onCloseApp = {
                        finishAffinity() // Close the app
                    })
            }
        }
    }

    override fun onRestart() {
        super.onRestart()
    }
}

@Composable
fun MainContent(threadCounter: Int, onNavigateToActivityB: () -> Unit, onNavigateToActivityC: () -> Unit, onCloseApp: () -> Unit) {
    var showDialog by remember { mutableStateOf(false) }

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
            Text("Activity A", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = onNavigateToActivityB) {
                Text("Go to Activity B")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = onNavigateToActivityC) {
                Text("Go to Activity C")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = { showDialog = true }) {
                Text("Show Dialog")
            }

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    confirmButton = {
                        TextButton(onClick = { showDialog = false }) {
                            Text("Close")
                        }
                    },
                    title = { Text("Simple Dialog") },
                    text = {
                        Text("This is a simple dialog.")
                    }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("Thread Counter: ${"%04d".format(threadCounter)}")

            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = onCloseApp) {
                Text("Close App")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        MainContent(threadCounter = 1, onNavigateToActivityB = {}, onNavigateToActivityC = {}, onCloseApp = {})
    }
}
