package com.example.sampleapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.sampleapp.domain.entities.Product


@Database(entities = [Product::class], version = 1)
abstract class ProductDB : RoomDatabase() {
    abstract fun getProductDao(): ProductDAO
}
