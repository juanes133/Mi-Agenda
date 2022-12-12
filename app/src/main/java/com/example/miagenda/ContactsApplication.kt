package com.example.miagenda

import android.app.Application
import com.example.miagenda.db.ContactsDb
import com.example.miagenda.features.contacts.repository.ContactsRepository

class ContactsApplication : Application(){

    private val roomDataBase by lazy { ContactsDb.getDatabase(this) }
    val contactsRepository by lazy { ContactsRepository(roomDataBase.contactsDao()) }
}