package com.dev.ucp2.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dev.ucp2.data.dao.BarangDao
import com.dev.ucp2.data.dao.SuplierDao
import com.dev.ucp2.data.entity.Barang
import com.dev.ucp2.data.entity.Suplier

@Database(entities = [Barang::class, Suplier::class], version = 1, exportSchema = false)
abstract class BrgDatabase : RoomDatabase() {


    abstract fun barangDao(): BarangDao

    abstract fun suplierDao(): SuplierDao

    companion object{
        @Volatile
        private var Instance: BrgDatabase? =null

        fun getDatabase(context: Context): BrgDatabase{
            return (Instance ?: synchronized(this){
                Room.databaseBuilder(
                    context,
                    BrgDatabase::class.java,
                    "BrgDatabase"
                )
                    .build().also { Instance = it }
            })


        }
    }
}
