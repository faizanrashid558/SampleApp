package com.example.sampleapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sampleapp.domain.entities.Product

@Dao
interface ProductDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun  addProducts(products:List<Product>)

    @Query("Select * FROM Product")
    suspend fun getProducts():List<Product>
}