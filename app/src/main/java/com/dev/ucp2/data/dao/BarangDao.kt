package com.dev.ucp2.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.dev.ucp2.data.entity.Barang
import kotlinx.coroutines.flow.Flow


@Dao
interface BarangDao {
    @Insert
    suspend fun insertBarang(
        barang: Barang
    )

    @Query("SELECT * FROM barang ORDER BY nama ASC")
    fun getAllBarang(): Flow<List<Barang>>

    @Query("SELECT * FROM barang WHERE idbarang = :id")
    fun getBarang(id: String): Flow<Barang>

    @Delete
    suspend fun deleteBarang(barang: Barang)

    @Update
    suspend fun updateBarang(barang: Barang)
}