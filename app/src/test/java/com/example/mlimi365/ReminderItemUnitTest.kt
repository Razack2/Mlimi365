package com.example.mlimi365

import com.example.mlimi365.ui.screens.ReminderItem
import org.junit.Assert.*
import org.junit.Test

class ReminderItemUnitTest {

    @Test
    fun testReminderItemToggleCompletion() {
        val reminder = ReminderItem("1", "Water crops", "Today", false)
        val completedReminder = reminder.copy(isCompleted = !reminder.isCompleted)
        assertTrue(completedReminder.isCompleted)
        val uncompletedReminder = completedReminder.copy(isCompleted = !completedReminder.isCompleted)
        assertFalse(uncompletedReminder.isCompleted)
    }
}
