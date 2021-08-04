package com.example.testappkotlin.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.testappkotlin.entity.Fruits

interface FruitsDao {

    @Query("SELECT * FROM fruits_table ORDER BY name ASC")
    fun allFruitsByAsc() : LiveData<List<Fruits>>

    @Query("DELETE FROM fruits_table")
    fun deleteAll()

    @Insert(entity = Fruits::class, onConflict = OnConflictStrategy.REPLACE)
    fun insert(fruits: Fruits)

    @Update(entity = Fruits::class)
    fun update(fruits: Fruits)

    @Delete
    fun delete(fruits: Fruits)
}