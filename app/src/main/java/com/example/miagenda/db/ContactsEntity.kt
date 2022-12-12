package com.example.miagenda.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts_table")
data class ContactsEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val lastName: String,
    val phone: String,
    val mail: String,
)
