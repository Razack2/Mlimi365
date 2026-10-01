package com.example.mlimi365

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.mlimi365.ui.navigation.AppNavigation
import com.example.mlimi365.ui.theme.Mlimi365Theme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            Mlimi365Theme {
                AppNavigation()
            }
        }
    }
}