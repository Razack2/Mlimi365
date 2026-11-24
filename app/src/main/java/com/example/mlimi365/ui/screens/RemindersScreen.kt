package com.example.mlimi365.ui.screens

import android.Manifest
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.mlimi365.model.FirebaseReminderRepository
import com.example.mlimi365.utils.ReminderScheduler
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

private val HomeGreen = Color(0xFF205338)
private val DarkText = Color(0xFF111827)
private val MutedText = Color(0xFF64748B)

data class ReminderItem(
    val id: String,
    val title: String,
    val dateText: String,
    val isCompleted: Boolean,
    val timestampMillis: Long = 0L,
)

private fun generateDefaultReminders(): List<ReminderItem> {
    val fullDateFormat = SimpleDateFormat("d MMM yyyy, hh:mm a", Locale.getDefault())

    fun getReminderData(daysOffset: Int, hour: Int = 9): Pair<String, Long> {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, daysOffset)
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return Pair(fullDateFormat.format(calendar.time), calendar.timeInMillis)
    }

    val (date0, ts0) = getReminderData(0, 10)
    val (date3, ts3) = getReminderData(3)
    val (date7, ts7) = getReminderData(7)
    val (date10, ts10) = getReminderData(10)
    val (date14, ts14) = getReminderData(14)

    return listOf(
        ReminderItem("1", "Prepare land for planting", date0, false, ts0),
        ReminderItem("2", "Check for weeds", date3, false, ts3),
        ReminderItem("3", "Apply fertilizer", date7, false, ts7),
        ReminderItem("4", "Monitor for pests", date10, false, ts10),
        ReminderItem("5", "First weeding", date14, false, ts14),
        ReminderItem("6", "Clear post-harvest residues", "Completed recently", true, 0L)
    )
}

