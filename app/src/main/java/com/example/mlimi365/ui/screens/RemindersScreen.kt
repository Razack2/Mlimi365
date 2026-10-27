package com.example.mlimi365.ui.screens

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val HomeGreen = Color(0xFF205338)
private val DarkText = Color(0xFF111827)
private val MutedText = Color(0xFF64748B)

data class ReminderItem(
    val id: String,
    val title: String,
    val dateText: String,
    val isCompleted: Boolean
)

private fun generateDefaultReminders(): List<ReminderItem> {
    val dateFormat = SimpleDateFormat("d MMM", Locale.getDefault())

    fun getDateString(daysOffset: Int): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, daysOffset)
        val formattedDate = dateFormat.format(calendar.time)
        return when (daysOffset) {
            0 -> "Today, $formattedDate"
            1 -> "Tomorrow, $formattedDate"
            else -> "In $daysOffset days, $formattedDate"
        }
    }

    return listOf(
        ReminderItem("1", "Prepare land for planting", getDateString(0), false),
        ReminderItem("2", "Check for weeds", getDateString(3), false),
        ReminderItem("3", "Apply fertilizer", getDateString(7), false),
        ReminderItem("4", "Monitor for pests", getDateString(10), false),
        ReminderItem("5", "First weeding", getDateString(14), false),
        ReminderItem("6", "Clear post-harvest residues", "Completed recently", true)
    )
}

private fun saveRemindersToPrefs(context: Context, reminders: List<ReminderItem>) {
    val prefs = context.getSharedPreferences("mlimi365_reminders_prefs", Context.MODE_PRIVATE)
    val set = reminders.map { "${it.id}:::${it.title}:::${it.dateText}:::${it.isCompleted}" }.toSet()
    prefs.edit().putStringSet("reminder_items_v2", set).apply()
}

private fun loadRemindersFromPrefs(context: Context): List<ReminderItem> {
    val prefs = context.getSharedPreferences("mlimi365_reminders_prefs", Context.MODE_PRIVATE)
    val set = prefs.getStringSet("reminder_items_v2", null)
    if (set == null) {
        val defaults = generateDefaultReminders()
        saveRemindersToPrefs(context, defaults)
        return defaults
    }
    return set.mapNotNull { itemStr ->
        val parts = itemStr.split(":::")
        if (parts.size == 4) {
            ReminderItem(
                id = parts[0],
                title = parts[1],
                dateText = parts[2],
                isCompleted = parts[3].toBoolean()
            )
        } else null
    }
}

