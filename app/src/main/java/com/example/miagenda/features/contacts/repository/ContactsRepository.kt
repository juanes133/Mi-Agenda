package com.example.miagenda.features.contacts.repository

import com.example.miagenda.db.ContactsDao
import com.example.miagenda.db.ContactsEntity
import com.example.miagenda.model.ContactsModel

class ContactsRepository(private val contactsDao: ContactsDao) {

    suspend fun getContacts(
        onSuccess: (ArrayList<ContactsEntity>) -> Unit,
        onFailure: (Exception) -> Unit,
    ) {
        try {
            onSuccess(
                contactsDao.getAll() as ArrayList<ContactsEntity>
            )
        } catch (e: Exception) {
            onFailure(e)
        }
    }

    suspend fun insertContacts(
        contactsModel: ContactsModel,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit,
    ) {
        try {
            val contacts = ContactsEntity(
                contactsModel.id,
                contactsModel.name,
                contactsModel.lastName,
                contactsModel.phone,
                contactsModel.mail
            )
            contactsDao.insertAll(contacts)
            onSuccess()
        } catch (e: Exception) {
            onFailure(e)
        }
    }

    suspend fun deleteContacts(
        contactsModel: ContactsModel,
        onSuccess: (ContactsModel) -> Unit,
        onFailure: (Exception) -> Unit,
    ) {
        try {
            contactsDao.deleteAll(contactsModel.id)
            onSuccess(contactsModel)
        } catch (e: Exception) {
            onFailure(e)
        }
    }

    suspend fun findById(
        idContact: String,
        onSuccess: (List<ContactsEntity>) -> Unit,
        onFailure: (Exception) -> Unit,
    ) {
        try {
            onSuccess(contactsDao.getById(idContact) as ArrayList<ContactsEntity>)
        } catch (e: Exception) {
            if (e is NoSuchElementException) {
                onSuccess(ArrayList())
            } else {
                onFailure(e)
            }
        }
    }
}