package com.example.mlimi365.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mlimi365.model.CropGuide
import com.example.mlimi365.model.OfflineFarmingRepository

private val HomeGreen = Color(0xFF205338)
private val DarkText = Color(0xFF111827)
private val MutedText = Color(0xFF64748B)

@Composable
fun CropDetailScreen(
    cropName: String,
    onBack: () -> Unit = {}
) {
    val crop = remember(cropName) {
        OfflineFarmingRepository.crops.find {
            it.name.equals(cropName, ignoreCase = true) || it.id.equals(cropName, ignoreCase = true)
        } ?: CropGuide(
            id = cropName.lowercase(),
            name = cropName,
            emoji = "🌱",
            category = "General Crop",
            plantingSeason = "Rainy Season",
            plantingGuide = "Prepare soil with the onset of rains and plant certified seeds at recommended depth.",
            spacing = "Follow standard local agronomy spacing guidelines.",
            fertilizer = "Apply basal fertilizer at planting and top-dress with nitrogen during vegetative stage.",
            weeding = "Perform at least two timely weedings during the early growth stages.",
            harvesting = "Harvest when mature and dry to ensure high quality and prevent storage loss."
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFCFA))
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = DarkText
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "${crop.name} Growing Guide",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText
            )
        }

        // Scrollable Guide Content
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 8.dp)
        ) {
            // Header card with emoji & category
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFEDF6F0))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .background(Color.White, RoundedCornerShape(14.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = crop.emoji,
                            fontSize = 36.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Text(
                            text = crop.category,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = HomeGreen
                        )

                        Text(
                            text = crop.name,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = DarkText
                        )

                        Text(
                            text = "Season: ${crop.plantingSeason}",
                            fontSize = 12.sp,
                            color = MutedText
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            GuideSection(
                title = "1. Planting Guide",
                content = crop.plantingGuide
            )

            Spacer(modifier = Modifier.height(14.dp))

            GuideSection(
                title = "2. Spacing & Plant Population",
                content = crop.spacing
            )

            Spacer(modifier = Modifier.height(14.dp))

            GuideSection(
                title = "3. Fertilizer Application",
                content = crop.fertilizer
            )

            Spacer(modifier = Modifier.height(14.dp))

            GuideSection(
                title = "4. Weeding Schedule",
                content = crop.weeding
            )

            Spacer(modifier = Modifier.height(14.dp))

            GuideSection(
                title = "5. Harvesting & Storage",
                content = crop.harvesting
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun GuideSection(
    title: String,
    content: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = content,
                fontSize = 14.sp,
                color = MutedText,
                lineHeight = 20.sp
            )
        }
    }
}
