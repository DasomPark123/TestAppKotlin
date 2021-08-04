package com.example.testapp

import androidx.sqlite.db.SupportSQLiteDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.testappkotlin.dao.FruitsDao
import com.example.testappkotlin.entity.Fruits
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Database(entities = [Fruits::class], version = 1, exportSchema = true )
abstract class FruitsDatabase : RoomDatabase() {
    abstract fun fruitsDao() : FruitsDao

    companion object {
        val databaseWriteExecutor : ExecutorService = Executors.newSingleThreadExecutor()
        private var INSTANCE : FruitsDatabase? = null

        fun getFruitsDatabase(context : Context) : FruitsDatabase {
            if(INSTANCE == null) {
                synchronized(FruitsDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context, FruitsDatabase::class.java, "fruit_database")
                        .addCallback(roomDataBaseCallback)
                        .build()
                }
            }
            return INSTANCE!!
        }

        private val roomDataBaseCallback: Callback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                databaseWriteExecutor.execute(Runnable {
                    val dao : FruitsDao = INSTANCE!!.fruitsDao()
                    //데이터 베이스가 생성되면 데이터를 모두 클리어 하고 시작
                    dao.deleteAll()
                    //초기 데이터 넣어줌
                    dao.insert(Fruits("melon","9000"))
                    dao.insert(Fruits("strawberry","15000"))
                })
            }
        }
    }
}