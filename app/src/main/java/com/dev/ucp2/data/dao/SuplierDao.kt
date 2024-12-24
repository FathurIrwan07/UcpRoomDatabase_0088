package com.dev.ucp2.data.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.dev.ucp2.data.entity.Suplier
import kotlinx.coroutines.flow.Flow

@Dao
interface SuplierDao {
    @Insert
    suspend fun insertSuplier(
        suplier: Suplier
    )

    @Query("SELECT * FROM suplier ORDER BY nama_suplier ASC")
    fun getAllSuplier(): Flow<List<Suplier>>

    @Query("SELECT * FROM suplier WHERE idsuplier = :id")
    fun getSuplier(id: String): Flow<Suplier>

}