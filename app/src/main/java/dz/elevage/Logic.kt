package dz.elevage

// Pure, unit-testable logic. MIT clean-room implementation.

object FeedMath {
    /** Sum of feed costs in DZD. Quantities in kg, unit prices in DZD/kg. */
    fun totalCost(items: List<Pair<Double, Double>>): Double =
        items.sumOf { (qtyKg, pricePerKg) -> qtyKg * pricePerKg }

    fun formatDzd(amount: Double): String = "${"%,.0f".format(amount)} DZD"
}

object ReminderMath {
    /** Days from [todayEpochDay] until [dueEpochDay]. Negative = overdue. */
    fun daysUntil(todayEpochDay: Long, dueEpochDay: Long): Long = dueEpochDay - todayEpochDay
    fun isOverdue(todayEpochDay: Long, dueEpochDay: Long): Boolean = daysUntil(todayEpochDay, dueEpochDay) < 0
}

object Validation {
    val SPECIES = setOf("sheep", "cattle", "goat", "poultry", "camel")
    fun animalTagValid(tag: String): Boolean = tag.trim().length in 1..64
    fun speciesValid(s: String): Boolean = s in SPECIES
    fun quantityValid(qty: Double): Boolean = qty > 0 && qty < 1_000_000
    fun priceValid(price: Double): Boolean = price >= 0 && price < 100_000_000
}
