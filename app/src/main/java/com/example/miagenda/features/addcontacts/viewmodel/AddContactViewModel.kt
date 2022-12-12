package com.example.miagenda.features.addcontacts.viewmodel

import androidx.lifecycle.*
import com.example.miagenda.Event
import com.example.miagenda.features.contacts.repository.ContactsRepository
import com.example.miagenda.model.ContactsModel
import kotlinx.coroutines.launch

class AddContactViewModel(private val contactsRepository: ContactsRepository) : ViewModel() {

    private val mutableContactsList = MutableLiveData<Event<ArrayList<ContactsModel>>>()
    val contactsList: LiveData<Event<ArrayList<ContactsModel>>> get() = mutableContactsList

    private val mutableContactsInsert = MutableLiveData<Event<Boolean>>()
    val contactsInsert: LiveData<Event<Boolean>> get() = mutableContactsInsert

    private val mutableContactsError = MutableLiveData<Event<Exception>>()
    val contactsError: LiveData<Event<Exception>> get() = mutableContactsError

    fun insertContacts(contactsModel: ContactsModel) {
        viewModelScope.launch {
            contactsRepository.insertContacts(contactsModel, {
                mutableContactsInsert.value = Event(true)
            }, {
                mutableContactsError.value = Event(it)
            })
        }
    }

    fun findById(id: String) {
        viewModelScope.launch {
            contactsRepository.findById(id, { list ->
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
            }) {
                mutableContactsError.value = Event(it)
            }
        }
    }
}

class AddContactsViewModelFactory(
    private val contactsRepository: ContactsRepository,
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddContactViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddContactViewModel(contactsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}