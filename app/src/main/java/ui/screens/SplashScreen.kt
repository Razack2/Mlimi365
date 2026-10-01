package com.example.mlimi365.ui.screens

import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SplashScreen(
    onFinished: () -> Unit
) {
    LaunchedEffect(Unit) {
        delay(2000)
        onFinished()
    }
    // Colors extracted from your HTML
    val bgColor = Color(0xFFFAFBF9)
    val titleColor = Color(0xFF153E26)
    val subtitleColor = Color(0xFF586F62)
    val homeIndicatorColor = Color(0xFF1A3822).copy(alpha = 0.7f)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor)
            .safeDrawingPadding() // Handles status bar and navigation bar
    ) {
        // Main Content (Logo and Text)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 60.dp, start = 24.dp, end = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 1. Logo Canvas
            Canvas(modifier = Modifier.size(112.dp)) {
                val scale = size.width / 120f
                val strokeWidth = 4.5f * scale

                // Soil Mound Base
                drawOval(
                    color = Color(0xFF9C6644),
                    topLeft = Offset(24f * scale, 80f * scale),
                    size = Size(72f * scale, 28f * scale)
                )
                drawOval(
                    color = Color(0xFF7F4F24),
                    topLeft = Offset(27f * scale, 81f * scale),
                    size = Size(66f * scale, 22f * scale)
                )
                // Soil texture
                val soilPath = Path().apply {
                    moveTo(38f * scale, 92f * scale)
                    cubicTo(44f * scale, 98f * scale, 76f * scale, 98f * scale, 82f * scale, 92f * scale)
                    cubicTo(75f * scale, 88f * scale, 45f * scale, 88f * scale, 38f * scale, 92f * scale)
                    close()
                }
                drawPath(soilPath, color = Color(0xFF583101), alpha = 0.6f)

                // Stem
                val stemPath = Path().apply {
                    moveTo(60f * scale, 90f * scale)
                    cubicTo(60f * scale, 76f * scale, 59f * scale, 58f * scale, 59f * scale, 44f * scale)
                }
                drawPath(stemPath, color = Color(0xFF4A7C36), style = Stroke(width = strokeWidth, cap = StrokeCap.Round))

                // Left Leaf
                val leftLeaf = Path().apply {
                    moveTo(59f * scale, 64f * scale)
                    cubicTo(50f * scale, 63f * scale, 32f * scale, 54f * scale, 36f * scale, 34f * scale)
                    cubicTo(44f * scale, 32f * scale, 60f * scale, 48f * scale, 59f * scale, 64f * scale)
                    close()
                }
                drawPath(leftLeaf, color = Color(0xFF3D7D4B))

                // Right Leaf
                val rightLeaf = Path().apply {
                    moveTo(59f * scale, 52f * scale)
                    cubicTo(67f * scale, 49f * scale, 84f * scale, 31f * scale, 78f * scale, 18f * scale)
                    cubicTo(68f * scale, 18f * scale, 56f * scale, 36f * scale, 59f * scale, 52f * scale)
                    close()
                }
                drawPath(rightLeaf, color = Color(0xFF549E51))

                // Small Center Leaf
                val centerLeaf = Path().apply {
                    moveTo(59f * scale, 44f * scale)
                    cubicTo(60f * scale, 38f * scale, 65f * scale, 30f * scale, 63f * scale, 24f * scale)
                    cubicTo(60f * scale, 25f * scale, 58f * scale, 35f * scale, 59f * scale, 44f * scale)
                    close()
                }
                drawPath(centerLeaf, color = Color(0xFF70B765))
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 2. Typography - UPDATED WITH APP NAME
            Text(
                text = "Mlimi365",
                fontSize = 36.sp,
                fontWeight = FontWeight.ExtraBold,
                color = titleColor,
                textAlign = TextAlign.Center,
                letterSpacing = (-0.5).sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Farm Season & Crop Guide",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = titleColor,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Better decisions.\nHealthier harvests.",
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = subtitleColor,
                textAlign = TextAlign.Center,
                lineHeight = 22.sp
            )
        }

        // 3. Bottom Landscape & Footer
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(410.dp)
        ) {
            // Sun Glow
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 16.dp)
                    .size(80.dp)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(Color(0xFFFBE8A4).copy(alpha = 0.9f), Color(0xFFFBE8A4).copy(alpha = 0f))
                        ),
                        shape = CircleShape
                    )
            )

            // Landscape Canvas
            Canvas(modifier = Modifier.fillMaxSize()) {
                val scaleX = size.width / 390f
                val scaleY = size.height / 410f

                // Layer 1: Distant Soft Hills
                val layer1 = Path().apply {
                    moveTo(-20f * scaleX, 220f * scaleY)
                    quadraticBezierTo(90f * scaleX, 180f * scaleY, 200f * scaleX, 205f * scaleY)
                    quadraticBezierTo(310f * scaleX, 230f * scaleY, 410f * scaleX, 190f * scaleY)
                    lineTo(410f * scaleX, 410f * scaleY)
                    lineTo(-20f * scaleX, 410f * scaleY)
                    close()
                }
                drawPath(layer1, color = Color(0xFFC5DDBB).copy(alpha = 0.6f))

                // Layer 2: Middle Ground Hills
                val layer2 = Path().apply {
                    moveTo(-10f * scaleX, 240f * scaleY)
                    quadraticBezierTo(110f * scaleX, 215f * scaleY, 230f * scaleX, 245f * scaleY)
                    quadraticBezierTo(350f * scaleX, 275f * scaleY, 410f * scaleX, 210f * scaleY)
                    lineTo(410f * scaleX, 410f * scaleY)
                    lineTo(-10f * scaleX, 410f * scaleY)
                    close()
                }
                drawPath(layer2, color = Color(0xFF9ECB96))

                // Distant Trees
                drawRect(Color(0xFF426938), topLeft = Offset(58f * scaleX, 210f * scaleY), size = Size(3f * scaleX, 15f * scaleY))
                drawOval(Color(0xFF6B9958), topLeft = Offset(49.5f * scaleX, 189f * scaleY), size = Size(20f * scaleX, 30f * scaleY))

                drawRect(Color(0xFF426938), topLeft = Offset(94f * scaleX, 222f * scaleY), size = Size(3f * scaleX, 12f * scaleY))
                drawOval(Color(0xFF7FA96B), topLeft = Offset(87.5f * scaleX, 208f * scaleY), size = Size(16f * scaleX, 20f * scaleY))

                // Farmhouse Group
                val houseX = 290f * scaleX
                val houseY = 196f * scaleY
                // Trees behind house
                drawOval(Color(0xFF5F8B4F), topLeft = Offset(houseX - 5f * scaleX, houseY - 4f * scaleY), size = Size(14f * scaleX, 22f * scaleY))
                drawOval(Color(0xFF5F8B4F), topLeft = Offset(houseX + 37f * scaleX, houseY - 4f * scaleY), size = Size(14f * scaleX, 22f * scaleY))
                drawOval(Color(0xFF719C61), topLeft = Offset(houseX + 31f * scaleX, houseY + 1f * scaleY), size = Size(12f * scaleX, 18f * scaleY))
                // House Wall
                drawRect(Color(0xFFFCEBD2), topLeft = Offset(houseX + 10f * scaleX, houseY + 11f * scaleY), size = Size(26f * scaleX, 17f * scaleY))
                // Roof
                val roofPath = Path().apply {
                    moveTo(houseX + 7f * scaleX, houseY + 11f * scaleY)
                    lineTo(houseX + 23f * scaleX, houseY + 2f * scaleY)
                    lineTo(houseX + 39f * scaleX, houseY + 11f * scaleY)
                    close()
                }
                drawPath(roofPath, color = Color(0xFFBC5A36))
                // Door
                drawRect(Color(0xFF5B3821), topLeft = Offset(houseX + 20f * scaleX, houseY + 18f * scaleY), size = Size(6f * scaleX, 10f * scaleY))
                // Window
                drawRect(Color(0xFFC79659), topLeft = Offset(houseX + 12f * scaleX, houseY + 15f * scaleY), size = Size(4f * scaleX, 4f * scaleY))

                // Layer 3: Main Rolling Field
                val layer3 = Path().apply {
                    moveTo(-10f * scaleX, 260f * scaleY)
                    quadraticBezierTo(140f * scaleX, 230f * scaleY, 400f * scaleX, 280f * scaleY)
                    lineTo(400f * scaleX, 410f * scaleY)
                    lineTo(-10f * scaleX, 410f * scaleY)
                    close()
                }
                drawPath(layer3, color = Color(0xFF84BA7B))

                // Layer 4: Foreground Hill
                val layer4 = Path().apply {
                    moveTo(-20f * scaleX, 310f * scaleY)
                    quadraticBezierTo(150f * scaleX, 260f * scaleY, 410f * scaleX, 320f * scaleY)
                    lineTo(410f * scaleX, 420f * scaleY)
                    lineTo(-20f * scaleX, 420f * scaleY)
                    close()
                }
                drawPath(layer4, color = Color(0xFF73AB68))

                // Contour Lines (Agricultural Furrows)
                val furrow1 = Path().apply {
                    moveTo(210f * scaleX, 270f * scaleY)
                    quadraticBezierTo(270f * scaleX, 320f * scaleY, 390f * scaleX, 350f * scaleY)
                }
                drawPath(furrow1, color = Color(0xFF63985A), alpha = 0.6f, style = Stroke(width = 4f * scaleX, cap = StrokeCap.Round))

                val furrow2 = Path().apply {
                    moveTo(160f * scaleX, 270f * scaleY)
                    quadraticBezierTo(230f * scaleX, 330f * scaleY, 340f * scaleX, 380f * scaleY)
                }
                drawPath(furrow2, color = Color(0xFF63985A), alpha = 0.6f, style = Stroke(width = 5f * scaleX, cap = StrokeCap.Round))

                val furrow3 = Path().apply {
                    moveTo(110f * scaleX, 275f * scaleY)
                    quadraticBezierTo(190f * scaleX, 350f * scaleY, 270f * scaleX, 410f * scaleY)
                }
                drawPath(furrow3, color = Color(0xFF63985A), alpha = 0.5f, style = Stroke(width = 5f * scaleX, cap = StrokeCap.Round))

                // Layer 5: Front Left Gentle Mound
                val layer5 = Path().apply {
                    moveTo(-30f * scaleX, 330f * scaleY)
                    quadraticBezierTo(80f * scaleX, 300f * scaleY, 260f * scaleX, 380f * scaleY)
                    lineTo(260f * scaleX, 420f * scaleY)
                    lineTo(-30f * scaleX, 420f * scaleY)
                    close()
                }
                drawPath(layer5, color = Color(0xFF669E5B), alpha = 0.55f)

                // Layer 6: Bottom Foreground Lush Slope
                val layer6 = Path().apply {
                    moveTo(-20f * scaleX, 355f * scaleY)
                    quadraticBezierTo(160f * scaleX, 330f * scaleY, 410f * scaleX, 370f * scaleY)
                    lineTo(410f * scaleX, 420f * scaleY)
                    lineTo(-20f * scaleX, 420f * scaleY)
                    close()
                }
                drawPath(layer6, color = Color(0xFF6E9F62))
            }

            // Home Indicator (Gesture Bar)
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 12.dp)
                    .width(128.dp)
                    .height(4.dp)
                    .background(homeIndicatorColor, RoundedCornerShape(2.dp))
            )
        }
    }
}

@Preview(showBackground = true, device = "id:pixel_5")
@Composable
fun SplashScreenPreview() {
    SplashScreen(  onFinished = {})
}