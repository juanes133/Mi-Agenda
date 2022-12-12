package com.example.miagenda.features.contacts.viewmodel

import androidx.lifecycle.*
import com.example.miagenda.Event
import com.example.miagenda.features.contacts.repository.ContactsRepository
import com.example.miagenda.model.ContactsModel
import kotlinx.coroutines.launch

class ContactsViewModel(private val contactsRepository: ContactsRepository) : ViewModel() {

    private val mutableContactsList = MutableLiveData<Event<ArrayList<ContactsModel>>>()
    val contactsList: LiveData<Event<ArrayList<ContactsModel>>> get() = mutableContactsList

    private val mutableContactsDeleted = MutableLiveData<Event<ContactsModel>>()
    val contactsDeleted: LiveData<Event<ContactsModel>> get() = mutableContactsDeleted

    private val mutableContactsError = MutableLiveData<Event<Exception>>()
    val contactsError: LiveData<Event<Exception>> get() = mutableContactsError

    fun getContacts() {
        viewModelScope.launch {
            contactsRepository.getContacts({ list ->
                val result = ArrayList<ContactsModel>()
                list.forEach {
                    result.add(
                        ContactsModel(
                            it.id,
                            it.name,
                            it.lastName,
                            it.phone,
                            it.mail
                        )
                    )
                }
                mutableContactsList.value = Event(result)
            }, {
                mutableContactsError.value = Event(it)
            })
        }
    }

    fun deleteContacts(contactsModel: ContactsModel) {
        viewModelScope.launch {
            contactsRepository.deleteContacts(contactsModel, {
                mutableContactsDeleted.value = Event(it)
            }) {
                mutableContactsError.value = Event(it)
            }
        }
    }
}

class ContactsViewModelFactory(
    private val contactsRepository: ContactsRepository,
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ContactsViewModel(contactsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

