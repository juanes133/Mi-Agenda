package com.example.miagenda.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ContactsDao {

    @Query("SELECT * FROM contacts_table")
    suspend fun getAll(): List<ContactsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(contacts: ContactsEntity)

    @Query("DELETE FROM contacts_table WHERE id = :id")
    suspend fun deleteAll(id: String)

    @Query("SELECT * FROM contacts_table WHERE id = :id")
    suspend fun getById(id: String): List<ContactsEntity>

}