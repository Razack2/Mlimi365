package com.example.mlimi365

import com.example.mlimi365.model.OfflineFarmingRepository
import org.junit.Assert.*
import org.junit.Test

class SearchFilteringUnitTest {

    @Test
    fun testSearchFilteringLogic() {
        val query = "bean"
        val filtered = OfflineFarmingRepository.crops.filter {
            it.name.contains(query, ignoreCase = true) ||
                    it.category.contains(query, ignoreCase = true) ||
                    it.plantingSeason.contains(query, ignoreCase = true)
        }
        assertTrue(filtered.any { it.name.equals("Beans", ignoreCase = true) })
    }
}
