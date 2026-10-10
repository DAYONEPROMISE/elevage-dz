package dz.elevage

import androidx.room.*

@Entity(tableName = "animals")
data class Animal(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val tag: String,
    val species: String,
    val batch: String = "",
    val notes: String = ""
)

@Entity(tableName = "health_events")
data class HealthEvent(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val animalTag: String,
    val product: String,
    val eventEpochDay: Long,
    val remindEpochDay: Long? = null
)

@Entity(tableName = "feed_costs")
data class FeedCost(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val label: String,
    val qtyKg: Double,
    val priceDzdPerKg: Double,
    val dayEpoch: Long
)

@Dao
interface AnimalDao {
    @Query("SELECT * FROM animals ORDER BY id DESC") suspend fun all(): List<Animal>
    @Insert suspend fun insert(a: Animal): Long
    @Query("DELETE FROM animals WHERE id=:id") suspend fun delete(id: Long)
}

@Dao
interface HealthDao {
    @Query("SELECT * FROM health_events ORDER BY eventEpochDay DESC") suspend fun all(): List<HealthEvent>
    @Insert suspend fun insert(e: HealthEvent): Long
    @Query("DELETE FROM health_events WHERE id=:id") suspend fun delete(id: Long)
}

@Dao
interface FeedDao {
    @Query("SELECT * FROM feed_costs ORDER BY dayEpoch DESC") suspend fun all(): List<FeedCost>
    @Insert suspend fun insert(f: FeedCost): Long
    @Query("DELETE FROM feed_costs WHERE id=:id") suspend fun delete(id: Long)
}

@Database(entities = [Animal::class, HealthEvent::class, FeedCost::class], version = 1)
abstract class AppDb : RoomDatabase() {
    abstract fun animals(): AnimalDao
    abstract fun health(): HealthDao
    abstract fun feed(): FeedDao
}