@Composable
fun RemindersScreen(
    onHomeClick: () -> Unit = {},
    onCalendarClick: () -> Unit = {},
    onSearchClick: () -> Unit = {}
) {
    var selectedTab by remember { mutableStateOf(0) }
    val context = LocalContext.current

    val reminders = remember {
        mutableStateListOf<ReminderItem>().apply {
            addAll(loadRemindersFromPrefs(context))
        }
    }

    var showAddDialog by remember { mutableStateOf(false) }
    var selectedReminder by remember { mutableStateOf<ReminderItem?>(null) }

    val displayedReminders = reminders.filter {
        if (selectedTab == 0) {
            !it.isCompleted
        } else {
            it.isCompleted
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFCFA))
            .statusBarsPadding()
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 14.dp)
        ) {

            Text(
                text = "Reminders",
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                color = DarkText
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Upcoming / Completed tabs
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color(0xFFE2E8F0),
                        RoundedCornerShape(12.dp)
                    )
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                TabButton(
                    text = "Upcoming",
                    selected = selectedTab == 0,
                    modifier = Modifier.weight(1f),
                    onClick = {
                        selectedTab = 0
                    }
                )

                TabButton(
                    text = "Completed",
                    selected = selectedTab == 1,
                    modifier = Modifier.weight(1f),
                    onClick = {
                        selectedTab = 1
                    }
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            items(
                items = displayedReminders,
                key = { it.id }
            ) { reminder ->

                ReminderCard(
                    reminder = reminder,
                    onClick = {
                        selectedReminder = reminder
                    }
                )
            }

            if (displayedReminders.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (selectedTab == 0) {
                                "No upcoming reminders"
                            } else {
                                "No completed reminders"
                            },
                            fontSize = 14.sp,
                            color = MutedText
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 20.dp,
                    vertical = 8.dp
                )
        ) {

            Button(
                onClick = {
                    showAddDialog = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = HomeGreen
                )
            ) {

                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add reminder",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Add Reminder",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }


        RemindersBottomNavigation(
            onHomeClick = onHomeClick,
            onCalendarClick = onCalendarClick,
            onSearchClick = onSearchClick,
            onReminderClick = {}
        )
    }

    if (showAddDialog) {

        ReminderDialog(
            dialogTitle = "Add Reminder",
            initialTitle = "",
            initialDate = "",
            saveButtonText = "Add Reminder",

            onDismiss = {
                showAddDialog = false
            },

            onSave = { title, date ->

                val newReminder = ReminderItem(
                    id = System.currentTimeMillis().toString(),
                    title = title,
                    dateText = date,
                    isCompleted = false
                )

                reminders.add(newReminder)
                saveRemindersToPrefs(context, reminders)

                showAddDialog = false
                selectedTab = 0
            }
        )
    }


    selectedReminder?.let { reminder ->

        ReminderEditDialog(
            reminder = reminder,

            onDismiss = {
                selectedReminder = null
            },

            onSave = { updatedReminder ->

                val index = reminders.indexOfFirst {
                    it.id == updatedReminder.id
                }

                if (index != -1) {
                    reminders[index] = updatedReminder
                    saveRemindersToPrefs(context, reminders)
                }

                selectedReminder = null
            },

            onDelete = {

                reminders.removeAll {
                    it.id == reminder.id
                }
                saveRemindersToPrefs(context, reminders)

                selectedReminder = null
            },

            onToggleComplete = {

                val index = reminders.indexOfFirst {
                    it.id == reminder.id
                }

                if (index != -1) {

                    reminders[index] = reminder.copy(
                        isCompleted = !reminder.isCompleted
                    )
                    saveRemindersToPrefs(context, reminders)
                }

                selectedReminder = null
            }
        )
    }
}


@Composable
private fun ReminderCard(
    reminder: ReminderItem,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .background(
                            Color(0xFFEDF6F0),
                            RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {

                    Icon(
                        imageVector = if (reminder.isCompleted) {
                            Icons.Default.CheckCircle
                        } else {
                            Icons.Default.Notifications
                        },
                        contentDescription = "Reminder",
                        tint = HomeGreen,
                        modifier = Modifier.size(22.dp)
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column {

                    Text(
                        text = reminder.title,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkText
                    )

                    Text(
                        text = reminder.dateText,
                        fontSize = 12.sp,
                        color = MutedText
                    )
                }
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Edit reminder",
                tint = MutedText
            )
        }
    }
}


