package com.example.testappkotlin.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.testapp.FruitsDatabase
import com.example.testappkotlin.dao.FruitsDao
import com.example.testappkotlin.entity.Fruits

class FruitsRepository(application: Application) {
    private val db : FruitsDatabase = FruitsDatabase.getFruitsDatabase(application)
    private val fruitDao : FruitsDao = db.fruitsDao()
    private val allFruits : LiveData<List<Fruits>> = fruitDao.allFruitsByAsc()

    fun insert(fruits: Fruits) {
        FruitsDatabase.databaseWriteExecutor.execute(Runnable {
            fruitDao.insert(fruits)
        })
    }

    fun update(fruits: Fruits) {
        FruitsDatabase.databaseWriteExecutor.execute(Runnable {
            fruitDao.update(fruits)
        })

    }

    fun delete(fruits: Fruits) {
        FruitsDatabase.databaseWriteExecutor.execute(Runnable {
            fruitDao.delete(fruits)
        })
    }
}