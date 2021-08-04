package com.example.testappkotlin.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "fruits_table")
class Fruits(name: String, price: String) {

    @Ignore
    var check: Boolean = false

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "name")
    var name: String = name

    @NonNull
    @ColumnInfo(name = "price")
    var price: String = price
}