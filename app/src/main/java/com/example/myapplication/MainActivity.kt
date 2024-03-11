package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                MainContent(onRestartCounter) {
                    when (it) {
                        "ActivityB" -> {
                            startActivity(Intent(this, ActivityB::class.java))
                        }
                        "ActivityC" -> {
                            startActivity(Intent(this, ActivityC::class.java))
                        }
                        "CloseApp" -> {
                            finishAffinity() // Close the app
                        }
                    }
                }
            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        val increment = PreferencesManager.getAndClearCounterIncrement(this)
        onRestartCounter += increment
    }
}

@Composable
fun MainContent(threadCounter: Int, navigate: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Activity A", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navigate("ActivityB") }) {
            Text("Go to Activity B")
        }
        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { navigate("ActivityC") }) {
            Text("Go to Activity C")
        }
        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { navigate("CloseApp") }) {
            Text("Close App")
        }
        Spacer(modifier = Modifier.height(24.dp))

        Text("Thread Counter: ${"%04d".format(threadCounter)}")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        MainContent(threadCounter = 1) {}
    }
}

object PreferencesManager {
    private const val PREFS_NAME = "activity_counter_prefs"
    private const val COUNTER_INCREMENT_KEY = "counter_increment"

    fun setCounterIncrement(context: Context, increment: Int) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().putInt(COUNTER_INCREMENT_KEY, increment).apply()
    }

    fun getAndClearCounterIncrement(context: Context): Int {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val increment = sharedPreferences.getInt(COUNTER_INCREMENT_KEY, 0)
        sharedPreferences.edit().remove(COUNTER_INCREMENT_KEY).apply()
        return increment
    }
}
