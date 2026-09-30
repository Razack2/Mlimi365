package com.example.mlimi365

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.mlimi365.ui.screens.SplashScreen
import com.example.mlimi365.ui.theme.Mlimi365Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Allows the app to draw behind the system status and navigation bars
        enableEdgeToEdge()

        setContent {
            Mlimi365Theme {
                SplashScreen()
            }
        }
    }
}