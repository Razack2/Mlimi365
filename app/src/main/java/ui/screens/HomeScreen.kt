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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Calendar

private val HomeGreen = Color(0xFF205338)
private val LightGreen = Color(0xFFEDF6F0)
private val DarkText = Color(0xFF111827)
private val MutedText = Color(0xFF64748B)

@Composable
fun HomeScreen(
    onCalendarClick: () -> Unit = {},
    onSearchClick: () -> Unit = {},
    onReminderClick: () -> Unit = {},
    onCropGuidesClick: () -> Unit = {},
    onCropClick: (String) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding()
    ) {
        // Main scrollable content
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            // Greeting
            GreetingHeader()

            Spacer(modifier = Modifier.height(18.dp))

            // Current farming period card
            CurrentPeriodCard(
                onClick = onCalendarClick
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Recommended crops
            Text(
                text = "Recommended Crops",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText
            )

            Spacer(modifier = Modifier.height(12.dp))

            RecommendedCrops(
                onCropClick = onCropClick
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Reminder
            ReminderCard(
                onClick = onReminderClick
            )

            Spacer(modifier = Modifier.height(20.dp))

            // View crop guides button
            Button(
                onClick = onCropGuidesClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(11.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = HomeGreen
                )
            ) {
                Text(
                    text = "View Crop Guides",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Bottom navigation
        HomeBottomNavigation(
            onHomeClick = {},
            onCalendarClick = onCalendarClick,
            onSearchClick = onSearchClick,
            onReminderClick = onReminderClick
        )
    }
}

@Composable
private fun GreetingHeader() {
    val (greetingText, weatherEmoji) = remember {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        when (hour) {
            in 5..11 -> "Good morning," to "☀"
            in 12..16 -> "Good afternoon," to "☀️"
            in 17..21 -> "Good evening," to "🌙"
            else -> "Good night," to "🌙"
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Column {
            Text(
                text = greetingText,
                fontSize = 17.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF64748B)
            )

            Text(
                text = "Farmer!",
                fontSize = 26.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF111827)
            )
        }

        Text(
            text = weatherEmoji,
            fontSize = 30.sp
        )
    }
}

@Composable
private fun CurrentPeriodCard(
    onClick: () -> Unit
) {
    // Dynamically calculates the season based on the current device date
    val period = remember { getCurrentFarmingPeriod() }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = LightGreen,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(48.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = period.emoji,
                    fontSize = 30.sp
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column {
                Text(
                    text = "CURRENT PERIOD",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2E6D49)
                )

                Text(
                    text = period.title,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkText
                )

                Text(
                    text = period.dateRange,
                    fontSize = 13.sp,
                    color = MutedText
                )
            }
        }

        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = "View period",
            tint = MutedText,
            modifier = Modifier.size(22.dp)
        )
    }
}

@Composable
private fun RecommendedCrops(
    onCropClick: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        CropItem(
            name = "Maize",
            emoji = "🌽",
            background = Color(0xFFEAF3EB),
            modifier = Modifier.weight(1f),
            onClick = { onCropClick("Maize") }
        )

        CropItem(
            name = "Beans",
            emoji = "🫘",
            background = Color(0xFFE6EFF3),
            modifier = Modifier.weight(1f),
            onClick = { onCropClick("Beans") }
        )

        CropItem(
            name = "Groundnuts",
            emoji = "🥜",
            background = Color(0xFFFBF0E4),
            modifier = Modifier.weight(1f),
            onClick = { onCropClick("Groundnuts") }
        )

        CropItem(
            name = "Soybeans",
            emoji = "🌱",
            background = Color(0xFFE8F3EA),
            modifier = Modifier.weight(1f),
            onClick = { onCropClick("Soybeans") }
        )
    }
}

@Composable
private fun CropItem(
    name: String,
    emoji: String,
    background: Color,
    modifier: Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
                .background(
                    color = background,
                    shape = RoundedCornerShape(12.dp)
                )
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = emoji,
                fontSize = 30.sp
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = name,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF334155)
        )
    }
}

@Composable
private fun ReminderCard(
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color.White,
                RoundedCornerShape(16.dp)
            )
            .clickable { onClick() }
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(
                        Color(0xFFFAEBD7),
                        RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Reminder",
                    tint = Color(0xFF925528),
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = "Today's Reminder",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0F172A)
                )

                Text(
                    text = "Prepare land for planting.",
                    fontSize = 13.sp,
                    color = Color(0xFF475569)
                )

                Text(
                    text = "Tap to view details",
                    fontSize = 11.sp,
                    color = Color(0xFF94A3B8)
                )
            }
        }

        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = "View reminder",
            tint = Color(0xFF94A3B8)
        )
    }
}

@Composable
private fun HomeBottomNavigation(
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
            active = true,
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
            active = false,
            onClick = onReminderClick
        )
    }
}

@Composable
private fun NavigationItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
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

private data class FarmingPeriod(
    val title: String,
    val dateRange: String,
    val emoji: String
)

private fun getCurrentFarmingPeriod(): FarmingPeriod {
    val month = Calendar.getInstance().get(Calendar.MONTH) // 0 = Jan, 11 = Dec

    return when (month) {
        // Nov (10) to Apr (3) -> Rainy / Planting Season
        Calendar.NOVEMBER, Calendar.DECEMBER, Calendar.JANUARY,
        Calendar.FEBRUARY, Calendar.MARCH, Calendar.APRIL -> FarmingPeriod(
            title = "Rainy Season",
            dateRange = "Nov – Apr",
            emoji = "🌧"
        )
        // May (4) to Jul (6) -> Harvest Season
        Calendar.MAY, Calendar.JUNE, Calendar.JULY -> FarmingPeriod(
            title = "Harvest Season",
            dateRange = "May – Jul",
            emoji = "🌾"
        )
        // Aug (7) to Oct (9) -> Dry / Land Preparation Season
        else -> FarmingPeriod(
            title = "Dry Season",
            dateRange = "Aug – Oct",
            emoji = "☀️"
        )
    }
}