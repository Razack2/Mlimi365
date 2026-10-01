package com.example.mlimi365.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight

private val BrandGreen = Color(0xFF326244)
private val BrandDark = Color(0xFF143121)
private val BrandMuted = Color(0xFF5E7265)
private val Background = Color(0xFFFAFCFA)
private val InactiveDot = Color(0xFFD4DED8)

@Composable
fun OnboardingScreen(
    onNext: () -> Unit = {},
    onSkip: () -> Unit = {}
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(horizontal = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        // Farmer illustration
        FarmerIllustration(
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Title
        Text(
            text = "Plan Your Farming\nSeason",
            color = BrandDark,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 31.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Description
        Text(
            text = "Get the right crops, at the right time,\nwith simple guidance.",
            color = BrandMuted,
            fontSize = 14.sp,
            lineHeight = 21.sp
        )

        Spacer(modifier = Modifier.height(28.dp))

        // Page indicators
        RowDots()

        Spacer(modifier = Modifier.height(30.dp))

        // Next button
        Button(
            onClick = onNext,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = BrandGreen
            )
        ) {
            Text(
                text = "Next",
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
private fun RowDots() {

    androidx.compose.foundation.layout.Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // Active dot
        Box(
            modifier = Modifier
                .size(10.dp)
                .background(
                    BrandGreen,
                    CircleShape
                )
        )

        // Inactive dots
        repeat(4) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .background(
                        InactiveDot,
                        CircleShape
                    )
            )
        }
    }
}

@Composable
private fun FarmerIllustration(
    modifier: Modifier = Modifier
) {

    Canvas(
        modifier = modifier
    ) {

        val w = size.width
        val h = size.height

        // Background hills
        drawOval(
            color = Color(0xFFE8F1EB),
            topLeft = androidx.compose.ui.geometry.Offset(
                -w * 0.25f,
                h * 0.48f
            ),
            size = androidx.compose.ui.geometry.Size(
                w * 0.65f,
                h * 0.20f
            )
        )

        drawOval(
            color = Color(0xFFE8F1EB),
            topLeft = androidx.compose.ui.geometry.Offset(
                w * 0.48f,
                h * 0.45f
            ),
            size = androidx.compose.ui.geometry.Size(
                w * 0.70f,
                h * 0.23f
            )
        )

        // Ground
        drawOval(
            color = Color(0xFFE2EEE6),
            topLeft = androidx.compose.ui.geometry.Offset(
                w * 0.08f,
                h * 0.58f
            ),
            size = androidx.compose.ui.geometry.Size(
                w * 0.84f,
                h * 0.12f
            )
        )

        // Farmer body
        drawRoundRect(
            color = Color(0xFF366446),
            topLeft = androidx.compose.ui.geometry.Offset(
                w * 0.32f,
                h * 0.48f
            ),
            size = androidx.compose.ui.geometry.Size(
                w * 0.32f,
                h * 0.25f
            ),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(
                18f,
                18f
            )
        )

        // Farmer head
        drawCircle(
            color = Color(0xFF2D543A),
            radius = 24f,
            center = androidx.compose.ui.geometry.Offset(
                w * 0.48f,
                h * 0.37f
            )
        )

        // Hat
        drawRoundRect(
            color = Color(0xFF4A7958),
            topLeft = androidx.compose.ui.geometry.Offset(
                w * 0.40f,
                h * 0.30f
            ),
            size = androidx.compose.ui.geometry.Size(
                w * 0.16f,
                25f
            ),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(
                10f,
                10f
            )
        )

        // Hat brim
        drawOval(
            color = Color(0xFF588B68),
            topLeft = androidx.compose.ui.geometry.Offset(
                w * 0.35f,
                h * 0.35f
            ),
            size = androidx.compose.ui.geometry.Size(
                w * 0.26f,
                14f
            )
        )

        // Hoe handle
        drawLine(
            color = Color(0xFF7E9B86),
            start = androidx.compose.ui.geometry.Offset(
                w * 0.43f,
                h * 0.73f
            ),
            end = androidx.compose.ui.geometry.Offset(
                w * 0.68f,
                h * 0.25f
            ),
            strokeWidth = 6f
        )

        // Hoe head
        val hoe = Path().apply {
            moveTo(w * 0.65f, h * 0.28f)
            lineTo(w * 0.73f, h * 0.22f)
            lineTo(w * 0.76f, h * 0.27f)
            lineTo(w * 0.68f, h * 0.34f)
            close()
        }

        drawPath(
            path = hoe,
            color = Color(0xFF3D6A4B)
        )

        // Farmer arms
        drawLine(
            color = Color(0xFF366446),
            start = androidx.compose.ui.geometry.Offset(
                w * 0.37f,
                h * 0.52f
            ),
            end = androidx.compose.ui.geometry.Offset(
                w * 0.45f,
                h * 0.67f
            ),
            strokeWidth = 16f
        )

        drawLine(
            color = Color(0xFF366446),
            start = androidx.compose.ui.geometry.Offset(
                w * 0.60f,
                h * 0.52f
            ),
            end = androidx.compose.ui.geometry.Offset(
                w * 0.54f,
                h * 0.64f
            ),
            strokeWidth = 16f
        )

        // Small plant 1
        drawLine(
            color = Color(0xFF487857),
            start = androidx.compose.ui.geometry.Offset(
                w * 0.62f,
                h * 0.68f
            ),
            end = androidx.compose.ui.geometry.Offset(
                w * 0.62f,
                h * 0.52f
            ),
            strokeWidth = 3f
        )

        drawCircle(
            color = Color(0xFF699A77),
            radius = 8f,
            center = androidx.compose.ui.geometry.Offset(
                w * 0.59f,
                h * 0.55f
            )
        )

        drawCircle(
            color = Color(0xFF4B7B59),
            radius = 8f,
            center = androidx.compose.ui.geometry.Offset(
                w * 0.65f,
                h * 0.59f
            )
        )

        // Small plant 2
        drawLine(
            color = Color(0xFF3D6A4B),
            start = androidx.compose.ui.geometry.Offset(
                w * 0.72f,
                h * 0.70f
            ),
            end = androidx.compose.ui.geometry.Offset(
                w * 0.72f,
                h * 0.45f
            ),
            strokeWidth = 3f
        )

        drawCircle(
            color = Color(0xFF60916E),
            radius = 10f,
            center = androidx.compose.ui.geometry.Offset(
                w * 0.68f,
                h * 0.52f
            )
        )

        drawCircle(
            color = Color(0xFF3D6A4B),
            radius = 10f,
            center = androidx.compose.ui.geometry.Offset(
                w * 0.76f,
                h * 0.55f
            )
        )
    }
}