private fun saveRemindersToPrefs(context: Context, reminders: List<ReminderItem>) {
    val prefs = context.getSharedPreferences("mlimi365_reminders_prefs", Context.MODE_PRIVATE)
    val set = reminders.map { "${it.id}:::${it.title}:::${it.dateText}:::${it.isCompleted}:::${it.timestampMillis}" }.toSet()
    prefs.edit().putStringSet("reminder_items_v2", set).apply()

    // Persist to local Room SQLite database for complete offline support and WorkManager
    try {
        val db = com.example.mlimi365.db.MlimiDatabase.getInstance(context)
        val currentEntities = db.reminderDao().getAllReminders()
        val newIds = reminders.map { it.id }.toSet()
        currentEntities.filter { it.id !in newIds }.forEach { db.reminderDao().deleteReminder(it) }
        reminders.forEach { item ->
            db.reminderDao().insertReminder(
                com.example.mlimi365.db.ReminderEntity(
                    id = item.id,
                    title = item.title,
                    dateText = item.dateText,
                    isCompleted = item.isCompleted,
                    timestampMillis = item.timestampMillis
                )
            )
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    FirebaseReminderRepository.syncAllRemindersToRemote(reminders)
}

private fun loadRemindersFromPrefs(context: Context): List<ReminderItem> {
    val prefs = context.getSharedPreferences("mlimi365_reminders_prefs", Context.MODE_PRIVATE)
    val set = prefs.getStringSet("reminder_items_v2", null)
    if (set != null) {
        val list = set.mapNotNull { itemStr ->
            val parts = itemStr.split(":::")
            if (parts.size >= 4) {
                ReminderItem(
                    id = parts[0],
                    title = parts[1],
                    dateText = parts[2],
                    isCompleted = parts[3].toBoolean(),
                    timestampMillis = parts.getOrNull(4)?.toLongOrNull() ?: 0L
                )
            } else null
        }
        if (list.isNotEmpty()) return list
    }

    // Fallback to local Room database if SharedPreferences was empty
    try {
        val db = com.example.mlimi365.db.MlimiDatabase.getInstance(context)
        val roomReminders = db.reminderDao().getAllReminders()
        if (roomReminders.isNotEmpty()) {
            val list = roomReminders.map {
                ReminderItem(it.id, it.title, it.dateText, it.isCompleted, it.timestampMillis)
            }
            saveRemindersToPrefs(context, list)
            return list
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    val defaults = generateDefaultReminders()
    saveRemindersToPrefs(context, defaults)
    return defaults
}

@Composable
fun RemindersScreen(
    onHomeClick: () -> Unit = {},
    onCalendarClick: () -> Unit = {},
    onSearchClick: () -> Unit = {}
) {
    var selectedTab by remember { mutableStateOf(0) }
    val context = LocalContext.current

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            ReminderScheduler.rescheduleAllReminders(context)
        }
    }

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                ReminderScheduler.rescheduleAllReminders(context)
            } else {
                permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        } else {
            ReminderScheduler.rescheduleAllReminders(context)
        }
    }

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
                    containerColor = HomeGreen,
                    contentColor = Color.White
                )
            ) {

                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add reminder",
                    modifier = Modifier.size(20.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Add Reminder",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
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
            onDismiss = {
                showAddDialog = false
            },
            onSave = { title, dateText, timestampMillis ->
                val newReminder = ReminderItem(
                    id = System.currentTimeMillis().toString(),
                    title = title,
                    dateText = dateText,
                    isCompleted = false,
                    timestampMillis = timestampMillis
                )

                reminders.add(newReminder)
                saveRemindersToPrefs(context, reminders)
                ReminderScheduler.scheduleReminder(context, newReminder)

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
                    ReminderScheduler.cancelReminder(context, reminder.id)
                    reminders[index] = updatedReminder
                    saveRemindersToPrefs(context, reminders)
                    if (!updatedReminder.isCompleted && (updatedReminder.timestampMillis > System.currentTimeMillis())) {
                        ReminderScheduler.scheduleReminder(context, updatedReminder)
                    }
                }

                selectedReminder = null
            },
            onDelete = {
                ReminderScheduler.cancelReminder(context, reminder.id)
                FirebaseReminderRepository.deleteReminderFromRemote(reminder.id)
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
                    val updatedCompleted = !reminder.isCompleted
                    val updatedReminder = reminder.copy(isCompleted = updatedCompleted)
                    reminders[index] = updatedReminder
                    saveRemindersToPrefs(context, reminders)
                    if (updatedCompleted) {
                        ReminderScheduler.cancelReminder(context, reminder.id)
                    } else if (updatedReminder.timestampMillis > System.currentTimeMillis()) {
                        ReminderScheduler.scheduleReminder(context, updatedReminder)
                    }
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
    onDismiss: () -> Unit,
    onSave: (title: String, dateText: String, timestampMillis: Long) -> Unit
) {
    val context = LocalContext.current
    var title by remember { mutableStateOf("") }

    val calendar = remember {
        Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
        }
    }

    var selectedTimeInMillis by remember { mutableLongStateOf(calendar.timeInMillis) }

    val dateFormat = remember { SimpleDateFormat("d MMM yyyy", Locale.getDefault()) }
    val timeFormat = remember { SimpleDateFormat("hh:mm a", Locale.getDefault()) }
    val fullDateFormat = remember { SimpleDateFormat("d MMM yyyy, hh:mm a", Locale.getDefault()) }

    var formattedDateText by remember {
        mutableStateOf(fullDateFormat.format(calendar.time))
    }

    fun updateDateTime() {
        selectedTimeInMillis = calendar.timeInMillis
        formattedDateText = fullDateFormat.format(calendar.time)
    }

    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                updateDateTime()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    val timePickerDialog = remember {
        TimePickerDialog(
            context,
            { _, hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
                updateDateTime()
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            false
        )
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color.White,
        titleContentColor = DarkText,
        textContentColor = DarkText,
        title = {
            Text(
                text = "Add Reminder",
                fontWeight = FontWeight.Bold,
                color = DarkText
            )
        },
        text = {
            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Reminder Title") },
                    placeholder = { Text("e.g. Apply fertilizer") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = DarkText,
                        unfocusedTextColor = DarkText,
                        focusedBorderColor = HomeGreen,
                        unfocusedBorderColor = Color(0xFFCBD5E1),
                        focusedLabelColor = HomeGreen,
                        unfocusedLabelColor = MutedText,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Select Date & Time",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkText
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = { datePickerDialog.show() },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, HomeGreen),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color.White,
                            contentColor = DarkText
                        ),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CalendarMonth,
                            contentDescription = "Select Date",
                            tint = HomeGreen,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = dateFormat.format(calendar.time),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkText,
                            maxLines = 1
                        )
                    }

                    OutlinedButton(
                        onClick = { timePickerDialog.show() },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, HomeGreen),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color.White,
                            contentColor = DarkText
                        ),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Select Time",
                            tint = HomeGreen,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = timeFormat.format(calendar.time),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkText,
                            maxLines = 1
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Scheduled for: $formattedDateText",
                    fontSize = 13.sp,
                    color = HomeGreen,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (title.isNotBlank()) {
                        onSave(title.trim(), formattedDateText, selectedTimeInMillis)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = HomeGreen,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Add Reminder", fontWeight = FontWeight.Bold, color = Color.White)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = HomeGreen, fontWeight = FontWeight.Bold)
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
    val context = LocalContext.current
    var title by remember(reminder.id) { mutableStateOf(reminder.title) }

    val calendar = remember(reminder.id) {
        Calendar.getInstance().apply {
            if (reminder.timestampMillis > 0L) {
                timeInMillis = reminder.timestampMillis
            } else {
                timeInMillis = System.currentTimeMillis()
            }
        }
    }

    var selectedTimeInMillis by remember(reminder.id) { mutableLongStateOf(calendar.timeInMillis) }

    val dateFormat = remember { SimpleDateFormat("d MMM yyyy", Locale.getDefault()) }
    val timeFormat = remember { SimpleDateFormat("hh:mm a", Locale.getDefault()) }
    val fullDateFormat = remember { SimpleDateFormat("d MMM yyyy, hh:mm a", Locale.getDefault()) }

    var formattedDateText by remember(reminder.id) {
        mutableStateOf(
            if (reminder.dateText.isNotBlank() && reminder.timestampMillis <= 0L) {
                reminder.dateText
            } else {
                fullDateFormat.format(calendar.time)
            }
        )
    }

    fun updateDateTime() {
        selectedTimeInMillis = calendar.timeInMillis
        formattedDateText = fullDateFormat.format(calendar.time)
    }

    val datePickerDialog = remember(reminder.id) {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                updateDateTime()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    val timePickerDialog = remember(reminder.id) {
        TimePickerDialog(
            context,
            { _, hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
                updateDateTime()
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            false
        )
    }

    val redColor = Color(0xFFDC2626)

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color.White,
        titleContentColor = DarkText,
        textContentColor = DarkText,
        title = {
            Text(
                text = "Edit Reminder",
                fontWeight = FontWeight.Bold,
                color = DarkText
            )
        },
        text = {
            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Reminder Title") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = DarkText,
                        unfocusedTextColor = DarkText,
                        focusedBorderColor = HomeGreen,
                        unfocusedBorderColor = Color(0xFFCBD5E1),
                        focusedLabelColor = HomeGreen,
                        unfocusedLabelColor = MutedText,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Select Date & Time",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkText
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = { datePickerDialog.show() },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, HomeGreen),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color.White,
                            contentColor = DarkText
                        ),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CalendarMonth,
                            contentDescription = "Select Date",
                            tint = HomeGreen,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = dateFormat.format(calendar.time),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkText,
                            maxLines = 1
                        )
                    }

                    OutlinedButton(
                        onClick = { timePickerDialog.show() },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, HomeGreen),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color.White,
                            contentColor = DarkText
                        ),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Select Time",
                            tint = HomeGreen,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = timeFormat.format(calendar.time),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkText,
                            maxLines = 1
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Scheduled for: $formattedDateText",
                    fontSize = 13.sp,
                    color = HomeGreen,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedButton(
                    onClick = onToggleComplete,
                    modifier = Modifier.fillMaxWidth(),
                    border = BorderStroke(1.dp, HomeGreen),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.White,
                        contentColor = HomeGreen
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = HomeGreen
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (reminder.isCompleted) {
                            "Mark as Upcoming"
                        } else {
                            "Mark as Completed"
                        },
                        color = HomeGreen,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedButton(
                    onClick = onDelete,
                    modifier = Modifier.fillMaxWidth(),
                    border = BorderStroke(1.dp, redColor),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.White,
                        contentColor = redColor
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        tint = redColor
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Delete Reminder",
                        color = redColor,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (title.isNotBlank()) {
                        onSave(
                            reminder.copy(
                                title = title.trim(),
                                dateText = formattedDateText,
                                timestampMillis = selectedTimeInMillis
                            )
                        )
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = HomeGreen,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Save Changes",
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = HomeGreen, fontWeight = FontWeight.Bold)
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