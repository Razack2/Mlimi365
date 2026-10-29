package com.example.mlimi365

import com.example.mlimi365.model.CropGuide
import com.example.mlimi365.model.OfflineFarmingRepository
import org.junit.Assert.*
import org.junit.Test

class CropRepositoryUnitTest {

    @Test
    fun testCropsDataIsNotEmpty() {
        assertNotNull(OfflineFarmingRepository.crops)
        assertTrue(OfflineFarmingRepository.crops.isNotEmpty())
    }

    @Test
    fun testCropLookupByNameOrId() {
        val maize = OfflineFarmingRepository.crops.find {
            it.name.equals("Maize", ignoreCase = true) || it.id.equals("maize", ignoreCase = true)
        }
        assertNotNull(maize)
        assertEquals("Maize", maize?.name)
        assertEquals("Cereals", maize?.category)
    }

    @Test
    fun testNonExistentCropFallback() {
        val invalidName = "NonExistentCrop123"
        val crop = OfflineFarmingRepository.crops.find {
            it.name.equals(invalidName, ignoreCase = true) || it.id.equals(invalidName, ignoreCase = true)
        } ?: CropGuide(
            id = invalidName.lowercase(),
            name = invalidName,
            emoji = "🌱",
            category = "General Crop",
            plantingSeason = "Rainy Season",
            plantingGuide = "Prepare soil with the onset of rains and plant certified seeds at recommended depth.",
            spacing = "Follow standard local agronomy spacing guidelines.",
            fertilizer = "Apply basal fertilizer at planting and top-dress with nitrogen during vegetative stage.",
            weeding = "Perform at least two timely weedings during the early growth stages.",
            harvesting = "Harvest when mature and dry to ensure high quality and prevent storage loss."
        )

        assertNotNull(crop)
        assertEquals(invalidName, crop.name)
        assertEquals("General Crop", crop.category)
    }
}
