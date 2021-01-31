package my.farhan.movie.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDao {
    @Query("SELECT * FROM Movie")
    suspend fun findAll(): List<Movie>

    @Query("SELECT * FROM Movie")
    fun findAllLD(): LiveData<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(users: List<Movie>)
}