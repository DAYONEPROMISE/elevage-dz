package dz.elevage

import org.junit.Assert.*
import org.junit.Test

class LogicTest {
    @Test fun feedTotal() {
        assertEquals(4500.0, FeedMath.totalCost(listOf(10.0 to 200.0, 5.0 to 500.0)), 0.001)
    }
    @Test fun feedTotalEmpty() {
        assertEquals(0.0, FeedMath.totalCost(emptyList()), 0.001)
    }
    @Test fun daysUntil() {
        assertEquals(5, ReminderMath.daysUntil(100, 105))
        assertTrue(ReminderMath.isOverdue(100, 99))
    }
    @Test fun validation() {
        assertTrue(Validation.animalTagValid("OV-001"))
        assertFalse(Validation.animalTagValid("   "))
        assertTrue(Validation.speciesValid("sheep"))
        assertFalse(Validation.speciesValid("fish"))
        assertTrue(Validation.quantityValid(10.0))
        assertFalse(Validation.quantityValid(0.0))
        assertTrue(Validation.priceValid(2100.0))
    }
    @Test fun stringsComplete() {
        assertTrue(Strings.fr.animals.isNotBlank())
        assertTrue(Strings.ar.animals.isNotBlank())
    }
}
