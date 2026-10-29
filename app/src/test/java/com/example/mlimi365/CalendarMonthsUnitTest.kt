package com.example.mlimi365

import com.example.mlimi365.model.OfflineFarmingRepository
import org.junit.Assert.*
import org.junit.Test

class  CalendarMonthsUnitTest {

    @Test
    fun testCalendarMonthsContains12Months() {
        val months = OfflineFarmingRepository.calendarMonths
        assertEquals(12, months.size)
        assertEquals("January", months[0].monthName)
        assertEquals("December", months[11].monthName)
    }
}