@Composable
private fun ReminderDialog(
    dialogTitle: String,
    initialTitle: String,
    initialDate: String,
    saveButtonText: String,
    onDismiss: () -> Unit,
    onSave: (String, String) -> Unit
) {

    var title by remember {
        mutableStateOf(initialTitle)
    }

    var date by remember {
        mutableStateOf(initialDate)
    }

    AlertDialog(
        onDismissRequest = onDismiss,

        title = {
            Text(
                text = dialogTitle,
                fontWeight = FontWeight.Bold
            )
        },

        text = {

            Column {

                OutlinedTextField(
                    value = title,
                    onValueChange = {
                        title = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text("Reminder")
                    },
                    placeholder = {
                        Text("e.g. Apply fertilizer")
                    },
                    singleLine = true
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                OutlinedTextField(
                    value = date,
                    onValueChange = {
                        date = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text("Date / Time")
                    },
                    placeholder = {
                        Text("e.g. Tomorrow, 10 Apr")
                    },
                    singleLine = true
                )
            }
        },

        confirmButton = {

            Button(
                onClick = {

                    if (
                        title.isNotBlank() &&
                        date.isNotBlank()
                    ) {
                        onSave(
                            title.trim(),
                            date.trim()
                        )
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = HomeGreen
                )
            ) {
                Text(
                    text = saveButtonText,
                    color = Color.White
                )
            }
        },

        dismissButton = {

            TextButton(
                onClick = onDismiss
            ) {
                Text("Cancel")
            }
        }
    )
}


@Composable
private fun ReminderEditDialog(
    reminder: ReminderItem,
    onDismiss: () -> Unit,
    onSave: (ReminderItem) -> Unit,
    onDelete: () -> Unit,
    onToggleComplete: () -> Unit
) {

    var title by remember(
        reminder.id
    ) {
        mutableStateOf(reminder.title)
    }

    var date by remember(
        reminder.id
    ) {
        mutableStateOf(reminder.dateText)
    }

    AlertDialog(
        onDismissRequest = onDismiss,

        title = {

            Text(
                text = "Edit Reminder",
                fontWeight = FontWeight.Bold
            )
        },

        text = {

            Column {

                OutlinedTextField(
                    value = title,
                    onValueChange = {
                        title = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text("Reminder")
                    },
                    singleLine = true
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                OutlinedTextField(
                    value = date,
                    onValueChange = {
                        date = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text("Date / Time")
                    },
                    singleLine = true
                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                OutlinedButton(
                    onClick = onToggleComplete,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null
                    )

                    Spacer(
                        modifier = Modifier.width(8.dp)
                    )

                    Text(
                        text = if (reminder.isCompleted) {
                            "Mark as Upcoming"
                        } else {
                            "Mark as Completed"
                        }
                    )
                }

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                OutlinedButton(
                    onClick = onDelete,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null
                    )

                    Spacer(
                        modifier = Modifier.width(8.dp)
                    )

                    Text("Delete Reminder")
                }
            }
        },

        confirmButton = {

            Button(
                onClick = {

                    if (
                        title.isNotBlank() &&
                        date.isNotBlank()
                    ) {

                        onSave(
                            reminder.copy(
                                title = title.trim(),
                                dateText = date.trim()
                            )
                        )
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = HomeGreen
                )
            ) {
                Text(
                    text = "Save Changes",
                    color = Color.White
                )
            }
        },

        dismissButton = {

            TextButton(
                onClick = onDismiss
            ) {
                Text("Cancel")
            }
        }
    )
}

@Composable
private fun TabButton(
    text: String,
    selected: Boolean,
    modifier: Modifier,
    onClick: () -> Unit
) {

    Box(
        modifier = modifier
            .background(
                color = if (selected) {
                    HomeGreen
                } else {
                    Color.Transparent
                },
                shape = RoundedCornerShape(10.dp)
            )
            .clickable {
                onClick()
            }
            .padding(vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {

        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = if (selected) {
                Color.White
            } else {
                MutedText
            }
        )
    }
}

@Composable
private fun RemindersBottomNavigation(
    onHomeClick: () -> Unit,
    onCalendarClick: () -> Unit,
    onSearchClick: () -> Unit,
    onReminderClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .navigationBarsPadding()
            .padding(
                horizontal = 24.dp,
                vertical = 10.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        NavigationItem(
            icon = Icons.Default.Home,
            label = "Home",
            active = false,
            onClick = onHomeClick
        )

        NavigationItem(
            icon = Icons.Default.CalendarMonth,
            label = "Calendar",
            active = false,
            onClick = onCalendarClick
        )

        NavigationItem(
            icon = Icons.Default.Search,
            label = "Search",
            active = false,
            onClick = onSearchClick
        )

        NavigationItem(
            icon = Icons.Default.Notifications,
            label = "Reminders",
            active = true,
            onClick = onReminderClick
        )
    }
}


@Composable
private fun NavigationItem(
    icon: ImageVector,
    label: String,
    active: Boolean,
    onClick: () -> Unit
) {

    val color = if (active) {
        HomeGreen
    } else {
        Color(0xFF94A3B8)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable {
            onClick()
        }
    ) {

        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = color,
            modifier = Modifier.size(24.dp)
        )

        Spacer(
            modifier = Modifier.height(3.dp)
        )

        Text(
            text = label,
            fontSize = 10.sp,
            fontWeight = if (active) {
                FontWeight.Bold
            } else {
                FontWeight.SemiBold
            },
            color = color
        )
    }
}