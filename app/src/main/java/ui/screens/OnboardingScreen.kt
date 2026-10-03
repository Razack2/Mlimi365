package com.example.mlimi365.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

private val BrandGreen = Color(0xFF326244)
private val BrandDark = Color(0xFF143121)
private val BrandMuted = Color(0xFF5E7265)
private val Background = Color(0xFFFAFCFA)
private val InactiveDot = Color(0xFFD4DED8)

private data class OnboardingItem(
    val title: String,
    val description: String,
    val illustrationType: Int
)

private val onboardingPages = listOf(
    OnboardingItem(
        title = "Plan Your Farming\nSeason",
        description = "January to December farming calendar showing important farming periods and suitable crops.",
        illustrationType = 0
    ),
    OnboardingItem(
        title = "Seasonal Farming\nReminders",
        description = "Get timely reminders for important seasonal farming activities like planting, fertilizing, and weeding.",
        illustrationType = 1
    ),
    OnboardingItem(
        title = "Crop Growing\nGuides",
        description = "Detailed guidance on planting, spacing, fertilizer application, weeding, and harvesting.",
        illustrationType = 2
    ),
    OnboardingItem(
        title = "Search Crops &\nInformation",
        description = "Quickly search for any crop and access its complete growing guide anytime.",
        illustrationType = 3
    ),
    OnboardingItem(
        title = "100% Offline\nAccess",
        description = "Access all main farming information offline without needing continuous internet connectivity.",
        illustrationType = 4
    )
)

