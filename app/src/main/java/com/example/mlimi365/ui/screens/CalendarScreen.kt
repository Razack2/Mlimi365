package com.example.mlimi365.ui.screens

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
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mlimi365.model.MonthInfo
import com.example.mlimi365.model.OfflineFarmingRepository
import java.util.Calendar

private val HomeGreen = Color(0xFF205338)
private val LightGreen = Color(0xFFEDF6F0)
private val DarkText = Color(0xFF111827)
private val MutedText = Color(0xFF64748B)

@Composable
fun CalendarScreen(
    onHomeClick: () -> Unit = {},
    onSearchClick: () -> Unit = {},
    onReminderClick: () -> Unit = {},
    onCropClick: (String) -> Unit = {}
) {
    val currentMonthNumber = remember {
        Calendar.getInstance().get(Calendar.MONTH) + 1 // 1 to 12
    }
    val months = remember { OfflineFarmingRepository.calendarMonths }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFCFA))
            .statusBarsPadding()
    ) {
        // Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 14.dp)
        ) {
            Text(
                text = "Farming Calendar",
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                color = DarkText
            )
            Text(
                text = "January to December seasonal guide (Offline)",
                fontSize = 13.sp,
                color = MutedText
            )
        }

        // Scrollable Month List
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(months) { month ->
                val isCurrent = month.monthNumber == currentMonthNumber
                MonthCard(
                    month = month,
                    isCurrent = isCurrent,
                    onCropClick = onCropClick
                )
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        // Bottom Navigation
        CalendarBottomNavigation(
            onHomeClick = onHomeClick,
            onCalendarClick = {},
            onSearchClick = onSearchClick,
            onReminderClick = onReminderClick
        )
    }
}

@Composable
private fun MonthCard(
    month: MonthInfo,
    isCurrent: Boolean,
    onCropClick: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isCurrent) LightGreen else Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isCurrent) 3.dp else 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = if (isCurrent) HomeGreen else Color(0xFFE2E8F0),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = month.monthName,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isCurrent) Color.White else DarkText
                        )
                    }

                    if (isCurrent) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .background(
                                    color = Color(0xFFD1FAE5),
                                    shape = RoundedCornerShape(6.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = "Current Month",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF065F46)
                            )
                        }
                    }
                }

                Text(
                    text = month.seasonType,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = HomeGreen
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = month.periodTitle,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = month.description,
                fontSize = 13.sp,
                color = MutedText
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Key Activities:",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText
            )

            month.activities.forEach { activity ->
                Text(
                    text = "• $activity",
                    fontSize = 12.sp,
                    color = MutedText,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            if (month.suitableCrops.isEmpty()) {
                Text(
                    text = "No specific crops associated for this period",
                    fontSize = 11.sp,
                    color = MutedText
                )
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    month.suitableCrops.forEach { crop ->
                        Box(
                            modifier = Modifier
                                .background(
                                    color = Color(0xFFF1F5F9),
                                    shape = RoundedCornerShape(6.dp)
                                )
                                .clickable { onCropClick(crop) }
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = crop,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF334155)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CalendarBottomNavigation(
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
            .padding(horizontal = 24.dp, vertical = 10.dp),
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
            active = true,
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
            active = false,
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
    val color = if (active) HomeGreen else Color(0xFF94A3B8)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = color,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.height(3.dp))

        Text(
            text = label,
            fontSize = 10.sp,
            fontWeight = if (active) FontWeight.Bold else FontWeight.SemiBold,
            color = color
        )
    }
}
