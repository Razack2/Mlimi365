package com.example.mlimi365

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.mlimi365.model.FirebaseReminderRepository
import com.example.mlimi365.ui.navigation.AppNavigation
import com.example.mlimi365.ui.theme.Mlimi365Theme
import com.example.mlimi365.worker.WorkManagerScheduler

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        try {
            FirebaseReminderRepository.syncAllCropsToRemote()
            FirebaseReminderRepository.syncAllMonthsToRemote()

            WorkManagerScheduler.scheduleDailyReminderWork(applicationContext)
            WorkManagerScheduler.scheduleFirebaseSyncWork(applicationContext)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        setContent {
            Mlimi365Theme {
                AppNavigation()
            }
        }
    }
}