@Composable
fun OnboardingScreen(
    onNext: () -> Unit = {},
    onSkip: () -> Unit = {}
) {
    val pagerState = rememberPagerState(pageCount = { onboardingPages.size })
    val coroutineScope = rememberCoroutineScope()
    val currentPageData = onboardingPages[pagerState.currentPage]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(horizontal = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        // Scrollable Pager containing circular functional illustrations
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
        ) { page ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                // Circular illustration container
                Box(
                    modifier = Modifier
                        .size(280.dp)
                        .background(Color(0xFFE8F1EB), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    FunctionIllustration(
                        type = onboardingPages[page].illustrationType,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Title
        Text(
            text = currentPageData.title,
            color = BrandDark,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 31.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Description
        Text(
            text = currentPageData.description,
            color = BrandMuted,
            fontSize = 14.sp,
            lineHeight = 21.sp
        )

        Spacer(modifier = Modifier.height(28.dp))

        // Page indicators
        RowDots(
            currentPage = pagerState.currentPage,
            pageCount = onboardingPages.size
        )

        Spacer(modifier = Modifier.height(30.dp))

        // Next button
        Button(
            onClick = {
                if (pagerState.currentPage < onboardingPages.size - 1) {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                } else {
                    onNext()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = BrandGreen
            )
        ) {
            Text(
                text = if (pagerState.currentPage == onboardingPages.size - 1) "Get Started" else "Next",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Skip
        Button(
            onClick = onSkip,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = BrandMuted
            )
        ) {
            Text(
                text = "Skip",
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Bottom indicator
        Box(
            modifier = Modifier
                .padding(bottom = 10.dp)
                .size(width = 120.dp, height = 4.dp)
                .background(
                    Color(0xFFD5D9D7),
                    RoundedCornerShape(10.dp)
                )
        )
    }
}

@Composable
private fun RowDots(
    currentPage: Int,
    pageCount: Int
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(pageCount) { index ->
            val isActive = index == currentPage
            Box(
                modifier = Modifier
                    .size(if (isActive) 12.dp else 10.dp)
                    .background(
                        color = if (isActive) BrandGreen else InactiveDot,
                        shape = CircleShape
                    )
            )
        }
    }
}

@Composable
private fun FunctionIllustration(
    type: Int,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        when (type) {
            0 -> {
                // 1. Planning / Calendar & Farmer
                drawOval(
                    color = Color(0xFFD5E6DA),
                    topLeft = Offset(w * 0.15f, h * 0.58f),
                    size = Size(w * 0.70f, h * 0.14f)
                )
                drawRoundRect(
                    color = Color(0xFF366446),
                    topLeft = Offset(w * 0.35f, h * 0.48f),
                    size = Size(w * 0.30f, h * 0.25f),
                    cornerRadius = CornerRadius(16f, 16f)
                )
                drawCircle(
                    color = Color(0xFF2D543A),
                    radius = 22f,
                    center = Offset(w * 0.50f, h * 0.37f)
                )
                drawRoundRect(
                    color = Color(0xFF4A7958),
                    topLeft = Offset(w * 0.42f, h * 0.30f),
                    size = Size(w * 0.16f, 22f),
                    cornerRadius = CornerRadius(8f, 8f)
                )
                drawLine(
                    color = Color(0xFF7E9B86),
                    start = Offset(w * 0.43f, h * 0.73f),
                    end = Offset(w * 0.68f, h * 0.25f),
                    strokeWidth = 6f
                )
                val hoe = Path().apply {
                    moveTo(w * 0.65f, h * 0.28f)
                    lineTo(w * 0.73f, h * 0.22f)
                    lineTo(w * 0.76f, h * 0.27f)
                    lineTo(w * 0.68f, h * 0.34f)
                    close()
                }
                drawPath(path = hoe, color = Color(0xFF3D6A4B))
            }
            1 -> {
                // 2. Reminders & Activities (Bell / Calendar badge)
                drawRoundRect(
                    color = Color(0xFF366446),
                    topLeft = Offset(w * 0.30f, h * 0.30f),
                    size = Size(w * 0.40f, h * 0.40f),
                    cornerRadius = CornerRadius(16f, 16f)
                )
                drawCircle(
                    color = Color(0xFFE5A93B),
                    radius = 18f,
                    center = Offset(w * 0.50f, h * 0.38f)
                )
                drawLine(
                    color = Color(0xFFE8F1EB),
                    start = Offset(w * 0.38f, h * 0.52f),
                    end = Offset(w * 0.62f, h * 0.52f),
                    strokeWidth = 4f
                )
                drawLine(
                    color = Color(0xFFE8F1EB),
                    start = Offset(w * 0.38f, h * 0.60f),
                    end = Offset(w * 0.54f, h * 0.60f),
                    strokeWidth = 4f
                )
            }
            2 -> {
                // 3. Crop Growing Guides (Leaf & Check)
                val leafPath = Path().apply {
                    moveTo(w * 0.50f, h * 0.22f)
                    quadraticTo(w * 0.75f, h * 0.30f, w * 0.75f, h * 0.55f)
                    quadraticTo(w * 0.75f, h * 0.80f, w * 0.50f, h * 0.85f)
                    quadraticTo(w * 0.25f, h * 0.80f, w * 0.25f, h * 0.55f)
                    quadraticTo(w * 0.25f, h * 0.30f, w * 0.50f, h * 0.22f)
                    close()
                }
                drawPath(path = leafPath, color = Color(0xFF366446))

                drawLine(
                    color = Color(0xFF7E9B86),
                    start = Offset(w * 0.50f, h * 0.30f),
                    end = Offset(w * 0.50f, h * 0.78f),
                    strokeWidth = 4f
                )
                drawCircle(
                    color = Color(0xFF4A7958),
                    radius = 20f,
                    center = Offset(w * 0.68f, h * 0.68f)
                )
            }
            3 -> {
                // 4. Search Crops (Magnifying glass & list)
                drawCircle(
                    color = Color(0xFF366446),
                    radius = 32f,
                    center = Offset(w * 0.45f, h * 0.45f)
                )
                drawCircle(
                    color = Color(0xFFE8F1EB),
                    radius = 22f,
                    center = Offset(w * 0.45f, h * 0.45f)
                )
                drawLine(
                    color = Color(0xFF366446),
                    start = Offset(w * 0.60f, h * 0.60f),
                    end = Offset(w * 0.75f, h * 0.75f),
                    strokeWidth = 8f
                )
            }
            else -> {
                // 5. 100% Offline Access (Cloud with check / shield)
                drawCircle(
                    color = Color(0xFF366446),
                    radius = 36f,
                    center = Offset(w * 0.50f, h * 0.45f)
                )
                drawCircle(
                    color = Color(0xFF4A7958),
                    radius = 26f,
                    center = Offset(w * 0.38f, h * 0.52f)
                )
                drawCircle(
                    color = Color(0xFF4A7958),
                    radius = 28f,
                    center = Offset(w * 0.62f, h * 0.52f)
                )
                // Checkmark / offline indicator
                drawLine(
                    color = Color(0xFFE8F1EB),
                    start = Offset(w * 0.42f, h * 0.46f),
                    end = Offset(w * 0.48f, h * 0.52f),
                    strokeWidth = 5f
                )
                drawLine(
                    color = Color(0xFFE8F1EB),
                    start = Offset(w * 0.48f, h * 0.52f),
                    end = Offset(w * 0.58f, h * 0.38f),
                    strokeWidth = 5f
                )
            }
        }
    }
}
